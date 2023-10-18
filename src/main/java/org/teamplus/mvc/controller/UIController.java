package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("ui")
public class UIController {
	
	// 성공메세지 컨트롤러
	@GetMapping("/success")
	public void success() {}

	// 404 에러 컨트롤러
	@GetMapping("/404Errors")
	public void error404() {}


	// 500 에러 컨트롤러
	@GetMapping("/500Errors")
	public void error500() {}

}






