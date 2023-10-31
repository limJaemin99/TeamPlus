package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.KakaoService;
import org.teamplus.mvc.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
@SessionAttributes("user")
public class KakaoController {
    private final KakaoService service;

    @GetMapping("/callback")
    public RedirectView callback(@RequestParam("code")String code, HttpServletRequest req, Model model) throws Exception {
        UsersDTO kakaoInfo = service.getKakaoInfo(code);
        log.info("----------------- {}",kakaoInfo.getEmail());
        if (kakaoInfo.getNickName() != null) {
            model.addAttribute("user", kakaoInfo);
            log.info("━━━━━━━━━━ 로그인 성공 ⭕");
            return new RedirectView("/");
        }
        req.getSession().setAttribute("email", kakaoInfo.getEmail());
        return new RedirectView("/users/snsUpdate");
    }

}
