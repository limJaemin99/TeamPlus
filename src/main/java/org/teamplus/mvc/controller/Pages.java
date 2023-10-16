package org.teamplus.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Pages {

	@GetMapping("/pages-starter")
	public String starter() {
		return "pages/starter";
	}
	
	@GetMapping("/pages-profile")
	public String profile() {
		return "pages/profile";
	}
	
	@GetMapping("/pages-profile-settings")
	public String profile_settings() {
		return "pages/profile-settings";
	}
	
	@GetMapping("/pages-team")
	public String team() {
		return "pages/team";
	}

	@GetMapping("/pages-sitemap")
	public String sitemap() {
		return "pages/sitemap";
	}

}
