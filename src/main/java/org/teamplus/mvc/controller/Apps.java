package org.teamplus.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Apps {

	@GetMapping("/apps-calendar")
	public String apps_calendar() {
		return "apps/calendar";
	}

	@GetMapping("/apps-projects-list")
	public String apps_projects_list() {
		return "apps/projects-list";
	}
	
	@GetMapping("/apps-projects-overview")
	public String apps_projects_overview() {
		return "apps/projects-overview";
	}
	
	@GetMapping("/apps-projects-create")
	public String apps_projects_create() {
		return "apps/projects-create";
	}
	
	@GetMapping("/apps-tasks-kanban")
	public String apps_tasks_kanban() {
		return "apps/tasks-kanban";
	}
	
	@GetMapping("/apps-tasks-list-view")
	public String apps_tasks_list_view() {
		return "apps/tasks-list-view";
	}
	
	@GetMapping("/apps-tasks-details")
	public String apps_tasks_list_details() {
		return "apps/tasks-list-details";
	}

	@GetMapping("/apps-file-manager")
	public String apps_file_manager() {
		return "apps/file-manager";
	}
	
	@GetMapping("/apps-todo")
	public String apps_todo() {
		return "apps/todo";
	}

}
