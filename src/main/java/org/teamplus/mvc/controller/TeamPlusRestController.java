package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.teamplus.mvc.dto.*;
import org.teamplus.mvc.util.*;
import org.teamplus.mvc.service.ProjectService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@RequiredArgsConstructor
public class TeamPlusRestController {

    private final ProjectService service;

    /* 개인 - 할일 삭제 기능 */
    @DeleteMapping("/private/privateDelete/{todoNo}")
    public Map<String, Integer> todoDelete(@PathVariable String todoNo){
        int count = service.delete(todoNo);
        Map<String,Integer> resultMap = new HashMap<>();
        resultMap.put("count",count);

        return resultMap;
    }

    /* 개인 - 할일 수정 기능 */
    @PatchMapping("/private/privateTodoUpdate/{todoNo}")
    public Map<String,Integer> todoUpdate(@RequestBody @Valid PrivateTodoDTO vo){
        Map<String,Integer> map = new HashMap<>();
        int count = service.update(vo);
        map.put("count",count);
        return map;
    }

    /* 개인 - 할일 한개 보기 */
    @GetMapping("/private/privateTodoSelectOne/{todoNo}")
    public PrivateTodoDTO selectOne(@PathVariable String todoNo){
        PrivateTodoDTO vo = service.selectPrivateOne(todoNo);

        return vo;
    }

    //개인 메모 검색
    @GetMapping("/private/tasklist/userSearch/{page}/{condition}/{word}")
    public UserSearchDTO userSearch(@SessionAttribute("user") UsersDTO user, @PathVariable int page,
                                    @PathVariable String condition, @PathVariable String word){
        UserSearchDTO userSearchDTO = new UserSearchDTO();

        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .userNo(user.getUserNo())
                .page(page)
                .type(condition)
                .keyword(word)
                .build();


        PageResponseDTO responseDTO = service.listWithSearchByUserNo(requestDTO);
        List<MyNoteDTO> mynoteDto = service.UsergetPageList(requestDTO);
        List<UsersDTO> usersList = new ArrayList<>();

        usersList.add(service.selectUserByUserNo(user.getUserNo()));

        userSearchDTO.setMyNoteList(mynoteDto);
        userSearchDTO.setUsersList(usersList);
        userSearchDTO.setPageResponseDTO(responseDTO);
        userSearchDTO.setPage(requestDTO.getPage());


        return userSearchDTO;
    }

//━━━━━ [R&R 비동기 컨트롤러] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

    //서버 세션을 비동기 js 로 넘기기
    @GetMapping("/getsession")
    public UsersDTO getSessionData(@SessionAttribute("user") UsersDTO user) {
        // 세션 데이터를 가져오는 코드를 작성합니다.
        UsersDTO session = user;

        return session;
    }

    //검색
    @GetMapping("/project/RnR/search/{projectNo}/{page}/{condition}/{word}")
    public SearchDTO search(@PathVariable String projectNo, @PathVariable int page,
                            @PathVariable String condition, @PathVariable String word){
        SearchDTO searchDTO = new SearchDTO();

        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .projectNo(projectNo)
                .page(page)
                .type(condition)
                .keyword(word)
                .build();

        if(word != null) {
            UsersDTO user = service.selectUserByNickName(word);
            if(user != null){
                String userNo = user.getUserNo();
                requestDTO.setUserNo(userNo);
            } else {
                requestDTO.setUserNo("일치하는 사용자가 없습니다.");
            }
        }

        PageResponseDTO responseDTO = service.listWithSearch(requestDTO);
        List<TeamTodoDTO> todoList = service.getPageList(requestDTO);

        searchDTO.setTeamTodoList(todoList);
        searchDTO.setPageResponseDTO(responseDTO);
        searchDTO.setPage(requestDTO.getPage());

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

        for(TeamTodoDTO list : todoList){
            userList.add(service.selectUserByUserNo(list.getUserNo()));
        }

        for(TeamDTO list : team){
            teamList.add(service.selectUserByUserNo(list.getUserNo()));
        }

        searchDTO.setUsersList(userList);
        searchDTO.setTeamMemberList(teamList);

        return searchDTO;
    }

    //Status 1개씩 변경
    @PatchMapping("/project/RnR/update")
    public Map<String,Object> changeStatus(@RequestBody @Valid TeamTodoDTO teamTodoDTO){
        log.info("━━━━━━━━━━ dto : {}",teamTodoDTO);
        Map<String,Object> map = new HashMap<>();
        int count = service.updateStatus(teamTodoDTO);
        map.put("count",count);

        return map;
    }

    //Status 기준 검색
    @GetMapping("/project/RnR/search/{projectNo}/{page}/{status}")
    public SearchDTO searchByStatus(@PathVariable String projectNo, @PathVariable int page, @PathVariable int status){
        SearchDTO searchDTO = new SearchDTO();

        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .projectNo(projectNo)
                .page(page)
                .status(status)
                .build();

        PageResponseDTO responseDTO = service.listWithSearchByStatus(requestDTO);
        List<TeamTodoDTO> todoList = service.getPageListByStatus(requestDTO);

        searchDTO.setTeamTodoList(todoList);
        searchDTO.setPageResponseDTO(responseDTO);
        searchDTO.setPage(requestDTO.getPage());

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

        for(TeamTodoDTO list : todoList){
            userList.add(service.selectUserByUserNo(list.getUserNo()));
        }

        for(TeamDTO list : team){
            teamList.add(service.selectUserByUserNo(list.getUserNo()));
        }

        searchDTO.setUsersList(userList);
        searchDTO.setTeamMemberList(teamList);

        return searchDTO;
    }

    //댓글 정보 불러오기
    @GetMapping("/project/RnR/comments/{todoNo}")
    public CommentsListDTO getComments(@PathVariable String todoNo){
        CommentsListDTO commentsListDTO = new CommentsListDTO();

        //댓글 리스트
        List<CommentsDTO> commentsList = service.getCommentsList(todoNo);
        //댓글 - 유저 정보
        List<UsersDTO> memberList = new ArrayList<>();
        //대댓글 맵 -(댓글번호,대댓글 리스트)
        List<SubCommentsDTO> subCommentsList = new ArrayList<>();
        List<UsersDTO> subMemberList = new ArrayList<>();

        for(CommentsDTO dto : commentsList){
            memberList.add(service.selectUserByUserNo(dto.getUserNo()));
        }

        for(CommentsDTO dto : commentsList){    //댓글 리스트를 하나씩 뺴서 유저넘버로 댓글 리스트를 뺸다.
            List<SubCommentsDTO> list = service.getSubCommentsList(dto.getCommentNo());
            //대댓글 저장 (대댓글 리스트)
            for(SubCommentsDTO subDTO : list){
                subCommentsList.add(subDTO);
                //대댓글 리스트에 대한 멤버 리스트를 뺴야함
                subMemberList.add(service.selectUserByUserNo(subDTO.getUserNo()));
            }
        }

        commentsListDTO.setCommentsList(commentsList);
        commentsListDTO.setMemberList(memberList);
        commentsListDTO.setSubCommentsList(subCommentsList);
        commentsListDTO.setSubMemberList(subMemberList);

        return commentsListDTO;
    }

    // ● 일반 댓글 작성
    @PatchMapping("/project/RnR/comments/comment")
    public Map<String,Object> writeComment(@RequestBody CommentsDTO commentsDTO){
        //일반 댓글 작성에 필요한 것들 : todoNo,userNo,content ▶ CommentsDTO

        Map<String,Object> map = new HashMap<>();

        int result = service.writeComment(commentsDTO);

        map.put("result",result);


        return map;
    }

    // ● 대댓글 작성
    @PatchMapping("/project/RnR/comments/subComment")
    public Map<String,Object> writeComment(@RequestBody SubCommentsDTO subCommentsDTO){
        //대댓글 작성에 필요한 것들 : UserNO,CommentNO,SubContent ▶ SubCommentsDTO

        Map<String,Object> map = new HashMap<>();

        int result = service.writeSubComment(subCommentsDTO);

        map.put("result",result);

        return map;
    }

    // 프로젝트 리스트에서 개별 프로젝트 상세정보 출력
    @GetMapping("/project/detail/{projectNo}")
    public ProjectInfoDTO showDetail(@ModelAttribute("projectNo") String projectNo){
        List<TeamDTO> team = service.teamListByProjectNo(projectNo);

        // 해당하는 프로젝트에 참여중인 user들을 담을 list
        List<UsersDTO> users = new ArrayList<>();

        for (TeamDTO dto:team) {
            users.add(service.selectUserByUserNo(dto.getUserNo()));
        }

        ProjectInfoDTO projectInfoDTO= ProjectInfoDTO.builder()
                .projectDTO(service.selectOne(projectNo))
                .todoList(service.selectListAfterToday(projectNo))
                .usersList(users)
                .build();

        return projectInfoDTO;
    }

    //팀원 초대 메일 보내기(모달)
    @GetMapping("/project/invite/{userNo}/{projectNo}/{email}")
    public Map<String,Integer> invite(@PathVariable String userNo, @PathVariable String projectNo, @PathVariable String email){
        InviteMail mail = new InviteMail();
        Map<String,Integer> map = new HashMap<>();
        UsersDTO user = service.selectUserByUserNo(userNo);
        String password = service.selectOne(projectNo).getPassword();

        mail.setCaller(user.getName());
        mail.setProjectNo(projectNo);
        mail.setPassword(password);

        int result = mail.sendMail(email);
        map.put("result",result);

        return map;
    }

    //프로젝트 캘린더
    @GetMapping("/project/project-Calendar/{projectNo}")
    @ResponseBody
    public JSONArray projectmonthPlan(@PathVariable String projectNo) {
        ProjectDTO project = service.selectOne(projectNo);
        List<ProjectCalendarDTO> list = service.projectselectAll(project.getProjectNo());

        JSONArray jsonArr = new JSONArray();

        for (ProjectCalendarDTO event : list) {



            JSONObject jsonObj = new JSONObject();
            jsonObj.put("projectNo", project.getProjectNo());
            jsonObj.put("id", event.getId());
            jsonObj.put("title", event.getTitle());
            jsonObj.put("start", event.getStartDate());
            jsonObj.put("end", event.getEndDate());
            jsonObj.put("location", event.getLocation());
            jsonObj.put("description", event.getDescription());

            jsonArr.add(jsonObj);
        }

        return jsonArr;
    }

    @PostMapping("/project/projectAddEvent/{projectNo}")
    public int projectinsert(@RequestBody ProjectCalendarDTO projectCalendarDTO,@PathVariable String projectNo){
        ProjectDTO project = service.selectOne(projectNo);
        projectCalendarDTO.setProjectNo(project.getProjectNo());
        return service.projectinsert(projectCalendarDTO);
    }

    @PostMapping("/project/projectUpdateEvent")
    public int projectupdate(@RequestBody ProjectCalendarDTO projectCalendarDTO){
        return service.projectupdate(projectCalendarDTO);}
    @DeleteMapping("/project/projectDeleteEvent/{eventId}")
    public int projectdelete(@PathVariable int eventId){
        return service.projectdelete(eventId);
    }

    @GetMapping("/private/private-Calendar")
    @ResponseBody
    public JSONArray privatemonthPlan(@SessionAttribute("user") UsersDTO user) {
        List<PrivateCalendarDTO> list = service.privateselectAll(user.getUserNo());

        JSONArray jsonArr = new JSONArray();

        for (PrivateCalendarDTO event : list) {

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("userNo", user.getUserNo());
            jsonObj.put("id", event.getId());
            jsonObj.put("title", event.getTitle());
            jsonObj.put("start", event.getStartDate());
            jsonObj.put("end", event.getEndDate());
            jsonObj.put("location", event.getLocation());
            jsonObj.put("description", event.getDescription());

            jsonArr.add(jsonObj);
        }

        return jsonArr;
    }

    @PostMapping("/private/privateaddEvent")
    public int privateinsert(@RequestBody PrivateCalendarDTO privateCalendarDTO){
        return service.privateinsert(privateCalendarDTO);
    }

    @PostMapping("/private/privateupdateEvent")
    public int privateupdate(@RequestBody PrivateCalendarDTO privateCalendarDTO){
        return service.privateupdate(privateCalendarDTO);}
    @DeleteMapping("/private/privatedeleteEvent/{eventId}")
    public int privatedelete(@PathVariable int eventId){
        return service.privatedelete(eventId);
    }


    

}
