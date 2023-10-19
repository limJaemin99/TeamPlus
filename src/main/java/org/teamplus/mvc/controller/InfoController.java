package org.teamplus.mvc.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("info")
public class InfoController {

	//사이트맵
	@GetMapping("/sitemap")
	public void sitemap() {}

	//자주 묻는 질문
	@GetMapping("/faq")
	public void faq() {}

	//[프로젝트 리스트용] 사이트맵
	@GetMapping("/sitemap-white")
	public void sitemapWhite() {}

	//[프로젝트 리스트용] 자주 묻는 질문
	@GetMapping("/faq-white")
	public void faqWhite() {}

	@GetMapping("/modal")
	public String model() {return "info/modals";}
}
