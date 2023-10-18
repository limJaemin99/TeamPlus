package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.teamplus.mvc.dto.ProjectDTO;
import org.teamplus.mvc.service.ProjectService;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("project")
public class ProjectController {

    private final ProjectService service;

//━━━━━ [프로젝트 선택 후 화면] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    //프로젝트 홈 (프로젝트 선택 후 나타나는 '해당 프로젝트의 홈화면')
    @GetMapping("/home")
    public String home() {
        return "dashboard/index";
    }

    //프로젝트 진행 현황
    @GetMapping("/tracking")
    public String trackingView() {
        return "dashboard/projects";
    }

    // 프로젝트 개요
    @GetMapping("/overview")
    public String overviewView() {
        return "dashboard/projects-overview";
    }

    // 작업 보드 (Git 의 Project 기능같은 화면)
    @GetMapping("/taskboard")
    public String taskboardView() {
        return "dashboard/tasks-kanban";
    }

    // 메모 목록 화면
    @GetMapping("/tasklist")
    public String tasklistView() {
        return "dashboard/tasks-list-view";
    }

    // 메모 등록 화면
    @GetMapping("/taskwrite")
    public String taskwriteView() {
        return "dashboard/tasks-list-details";
    }

    // 파일 관리자
    @GetMapping("/files")
    public String filesView() {
        return "dashboard/file-manager";
    }

    // To-Do 화면
    @GetMapping("/todo")
    public String todoView() {
        return "dashboard/todo";
    }

    // 팀(팀원) 관리
    @GetMapping("/team")
    public String teamView() { return "dashboard/team"; }

//━━━━━ [로그인 후 프로젝트 선택하기 전 화면] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    //프로젝트 리스트 (로그인 후 프로젝트 리스트를 출력하는 화면)
    @GetMapping("/list")
    public String listView() {
        return "dashboard/project-index";
    }

    //프로젝트 생성 화면
    @GetMapping("/create")
    public String createView() { return "dashboard/project-create"; }

    //프로젝트 참가 화면
    @GetMapping("/join")
    public String joinView(){
        return "dashboard/project-join";
    }

    //프로젝트 참가 todo [POST]
    @PostMapping("/join")
    public void joinPost(String userNo , ProjectDTO dto) {
        //TODO 로그인 화면이 아직 구성되지 않았으므로 userNo는 임의로 값을 부여함 2023-10-18 오전 (재민)
        userNo = "test1";
        log.info("┏━━━━━━━━━ userNo : {}",userNo);
        log.info("━━━━━━━━━━ projectNo : {}",dto.getProjectNo());
        log.info("┗━━━━━━━━━ password : {}",dto.getPassword());

    }

}
