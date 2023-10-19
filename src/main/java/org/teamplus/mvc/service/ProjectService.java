package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.ProjectMapper;
import org.teamplus.mvc.dao.TeamMapper;
import org.teamplus.mvc.dao.UsersMapper;
import org.teamplus.mvc.dto.ProjectDTO;
import org.teamplus.mvc.dto.TeamDTO;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectMapper projectDao;
    private final TeamMapper teamDao;
    private final UsersMapper userDao;

    //프로젝트 번호 시퀀스
    public int getSequence(){return projectDao.getSequence();}

    //프로젝트 [등록]
    public int newProject(ProjectDTO dto){return projectDao.newProject(dto);}

    //프로젝트 [수정]
    public int update(ProjectDTO dto){return projectDao.update(dto);}

    //프로젝트 [삭제]
    public int delete(Map<String,String> map){return projectDao.delete(map);}

    //프로젝트 정보 [출력]
    public ProjectDTO selectOne(String projectNo){return projectDao.selectOne(projectNo);}

    //프로젝트 로그인 (프로젝트 참여용)
    public int isExist(ProjectDTO dto){return projectDao.isExist(dto);}

    //프로젝트 참가
    public int join(TeamDTO dto){return teamDao.join(dto);}

    //[사용자별] 팀 리스트 출력
    public List<TeamDTO> teamListByUserNo(String userNo){return teamDao.selectByUserNo(userNo);}

    //[프로젝트별] 팀 리스트 출력
    public List<TeamDTO> teamListByProjectNo(String projectNo){return teamDao.selectByProjectNo(projectNo);}

    //유저 [사용자 번호별 출력]
    public String nameByUserNo(String userNo){return userDao.selectOne(userNo).getNickName();}
}
