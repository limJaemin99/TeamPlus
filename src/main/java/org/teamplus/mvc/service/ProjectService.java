package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.*;
import org.teamplus.mvc.dto.*;
import org.teamplus.mvc.util.PageRequestDTO;
import org.teamplus.mvc.util.PageResponseDTO;

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
    private final TeamTodoMapper teamTodoMapper;
    private final CommentsMapper commentsMapper;
    private final SubCommentsMapper subCommentsMapper;
    private final DataShareMapper dataShareMapper;
    private final PrivateCalendarMapper privateCalendarMapper;
    private final ProjectCalendarMapper projectCalendarMapper;

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
    public List<MyNoteDTO> selectList(String userNo){
        List<MyNoteDTO> list = mynoteDao.selectList(userNo);

        return list;
    }

    //노트 내용 출력
    public MyNoteDTO selectOne(int noteno){return mynoteDao.selectOne(noteno);}

    //사용자 + 날짜 지정 노트 글 [삭제]
    public int delete(int noteno){return mynoteDao.delete(noteno);}

    //사용자 + 날짜 지정 노트 글 [수정]
    public int update(MyNoteDTO dto){return mynoteDao.update(dto);}

    //페이지네이션
    public List<MyNoteDTO> UsergetPageList(PageRequestDTO pageRequestDTO){
        pageRequestDTO.setSize(10);	//한 페이지에 보이는 글의 갯수 설정
        pageRequestDTO.setDatas();	//start 와 end 계산

        return mynoteDao.getPageList(pageRequestDTO);
    }

    //[페이지네이션] 페이지 수를 계산하기 위한 메소드
    public PageResponseDTO UserlistWithSearch(PageRequestDTO pageRequestDTO){
        //페이지 목록과 글 목록을 저장하는 DTO 를 리턴타입으로 한다.
        pageRequestDTO.setSize(10);
        pageRequestDTO.setDatas();
        List<MyNoteDTO> list = mynoteDao.getPageList(pageRequestDTO);

        PageResponseDTO pageResponseDTO = PageResponseDTO.of(pageRequestDTO,mynoteDao.count(pageRequestDTO),10);
        pageResponseDTO.setNoteList(list);

        return pageResponseDTO;
    }

    //[페이지네이션] 페이지 수를 계산하기 위한 메소드
    public PageResponseDTO listWithSearchByUserNo(PageRequestDTO pageRequestDTO){
        //페이지 목록과 글 목록을 저장하는 DTO 를 리턴타입으로 한다.
        pageRequestDTO.setSize(10);
        pageRequestDTO.setDatas();
        List<MyNoteDTO> list = mynoteDao.getPageList(pageRequestDTO);

        PageResponseDTO pageResponseDTO = PageResponseDTO.of(pageRequestDTO,mynoteDao.countByUserNo(pageRequestDTO),10);
        pageResponseDTO.setNoteList(list);

        return pageResponseDTO;
    }

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

    //[팀] To-Do 리스트 출력
    public List<TeamTodoDTO> getTodoList(String projectNo){return teamTodoMapper.selectList(projectNo);}

    //프로젝트 To-Do 리스트 [제목별 출력]
    public List<TeamTodoDTO> getTodoListByTitle(TeamTodoDTO dto){return teamTodoMapper.selectListByTitle(dto);}

    //프로젝트 To-Do List [설명별 출력]
    public List<TeamTodoDTO> getTodoListByDescription(TeamTodoDTO dto){return teamTodoMapper.selectListByDescription(dto);}

    //프로젝트 To-Do List [작성자별 출력]
    public List<TeamTodoDTO> getTodoListByUserNo(TeamTodoDTO dto){return teamTodoMapper.selectListByUserNo(dto);}

    // 프로젝트 To-Do [시퀀스 출력]
    public int getTeamTodoSeq(){return teamTodoMapper.getSequence();}

    //프로젝트 To-Do [생성]
    public int newTeamTodo(TeamTodoDTO todoDTO){return teamTodoMapper.newTeamTodo(todoDTO);}


    //[닉네임] 으로 유저 검색
    public UsersDTO selectUserByNickName(String nickName){return userDao.selectByNickName(nickName);}

    //To-Do status 상태 [todoNO]로 변경
    public int updateStatus(TeamTodoDTO dto){return teamTodoMapper.updateStatus(dto);}

    //Status 기준 검색
    public List<TeamTodoDTO> getTodoListByStatus(TeamTodoDTO dto){return teamTodoMapper.selectByStatus(dto);}

    //To-Do 리스트 [마감 임박(3일)] 출력
    public List<TeamTodoDTO> getTodoListImminent(){return teamTodoMapper.selectListImminent();}

    //페이지네이션
    public List<TeamTodoDTO> getPageList(PageRequestDTO pageRequestDTO){
        pageRequestDTO.setSize(10);	//한 페이지에 보이는 글의 갯수 설정
        pageRequestDTO.setDatas();	//start 와 end 계산

        return teamTodoMapper.getPageList(pageRequestDTO);
    }

    //[페이지네이션] 페이지 수를 계산하기 위한 메소드 [일반 검색 / 리스트 출력]
    public PageResponseDTO listWithSearch(PageRequestDTO pageRequestDTO){
        //페이지 목록과 글 목록을 저장하는 DTO 를 리턴타입으로 한다.
        pageRequestDTO.setSize(10);
        pageRequestDTO.setDatas();
        List<TeamTodoDTO> list = teamTodoMapper.getPageList(pageRequestDTO);

        PageResponseDTO pageResponseDTO = PageResponseDTO.of(pageRequestDTO,teamTodoMapper.count(pageRequestDTO),10);
        pageResponseDTO.setList(list);

        return pageResponseDTO;
    }

    //페이지네이션 [Status 검색]
    public List<TeamTodoDTO> getPageListByStatus(PageRequestDTO pageRequestDTO){
        pageRequestDTO.setSize(10);	//한 페이지에 보이는 글의 갯수 설정
        pageRequestDTO.setDatas();	//start 와 end 계산

        return teamTodoMapper.getPageListByStatus(pageRequestDTO);
    }

    //[페이지네이션] 페이지 수를 계산하기 위한 메소드 [Status 검색]
    public PageResponseDTO listWithSearchByStatus(PageRequestDTO pageRequestDTO){
        //페이지 목록과 글 목록을 저장하는 DTO 를 리턴타입으로 한다.
        pageRequestDTO.setSize(10);
        pageRequestDTO.setDatas();
        List<TeamTodoDTO> list = teamTodoMapper.getPageListByStatus(pageRequestDTO);

        PageResponseDTO pageResponseDTO = PageResponseDTO.of(pageRequestDTO,teamTodoMapper.countByStatus(pageRequestDTO),10);
        pageResponseDTO.setList(list);

        return pageResponseDTO;
    }

    //[댓글] 리스트 출력
    public List<CommentsDTO> getCommentsList(String todoNo){return commentsMapper.selectList(todoNo);}

    //[대댓글] 리스트 출력
    public List<SubCommentsDTO> getSubCommentsList(int commentNo){return subCommentsMapper.selectByCommentNo(commentNo);}



    //파일 공유 번호 시퀀스
    public int DatagetSequence(){return dataShareMapper.getSequence();}

    //파일 공유글 작성
    public int Datawrite(DataShareDTO dto){return dataShareMapper.write(dto);}

    //파일 공유글 리스트 [출력]
    public List<DataShareDTO> DataselectList(String projectNo){return dataShareMapper.selectList(projectNo);}

    //파일 다운로드 count update
    public int countUpdate(String dataURL){return dataShareMapper.countUpdate(dataURL);}

    //[댓글] 작성
    public int writeComment(CommentsDTO dto){return commentsMapper.write(dto);};

    //[대댓글] 작성
    public int writeSubComment(SubCommentsDTO dto){return subCommentsMapper.write(dto);}

    // 프로젝트 투두리스트 [종료 예정일이 오늘 이후 출력]
    public List<TeamTodoDTO> selectListAfterToday(String projectNo){return teamTodoMapper.selectListAfterToday(projectNo);}

    //개인 캘린더
    public List<PrivateCalendarDTO> privateselectAll(String userNo){return privateCalendarMapper.selectAll(userNo);}
    public int privateinsert(PrivateCalendarDTO privateCalendarDTO){return privateCalendarMapper.insert(privateCalendarDTO);}
    public int privateupdate(PrivateCalendarDTO privateCalendarDTO){return privateCalendarMapper.update(privateCalendarDTO);}
    public int privatedelete(int id){return privateCalendarMapper.delete(id);}

    //프로젝트 캘린더
    public List<ProjectCalendarDTO> projectselectAll(String projectNo){return projectCalendarMapper.selectAll(projectNo);}
    public int projectinsert(ProjectCalendarDTO projectCalendarDTO){return projectCalendarMapper.insert(projectCalendarDTO);}
    public int projectupdate(ProjectCalendarDTO projectCalendarDTO){return projectCalendarMapper.update(projectCalendarDTO);}
    public int projectdelete(int id){return projectCalendarMapper.delete(id);}

    //회원 번호로 프로필 주소 가져오기
    public String getProfileURL(String userNo){return userDao.getProfileURL(userNo);}
}
