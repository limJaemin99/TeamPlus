package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.GoogleService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "google")
@Slf4j
@SessionAttributes("user")
public class GoogleController {

    private final GoogleService service;

    @GetMapping("/callback")
    public RedirectView googlecallback(@RequestParam("code")String code, HttpServletRequest req, Model model) throws Exception {
        UsersDTO googleInfo = service.getGoogleInfo(code);
        log.info("----------------- {}", googleInfo.getEmail());
        if (googleInfo.getJob() != null) {
            model.addAttribute("user", googleInfo);
            log.info("━━━━━━━━━━ 로그인 성공 ⭕");
            return new RedirectView("/");
        }
        req.getSession().setAttribute("email", googleInfo.getEmail());
        return new RedirectView("/users/snsUpdate");
    }
}
