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
	@GetMapping("/project-index-sitemap")
	public void projectIndexSitemap() {}

	//[프로젝트 리스트용] 자주 묻는 질문
	@GetMapping("/project-index-faq")
	public void projectIndexFaq() {}

	//[프로젝트 리스트용] 사이트맵
	@GetMapping("/profile-sitemap")
	public void profileSitemap() {}

	//[프로젝트 리스트용] 자주 묻는 질문
	@GetMapping("/profile-faq")
	public void profileFaq() {}

	@GetMapping("/modal")
	public String model() {return "info/modals";}
}
