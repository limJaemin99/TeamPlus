package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.teamplus.mvc.dto.MyNoteDTO;
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

    // 프로젝트 홈 (프로젝트 진행 현황) 10.20 재민
    @GetMapping("/home")
    public String overviewView(@ModelAttribute("projectNo") String projectNo) {
        return "dashboard/projects";
    }

    //프로젝트 개요
    @GetMapping("/overview")
    public String trackingView(@ModelAttribute("projectNo") String projectNo) {
        return "dashboard/projects-overview";
    }

    //프로젝트 수정 View (팀장만 접근 가능)
    @GetMapping("/modify")
    public String modifyView(@SessionAttribute("user") UsersDTO user,@ModelAttribute("projectNo") String projectNo,Model model){
        List<TeamDTO> teamList = service.teamListByProjectNo(projectNo);
        ProjectDTO project = service.selectOne(projectNo);
        UsersDTO leader = null;
        for(TeamDTO team : teamList){
            if(team.getLeader() == 1){
                leader = service.selectUserByUserNo(team.getUserNo());
            }
        }

        if(leader != null){ //접속한 사용자가 리더 ⭕
            model.addAttribute("isLeader",1);
            model.addAttribute("project",project);
        } else {//접속한 사용자가 리더 ❌
            model.addAttribute("isLeader",0);
            model.addAttribute("project",project);
        }

        return "dashboard/project-modify";
    }

    //프로젝트 수정 POST (팀장만 접근 가능)
    @PostMapping("/modify")
    public String modify(@ModelAttribute("projectNo") String projectNo,ProjectDTO project){
        log.info("━━━━━ [프로젝트 수정] ━━━━━━━━━━");
        log.info("┏┏┏┏┏┏┏┏┏┏ projectNo : {}",projectNo);
        log.info("━━━━━━━━━━ status : {}",project.getStatus());
        log.info("━━━━━━━━━━ title : {}",project.getTitle());
        log.info("━━━━━━━━━━ description : {}",project.getDescription());
        log.info("━━━━━━━━━━ startDate : {}",project.getStartDate());
        log.info("━━━━━━━━━━ dueDate : {}",project.getDueDate());
        log.info("┗┗┗┗┗┗┗┗┗┗ password : {}",project.getPassword());

        if(project.getStatus() == 0){
            project.setEndDate(null);
        } else {
            if(service.selectOne(projectNo).getStatus() == 0){  //진행 ▶ 종료 인지 확인 : 현재 날짜 입력
                project.setEndDate(LocalDate.now());
            } else {    //종료 ▶ 종료 인지 확인 : 기존 종료일 입력
                project.setEndDate(service.selectOne(projectNo).getEndDate());
            }
        }

        int result = service.update(project);

        if(result == 1){    //업데이트 성공 ⭕
            log.info("⭕ 업데이트 성공 ⭕");

        } else {    //업데이트 실패 ❌
            log.info("❌ 업데이트 실패 ❌");

        }

        return "redirect:/project/home";
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
    public String listView(@SessionAttribute("user") UsersDTO user, Model model) {
        String userNo = user.getUserNo();
        //todo 아직 session 에 user 정보가 없으므로 임의로 값을 부여함 2023-10-18 오후 (재민)
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

    @PostMapping("/create")
    public String createAction(ProjectDTO dto){
        String projectNo="project"+service.getSequence();
        log.info(">>>>> 생성된 프로젝트번호 : {}",projectNo);
        dto.setProjectNo(projectNo);
        service.newProject(dto);
        return "redirect:/project/list";
    }

    //프로젝트 참가 화면
    @GetMapping("/join")
    public String joinView(){
        return "dashboard/project-join";
    }

    //프로젝트 참가 todo [POST]
    @PostMapping("/join")
    public String joinPost(@SessionAttribute("user") UsersDTO user , ProjectDTO dto) {

        log.info("┏━━━━━━━━━ userNo : {}",user.getUserNo());
        log.info("━━━━━━━━━━ projectNo : {}",dto.getProjectNo());
        log.info("┗━━━━━━━━━ password : {}",dto.getPassword());

        int exist = service.isExist(dto);
        int result = 0;

        log.info("━━━━━━━━━━ 프로젝트 존재 확인 : {}",exist);

        if(exist == 1){
            TeamDTO teamDTO = TeamDTO.builder()
                            .userNo(user.getUserNo())
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
