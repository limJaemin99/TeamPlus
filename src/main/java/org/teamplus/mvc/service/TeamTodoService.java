package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.TeamTodoMapper;
import org.teamplus.mvc.dto.TeamDTO;
import org.teamplus.mvc.dto.TeamTodoDTO;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeamTodoService {

    private final TeamTodoMapper dao;

    // 프로젝트 투두리스트 [시퀀스 추출]
    public int getSequence(){
        return dao.getSequence();
    }

    // 프로젝트 투두리스트 [등록]
    public int newTeamTodo(TeamTodoDTO dto){
        return dao.newTeamTodo(dto);
    }

    // 프로젝트 투두리스트 [진행상태 수정]
    public int setStatus(Map<String,Object> map){
        return dao.setStatus(map);
    }

    // 프로젝트 투두리스트 [내용 수정]
    int update(TeamTodoDTO dto){
        return dao.update(dto);
    }

    // 프로젝트 투두리스트 [진행상태 : 완료 설정]
    // *** 진행여부 수정과 비교하여 수정 필요
    int setComplete(TeamTodoDTO dto){
        return dao.setComplete(dto);
    }

    // 프로젝트 투두리스트 [삭제]
    int delete(TeamTodoDTO dto){
        return dao.delete(dto);
    }

    // 프로젝트 투두리스트 [전체 출력]
    public List<TeamTodoDTO> selectList(String projectNo){
        return dao.selectList(projectNo);
    }

    // 프로젝트 투두리스트 [ProjectNo 와 UserNo별 조회]PRIVATECALENDAR
    public List<TeamTodoDTO> selectByUserNo(TeamTodoDTO dto){
        return dao.selectByUserNo(dto);
    }

    // 프로젝트 투두리스트 [진행상태별 출력]
    public List<TeamTodoDTO> selectByStatus(TeamTodoDTO dto){
        return dao.selectByStatus(dto);
    }

    // 프로젝트 투두리스트 [종료 예정일이 오늘 이후 출력]
    public List<TeamTodoDTO> selectListAfterToday(String projectNo){return dao.selectListAfterToday(projectNo);}

}
