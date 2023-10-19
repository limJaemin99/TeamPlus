package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.TeamDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeamMapper {

    // 팀 [리더 설정]
    int setLeader(Map<String,Object> map);

    // 팀 [팀원 삭제] : ProjectNo 과 userNo으로 삭제
    int delete(TeamDTO dto);

    // 팀 [전체 출력]
    List<TeamDTO> selectList();

    // 팀 [프로젝트별 출력]
    List<TeamDTO> selectByProjectNo(String projectNo);

    // 팀 [유저별 출력]
    List<TeamDTO> selectByUserNo(String userNo);

    //프로젝트 참가
    int join(TeamDTO dto);
}
