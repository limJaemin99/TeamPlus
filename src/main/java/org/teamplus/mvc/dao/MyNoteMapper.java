package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.MyNoteDTO;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface MyNoteMapper {

    //노트 번호 시퀀스
    int getMynoteSequence();

    //노트 글 작성
    int write(MyNoteDTO dto);

    //사용자 + 날짜 지정 노트 글 리스트 [출력]
    List<MyNoteDTO> selectList(String userno);
    
    //노트 내용 출력
    MyNoteDTO selectOne(int noteno);

    //사용자 + 날짜 지정 노트 글 [삭제]
    int delete(int noteno);

    //사용자 + 날짜 지정 노트 글 [수정]
    int update(MyNoteDTO dto);

    //노트 번호 + 패스워드
    int NoteIsExist(MyNoteDTO dto);
}
