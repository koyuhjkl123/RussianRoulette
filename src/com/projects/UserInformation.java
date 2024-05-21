package com.projects;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserInformation extends AdminInformation implements UserAdmin {

//	 유저가 회원가입 및 로그인 관련 클래스
//	UserMerber
	UserGender gender; // 성별
	String sql = ""; // DB 등록
//	당일 날짜
	LocalDateTime now = LocalDateTime.now();
//	SQL에 문법 : LocalDateTime을 java.sql.Timestamp로 변환
	Timestamp timestamp = Timestamp.valueOf(now);
	

	@Override
	public void UserMenubar() {
//		유저에게 선택권을 준다.

		System.out.println("Jinsu의 게임에 오신것을 환영합니다.");
		System.out.println("반드시 로그인을 하셔야 게임이 가능합니다.");
		System.out.println("--------------------");
		System.out.println("1. 비회원 로그인하기  2. 회원가입  3. 로그인 4. 아이디 찾기 5. 비밀번호 찾기 6. 로그인 정보 변경");

		int number = 0;

		number = sc.nextInt();

		switch (number) {
		case 1:
			UserNonMemberInfo();
			break;
		case 2:
			UserRegister(); // 회원가입 메서드 호출
			break;
		case 3:
			UserLogin();
			break;
		case 4:
			UserIdSelect();
			break;
		case 5:
			UserPwdSelect();
			break;
		case 6:
			UserUpdateLoginInfo();
			break;
		}
	}

	@Override
	public void UserNonMemberLogin(String key, String name) {
//		비 회원 로그인하기
		System.out.println("받으신 인증 번호 입력 : ");
		String in_key = sc.next();

		if (key.equals(in_key)) {
			System.out.println(name+"님 로그인 하셨습니다.");
		} else {
			System.out.println("로그인 실패 하셨습니다.");
		}

	}

	@Override
	public void UserNonMemberInfo() {
//		비회원 로그인은 랜덤 키로 받은 값으로만 로그인이 가능하다.
		sql = "insert into UserNonMember(num, approval_key, name) values(null, ?, ?) ";

		System.out.println("비회원 로그인에 오신것을 환영합니다.");
		System.out.println("비 회원 로그인은 랜덤 키 10자리로 로그인이 가능하며,");
		System.out.println("잃어 버린신다면 로그인이 불가한다는 점을 알려드립니다.");
		System.out.println("1. 비 회원 인증번호 받기 2. 비 회원 로그인");
		int number = 0;
		number = sc.nextInt();
		if (number == 1) {
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				String is_key = generateRandomKey(); // 랜덤 키 10자리 생성
				pstmt.setString(1, is_key); // DB에 넣기
				
				System.out.println("이름을 입력하세요");
				String name = sc.next();
				pstmt.setString(2, name);

				int result_sum = pstmt.executeUpdate();

				if (result_sum > 0) {
					System.out.println(name +"님의 해당 로그인 키 : " + is_key + " 입니다.");
					System.out.println("로그인 창에 가시겠습니까? ");
					System.out.println("1. 네  2. 아니요");
					int login_m = sc.nextInt();
					if (login_m == 1) {
						System.out.println("로그인 창에 들어갑니다.");
						UserNonMemberLogin(is_key, name);
					} else if (login_m == 2) {
						System.out.println("처음으로 돌아갑니다.");
						UserMenubar();
					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (number == 2) {
			
			

		}
	}

	@Override
	public void UserRegister() {

//		회원가입
		System.out.println("------ 회원가입 창 ------");
		System.out.println("성함을 입력하세요 : ");
		setUsername(sc.next());
		while (true) {
			System.out.println("아이디 입력 : ");
			setUserid(sc.next());
			if (UserRegisterIs(getUserid())) {
				System.out.println("기존 아이디가 존재합니다 다른 아이디를 사용하세요 ");
				continue;
			} else {
				System.out.println("비밀번호 입력 : ");
				setUserpwd(sc.next()); // 비밀번호 입력
				System.out.println("비밀번호 재확인 입력 :");
				setUserpwd_is(sc.next());

				while (!(getUserpwd().equals(getUserpwd_is()))) {
					System.out.println("비밀번호와 일치하지 않습니다 다시 입력하시길 바랍니다.");
					System.out.println("비밀번호 재확인 입력 :");
					setUserpwd_is(sc.next());
				}
				System.out.println("현재 비밀번호와 일치합니다.");
				System.out.println("이메일 입력 : (예 : 이메일명@naver.com");
				setUseremail(sc.next());
				if (UserRegisteremail(getUseremail())) {
					System.out.println("기존 이메일 정보가 존재합니다 다른 이메일를 사용하세요 ");
					continue;
				}
				System.out.println("생년월일 입력하세요 : ");
				setUserDateBirth(sc.next());
				System.out.println("연락처를 입력 : (01012345678)하이픈(-)없이");
				setUserphone(sc.next());
				System.out.println("성별을 입력하세요 : (남자, 여자)");

				while (!(UserGenders(sc.next()))) {
					System.out.println("성별을 양식에 맞지 않게 입력하셨습니다. 재입력 바랍니다.");
					System.out.println("성별을 입력하세요 : (남자, 여자)");
				}
				break;
			}
		}

		sql = "insert into UserMerber(id, name, pw, email, mobile, DateBirth, Gender, reg_date, log_date, approval_status, approval_key, point) "
				+ "values(?, ?, ?, ?,?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, getUserid());
			pstmt.setString(2, getUsername());
			pstmt.setString(3, getUserpwd());
			pstmt.setString(4, getUseremail());
			pstmt.setString(5, formatPhoneNumber(getUserphone()));
			pstmt.setString(6, formatBirth(getUserDateBirth()));
			pstmt.setString(7, getUsergender());
			pstmt.setTimestamp(8, timestamp);
			pstmt.setTimestamp(9, timestamp);
			pstmt.setBoolean(10, isUserapproval_status());
			pstmt.setString(11, generateRandomKey());
			pstmt.setInt(12, getUserpoint());

			int result_sum = pstmt.executeUpdate();

			if (result_sum == 1) {
				System.out.println("회원가입이 정상적으로 완료 되었습니다.");
				System.out.println("이메일 인증처리를 하셔야 정상 활동이 가능합니다.");
				System.out.println("1. 이메일 인증하기 2. 이메일 인증 나중에 하기");
				int email_number = 0;
				email_number = sc.nextInt();

				while (true) {
					if (email_number == 1) {
						AdminEmail(getUseremail()); // 고객이 입력한 이메일명
						break;
					} else if (email_number == 2) {
						System.out.println("나중에 인증 처리는 할 수 있으나 로그인은 불가합니다.");
						break;
					} else {
						System.out.println("올바른 선택지를 선택하세요");
					}
				}

			} else {
				System.out.println("회원가입이 정상적으로 실패 되었습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void UserIdSelect() {
//		유저 아이디 찾기
//		이메일 인증
		sql = "select id from UserMerber where name = ? and email = ? ";
		String is_name = ""; // 가입된 이름
		String is_email = ""; // 가입된 이메일정보

		System.out.println("가입하신 이름을 입력하세요 : ");
		is_name = sc.next();

		System.out.println("가입하신 이메일 정보를 입력하세요 : ");
		is_email = sc.next();

		try {
//			아이디 찾기..
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, is_name);
			pstmt.setString(2, is_email);
			ResultSet result = pstmt.executeQuery();

			if (result.next()) { // 한 번 더 호출하지 않도록 수정

				// 회원가입한 가입정보가 이름과 이메일이 일치하면
				AdminEmail(is_email);
				System.out.println("이메일이 인증완료되었습니다.");

				// 현재 행의 "id" 값을 가져와서 출력
				System.out.println("찾으신 아이디 : " + result.getString("id"));

			} else {
				System.out.println("가입하신 정보와 일치하지 않습니다");

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void UserPwdSelect() {

//		유저 아이디 비밀번호 찾기
//		이메일 인증
		sql = "select pw from UserMerber where name = ? and email = ? and id = ? ";

		String sql_pw = "update UserMerber set pw = ? where name = ? and id = ?";
		String is_name = ""; // 가입된 이름
		String is_id = ""; // 가입된 아이디
		String is_email = ""; // 가입된 이메일정보

		System.out.println("가입하신 이름을 입력하세요 : ");
		is_name = sc.next();

		System.out.println("가입하신 아이디를 입력하세요 : ");
		is_id = sc.next();

		System.out.println("가입하신 이메일 정보를 입력하세요 : ");
		is_email = sc.next();

		try {
//			비밀번호찾기 찾기..
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, is_name);
			pstmt.setString(2, is_email);
			pstmt.setString(3, is_id);
			ResultSet result = pstmt.executeQuery();

			if (result.next()) { // 한 번 더 호출하지 않도록 수정

				// 회원가입한 가입정보가 이름과 이메일이 일치하면
				AdminEmail(is_email);
				System.out.println("이메일이 인증완료되었습니다.");

//			        임시 비밀번호를 부여한다.
				String password_m = generateRandomKey();

				PreparedStatement pstmt_pw = con.prepareStatement(sql_pw);
				pstmt.setString(1, password_m);
				pstmt.setString(2, is_name);
				pstmt.setString(3, is_id);

				int result_pw = pstmt_pw.executeUpdate();
				// 현재 행의 "id" 값을 가져와서 출력

				if (result_pw > 0) {
//						임시 비밀번호로 성공했을 경우
					System.out.println("임시 비밀번호 : " + password_m);
					System.out.println("원하시는 비밀번호로 반드시 변경하시길 바랍니다.");
				}

			} else {
				System.out.println("가입하신 정보와 일치하지 않습니다");

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void UserLogin() {

		System.out.println("아이디를 입력하세요 : ");
		setUserid(sc.next());
		System.out.println("비밀번호를 입력하세요 : ");
		setUserpwd(sc.next());

//		로그인 구현 메서드
		UserLoginInfo(getUserid(), getUserpwd());
//		유저 로그인

	}

	@Override
	public void UserLoginInfo(String id, String pw) {

		sql = "select id, pw, name, approval_status, email from UserMerber where id = ? and pw = ?";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			ResultSet result = pstmt.executeQuery();

			if (result.next()) {
//				로그인 상태가 true인 경우 로그인 아닐경우 인증이 필요함
				if (result.getBoolean("approval_status")) {
//					회원가입된 DB에 저장된 id, pw 등 일치 시 로그인
					if (result.getString("id").equals(id) && result.getString("pw").equals(pw)) {
						System.out.println(result.getString("name") + "님 로그인 하셨습니다.");
						UserBollet(id); // 러시안 룰렛 게임 메서드 호출
					}
				} else {
					System.out.println("이메일 인증이 안되어 로그인이 불가합니다.");
					System.out.println("로그인 인증 하겠습니까?");
					System.out.println("1. 로그인 이메일 인증  2. 나중에 하기");
					int number = 0;
					number = sc.nextInt();
					if (number == 1) {
						AdminEmail(result.getString("email"));
					} else if (number == 2) {
						System.out.println("로그인 불가합니다. 처음으로 돌아갑니다.");
						UserMenubar(); // 처음으로 돌아가기 때문에 메뉴바 메서드 호출
					}
				}
//				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void UserUpdateLoginInfo() {

		System.out.println("로그인 정보 변경 창");
		System.out.println("아이디를 입력 : ");
		setUserid(sc.next());
		System.out.println("비밀번호 변경");

		sql = "update UserMerber set pw = ? where id = ? ";

		String pwd = sc.next();
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pwd);
			pstmt.setString(2, getUserid());

			int numberss = pstmt.executeUpdate();

			if (numberss > 0) {

				System.out.println("비밀번호 변경 완료 하였습니다.");
			} else {
				System.out.println("비밀번호 변경 실패 하였습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Boolean UserRegisterIs(String userid) {

		String sql = "SELECT id FROM UserMerber WHERE id = ?";

		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);

			ResultSet result = pstmt.executeQuery();

//			DB에 중복이 있으면 true을 반환
			return result.next();
		} catch (SQLException e) {
			return false; // 중복이 없으면 반환
		}

	}

	@Override
	public Boolean UserRegisteremail(String email) {
		String sql = "SELECT id FROM UserMerber WHERE email = ?";

		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);

			ResultSet result = pstmt.executeQuery();

//			DB에 중복이 있으면 true을 반환
			return result.next();
		} catch (SQLException e) {
			return false; // 중복이 없으면 반환
		}

	}

	@Override
	public Boolean UserGenders(String gender) {

		if (gender.equalsIgnoreCase("남자")) {
			setUsergender("남자");
		} else if (gender.equalsIgnoreCase("여자")) {
			setUsergender("여자");
		}

//		equalsIgnoreCase : 문자열을 비교할 때 대소문자를 무시하고 비교하는 메서드
//		두 문자열이 서로 같은지를 확인하는데, 대소문자 구분 없이 비교
		return gender.equalsIgnoreCase("남자") || gender.equalsIgnoreCase("여자");
	}

	@Override
	public String formatPhoneNumber(String phoneNumber) {
		// 숫자 이외의 문자 모두 제거
		String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");

		// 형식에 맞게 변환 (010-1234-5678)
		if (cleanedNumber.length() == 11) {
			return cleanedNumber.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
		} else {
			// 형식에 맞지 않는 경우 그대로 반환
			return cleanedNumber;
		}
	}

	@Override
	public String formatBirth(String birth) {
		return birth.replaceFirst("(\\d{4})(\\d{2})(\\d{2})", "$1년$2월$3일");

	}

//	랜덤 10자리 숫자를 생성하고 생성된 int값을 String으로 변환 후 반환
	@Override
	public String generateRandomKey() {
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder randomKeyBuilder = new StringBuilder(10);

		for (int i = 0; i < 10; i++) {
			randomKeyBuilder.append(secureRandom.nextInt(10));
		}

		return randomKeyBuilder.toString();
	}

	@Override
	public void UserBollet(String user_id) {
//		유저가 러시안 룰렛 겜

		String sql = "select name from UserMerber where id = ?";
//		러시안 룰렛 게임!

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user_id); // 로그인을 한 유저의 정보를 가져오기 위해

			ResultSet user_info = pstmt.executeQuery();

			while (user_info.next()) { // 해당 정보를 읽어와서 이름과 등급을 유저에게 공유
				System.out.println("---------- 러시안룰렛의 게임 ----------");
				System.out.println("1. 시작하기  2. 나가기");
				int rusian_number = sc.nextInt();
				health.put(user_info.getString("name"), 5);
				
				String user_name = user_info.getString("name");
				
				while (true) {
					
					if (rusian_number == 1) {
						System.out.println("게임을 시작하겠습니다!");
						AdminBullets(tans); // 실탄, 공포탄 값 가져오기
						UserBollets(user_name); // 사용자 턴
						
						
					} else if (rusian_number == 2) {
						break;
					}
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void UserBollets(String user_name) {
//		러시안 룰렛 게임!
		while (true) {
			
			if((tans.get("실탄") == 0) && (tans.get("공포탄") == 0)) {
				AdminRussianRoulett(user_name); // 충전
			}
			System.out.println(user_name +"님의 턴입니다.");
			System.out.println("1. 상대에게 발사하기 2. 내 자신에게 발사하기");
			int user_bullet = sc.nextInt();
			String bulletType = AdminBulletType();
			if (user_bullet == 1) {
				
				if(!((tans.get("실탄") == 0) && (tans.get("공포탄") == 0))) {
	//				내가 상대방에게 발사한 경우
					if (bulletType.equals("실탄")) { // 실탄이라면..
						System.out.println("빵야!");
						health.put(bot, health.get(bot) - 1); // 상대방은 체력이 1감소
						AdminUseBullet("실탄"); // 실탄을 사용 했으니 1감소
						System.out.println("상대 체력 : " + health.get(bot));
//						int winLoss = AdminWinLoss(health.get(user_name), health.get(bot));
						if(AdminWinLoss(health.get(user_name), health.get(bot)) == 1) {
							System.out.println(user_name + "님 께서 승리하셧습니다.");
							break;
						}
						AdminRussianRoulett(user_name);
						
						continue;
					} else if (bulletType.equals("공포탄")) { // 공포탄이라면 턴은 상대에게 넘어간다.
						System.out.println("공포탄..을 맞추셨습니다.");
						AdminUseBullet("공포탄"); // 공포탄을 사용 했으니 1감소
						System.out.println("다음 턴은 봇에게 넘어갑니다.");
						AdminRussianRoulett(user_name); // 봇에게 턴
					}
				}else { // 탄이 0이라면
					AdminBullets(tans); // 탄을 공급
					UserBollets(user_name); // 총알을 공급한 후 사용자에게 턴을 제공
				}
			} else if (user_bullet == 2) {
	//	자신에게 쏘는 경우
				if (bulletType.equals("실탄")) { // 내 자신에게 쏘는데 실탄이라면 자기 체력 1감소
					System.out.println("안타깝게도 실탄입니다..");
					AdminUseBullet(bulletType);
					health.put(user_name, health.get(user_name) - 1);
					System.out.println("내 체력 : " + health.get(user_name));
					
					// 사용자가 패배시
					if(AdminWinLoss(health.get(user_name), health.get(bot)) == 2) {
						System.out.println(user_name + "님 께서 패배하셨습니다.");
						break;
					}
					AdminBotTurn(user_name); // 봇에게 턴
					break;
				} else if (bulletType.equals("공포탄")) {
					System.out.println("공포탄입니다!");
					AdminUseBullet(bulletType);
					continue;
				}
			}
			System.exit(0); // 승리 및 패배 시 종료
		}
	}
}

