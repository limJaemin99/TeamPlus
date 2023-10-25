package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.teamplus.mvc.dto.*;
import org.teamplus.mvc.util.CommentsListDTO;
import org.teamplus.mvc.util.SearchDTO;
import org.teamplus.mvc.service.ProjectService;
import org.teamplus.mvc.util.PageRequestDTO;
import org.teamplus.mvc.util.PageResponseDTO;

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

        List<CommentsDTO> commentsList = service.getCommentsList(todoNo);
        Map<String,Object> subCommentsMap = new HashMap<>();

        for(CommentsDTO dto : commentsList){
            List<SubCommentsDTO> list = service.getSubCommentsList(dto.getCommentNo());
            for(SubCommentsDTO subdto : list){
                subCommentsMap.put(String.valueOf(subdto.getCommentNo()),subdto);
            }
        }

        commentsListDTO.setCommentsList(commentsList);
        commentsListDTO.setSubCommentsMap(subCommentsMap);

        return commentsListDTO;
    }

}
