package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.teamplus.mvc.dto.MailCodeDTO;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.UsersService;
import org.teamplus.mvc.util.MailCheck;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("users")
@SessionAttributes("user")
public class UsersController {

    private final UsersService service;

//━━━━━ [로그인 관련] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    // 로그인
    @GetMapping("/signin")
    public String signinView() {
        return "MyPage/signin";
    }

    @PostMapping("/signin")
    public String signin(UsersDTO dto, Model model) {
        String redirect = "";

        log.info("━━━━━━━━━━ 입력받은 id/pw : {}",dto.toString());

        UsersDTO user = service.signin(dto);

        log.info("━━━━━━━━━━ 로그인 결과 : {}",user.toString());

        if (user != null) {
            log.info("━━━━━━━━━━ 로그인 성공 ⭕");
            model.addAttribute("user",user);
            redirect = "redirect:/project/list";
        } else {
            log.info("━━━━━━━━━━ 로그인 실패 ❌");
            model.addAttribute("error", "로그인 실패");
            redirect = "redirect:/users/signin";
        }

        return redirect; // 로그인 페이지로 리디렉션
    }

    // 회원가입
    @GetMapping("/signup")
    public String signupView() { return "MyPage/signup"; }

    // 회원가입 (정보 insert)
    @PostMapping("/signup")
    public String signup(UsersDTO dto){
        service.signUp(dto);
        return "redirect:/users/signin";
    }

    // 비밀번호 재설정 1 (메일 인증)
    @GetMapping("/sendmail")
    public String sendmailView() {
        return "MyPage/pass-reset";
    }

    // 비밀번호 재설정 2 (인증번호 기입)
    @PostMapping("/auth")
    public String authView(String email, Model model) {

        log.info("━━━━━━━━━━ 입력한 email : {}",email);

        MailCheck mail = new MailCheck();

        String code = mail.random();
        log.info("━━━━━━━━━━ 생성된 코드 : {}",code);

        mail.setCode(code);
        mail.sendMail(email);

        model.addAttribute("code",code);

        return "MyPage/twostep";
    }



    // 비밀번호 재설정 3 (비밀번호 재설정)
    @PostMapping ("/resetpw")
    public String resetpwView(MailCodeDTO dto, String code) {
        String redirect = "";

        StringBuilder insertDTOCode = new StringBuilder();
        insertDTOCode.append(dto.getFirst()).append(dto.getSecond()).append(dto.getThird()).append(dto.getFourth());

        String insertCode = insertDTOCode.toString();
        log.info("━━━━━━━━━━ 생성된 코드 : {}",code);
        log.info("━━━━━━━━━━ 입력한 코드 : {}",insertCode);

        if(insertCode.equals(code)){    //인증번호 일치
            redirect = "MyPage/pass-change";
            log.info("━━━━━━━━━━ 인증번호 일치 ⭕");
        } else {    //인증번호 불일치
            log.info("━━━━━━━━━━ 인증번호 불일치 ❌");
        }

        return redirect;
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
