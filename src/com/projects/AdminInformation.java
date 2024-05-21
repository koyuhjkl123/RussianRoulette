package com.projects;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AdminInformation extends User implements UserAdmin {

//	총알 실탄, 공포탄 랜덤 생성
	Map<String, Integer> tans = new HashMap<String, Integer>();

//	player1, 유저 각 기본 체력 5를 부여한다.
	Map<String, Integer> health = new HashMap<String, Integer>();
	

	String user_name = "";
	String bot = "Bot_Jinsu";

	AdminInformation() {
		health.put(bot, 5);
	}
	
	

	@Override
	public void AdminEmail(String email) {

		System.out.println("입력하신 해당 메일에 인증번호를 전송합니다.");
		String user_email = email; // 사용자 이메일

		String email_title = "이메일 인증"; // 사용자가 받을 이메일 제목
//			        6자리 숫자가 랜덤으로 발송됨
		String user_email_body = "인증 코드: " + GenerateVerificationCode();

		String is_key = user_email_body.substring(7); // 해당 발송된 인증 메일
		System.out.println("생성된 인증 코드: " + is_key);
		// 발신 이메일 계정 설정
		String admin_email = "koyu12315@gmail.com"; // 관리자 이메일 정보
		String admin_email_pwd = "qoli ivvo sasc ofae"; // 관리자 이메일 비밀번호

		// SMTP 서버 설정
		String email_server_host = "smtp.gmail.com";
		String email_server_port = "587";

		// 이메일 전송
		SendEmail(user_email, email_title, user_email_body, admin_email, admin_email_pwd, email_server_host,
				email_server_port);

		System.out.println("해당 메일로 받으신 인증번호를 입력하세요 : ");
		String user_is_key = sc.next();

		if (user_is_key.equals(is_key)) {
			String sql = "select approval_status from UserMerber where id = ?";

			try {
				PreparedStatement pstmt_status = con.prepareStatement(sql);
				pstmt_status.setString(1, getUserid());
				ResultSet result_status = pstmt_status.executeQuery();
				if (result_status.next()) {
					boolean userApprovalStatus = result_status.getBoolean("approval_status");
					if (userApprovalStatus) {
						System.out.println("고객님의 계정은 이미 인증이 완료되었습니다.");
					} else {
						UserApprovalStatus(); // 인증 절차
					}
				} else {
					System.out.println("사용자 정보를 찾을 수 없습니다.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("고객님의 인증메일이 실패하였습니다.");
		}
	}

	@Override
	public void UserApprovalStatus() {
//		인증 상태가 false라면 인증는 필수.. 아닐 경우 정상
		String sql = "update UserMerber set approval_status = ? where id = ? ";

		PreparedStatement pstmt_select;
		try {
			setUserapproval_status(true);
			pstmt_select = con.prepareStatement(sql);
			pstmt_select.setBoolean(1, isUserapproval_status());
			pstmt_select.setString(2, getUserid());

			int results = pstmt_select.executeUpdate();

			if (results > 0) {
				System.out.println("정상 인증이 완료되었습니다.");
			} else {
				System.out.println("인증이 실패 하였습니다.");
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void SendEmail(String user_email, String email_title, String email_body, String admin_email,
			String admin_email_pwd, String email_server_host, String email_server_port) {

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true"); // SMTP 인증을 사용할지 여부를 나타냅니다. "true"로 설정하면 SMTP 서버에 대한 인증
//		SMTP 인증은 이메일을 보내려는 사용자가 자신의 계정 정보로 서버에 로그인하여 승인되어야만 메일을 발송할 수 있도록 하는 보안 기능
		properties.put("mail.smtp.starttls.enable", "true"); // 사용하여 암호화된 통신을 활성화할지 여부를 나타냅니다. "true"로 설정하면 암호화된 통신이 활성화
//		이메일 클라이언트는 SMTP 서버와의 통신 시에 TLS를 사용하여 데이터를 암호화합니다. T
//		LS를 사용하면 데이터가 전송 중에 안전하게 보호되므로 중요한 정보가 포함된 이메일이 누출되거나 조작되지 않도록 보안성을 향상
		properties.put("mail.smtp.host", email_server_host);
		properties.put("mail.smtp.port", email_server_port);
//	        qoli ivvo sasc ofae // 2차 앱 비밀번호

//	       session 객체 생성: 이메일 전송에 필요한 세션을 설정
//	        Authenticator를 이용한 인증: Authenticator 클래스를 상속받아 발신자 계정의 아이디와 비밀번호를 제공하여 이메일 서버에 인증
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
//	           해당 클래스의 메소드는 아이디와 비밀번호를 저장하는데 사용되고 이메일 발송 시 이 정보를 사용하여 
//	            이메일 서버에 로그인하고 발신자 이메일 주소와 관련된 계정으로 인증을 수행
			protected PasswordAuthentication getPasswordAuthentication() {
//				JavaMail API를 사용하여 이메일을 보낼 때, 발신자 이메일 주소와 관련된 계정으로 SMTP 서버에 로그인하여 발신자의 신원을 확인하고 이메일을 보낼 수 있도록 하는 역할
				return new PasswordAuthentication(admin_email, admin_email_pwd);
			}
		});
		try {
//	        	MimeMessage : 메세지의 구조와 내용을 설정
//	        	수신자, 제목, 본문 설정: 수신자 주소, 이메일 제목, 본문 내용을 설정
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(admin_email)); // 관리자 이메일 정보
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user_email)); // 사용자 이메일
			message.setSubject(email_title); // 이메일 제목
			message.setText(email_body); // 이메일 본문

			Transport.send(message); // 설정된 메세지를 전송함
			System.out.println("이메일이 성공적으로 전송되었습니다.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String GenerateVerificationCode() {

//		SecureRandom : 클래스는 자바에서 암호학적으로 안전한 난수를 생성하기 위한 클래스
//		다양한 알고리즘을 사용하여 난수를 생성할 수 있으며, 예측 불가능하고 안전한 특성을 갖도록 구현
//		보안 관련한 인증코드 발송 시 해당 클래스를 사용하는 것이 좋다
//		Random 클래스와는 달리 보다 안전한 난수를 생성
//		일반적인 Random 클래스는 알고리즘이 예측 가능하고, 암호학적으로 안전하지 않은 난수를 생성할 수 있기 때문에 보안적인 요구사항이 있는 상황에서는 사용 x
		SecureRandom random = new SecureRandom();
//		10자리의 인증번호를 생성하기 위해 10개의 스트링빌더를 생성한다.
		StringBuilder randomKey = new StringBuilder(6);

		for (int i = 0; i < 6; i++) {
//			0 ~ 9까지 랜덤으로 6번 반복해서 randomKeyBuilder에 넣는다.
			randomKey.append(random.nextInt(10));
		}

		return randomKey.toString();
	}

	@Override
	public void AdminRussianRoulett(String user_name) {
		
		// 실탄과 공포탄이 떨어질 경우 재 충전
		if (tans.get("실탄") == 0 && tans.get("공포탄") == 0) {
			AdminBullets(tans); // 실탄, 공포탄 재충전
		} else if(health.get(user_name) == 0){
			System.out.println(user_name + "님께서 패배하셨습니다.");
		}
		else {
			AdminBotTurn(user_name); // 봇 턴
		}
	}

//				

	@Override
	public void AdminBotTurn(String user_name) {
		System.out.println("봇의 턴입니다.");

		String bulletType = AdminBulletType();
		// 봇의 행동 처리 (랜덤으로 선택)
//		 	1. 공격 처리  2. 봇이 자신을 공격
		int bot_bullet = 1 + (int) (Math.random() * 2);

		if(!(tans.get("실탄") == 0 && tans.get("공포탄") == 0)) {
			if (bot_bullet == 1) {
//				봇이 사용자에게 공격을 한 경우
				AdminBotAttack(user_name, bot);
			} else if (bot_bullet == 2) {
//			    	봇 자신이 총을 쏠 경우
				if (bulletType.equals("실탄") && tans.get("실탄") > 0) {
					System.out.println("봇이 스스로 실탄을 쐈네요!ㅋㅋ");
					health.put(bot, health.get(bot) - 1);
					System.out.println(health);
					System.out.println("봇 체력 : " + health.get(bot));
					AdminUseBullet(bulletType);
					if(AdminWinLoss(health.get(user_name), health.get(bot)) == 1) {
						System.out.println(user_name + "님 께서 승리하셧습니다.");
					}
					UserBollets(user_name);
				} else if (bulletType.equals("공포탄") && tans.get("공포탄") > 0) {
					System.out.println("봇이 공포탄을 사용했습니다. 턴을 넘깁니다.");
					AdminUseBullet(bulletType);
					AdminBotTurn(user_name); // 공포탄을 사용한 경우 재 실행
				}else {
					AdminBullets(tans); // 봇 턴에서 총알이 없으면 공급
				}

			}
		}
	}
	@Override
	public void AdminBotAttack(String user_name, String bot) {
		String bulletType = AdminBulletType();

		if (bulletType.equals("실탄") && tans.get("실탄") > 0) {
			
			System.out.println(bot + "가 " + user_name + "에게 공격 합니다.");
			health.put(user_name, health.get(user_name) - 1); // 사용자 체력 1감소
			AdminUseBullet(bulletType);
			System.out.println(user_name + "는 공격에 맞았습니다! " + user_name + " 체력: " + health.get(user_name));
			
			// 봇이 승리 한 경우
			if(AdminWinLoss(health.get(user_name), health.get(bot)) == 2) {
				System.out.println(user_name+"님 께서 패배하셨습니다.");
			}
			UserBollets(user_name);
		} else if (bulletType.equals("공포탄") && tans.get("공포탄") > 0) {
			System.out.println(bot + "가 " + user_name + "에게 공격을 시도합니다!");
			System.out.println(bot + "가 " + user_name + "에게 공포탄을 사용합니다. 턴을 넘깁니다.");
			AdminUseBullet(bulletType);
			UserBollets(user_name);
		}
	}

	@Override
	public void AdminBullets(Map<String, Integer> bb) {
//		실탄, 공포탄 <= 8

		int bullet = 0; // 실탄
		int blank = 0; // 공포탄

		Random bullet_random = new Random(); //
		Random blank_random = new Random(); //

		while (true) {
			bullet = bullet_random.nextInt(10) + 1;
			blank = blank_random.nextInt(10) + 1;

			if ((bullet + blank) <= 8) {
				bb.put("실탄", bullet); 
				bb.put("공포탄", blank);
				System.out.print("실탄 : " + tans.get("실탄") + "\t");
				System.out.println("공포탄 : " + tans.get("공포탄"));
				break;
			} else {
				continue; // 10 이상이라면 재 수정
			}
		}
	}

	@Override
	public String AdminBulletType() {
//		실탄, 공포탄 각 랜덤으로 뽑은 수량에 따라 확률 실탄 및 공포탄 String타입으로 제공
		Random random = new Random();
		double totalbollet = tans.get("실탄") + tans.get("공포탄");
		double bolletprobabilly = tans.get("실탄") / totalbollet;

		double randomValue = random.nextDouble();
		return randomValue < bolletprobabilly ? "실탄" : "공포탄";
	}

	@Override
	public void AdminUseBullet(String bullet) {
//		실탄 및 공포탄 사용 시 감소 시켜주는 메서드
		
		if (bullet.equals("실탄") && tans.get("실탄") > 0) {
			tans.put("실탄", tans.get("실탄") - 1); // 실탄이 발사되면 1감소

		} else if (bullet.equals("공포탄") && tans.get("공포탄") > 0) {
			tans.put("공포탄", tans.get("공포탄") - 1);
		}
		
		System.out.println("감소 시키는 메소드에서 실행 : " +tans.get("실탄")+":"+tans.get("공포탄"));

	}
	
	
	@Override
	public int AdminWinLoss(int user_health, int bot_health) {
		
		// 사용자와 봇의 승리 여부 확인
		// 0 : 승, 패 정해지지 않았음을 정의
		// 1 : 사용자 승리
		// 2 : 봇이 승리
		if(bot_health == 0){
			return 1; // 사용자가 승리 시 1
		}else if(user_health == 0) {
			return 2; // 사용자가 패배 시 2
		}
		return 0;
	}

}
