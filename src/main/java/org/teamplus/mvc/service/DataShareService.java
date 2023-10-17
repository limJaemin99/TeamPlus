package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.DataShareMapper;
import org.teamplus.mvc.dto.DataShareDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataShareService {

    private final DataShareMapper dao;

    //파일 공유 번호 시퀀스
    public int getSequence(){return dao.getSequence();}

    //파일 공유글 작성
    public int write(DataShareDTO dto){return dao.write(dto);}

    //파일 공유글 리스트 [출력]
    public List<DataShareDTO> selectList(String projectNo){return dao.selectList(projectNo);}
}
