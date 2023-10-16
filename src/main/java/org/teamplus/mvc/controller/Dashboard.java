package org.teamplus.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Dashboard {

	@GetMapping("/")
	public String index() {
		return "dashboard/index";
	}
	
	@GetMapping("/dashboard-analytics")
	public String dashboard_analytics() {
		return "dashboard/analytics";
	}
	
	@GetMapping("/dashboard-crm")
	public String dashboard_crm() {
		return "dashboard/crm";
	}
	
	@GetMapping("/dashboard-crypto")
	public String dashboard_crypto() {
		return "dashboard/crypto";
	}
	
	@GetMapping("/dashboard-projects")
	public String dashboard_projects() {
		return "dashboard/projects";
	}
	
	@GetMapping("/dashboard-nft")
	public String dashboard_nft() {
		return "dashboard/nft";
	}
	
	@GetMapping("/dashboard-job")
	public String dashboard_job() {
		return "dashboard/job";
	}
}
