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
    public int getSequence(){return dao.getMynoteSequence();}

    //노트 글 작성
    public int write(MyNoteDTO dto){return dao.write(dto);}

    //사용자 + 날짜 지정 노트 글 리스트 [출력]
    public List<MyNoteDTO> selectList(String userno){return dao.selectList(userno);}

    //노트 내용 출력
    public MyNoteDTO selectOne(int noteno){return dao.selectOne(noteno);}

    //사용자 + 날짜 지정 노트 글 [삭제]
    public int delete(int noteno){return dao.delete(noteno);}

    //사용자 + 날짜 지정 노트 글 [수정]
    public int update(MyNoteDTO dto){return dao.update(dto);}
}
