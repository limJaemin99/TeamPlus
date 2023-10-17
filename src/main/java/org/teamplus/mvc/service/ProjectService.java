package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.ProjectMapper;
import org.teamplus.mvc.dto.ProjectDTO;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectMapper dao;

    //프로젝트 번호 시퀀스
    public int getSequence(){return dao.getSequence();}

    //프로젝트 [등록]
    public int newProject(ProjectDTO dto){return dao.newProject(dto);}

    //프로젝트 [수정]
    public int update(ProjectDTO dto){return dao.update(dto);}

    //프로젝트 [삭제]
    public int delete(Map<String,String> map){return dao.delete(map);}

    //프로젝트 정보 [출력]
    public ProjectDTO selectOne(Map<String,String> map){return dao.selectOne(map);}

}
