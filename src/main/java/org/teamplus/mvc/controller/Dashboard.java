package org.teamplus.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Dashboard {

	// index, home 컨트롤러
	@GetMapping("/home")
	public String index() {
		return "dashboard/index";
	}

	// 프로젝트 진행사항 컨트롤러
	@GetMapping("/dashboard-projects")
	public String dashboard_projects() {
		return "dashboard/projects";
	}

	// 달력 컨트롤러
	@GetMapping("/dashboard-calendar")
	public String apps_calendar() {
		return "dashboard/calendar";
	}

	// 프로젝트 목록 컨트롤러
	@GetMapping("/dashboard-projects-list")
	public String apps_projects_list() {
		return "dashboard/projects-list";
	}

	// 프로젝트 개요 컨트롤러
	@GetMapping("/dashboard-projects-overview")
	public String apps_projects_overview() {
		return "dashboard/projects-overview";
	}

	// 프로젝트 생성 컨트롤러
	@GetMapping("/dashboard-projects-create")
	public String apps_projects_create() {
		return "dashboard/projects-create";
	}

	// 작업 보드 컨트롤러
	@GetMapping("/dashboard-tasks-kanban")
	public String apps_tasks_kanban() {
		return "dashboard/tasks-kanban";
	}

	// 메모 목록 컨트롤러
	@GetMapping("/dashboard-tasks-list-view")
	public String apps_tasks_list_view() {
		return "dashboard/tasks-list-view";
	}

	// 메모 등록
	@GetMapping("/dashboard-tasks-details")
	public String apps_tasks_list_details() {
		return "dashboard/tasks-list-details";
	}
	
	// 나중에 사용할 일 있으면 사용해도 됨
	@GetMapping("/dashboard-tasks-details2")
	public String apps_tasks_list_details2() {
		return "dashboard/tasks-list-details2";
	}
	
	// 작업 세부정보 컨트롤러
	@GetMapping("/dashboard-tasks-read")
	public String apps_tasks_reads() {
		return "dashboard/tasks-read";
	}

	// 파일 관리자 컨트롤러
	@GetMapping("/dashboard-file-manager")
	public String apps_file_manager() {
		return "dashboard/file-manager";
	}

	// Todo 컨트롤러
	@GetMapping("/dashboard-todo")
	public String apps_todo() {
		return "dashboard/todo";
	}

	// 팀 컨트롤러
	@GetMapping("/dashboard-team")
	public String team() {
		return "dashboard/team";
	}

	// 프로젝트 목록/생성/참가 화면 index
	@GetMapping("/project-index")
	public String project_index() {
		return "dashboard/project-index";
	}

	//프로젝트 참가 화면
	@GetMapping("/project-join")
	public String project_join(){
		return "dashboard/project-join";
	}
}
