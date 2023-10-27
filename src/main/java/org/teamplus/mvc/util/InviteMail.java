package org.teamplus.mvc.util;


import lombok.Setter;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

@Setter
public class InviteMail {
	
	//메일 HOST
	private static final String HOST = "smtp.naver.com";
	//메일 PORT
	private static final String PORT = "587";
	//메일 ID
	private static final String MAIL_ID = "teamplus0419@naver.com";
	//메일 PW
	private static final String MAIL_PW = "iclass0419";
	
	//인증번호 생성
	private String caller = "";
	private String projectNo = "";
	private String password = "";

	//이메일 인증 보내는 메소드
	public int sendMail(String receiverMail) {
		try {
			InternetAddress[] receiverList = new InternetAddress[1];
			receiverList[0] = new InternetAddress(receiverMail);
			
			// SMTP 발송 Properties 설정
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", InviteMail.HOST);
			props.put("mail.smtp.port", InviteMail.PORT);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.ssl.trust", InviteMail.HOST);
			props.put("mail.smtp.auth", "true");
			
			// SMTP Session 생성
			Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new javax.mail.PasswordAuthentication(InviteMail.MAIL_ID, InviteMail.MAIL_PW);
				}
			});
			
			// Mail 조립
			Message mimeMessage = new MimeMessage(mailSession);
			mimeMessage.setFrom(new InternetAddress(InviteMail.MAIL_ID));
			mimeMessage.setRecipients(Message.RecipientType.TO, receiverList);
            
			// 메일 제목
			mimeMessage.setSubject("Team Plus에 당신을 초대합니다!");

			// 메일 본문	▶ TODO 로그인/회원가입 경로는 서버 환경에 따라 다를 수 있음
			mimeMessage.setContent(String.format("안녕하세요. Team Plus 입니다.<br>[%s] 님께서 당신을 초대하셨습니다.<br>로그인 후 프로젝트에 참여해주세요. <a href='http://localhost:8086/users/signin'> ☞ 로그인하러 가기<br></a>계정이 없으신가요? <a href='http://localhost:8086/users/signup'> ☞ 회원가입<br></a>감사합니다.<br><br>▶ 초대 코드 : %s<br>▶ 비밀번호 : %s",caller,projectNo,password), "text/html; charset=UTF-8");
			
			// Mail 발송
			Transport.send(mimeMessage);

			return 1;
		} catch(Exception e) {
			System.out.println("메일 발송 오류!!");
			return 0;
		}
	}//sendNotiMail end
}