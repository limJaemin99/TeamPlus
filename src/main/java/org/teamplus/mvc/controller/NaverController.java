package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.NaverService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("naver")
@Slf4j
@SessionAttributes("user")
public class NaverController {
    private final NaverService service;

    @GetMapping("/callback")
    public RedirectView navercallback(@RequestParam("code")String code, HttpServletRequest req, Model model) throws Exception {
        UsersDTO naverInfo = service.getNaverInfo(code);
        log.info("----------------- {}",naverInfo.getEmail());
        if (naverInfo.getNickName() != null) {
            model.addAttribute("user", naverInfo);
            log.info("━━━━━━━━━━ 로그인 성공 ⭕");
            return new RedirectView("/");
        }
        req.getSession().setAttribute("email", naverInfo.getEmail());
        return new RedirectView("/users/snsUpdate");
    }

}
