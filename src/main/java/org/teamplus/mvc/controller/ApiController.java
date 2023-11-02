package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.UsersService;
import org.teamplus.mvc.util.MailCheck;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final UsersService service;

    /* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━아이디 중복검사(회원정보수정) ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    @PostMapping("/idcheck")
    public ResponseEntity<String> idCheck(@RequestBody Map<String, String> requestBody) {
        String newid = requestBody.get("newid");
        UsersDTO dto = new UsersDTO();
        dto.setId(newid);

        int idCheck = service.isIdExist(dto);
        if (idCheck != 1) {
            return ResponseEntity.ok("사용 가능한 닉네임입니다.");
        } else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 닉네임입니다.");
    }

    /* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━닉네임 중복검사(회원정보수정) ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    @PostMapping("/nickcheck")
    public ResponseEntity<String> nickCheck(@RequestBody Map<String, String> requestBody) {
        String nickName = requestBody.get("newnick");
        UsersDTO dto = new UsersDTO();
        dto.setNickName(nickName);

        // 서비스를 사용하여 닉네임 중복 검사를 수행합니다.
        int isDuplicate = service.isNickNameExist(dto);
        if (isDuplicate != 1) {
            return ResponseEntity.ok("사용 가능한 닉네임입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 닉네임입니다.");
        }
    }

    /* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━비밀번호변경(회원정보수정) ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestBody) {
        String userid = requestBody.get("idForChangePassword"); // 이 부분을 수정하여 사용자 이름을 받아오세요
        String newPassword = requestBody.get("confirmpassword"); // 이 부분을 수정하여 새 비밀번호를 받아오세요

        log.info("Username: " + userid);
        log.info("New Password: " + newPassword);
        UsersDTO dto = new UsersDTO();
        dto.setPassword(newPassword);
        dto.setId(userid);

        var result = service.loginChangePassword(dto);
        if (result != 1) {
            log.info("비밀번호 변경 실패");
            log.error("비밀번호 변경 실패 - 사용자: {}, 새 비밀번호: {}", userid, newPassword);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("변경 실패.");
        } else {
            log.info("비밀번호 변경 성공 - 사용자: {}", userid);
            return ResponseEntity.ok("변경 성공.");
        }
    }

    /* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━이메일 인증 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    private String sentVerificationCode = ""; // 'sendmail' 메소드에서 생성한 코드를 저장하는 변수
    private boolean isVerificationComplete = false; // 인증 성공 후 업데이트 허가를 위한 변수
    @PostMapping("/sendmail")
    public ResponseEntity<String> sendMail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("checkemail");

        // 여기에서 이메일 전송 로직을 구현하고 인증 코드를 생성하여 전송
        String redirect = "redirect:";

        log.info("━━━━━━━━━━ 입력한 email : {}", email);

        //있는 메일인지 검증
        int isExist = service.isEmailExist(email);
        log.info("━━━━━━━━━━ isExist : {}", isExist);

        if (isExist == 1) { //존재하는 메일
            MailCheck mail = new MailCheck();

            String code = mail.random();
            log.info("━━━━━━━━━━ 생성된 코드 : {}", code);

            mail.setCode(code);
            mail.sendMail(email);

            sentVerificationCode = code;

            redirect = "/";
        } else {    //없는 메일
            log.info("⚠⚠⚠⚠⚠⚠⚠⚠⚠⚠ 없는 메일 입니다 : {}", email);
            redirect += "/";
        }
        return ResponseEntity.ok("이메일이 전송되었습니다.");
    }

    @PostMapping("/verifycode")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> requestBody, HttpSession session) {
        String verificationCode = requestBody.get("verificationCode");

        if (verificationCode.equals(sentVerificationCode)) {
            // 클라이언트가 입력한 인증 코드와 'sendmail'에서 생성한 코드가 일치하는 경우
            session.setAttribute("isVerificationComplete", true);
            return ResponseEntity.ok("인증 코드 확인 성공.");
        } else {
            // 인증 코드 불일치
            return ResponseEntity.status(HttpStatus.CONFLICT).body("인증 코드 확인 실패.");
        }
    }

    /* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━회원 탈퇴 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
    @PostMapping("/delete")
    public String delete(@RequestBody Map<String, String> requestBody, SessionStatus sessionStatus) {
        String userNo = requestBody.get("userNo"); // 요청 바디에서 userNo를 추출
        if (userNo != null && !userNo.isEmpty()) {
            // userNo를 이용해 탈퇴 작업 수행
            UsersDTO dto = new UsersDTO();
            dto.setUserNo(userNo); // userNo를 숫자로 변환
            service.delete(dto);
            // 세션을 종료하여 사용자 로그아웃 처리
            sessionStatus.setComplete();
            return "redirect:/users/signin"; // 로그인 페이지로 리디렉션
        } else {
            return "/";
        }
    }
}
