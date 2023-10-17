package org.teamplus.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPage {

	// 로그인 컨트롤러
	@GetMapping("/MyPage-signin")
	public String auth_signin_basic() {
		return "MyPage/signin";
	}
	
	// 회원가입 컨트롤러
	@GetMapping("/MyPage-signup")
	public String auth_signup_basic() {
		return "MyPage/signup";
	}

	// 비밀번호 초기화 컨트롤러
	@GetMapping("/MyPage-pass-reset")
	public String auth_pass_reset_basic() {
		return "MyPage/pass-reset";
	}
	
	// 비밀번호 변경 컨트롤러
	@GetMapping("/MyPage-pass-change")
	public String auth_pass_change_basic() {
		return "MyPage/pass-change";
	}
	
	// 잠금 모드 컨트롤러
	@GetMapping("/MyPage-lockscreen")
	public String auth_lockscreen_basic() {
		return "MyPage/lockscreen";
	}
	
	// 로그아웃 컨트롤러
	@GetMapping("/MyPage-logout")
	public String auth_logout_basic() {
		return "MyPage/logout";
	}

	// 2단계 인증 컨트롤러
	@GetMapping("/MyPage-twostep")
	public String auth_twostep_basic() {
		return "MyPage/twostep";
	}

	// Starter 컨트롤러
	@GetMapping("/MyPage-starter")
	public String starter() {
		return "MyPage/starter";
	}

	// 프로필 보기 컨트롤러
	@GetMapping("/MyPage-profile")
	public String profile() {
		return "MyPage/profile";
	}

	// 프로필 수정 컨트롤러
	@GetMapping("/MyPage-profile-settings")
	public String profile_settings() {
		return "MyPage/profile-settings";
	}
	

}






