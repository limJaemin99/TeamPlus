package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.TeamTodoDTO;
import org.teamplus.mvc.util.PageRequestDTO;

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

    //프로젝트 To-Do List [제목별 출력]
    List<TeamTodoDTO> selectListByTitle(TeamTodoDTO dto);

    //프로젝트 To-Do List [설명별 출력]
    List<TeamTodoDTO> selectListByDescription(TeamTodoDTO dto);

    //프로젝트 To-Do List [작성자별 출력]
    List<TeamTodoDTO> selectListByUserNo(TeamTodoDTO dto);

    //To-Do status 상태 [todoNO]로 변경
    int updateStatus(TeamTodoDTO dto);

    //To-Do 리스트 [마감 임박(3일)] 출력
    List<TeamTodoDTO> selectListImminent();

    //페이지네이션
    List<TeamTodoDTO> getPageList(PageRequestDTO pageRequestDTO);

    //[페이지네이션] 페이지 수를 계산하기 위한 메소드 - 검색 조건에 맞는 글 갯수
    int count(PageRequestDTO pageRequestDTO);

    //페이지네이션 [Status 검색]
    List<TeamTodoDTO> getPageListByStatus(PageRequestDTO pageRequestDTO);

    //[페이지네이션] 페이지 수를 계산하기 위한 메소드 - 검색 조건에 맞는 글 갯수 [Status 검색용]
    int countByStatus(PageRequestDTO pageRequestDTO);

    // 프로젝트 투두리스트 [종료 예정일이 오늘 이후 출력]
    List<TeamTodoDTO> selectListAfterToday(String projectNo);

}
