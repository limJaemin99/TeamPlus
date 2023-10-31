package org.teamplus.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.teamplus.mvc.dto.UsersDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsersMapper {
    //유저 번호 시퀀스
    int getSequence();

    // 유저 [아이디 중복확인] : ID가 이미 존재하면 1 반환/ 아니면 0 반환
    int isIdExist(UsersDTO dto);

    // 유저 [이메일 중복확인] : email가 이미 존재하면 1 반환/ 아니면 0 반환
    int isEmailExist(String email);

    // 유저 [닉네임 중복확인] : nickName가 이미 존재하면 1 반환/ 아니면 0 반환
    int isNickNameExist(UsersDTO dto);

    // 유저 [회원가입]
    int signUp(UsersDTO dto);

    // 유저 [회원정보 수정(닉네임)]
    int changeNickName(UsersDTO dto);

    // 유저 [회원정보 수정(직업)]
    int changeJob(UsersDTO dto);

    // 유저 [회원정보 수정(자기소개)]
    int changeDescription(UsersDTO dto);

    // 유저 [회원정보 수정(프로필 사진)]
    int changeProfile(UsersDTO dto);

    // 유저 [회원 탈퇴]
    int delete(UsersDTO dto);

    // 유저 [전체회원 출력]
    List<UsersDTO> selectList();

    // 유저 [사용자번호별 출력]
    UsersDTO selectOne(String userNO);

    // 유저 [name과 email로 id 찾기]
    UsersDTO findId(UsersDTO dto);

    // 유저 [비밀번호 재설정] : id랑 email 입력해서 일치하는 유저 비밀번호 변경
    int changePassword(UsersDTO dto);

    // 유저 [비밀번호 재설정] : 로그인 상태에서 새로 입력한 비밀번호로 변경
    int loginChangePassword(UsersDTO dto);

    UsersDTO signin(UsersDTO dto);

    //[비밀번호 재설정용] email 로 정보 가져오기
    UsersDTO selectByEmail(String email);

    //[닉네임] 으로 유저 검색
    UsersDTO selectByNickName(String nickName);

    //유저 번호로 [프로필사진] 가져오기
    String getProfileURL(String userNo);

    // 유저 [sns 로그인]
    UsersDTO snslogin(String email);

    // 유저 [sns 회원가입]
    void snsinsert(UsersDTO dto);

    // 유저 [sns회원가입한 유저 업데이트]
    void snsUpdate(UsersDTO dto);
}
