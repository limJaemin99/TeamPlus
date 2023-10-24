package org.teamplus.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.teamplus.mvc.dto.PrivateTodoDTO;
import org.teamplus.mvc.dto.SearchDTO;
import org.teamplus.mvc.dto.TeamTodoDTO;
import org.teamplus.mvc.dto.UsersDTO;
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

//━━━━━ [R&R 비동기 컨트롤러] ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

    //서버 세션을 비동기 js 로 넘기기
    @GetMapping("/getsession")
    public UsersDTO getSessionData(@SessionAttribute("user") UsersDTO user) {
        // 세션 데이터를 가져오는 코드를 작성합니다.
        UsersDTO session = user;

        return session;
    }

    //검색
    @GetMapping("/project/RnR/search/{projectNo}/{condition}/{word}")
    public SearchDTO search(@PathVariable String projectNo,@PathVariable String condition,@PathVariable String word){
        SearchDTO searchDTO = new SearchDTO();
        TeamTodoDTO teamTodoDTO = TeamTodoDTO.builder().projectNo(projectNo).description(word).title(word).build();
        List<UsersDTO> inchagerList = new ArrayList<>();

        switch (condition){
            case "all" :
                searchDTO.setTeamTodoList(service.getTodoList(projectNo));
                for(TeamTodoDTO dto : searchDTO.getTeamTodoList()){
                    inchagerList.add(service.selectUserByUserNo(dto.getUserNo()));
                }
                searchDTO.setUsersList(inchagerList);
                log.info("‼ 전체 검색 실행 완료 ‼");
                return searchDTO;
            case "title" :
                searchDTO.setTeamTodoList(service.getTodoListByTitle(teamTodoDTO));
                for(TeamTodoDTO dto : searchDTO.getTeamTodoList()){
                    inchagerList.add(service.selectUserByUserNo(dto.getUserNo()));
                }
                searchDTO.setUsersList(inchagerList);
                log.info("‼ 제목 검색 실행 완료 ‼");
                return searchDTO;
            case "description" :
                searchDTO.setTeamTodoList(service.getTodoListByDescription(teamTodoDTO));
                for(TeamTodoDTO dto : searchDTO.getTeamTodoList()){
                    inchagerList.add(service.selectUserByUserNo(dto.getUserNo()));
                }
                searchDTO.setUsersList(inchagerList);
                log.info("‼ 설명 검색 실행 완료 ‼");
                return searchDTO;
            case "incharge" :
                String userNo = service.selectUserByNickName(word).getUserNo();
                teamTodoDTO.setUserNo(userNo);
                searchDTO.setTeamTodoList(service.getTodoListByUserNo(teamTodoDTO));
                for(TeamTodoDTO dto : searchDTO.getTeamTodoList()){
                    inchagerList.add(service.selectUserByUserNo(dto.getUserNo()));
                }
                searchDTO.setUsersList(inchagerList);
                log.info("‼ 작성자 검색 실행 완료 ‼");
                return searchDTO;
            case "imminent" :
                searchDTO.setTeamTodoList(service.getTodoListImminent());
                for(TeamTodoDTO dto : searchDTO.getTeamTodoList()){
                    inchagerList.add(service.selectUserByUserNo(dto.getUserNo()));
                }
                searchDTO.setUsersList(inchagerList);
                log.info("‼ 마감 임박 검색 실행 완료 ‼");
                return searchDTO;
        }

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
    @GetMapping("/project/RnR/search/{projectNo}/{status}")
    public SearchDTO searchByStatus(@PathVariable String projectNo,@PathVariable int status){
        TeamTodoDTO teamTodoDTO = TeamTodoDTO.builder().projectNo(projectNo).status(status).build();
        SearchDTO searchDTO = new SearchDTO();
        List<UsersDTO> inchagerList = new ArrayList<>();

        searchDTO.setTeamTodoList(service.getTodoListByStatus(teamTodoDTO));
        for(TeamTodoDTO dto : searchDTO.getTeamTodoList()){
            inchagerList.add(service.selectUserByUserNo(dto.getUserNo()));
        }
        searchDTO.setUsersList(inchagerList);
        log.info("‼ Status 검색 실행 완료 ‼");

        return searchDTO;
    }
}
