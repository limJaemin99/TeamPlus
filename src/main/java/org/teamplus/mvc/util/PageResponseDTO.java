package org.teamplus.mvc.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamplus.mvc.dto.MyNoteDTO;
import org.teamplus.mvc.dto.TeamTodoDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO {

    //페이지 목록의 범위를 계산하기
    //필요한 값
    private int totalPage;
    private int totalCount;

    private int startPage;
    private int endPage;
    private List<TeamTodoDTO> list;   //서비스에서 처리할 때 PageResponseDTO에 글 목록을 포함시키면 리턴타입 정하기가 간단하고
                                        //View 에 전달도 한번에 가능하다.

    private List<MyNoteDTO> noteList;

    public static PageResponseDTO of(PageRequestDTO dto , int totalCount , int pageSize){
        int totalPage = (int) Math.ceil((double) totalCount/dto.getSize());  //ceil 은 올림입니다.
        //현재 페이지에 대한 페이지 목록 시작값 계산 - ex) 현재 페이지가 1~10 일 경우 startPage = 1 , 11~20 일 경우 startPage = 11
        int startPage = (dto.getPage()-1)/pageSize*pageSize+1;  //페이지 번호 리스트 pageSize 만큼. pageSize 가 10 일 경우
        int endPage = Math.min(startPage+9, totalPage);

        //계산된 값으로 객체 생성하여 리턴
        return PageResponseDTO.builder()
                .totalPage(totalPage)
                .totalCount(totalCount)
                .startPage(startPage)
                .endPage(endPage)
                .build();
    }

}
