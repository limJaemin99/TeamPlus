package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.*;
import org.teamplus.mvc.dto.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectMapper projectDao;
    private final TeamMapper teamDao;
    private final UsersMapper userDao;
    private final MyNoteMapper mynoteDao;
    private final PrivateTodoMapper privateTodoMapper;

    //프로젝트 번호 시퀀스
    public int getSequence(){return projectDao.getSequence();}

    //프로젝트 [등록]
    public int newProject(ProjectDTO dto){return projectDao.newProject(dto);}

    //프로젝트 [수정]
    public int update(ProjectDTO dto){return projectDao.update(dto);}

    //프로젝트 [삭제]
    public int delete(Map<String,String> map){return projectDao.delete(map);}

    //프로젝트 정보 [출력]
    public ProjectDTO selectOne(String projectNo){return projectDao.selectOne(projectNo);}

    //프로젝트 로그인 (프로젝트 참여용)
    public int isExist(ProjectDTO dto){return projectDao.isExist(dto);}

    //프로젝트 참가
    public int join(TeamDTO dto){return teamDao.join(dto);}

    //[사용자별] 팀 리스트 출력
    public List<TeamDTO> teamListByUserNo(String userNo){return teamDao.selectByUserNo(userNo);}

    //[프로젝트별] 팀 리스트 출력
    public List<TeamDTO> teamListByProjectNo(String projectNo){return teamDao.selectByProjectNo(projectNo);}

    //유저 [사용자 번호별 출력]
    public String nameByUserNo(String userNo){return userDao.selectOne(userNo).getNickName();}

    //노트 번호 시퀀스
    public int getMynoteSequence(){return mynoteDao.getMynoteSequence();}

    //노트 글 작성
    public int write(MyNoteDTO dto){return mynoteDao.write(dto);}

    //사용자 + 날짜 지정 노트 글 리스트 [출력]
    public List<MyNoteDTO> selectList(String userno){return mynoteDao.selectList(userno);}

    //노트 내용 출력
    public MyNoteDTO selectOne(int noteno){return mynoteDao.selectOne(noteno);}

    //사용자 + 날짜 지정 노트 글 [삭제]
    public int delete(int noteno){return mynoteDao.delete(noteno);}

    //사용자 + 날짜 지정 노트 글 [수정]
    public int update(MyNoteDTO dto){return mynoteDao.update(dto);}

    //회원 번호로 회원 정보 가져오기
    public UsersDTO selectUserByUserNo(String userNo){return userDao.selectOne(userNo);}

    //개인 - 할 일 리스트 시퀀스
    public int getPrivateSequence(){return privateTodoMapper.getSequence();}

    //개인 - 할 일 리스트 [작성]
    public int write(PrivateTodoDTO dto){return privateTodoMapper.write(dto);}

    //개인 - 할 일 리스트 [출력]
    public List<PrivateTodoDTO> selectPrivateList(Map<String,Object> map){return privateTodoMapper.selectList(map);}

    //개인 - 할 일 리스트 [출력]
    public PrivateTodoDTO selectPrivateOne(String todoNo){return privateTodoMapper.selectOne(todoNo);}

    //개인 - 할 일 리스트 [삭제]
    public int delete(String todoNo){return privateTodoMapper.delete(todoNo);}

    //개인 - 할 일 리스트 [수정]
    public int update(PrivateTodoDTO dto){return privateTodoMapper.update(dto);}

    //status 카운트
    public int statusCount(Map<String, Object> map){return privateTodoMapper.statusCount(map);}



}
