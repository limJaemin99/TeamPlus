package org.teamplus.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Authentication {
	
	// 성공메세지 컨트롤러
	@GetMapping("/success-msg")
	public String auth_success_msg_basic() {
		return "authentication/success-msg";
	}

	// 404 에러 컨트롤러
	@GetMapping("/404Errors")
	public String auth_not_found_basic() {
		return "authentication/404Errors";
	}


	// 500 에러 컨트롤러
	@GetMapping("/500Errors")
	public String auth_server_error() {
		return "authentication/500Errors";
	}

}






