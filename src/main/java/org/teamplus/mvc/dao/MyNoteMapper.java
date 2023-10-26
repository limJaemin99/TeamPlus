package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.teamplus.mvc.dto.MyNoteDTO;
import org.teamplus.mvc.dto.TeamTodoDTO;
import org.teamplus.mvc.util.PageRequestDTO;

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

    //페이지네이션
    List<MyNoteDTO> getPageList(PageRequestDTO pageRequestDTO);

    //[페이지네이션] 페이지 수를 계산하기 위한 메소드 - 검색 조건에 맞는 글 갯수
    int count(PageRequestDTO pageRequestDTO);


    //[페이지네이션] 페이지 수를 계산하기 위한 메소드 - 검색 조건에 맞는 글 갯수 (userNo)
    int countByUserNo(PageRequestDTO pageRequestDTO);
}
