package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.TeamMapper;
import org.teamplus.mvc.dto.TeamDTO;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamMapper dao;

    // 팀 [리더 설정]
    public int setLeader(Map<String,Object> map){
        return dao.setLeader(map);
    }

    // 팀 [팀원 삭제] : ProjectNo 과 userNo으로 삭제
    public int delete(TeamDTO dto){
        return dao.delete(dto);
    }

    // 팀 [전체 출력]
    public List<TeamDTO> selectList(){
        return dao.selectList();
    }

    // 팀 [프로젝트별 출력]
    public List<TeamDTO> selectByProjectNo(String projectNo){
        return dao.selectByProjectNo(projectNo);
    }

    // 팀 [유저별 출력]
    public List<TeamDTO> selectByUserNo(String userNo){
        return dao.selectByUserNo(userNo);
    }

    //프로젝트 참가
    int join(TeamDTO dto){return dao.join(dto);}

}
