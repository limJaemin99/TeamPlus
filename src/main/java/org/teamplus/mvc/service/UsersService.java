package org.teamplus.mvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.teamplus.mvc.dao.UsersMapper;
import org.teamplus.mvc.dto.UsersDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {

    private final UsersMapper dao;

    //유저 번호 시퀀스
    public int getSequence() {
        return dao.getSequence();
    }

    // 유저 [아이디 중복확인] : ID가 이미 존재하면 1 반환/ 아니면 0 반환
    public int isIdExist(UsersDTO dto) {
        return dao.isIdExist(dto);
    }

    // 유저 [이메일 중복확인] : email가 이미 존재하면 1 반환/ 아니면 0 반환
    public int isEmailExist(String email) {
        return dao.isEmailExist(email);
    }

    // 유저 [닉네임 중복확인] : nickName가 이미 존재하면 1 반환/ 아니면 0 반환
    public int isNickNameExist(UsersDTO dto) {
        return dao.isNickNameExist(dto);
    }

    // 유저 [회원가입]
    public int signUp(UsersDTO dto) {
        return dao.signUp(dto);
    }

    // 유저 [회원정보 수정(닉네임)]
    public int changeNickName(UsersDTO dto) {
        return dao.changeNickName(dto);
    }

    // 유저 [회원정보 수정(직업)]
    public int changeJob(UsersDTO dto) {
        return dao.changeJob(dto);
    }

    // 유저 [회원정보 수정(자기소개)]
    public int changeDescription(UsersDTO dto) {
        return dao.changeDescription(dto);
    }

    // 유저 [회원정보 수정(프로필 사진)]
    public int changeProfile(UsersDTO dto) {
        return dao.changeProfile(dto);
    }

    // 유저 [회원 탈퇴]
    public int delete(UsersDTO dto) {
        return dao.delete(dto);
    }

    // 유저 [전체회원 출력]
    public List<UsersDTO> selectList() {
        return dao.selectList();
    }

    // 유저 [사용자번호별 출력]
    public UsersDTO selectOne(String userNo) {
        return dao.selectOne(userNo);
    }

    // 유저 [name과 email로 id 찾기]
    public UsersDTO findId(UsersDTO dto) {
        return dao.findId(dto);
    }

    // 유저 [비밀번호 재설정] : id랑 email 입력해서 일치하는 유저 비밀번호 변경
    public int changePassword(UsersDTO dto) {
        return dao.changePassword(dto);
    }

    //[비밀번호 재설정용] email 로 정보 가져오기
    public UsersDTO selectByEmail(String email){ return dao.selectByEmail(email); }

    //[비밀번호 재설정용] 로그인 상태에서 비밀번호 변경
    public int loginChangePassword(UsersDTO dto){ return dao.loginChangePassword(dto); }

    // 유저 [로그인]
    public UsersDTO signin(UsersDTO dto) {
        UsersDTO user = dao.signin(dto);

        if (user != null) {
            return user; // 로그인 성공
        } else {
            return null; // 사용자를 찾을 수 없거나 비밀번호가 일치하지 않는 경우
        }
    }

    public UsersDTO snslogin(String email) { return dao.snslogin(email); }

    public void snsinsert(UsersDTO dto) {
        dao.snsinsert(dto);
    }

    public UsersDTO snsUpdate(UsersDTO dto) {
        dao.snsUpdate(dto);
        return dto;
    }

}



