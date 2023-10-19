package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.teamplus.mvc.service.UsersService;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("users")
public class UsersController {

    private final UsersService service;

//━━━━━ [로그인 관련] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    // 로그인
    @GetMapping("/signin")
    public String signinView() {
        return "MyPage/signin";
    }

    // 회원가입
    @GetMapping("/signup")
    public String signupView() { return "MyPage/signup"; }

    // 비밀번호 재설정 1 (메일 인증)
    @GetMapping("/sendmail")
    public String sendmailView() {
        return "MyPage/pass-reset";
    }

    // 비밀번호 재설정 2 (인증번호 기입)
    @GetMapping("/auth")
    public String authView() {
        return "MyPage/twostep";
    }

    // 비밀번호 재설정 3 (비밀번호 재설정)
    @GetMapping("/resetpw")
    public String resetpwView() {
        return "MyPage/pass-change";
    }

    // 잠금 모드 (비밀번호 입력시 해제)
    @GetMapping("/lockscreen")
    public String lockscreen() {
        return "MyPage/lockscreen";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(SessionStatus session) {
        session.setComplete();

        return "MyPage/logout";
    }

//━━━━━ [프로필 관련] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    // Starter 화면 (아직 내용 없음 10.18 재민)
    @GetMapping("/starter")
    public String starter() {
        return "MyPage/starter";
    }

    // 프로필 보기 컨트롤러
    @GetMapping("/profile")
    public String profileView() {
        return "MyPage/profile";
    }

    // 프로필 수정 컨트롤러
    @GetMapping("/modify")
    public String modifyView() {
        return "MyPage/profile-settings";
    }

}
