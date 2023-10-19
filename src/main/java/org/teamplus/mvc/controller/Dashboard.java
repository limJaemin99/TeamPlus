package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
public class Dashboard {

	// 달력 컨트롤러 todo 보류 : 팀 달력 , 개인 달력 구분해야 할 듯 10.18 (재민)
	@GetMapping("/dashboard-calendar")
	public String apps_calendar() {
		return "dashboard/calendar";
	}

	// 프로젝트 목록 컨트롤러 todo 사용 안함 ❌
	@GetMapping("/dashboard-projects-list")
	public String apps_projects_list() {
		return "dashboard/projects-list";
	}

	// 프로젝트 생성 컨트롤러 todo 사용 안함 ❌ : 수정하여 만듦
	@GetMapping("/dashboard-projects-create")
	public String apps_projects_create() {
		return "project-create";
	}

	
	// 나중에 사용할 일 있으면 사용해도 됨 todo 보류 ❗
	@GetMapping("/dashboard-tasks-details2")
	public String apps_tasks_list_details2() {
		return "dashboard/tasks-list-details2";
	}
	




}
