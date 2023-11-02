package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.DataShareDTO;

import java.util.List;

@Mapper
public interface DataShareMapper {

    //파일 공유 번호 시퀀스
    int getSequence();

    //파일 공유글 작성
    int write(DataShareDTO dto);

    //파일 공유글 리스트 [출력]
    List<DataShareDTO> selectList(String projectNo);

    //파일 다운로드 count update
    int countUpdate(String dataURL);
}
