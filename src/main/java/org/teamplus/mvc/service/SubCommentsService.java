package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.SubCommentsMapper;
import org.teamplus.mvc.dto.SubCommentsDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCommentsService {
    private final SubCommentsMapper dao;

    // 대댓글 [등록]
    public int newSubComments(SubCommentsDTO dto){
        return dao.newSubComments(dto);
    }

    // 대댓글 [전체 출력]
    public List<SubCommentsDTO> selectList(){
        return dao.selectList();
    }

    // 대댓글 [댓글별 조회]
    public SubCommentsDTO selectByCommentNo(int commentNo){
        return dao.selectByCommentNo(commentNo);
    }


}
