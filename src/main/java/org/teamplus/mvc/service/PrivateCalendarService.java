package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.PrivateCalendarMapper;
import org.teamplus.mvc.dto.PrivateCalendarDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateCalendarService {
    private final PrivateCalendarMapper dao;

    public List<PrivateCalendarDTO> selectAll(String userNo){return dao.selectAll(userNo);}
    public int insert(PrivateCalendarDTO privateCalendarDTO){return dao.insert(privateCalendarDTO);}
    public int update(PrivateCalendarDTO privateCalendarDTO){return dao.update(privateCalendarDTO);}
    public int delete(int id){return dao.delete(id);}
}
