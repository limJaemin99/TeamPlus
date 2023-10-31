package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.PrivateCalendarMapper;
import org.teamplus.mvc.dao.ProjectCalendarMapper;
import org.teamplus.mvc.dto.PrivateCalendarDTO;
import org.teamplus.mvc.dto.ProjectCalendarDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectCalendarService {
    private final ProjectCalendarMapper dao;

    public List<ProjectCalendarDTO> selectAll(String projectNo){return dao.selectAll(projectNo);}
    public int insert(ProjectCalendarDTO projectCalendarDTO){return dao.insert(projectCalendarDTO);}
    public int update(ProjectCalendarDTO projectCalendarDTO){return dao.update(projectCalendarDTO);}
    public int delete(int id){return dao.delete(id);}
}
