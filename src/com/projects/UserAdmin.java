package com.projects;

import java.util.Map;

public interface UserAdmin {
	
//	유저 사용 메뉴바
	default public void UserMenubar() {};
//	비회원 로그인
	default public void UserNonMemberLogin(String key, String name) {};
//	비회원 로그인 Info
	default public void UserNonMemberInfo() {};
//  비회원 로그인 체크
	
//	유저 회원가입
	default public void UserRegister() {}; // 완료
//	유저 로그인
	default public void UserLogin() {};
//	유저 아이디 찾기
	default public void UserIdSelect() {};
//	유저 비밀번호 찾기
	default public void UserPwdSelect() {};
//	로그인 정보 변경 
	default public void UserUpdateLoginInfo() {};
//	유저 로그인 표시 구현
	default public void UserLoginInfo(String id, String pw) {};
//	유저 회원가입 시 중복 검사
	default public Boolean UserRegisterIs(String userid) {return false;};
//	유저 회원가입 시 이메일 중복 검사
	default public Boolean UserRegisteremail(String email) {return false;};
//	유저 남자, 여자 확인
	default public Boolean UserGenders(String gender) {return false;};
//	랜덤 10자리 숫자를 생성
	default public String generateRandomKey() {return "";};
//	연락처 하이픈(-) 양식에 맞게 설정
	default public String formatPhoneNumber(String phoneNumber) {return "";};
//	생년월일 양식 맞게 설정
	default public String formatBirth(String birth) {return "";};
//	유저가 게임을 시작하는 메서드
	default public void UserBollet(String user_id) {};
//	유저가 사용하는 턴
	default public void UserBollets(String user_name) {};
	
//	관리자 

//	이메일
	default public void AdminEmail(String email) {};
//	관리자 메일로 사용자에게 인증메일 발송
	default public void SendEmail(String user_email, String email_title, String email_body, String admin_email,
			String admin_email_pwd, String email_server_host, String email_server_port) {};
	// 사용자에게 이메일 발송 시 인증코드 6자리 생성하는 메서드 
	default public String GenerateVerificationCode() {return "";};
//	유저가 로그인 후 로그인 상태를 true 변환
	default public void UserApprovalStatus() {};
	
//  관리자 러시안 룰렛
	default public void AdminRussianRoulett(String user_name) {};
//	총알(실탄, 공포탄) 생성
	default public void AdminBullets(Map<String, Integer> buttles) {};
//	총알 실탄, 공포탄 반환
	default public String AdminBulletType() {return "";};
//	실탄, 공포탄 반환되는 문자열에 따라 발포 메서드
	default public void AdminUseBullet(String bullet) {};
//  봇이 턴 제어
	default public void AdminBotTurn(String user_name) {};
//	봇이 공격하는 제어 메서드
	default public void AdminBotAttack(String user, String bot) {};
//	사용자 및 봇 승패
	default public int AdminWinLoss(int user_health,int bot_health) {return 0;};
	
	
}
