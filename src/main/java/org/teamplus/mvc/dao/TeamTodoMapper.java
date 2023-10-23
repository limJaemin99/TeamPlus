package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.TeamTodoDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeamTodoMapper {
    // 프로젝트 투두리스트 [시퀀스 추출]
    int getSequence();

    // 프로젝트 투두리스트 [등록]
    int newTeamTodo(TeamTodoDTO dto);

    // 프로젝트 투두리스트 [진행상태 수정]
    int setStatus(Map<String,Object> map);

    // 프로젝트 투두리스트 [내용 수정]
    int update(TeamTodoDTO dto);

    // 프로젝트 투두리스트 [진행상태 : 완료 설정]
    // *** 진행여부 수정과 비교하여 수정 필요
    int setComplete(TeamTodoDTO dto);

    // 프로젝트 투두리스트 [삭제]
    int delete(TeamTodoDTO dto);

    // 프로젝트 투두리스트 [전체 출력]
    List<TeamTodoDTO> selectList(String projectNo);

    // 프로젝트 투두리스트 [ProjectNo 와 UserNo별 조회]
    List<TeamTodoDTO> selectByUserNo(TeamTodoDTO dto);

    // 프로젝트 투두리스트 [진행상태별 출력]
    List<TeamTodoDTO> selectByStatus(TeamTodoDTO dto);

    // 프로젝트 투두리스트 [종료 예정일별 출력] : 필요시 추가
}
