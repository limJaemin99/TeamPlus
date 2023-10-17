package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.MyNoteMapper;
import org.teamplus.mvc.dto.MyNoteDTO;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MyNoteService {

    private final MyNoteMapper dao;

    //노트 번호 시퀀스
    public int getSequence(){return dao.getSequence();}

    //노트 글 작성
    public int write(MyNoteDTO dto){return dao.write(dto);}

    //사용자 + 날짜 지정 노트 글 리스트 [출력]
    public List<MyNoteDTO> selectList(Map<String,Object> map){return dao.selectList(map);}

    //사용자 + 날짜 지정 노트 글 [삭제]
    public int delete(String noteNo){return dao.delete(noteNo);}

    //사용자 + 날짜 지정 노트 글 [수정]
    public int update(MyNoteDTO dto){return dao.update(dto);}
}
