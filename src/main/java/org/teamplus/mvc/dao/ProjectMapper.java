package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.ProjectDTO;

import java.util.Map;

@Mapper
public interface ProjectMapper {

    //프로젝트 번호 시퀀스
    int getSequence();

    //프로젝트 [등록]
    int newProject(ProjectDTO dto);

    //프로젝트 [수정]
    int update(ProjectDTO dto);

    //프로젝트 [삭제]
    int delete(Map<String,String> map);

    //프로젝트 정보 [출력]
    ProjectDTO selectOne(String projectNo);

    //프로젝트 로그인 (프로젝트 참여용)
    int isExist(ProjectDTO dto);
}
