package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.CommentsMapper;
import org.teamplus.mvc.dto.CommentsDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsMapper dao;

    //댓글 작성
    public int write(CommentsDTO dto){return dao.write(dto);}

    //댓글 리스트 [출력]
    public List<CommentsDTO> selectList(String todoNo){return dao.selectList(todoNo);}
}
