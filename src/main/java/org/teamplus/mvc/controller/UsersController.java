package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.teamplus.mvc.util.MailCodeDTO;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.UsersService;
import org.teamplus.mvc.util.MailCheck;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
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
    public String signin(UsersDTO dto, Model model, RedirectAttributes redirectAttributes) {
        String redirect = "";

        log.info("━━━━━━━━━━ 입력받은 id/pw : {}",dto.toString());

        try {
            UsersDTO user = service.signin(dto);

            log.info("━━━━━━━━━━ 로그인 결과 : {}",user.toString());

            log.info("━━━━━━━━━━ 로그인 성공 ⭕");
            model.addAttribute("user",user);
            redirect = "redirect:/";
            redirectAttributes.addFlashAttribute("login",1);

        } catch (NullPointerException e) {  //로그인 실패시 NullPointerException 발생
            log.info("━━━━━━━━━━ 로그인 실패 ❌");
            model.addAttribute("error", "로그인 실패");
            redirect = "redirect:/users/signin";
            redirectAttributes.addFlashAttribute("login",0);
        }

        return redirect; // 로그인 페이지로 리디렉션
    }

    // 회원가입
    @GetMapping("/signup")
    public String signupView() { return "MyPage/signup"; }

    // 회원가입 (정보 insert)
    @PostMapping("/signup")
    public String signup(UsersDTO dto, RedirectAttributes redirectAttributes){
        service.signUp(dto);
        redirectAttributes.addFlashAttribute("signup",1);
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

        String redirect = "redirect:";

        log.info("━━━━━━━━━━ 입력한 email : {}",email);

        //있는 메일인지 검증
        int isExist = service.isEmailExist(email);

        log.info("━━━━━━━━━━ isExist : {}",isExist);

        if (isExist == 1) { //존재하는 메일
            MailCheck mail = new MailCheck();

            String code = mail.random();
            log.info("━━━━━━━━━━ 생성된 코드 : {}", code);

            mail.setCode(code);
            mail.sendMail(email);

            model.addAttribute("code", code);
            model.addAttribute("email",email);

            redirect = "MyPage/twostep";
        } else {    //없는 메일
            log.info("⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠ 없는 메일 입니다 : {}",email);
            redirect += "/users/sendmail";
        }

        return redirect;
    }



    // 비밀번호 재설정 3 (새로운 비밀번호 입력)
    @PostMapping ("/resetpw")
    public String resetpwView(MailCodeDTO dto,String code,@ModelAttribute("email") String email) {
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

    // 비밀번호 재설정 4 (비밀번호 재설정)
    @PostMapping("/confirmpw")
    public String comfirmpw(String email,String password,String confirm){
        UsersDTO user = service.selectByEmail(email);
        String redirect = "redirect:/users/sendmail";
        log.info("━━━━━━━━━━ email : {}",email);
        log.info("━━━━━━━━━━ 변경할 user 정보 : {}",user.toString());
        log.info("━━━━━━━━━━ 입력한 비밀번호 1 : {}",password);
        log.info("━━━━━━━━━━ 입력한 비밀번호 2 : {}",confirm);

        if(password.equals(confirm)){   //비밀번호 재확인 O
            user.setPassword(password);
            int result = service.changePassword(user);
            log.info("━━━━━━━━━━ 비밀번호 변경 결과 : {}",result);
            if(result == 1){    //비밀번호 변경 완료
                log.info("━━━━━━━━━━ 변경 성공 ⭕");
                redirect = "redirect:/users/signin";
            } else {    //비밀번호 변경 실패
                log.info("━━━━━━━━━━ 변경 실패 ❌");
            }
        } else {   //비밀번호 재확인 X
            log.info("━━━━━━━━━━ 비밀번호가 일치하지 않아 변경 실패 ❌");
        }


        return redirect;
    }


    // 잠금 모드 (비밀번호 입력시 해제)
    @GetMapping("/lockscreen")
    public String lockscreen(@SessionAttribute("user") UsersDTO user,HttpServletRequest request,Model model) {
        String password = user.getPassword();

        String referer = request.getHeader("referer");

        if(!referer.equals("http://localhost:8086/users/lockscreen")) {
            model.addAttribute("referer", referer);

            log.info("▶▶▶▶▶▶▶▶▶▶ model로 보낸 referer : {}", referer);
        }

        model.addAttribute("password",password);

        return "MyPage/lockscreen";
    }

    // 잠금 모드 해제 (비밀번호 입력시 해제)
    @PostMapping("/lockscreen")
    public String unLockscreen(@SessionAttribute("user") UsersDTO user,String password,String referer,Model model) {  return "redirect:"+referer.substring(21); }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(SessionStatus session) {
        session.setComplete();

        return "MyPage/logout";
    }

    @GetMapping("/snsUpdate")
    public String snsUpdateView() {
        return "MyPage/snsUpdate";
    }

    @PostMapping("/snsUpdate")
    public RedirectView snsUpdate(@RequestParam("email") String email, RedirectAttributes attributes, UsersDTO dto, Model model) {
        // email을 사용하여 사용자 정보를 업데이트하고 성공 또는 실패에 따라 리다이렉션합니다.
        dto.setEmail(email);  // 이메일 주소 할당
        UsersDTO updatedUser = service.snsUpdate(dto);
        model.addAttribute("user",dto);
        log.info("------------------ Email: {} ", email);

        if (updatedUser != null) {
            attributes.addFlashAttribute("successMessage", "회원 정보가 성공적으로 수정되었습니다.");
        } else {
            attributes.addFlashAttribute("errorMessage", "회원 정보 수정에 실패했습니다.");
        }
        return new RedirectView("/");
    }


//━━━━━ [프로필 관련] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    // Starter 화면 todo 사용 안할듯 ❌ 10.20 (재민)
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
