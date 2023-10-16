package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.CommentsDTO;

import java.util.List;

@Mapper
public interface CommentsMapper {

    //댓글 작성
    int write(CommentsDTO dto);

    //댓글 리스트 [출력]
    List<CommentsDTO> selectList(String todoNo);
}
