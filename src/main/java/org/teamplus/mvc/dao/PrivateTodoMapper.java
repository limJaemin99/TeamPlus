package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.PrivateTodoDTO;

import java.util.List;

@Mapper
public interface PrivateTodoMapper {

    //개인 - 할 일 리스트 시퀀스
    int getSequence();

    //개인 - 할 일 리스트 [작성]
    int write(PrivateTodoDTO dto);

    //개인 - 할 일 리스트 [출력]
    List<PrivateTodoDTO> selectList(String userNo);

    //개인 - 할 일 리스트 [삭제]
    int delete(String todoNo);

    //개인 - 할 일 리스트 [수정]
    int update(PrivateTodoDTO dto);
}
