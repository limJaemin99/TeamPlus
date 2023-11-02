package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.teamplus.mvc.dto.*;
import org.teamplus.mvc.service.ProjectService;
import org.teamplus.mvc.util.PageRequestDTO;
import org.teamplus.mvc.util.PageResponseDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("project")
public class ProjectController {

    private final ProjectService service;

//━━━━━ [프로젝트 선택 후 화면] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    // 프로젝트 홈 (프로젝트 진행 현황) 10.20 재민
    @GetMapping("/home")
    public String overviewView(@ModelAttribute("projectNo") String projectNo, RedirectAttributes redirectAttributes) {

        //프로젝트 수정 후 모달에 사용할 Attribute
        if(redirectAttributes.getFlashAttributes().get("modify") == null)
            redirectAttributes.addAttribute("modify",0);

        if(redirectAttributes.getFlashAttributes().get("create") == null)
            redirectAttributes.addAttribute("create",0);

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
    public String modify(@ModelAttribute("projectNo") String projectNo, ProjectDTO project, RedirectAttributes redirectAttributes){
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

        redirectAttributes.addFlashAttribute("modify",result);

        return "redirect:/project/home?projectNo="+projectNo;
    }

    // 파일 관리자
    @GetMapping("/files")
    public String filesView(@ModelAttribute("projectNo") String projectNo, Model model) {
        ProjectDTO project = service.selectOne(projectNo);
        List<DataShareDTO> dataShareList = service.DataselectList(projectNo);
        model.addAttribute("dataList",dataShareList);
        model.addAttribute("project",project);


        return "dashboard/file-manager";
    }
    @PostMapping("/filesSave")
    public String filesAction(@ModelAttribute("projectNo") String projectNo,DataShareDTO dto){

//        String path ="D:\\iclass0419\\upload";
        String path ="C:\\iclass0419\\upload";
        StringBuilder filenames = new StringBuilder();

        if(dto.getFile().getSize()!=0) {   //getSize()는 첨부파일의 크기
            String ofilename = dto.getFile().getOriginalFilename();      // 원래의 파일명 (파일이름.확장자)
//            String prefix = ofilename.substring(0, ofilename.lastIndexOf("."));   // original 파일이름
            String postfix = ofilename.substring(ofilename.lastIndexOf("."));    //확장자
            StringBuilder newfile = new StringBuilder("project_")
                    //      .append(prefix)      //원래의 파일이름
                    .append(UUID.randomUUID().toString().substring(0,8)).append(postfix);
            // 사용자가 전송한 파일명 사용하지 않고 UUID.randomUUID() 로 랜덤 문자열 생성한 것 8글자로 함.
            //path 폴더에 newfile 로  File 객체 생성해서 저장 준비
            File file = new File(path + "\\"+newfile);
            //저장
            try {
                dto.getFile().transferTo(file);         // f 파이르이 내용을 file 객체로 전송.(파일복사)
                filenames.append(newfile);  //db 테이블에 들어갈 파일명
            } catch (IOException e) {   }
        }

        dto.setDataURL(filenames.toString());
        dto.setShareNo(service.DatagetSequence());
        service.Datawrite(dto);

        return "redirect:/project/files";
    }

    @GetMapping("/download")
    public StreamingResponseBody downloadFile(@RequestParam("file") String dataURL, HttpServletResponse response,Model model) {
        response.setHeader("Content-Disposition", "attachment; filename=" + dataURL);

        StreamingResponseBody stream = out -> {
            try (InputStream is = new FileInputStream("C:/iclass0419/upload/" + dataURL)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                int count = service.countUpdate(dataURL);
                if (count > 0) {
                    log.info("update 성공 ⭕");
                } else {
                    log.info("update 실패 ❌");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        model.addAttribute("dataURL",dataURL);
        return stream;
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
    public String createTodoAction(TeamTodoDTO dto, @SessionAttribute("user")UsersDTO user,
                                   @ModelAttribute("projectNo") String projectNo, RedirectAttributes redirectAttributes){
        log.info(">>>> projectNo : {}",projectNo);
        String todoNo = ""+service.getTeamTodoSeq();
        log.info("::::::: 팀할일번호: {}",todoNo);
        dto.setTodoNo(todoNo);
        dto.setUserNo(user.getUserNo());
        int result = service.newTeamTodo(dto);

        redirectAttributes.addFlashAttribute("create",result);

        return "redirect:/project/home?projectNo="+projectNo;

    }
}
