package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;
import org.teamplus.mvc.dto.ProjectDTO;
import org.teamplus.mvc.dto.TeamDTO;
import org.teamplus.mvc.dto.UsersDTO;
import org.teamplus.mvc.service.ProjectService;
import org.teamplus.mvc.service.TeamService;
import org.teamplus.mvc.service.UsersService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String listView(/*@SessionAttribute("user")*/UsersDTO user,Model model) {
        String userNo = user.getUserNo();
        //todo 아직 session 에 user 정보가 없으므로 임의로 값을 부여함 2023-10-18 오후 (재민)
        userNo = "test1";
        log.info("━━━━━━━━━━ userNo : {}",userNo);

        LocalDate today = LocalDate.now();

        int testCount = 1;

        List<TeamDTO> teamList = service.teamListByUserNo(userNo);
        List<String> projectNoList = new ArrayList<>();
        for(TeamDTO dto : teamList){
            projectNoList.add(dto.getProjectNo());
            log.info("━━━━━━━━━━ projectNoList {}번 : {}",testCount,dto.getProjectNo());
            testCount++;
        }

        testCount = 1;

        List<ProjectDTO> projectList = new ArrayList<>();
        for(String projectNo : projectNoList){
            projectList.add(service.selectOne(projectNo));
            log.info("━━━━━━━━━━ projectList {}번 : {}",testCount,service.selectOne(projectNo));
            testCount++;
        }

        testCount = 1;

        Map<String,Object> members = new HashMap<>();
        for(String projectNo : projectNoList){
            List<String> memberName = new ArrayList<>();
            List<TeamDTO> memberList = service.teamListByProjectNo(projectNo);
            for(TeamDTO member : memberList){
                memberName.add(service.nameByUserNo(member.getUserNo()));
            }
            members.put(projectNo,memberName);
            log.info("━━━━━━━━━━ members {}번 : {}",testCount,memberName.toString());
            testCount++;
        }


        model.addAttribute("list",projectList);
        model.addAttribute("members",members);
        model.addAttribute("today",today);

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
    public String joinPost(String userNo , ProjectDTO dto) {
        //TODO 로그인 화면이 아직 구성되지 않았으므로 userNo는 임의로 값을 부여함 2023-10-18 오전 (재민)
        userNo = "test3";
        log.info("┏━━━━━━━━━ userNo : {}",userNo);
        log.info("━━━━━━━━━━ projectNo : {}",dto.getProjectNo());
        log.info("┗━━━━━━━━━ password : {}",dto.getPassword());

        int exist = service.isExist(dto);
        int result = 0;

        log.info("━━━━━━━━━━ 프로젝트 존재 확인 : {}",exist);

        if(exist == 1){
            TeamDTO teamDTO = TeamDTO.builder()
                            .userNo(userNo)
                            .projectNo(dto.getProjectNo())
                            .build();
            result = service.join(teamDTO);
        } else {
            log.info("▶▶▶▶▶▶▶▶▶▶ 존재하지 않는 프로젝트입니다 : {}",dto.getProjectNo());
        }

        if(result == 1){
            log.info("▶▶▶▶▶▶▶▶▶▶ 프로젝트 참여 성공 ⭕");    //todo 이 부분에 모달 띄워야함
        } else {
            log.info("▶▶▶▶▶▶▶▶▶▶ 프로젝트 참여 실패 ❌");    //todo 이 부분에 모달 띄워야함
        }

        return "redirect:/project/list" ;
    }

}
