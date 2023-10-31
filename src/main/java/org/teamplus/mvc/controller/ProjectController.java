package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.teamplus.mvc.dto.*;
import org.teamplus.mvc.service.ProjectService;
import org.teamplus.mvc.util.PageRequestDTO;
import org.teamplus.mvc.util.PageResponseDTO;

import java.time.LocalDate;
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
    public String trackingView(@ModelAttribute("projectNo") String projectNo,Model model) {
        ProjectDTO project = service.selectOne(projectNo);
        //팀 정보
        List<TeamDTO> team = service.teamListByProjectNo(projectNo);
        //팀원 정보 리스트
        List<UsersDTO> teamList = new ArrayList<>();
        List<DataShareDTO> dataShareList = service.DataselectList(projectNo);

        for(TeamDTO list : team){
            teamList.add(service.selectUserByUserNo(list.getUserNo()));
        }
        model.addAttribute("project",project);
        model.addAttribute("teamList",teamList);
        model.addAttribute("dataList",dataShareList);

        return "dashboard/projects-overview";
    }

    //프로젝트 캘린더
    @GetMapping("/projectCalendar")
    public String projectCalendar(@ModelAttribute("projectNo") String projectNo,Model model) {
        ProjectDTO project = service.selectOne(projectNo);
        model.addAttribute("project",project);

        return "/dashboard/projectCalendar";
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
            List<String> memberProfile = new ArrayList<>();
            List<TeamDTO> memberList = service.teamListByProjectNo(projectNo);
            for(TeamDTO member : memberList){
                memberProfile.add(service.getProfileURL(member.getUserNo()));
            }
            members.put(projectNo,memberProfile);
            log.info("━━━━━━━━━━ members {}번 : {}",testCount,memberProfile.toString());
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
    public String createAction(@SessionAttribute("user") UsersDTO user, ProjectDTO dto, RedirectAttributes redirectAttributes, Model model){
        String projectNo="project"+service.getSequence();
        log.info(">>>>> 생성된 프로젝트번호 : {}",projectNo);
        dto.setProjectNo(projectNo);
        int result=service.newProject(dto);
        if(result==1) {
            //생성했을 때 팀에 등록과 동시에 status 1로 지정
            TeamDTO teamDTO = TeamDTO.builder()
                    .projectNo(projectNo)
                    .userNo(user.getUserNo())
                    .leader(1)
                    .build();
            service.join(teamDTO);

            redirectAttributes.addFlashAttribute("create",1);
            redirectAttributes.addFlashAttribute("projectNo",projectNo);
            redirectAttributes.addFlashAttribute("userNo",user.getUserNo());
        }else {
            redirectAttributes.addFlashAttribute("message","프로젝트 생성 실패");
            redirectAttributes.addFlashAttribute("title","Fail");
        }
        redirectAttributes.addFlashAttribute("result",result);
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

    //R&R View
    @GetMapping("/RnR")
    public String RnRView(@ModelAttribute("projectNo") String projectNo, PageRequestDTO pageRequestDTO, Model model){
        pageRequestDTO.setProjectNo(projectNo);
        PageResponseDTO responseDTO = service.listWithSearch(pageRequestDTO);
        List<TeamTodoDTO> todoList = service.getPageList(pageRequestDTO);
        model.addAttribute("todoList",todoList);
        model.addAttribute("paging",responseDTO);
        model.addAttribute("page",pageRequestDTO.getPage());

        //To-Do 작성자 정보
        List<UsersDTO> userList = new ArrayList<>();

        //프로젝트 팀 정보
        List<TeamDTO> team = service.teamListByProjectNo(projectNo);

        //팀원 정보 리스트
        List<UsersDTO> teamList = new ArrayList<>();

        for(TeamTodoDTO list : todoList){
            userList.add(service.selectUserByUserNo(list.getUserNo()));
        }

        for(TeamDTO list : team){
            teamList.add(service.selectUserByUserNo(list.getUserNo()));
        }

        model.addAttribute("userList",userList);
        model.addAttribute("teamList",teamList);

        return "dashboard/RnR";
    }

    // 할 일 생성 화면
    @GetMapping("/todo-create")
    public String createTodoView(@SessionAttribute("user")UsersDTO user,
                                 @ModelAttribute("projectNo") String projectNo) {
        return "dashboard/team-todo-create";
    }

    @PostMapping("/todo-create")
    public String createTodoAction(TeamTodoDTO dto,@SessionAttribute("user")UsersDTO user,
                                   @ModelAttribute("projectNo") String projectNo){
        // TODO 현재 임의값으로 테스트 완료, 결로 설정 후 테스트 필요
        log.info(">>>> projectNo : {}",projectNo);
        String todoNo = service.selectOne(projectNo).getTitle()+"_"+user.getId()+"_"+service.getTeamTodoSeq();
        log.info("::::::: 팀할일번호: {}",todoNo);
        dto.setTodoNo(todoNo);
        dto.setUserNo(user.getUserNo());
        int result = service.newTeamTodo(dto);
        log.info("할일생성 성공 여부 : {}",result);
        return "redirect:/project/home";

    }
}
