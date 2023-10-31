package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.PrivateCalendarDTO;
import org.teamplus.mvc.dto.ProjectCalendarDTO;

import java.util.List;

@Mapper
public interface ProjectCalendarMapper {
    List<ProjectCalendarDTO> selectAll(String projectNo);
    int insert(ProjectCalendarDTO projectCalendarDTO);

    int update(ProjectCalendarDTO projectCalendarDTO);
    int delete(int id);
}
