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
public class MailCheck {
	
	//메일 HOST
	private static final String HOST = "smtp.naver.com";
	//메일 PORT
	private static final String PORT = "587";
	//메일 ID
	private static final String MAIL_ID = "teamplus0419@naver.com";
	//메일 PW
	private static final String MAIL_PW = "iclass0419";
	
	//인증번호 생성
	private String code = "";
	
	
	//이메일 인증 보내는 메소드
	public void sendMail(String receiverMail) {
		try {
			InternetAddress[] receiverList = new InternetAddress[1];
			receiverList[0] = new InternetAddress(receiverMail);
			
			// SMTP 발송 Properties 설정
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", MailCheck.HOST);
			props.put("mail.smtp.port", MailCheck.PORT);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.ssl.trust", MailCheck.HOST);
			props.put("mail.smtp.auth", "true");
			
			// SMTP Session 생성
			Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new javax.mail.PasswordAuthentication(MailCheck.MAIL_ID, MailCheck.MAIL_PW);
				}
			});
			
			// Mail 조립
			Message mimeMessage = new MimeMessage(mailSession);
			mimeMessage.setFrom(new InternetAddress(MailCheck.MAIL_ID));
			mimeMessage.setRecipients(Message.RecipientType.TO, receiverList);
            
			// 메일 제목
			mimeMessage.setSubject("TeamPlus 인증번호");
			
			// 메일 본문
			mimeMessage.setContent("TeamPlus 인증번호 : " + code, "text/html; charset=UTF-8");
			
			// Mail 발송
			Transport.send(mimeMessage);
			
		} catch(Exception e) {
			System.out.println("메일 발송 오류!!");
		}
	}//sendNotiMail end
	
	//인증 코드 난수 생성 메소드
	public String random() {
		String code = null;
		
		StringBuffer tmp = new StringBuffer();
		Random rnd = new Random();
		
		for (int i = 0; i < 4; i++) {
			int rndIdx = rnd.nextInt(3);
			switch(rndIdx) {
			case 0:
				// a-z
				tmp.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1:
				// A-Z
				tmp.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				// 0-9
				tmp.append(rnd.nextInt(10));
				break;
			}
		}
		code = tmp.toString();
		return code;
	}//random end
}