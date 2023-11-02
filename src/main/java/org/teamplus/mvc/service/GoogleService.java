package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.teamplus.mvc.dto.UsersDTO;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleService {

    private final UsersService service;

    @Value("${google.client.id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${google.client.secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${google.redirect.url}")
    private String GOOGLE_REDIRECT_URL;

    @Value("${google.token.uri}")
    private String GOOGLE_AUTH_URI;

    @Value("${google.resource.uri}")
    private String GOOGLE_API_URI;

    public UsersDTO getGoogleInfo(String code) throws Exception {
        if (code == null) throw new Exception("Failed to get authorization code");

        String accessToken = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", GOOGLE_CLIENT_ID);
            params.add("client_secret", GOOGLE_CLIENT_SECRET);
            params.add("code", code);
            params.add("redirect_uri", GOOGLE_REDIRECT_URL);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    GOOGLE_AUTH_URI,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            accessToken = (String) jsonObj.get("access_token");
        } catch (Exception e) {
            throw new Exception("API call failed");
        }

        return getUserInfoWithToken(accessToken);
    }

    private UsersDTO getUserInfoWithToken(String accessToken) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate rt = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                GOOGLE_API_URI,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

        String email = String.valueOf(jsonObj.get("email"));

        UsersDTO user = service.snslogin(email);

        if (user == null) {
            UsersDTO newUser = new UsersDTO();
            newUser.setEmail(email);
            newUser.setSns("google");
            service.snsinsert(newUser);
            user = service.snslogin(email);
        }
        return user;
    }
}
