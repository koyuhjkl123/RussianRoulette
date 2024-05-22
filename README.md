# 러시아 룰렛 게임
권총 한자루로 봇과 죽음의 대결을 통해 승리를 쟁취하세요! <br>
이 게임은 실탄과 공포탄이 랜덤으로 장착된 권총을 사용하여, 체력이 0이 되기 전에 상대를 쓰러뜨리는 것을 목표로 합니다.

# 목차
* 개요
* 게임 설명
* 게임 플레이 방식


# 개요
* 프로젝트 이름 : 실탄과 공포탄 랜덤 총게임
* 프로젝트 개발 기간 : 2024.01 ~ 2024.01 (7일)
* 개발 환경
  * 언어 : Java 11
  * DB : MySQL
* 개발 인원 : 1명(개인 프로젝트)

## 프로젝트 설계

<details>
    <summary>프로젝트 일정</summary>

![image](https://github.com/koyuhjkl123/RussianRoulette/assets/94844952/a58e03f1-a049-412e-8025-d684262d21e4)

</details>
 
<details>
    <summary>인터페이스 메소드</summary>

```java
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

```

</details>
<br>

# 게임 설명
권총 한자루로 봇과 죽음의 대결을 통해 승리를 쟁취하세요! <br>
<br>

1. 실탄과 공포탄 합쳐서 1개 ~ 8개로 랜덤 장착됩니다. <br>
2. 체력은 사용자와 봇 5개 씩 부여 되며, 0이 되는 순간 승리 혹은 패배됩니다. <br>
3. 공포탄을 자신에게 발사 하면 턴이 유지하며, 실탄을 자신 혹은 상대에게 발사 할 경우 상대에게 턴이 돌아갑니다. <br>
4. 장착된 탄을 전부 소진한 경우 탄 보급이 이루어 지며, 사용자에게 턴이 돌아갑니다. <br>

# 게임 플레이 방식
* 게임 방법
  * 게임 시작 하기 전 회원 가입을 통해 이메일 인증 하신 후 게임 진행이 가능

* 회원 가입

![image](https://github.com/koyuhjkl123/RussianRoulette/assets/94844952/f9c5e944-d3f2-48b7-b2d1-c7e68af4d7c2)

* 이메일 인증

![image](https://github.com/koyuhjkl123/RussianRoulette/assets/94844952/5f0cbb92-bc59-462f-a879-1e50c38d420e)
<br>

![image](https://github.com/koyuhjkl123/RussianRoulette/assets/94844952/8a8ad9da-fe6e-43c7-8e38-d5e43e6c3a88)
<br>

* 로그인 후 게임 진행

![image](https://github.com/koyuhjkl123/RussianRoulette/assets/94844952/b26fb648-e4f7-4451-b457-56fc50b59208)
<br>

![image](https://github.com/koyuhjkl123/RussianRoulette/assets/94844952/cffe3f41-d3b9-4082-86e5-1bc99b4f3c13)


