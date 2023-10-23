package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.PrivateTodoMapper;
import org.teamplus.mvc.dto.MyNoteDTO;
import org.teamplus.mvc.dto.PrivateTodoDTO;

import javax.management.monitor.StringMonitor;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrivateTodoService {

    private final PrivateTodoMapper dao;

    //개인 - 할 일 리스트 시퀀스
    public int getSequence(){return dao.getSequence();}

    //개인 - 할 일 리스트 [작성]
    public int write(PrivateTodoDTO dto){return dao.write(dto);}

    //개인 - 할 일 리스트 [출력]
    public List<PrivateTodoDTO> selectList(Map<String,Object> map){return dao.selectList(map);}

    //노트 내용 출력
    public PrivateTodoDTO selectOne(String todoNo){return dao.selectOne(todoNo);}

    //개인 - 할 일 리스트 [삭제]
    public int delete(String todoNo){return dao.delete(todoNo);}

    //개인 - 할 일 리스트 [수정]
    public int update(PrivateTodoDTO dto){return dao.update(dto);}




}
