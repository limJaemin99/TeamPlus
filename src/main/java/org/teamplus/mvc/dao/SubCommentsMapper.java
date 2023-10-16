package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.SubCommentsDTO;

import java.util.List;

@Mapper
public interface SubCommentsMapper {
    // 대댓글 [등록]
    int newSubComments(SubCommentsDTO dto);

    // 대댓글 [전체 출력]
    List<SubCommentsDTO> selectList();

    // 대댓글 [댓글별 조회]
    SubCommentsDTO selectByCommentNo(int commentNo);
}
