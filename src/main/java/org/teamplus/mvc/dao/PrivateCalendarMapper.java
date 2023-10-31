package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.PrivateCalendarDTO;

import java.util.List;

@Mapper
public interface PrivateCalendarMapper {
    List<PrivateCalendarDTO> selectAll(String userNo);
    int insert(PrivateCalendarDTO privateCalendarDTO);

    int update(PrivateCalendarDTO privateCalendarDTO);
    int delete(int id);
}
