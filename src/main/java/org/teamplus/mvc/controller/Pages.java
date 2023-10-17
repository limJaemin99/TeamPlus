package org.teamplus.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Pages {

	// 사이트맵 컨트롤러
	@GetMapping("/TeamPlus-sitemap")
	public String sitemap() {
		return "TeamPlus/sitemap";
	}

	// 자주 묻는 질문 컨트롤러
	@GetMapping("/TeamPlus-faqs")
	public String faqs() {
		return "TeamPlus/faqs";
	}

}
