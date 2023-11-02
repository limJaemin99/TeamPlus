package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.teamplus.mvc.dto.UsersDTO;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoService {
    private final UsersService service;


    @Value("${kakao.client.id}") // value의 옵션을 설정해서 application properties의 key의 값을 가져온다.
    private String kakaoClientId;
    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;
    @Value("${kakao.redirect.url}")
    private String kakaoRedirectUrl;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com"; // 이 요청으로 보내야 카카오측에서 인증을 해줌
    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/oauth/authorize" //rest api 가져올 때 경로설정
                + "?client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUrl
                + "&response_type=code";

    }
    public UsersDTO getKakaoInfo(String code) throws Exception{

        if (code == null) throw new Exception("Failed get authorization code");

        String accessToken = "";
        String refreshToken = "";

        try {
            HttpHeaders headers = new HttpHeaders();

            headers.add("Content-type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type"   , "authorization_code");
            params.add("client_id"    , kakaoClientId);
            params.add("client_secret", kakaoClientSecret);
            params.add("code"         , code);
            params.add("redirect_uri" , kakaoRedirectUrl);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_AUTH_URI + "/oauth/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            accessToken  = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getUserInfoWithToken(accessToken);
    }

    private UsersDTO getUserInfoWithToken(String accessToken) throws Exception {
        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                KAKAO_API_URI + "/v2/user/me",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        //Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());

        JSONObject account = (JSONObject) jsonObj.get("kakao_account");
        JSONObject profile = (JSONObject) account.get("profile");

//        String id = (String) jsonObj.get("id");

        String email = String.valueOf(account.get("email"));
        String Name = String.valueOf(profile.get("nickname"));

        log.info("================");
        log.info(email);
        log.info(Name);
        log.info("================");

        UsersDTO user;

        user = service.snslogin(email);

        if (user == null) {
            UsersDTO newUser = new UsersDTO();
            newUser.setEmail(email);
            newUser.setSns("kakao");

            service.snsinsert(newUser);

            user = service.snslogin(email);
        }
        return user;

    }

}
