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
	
	@GetMapping("/pages-timeline")
	public String timeline() {
		return "pages/timeline";
	}
	

	@GetMapping("/pages-pricing")
	public String pricing() {
		return "pages/pricing";
	}
	
	@GetMapping("/pages-gallery")
	public String gallery() {
		return "pages/gallery";
	}
	

	@GetMapping("/pages-coming-soon")
	public String coming_soon() {
		return "pages/coming-soon";
	}
	
	@GetMapping("/pages-sitemap")
	public String sitemap() {
		return "pages/sitemap";
	}

}
