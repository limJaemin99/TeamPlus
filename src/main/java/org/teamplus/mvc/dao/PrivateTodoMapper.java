package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.MyNoteDTO;
import org.teamplus.mvc.dto.PrivateTodoDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface PrivateTodoMapper {

    //개인 - 할 일 리스트 시퀀스
    int getSequence();

    //개인 - 할 일 리스트 [작성]
    int write(PrivateTodoDTO dto);

    //개인 - 할 일 리스트 [출력]
    List<PrivateTodoDTO> selectList(Map<String,Object> map);

    //개인 - 할 일 리스트 [출력]
    PrivateTodoDTO selectOne(String todoNo);

    //개인 - 할 일 리스트 [삭제]
    int delete(String todoNo);

    //개인 - 할 일 리스트 [수정]
    int update(PrivateTodoDTO dto);

    //sataus 카운트
    int statusCount(Map<String,Object> map);


}
