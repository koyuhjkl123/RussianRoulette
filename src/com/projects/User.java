package com.projects;

public class User extends Admin{
	
//	사용자(유저)의 get, setter의 용도

//	유저의 회원가입
	private String username; // 이름
	private String userid; // 아이디
	private String userpwd; // 비밀번호
	private String userpwd_is; // 비밀번호 확인
	private String useremail; // 이메일
	private String userDateBirth; // 생년월일
	private String userphone; // 연락처
	private String usergender; // 성별
	private int userpoint; // 사용자 등급 포인트
	private boolean userapproval_status = false;
	
	
	enum UserGender {// 성별
	    MALE("남자"), FEMALE("여자");

		private final String gender;

	    private UserGender(String gender) {
	        this.gender = gender;
	    }

	    public String getGender() {
	        return gender;
	    }
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getUserpwd_is() {
		return userpwd_is;
	}
	public void setUserpwd_is(String userpwd_is) {
		this.userpwd_is = userpwd_is;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getUserDateBirth() {
		return userDateBirth;
	}
	public void setUserDateBirth(String userDateBirth) {
		this.userDateBirth = userDateBirth;
	}
	public String getUserphone() {
		return userphone;
	}
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	} 
	public String getUsergender() {
		return usergender;
	}
	public void setUsergender(String usergender) {
		this.usergender = usergender;
	}
	public boolean isUserapproval_status() {
		return userapproval_status;
	}
	public void setUserapproval_status(boolean userapproval_status) {
		this.userapproval_status = userapproval_status;
	}
	public int getUserpoint() {
		return userpoint;
	}
	public void setUserpoint(int userpoint) {
		this.userpoint = userpoint;
	}
	
	
//	User 회원 로그인
	// User 테이블
	
//	create table UserMerber(
//			  id varchar(20) primary key,
//			  name varchar(10) not null,
//			  pw varchar(20) not null, 
//			  email varchar(40) unique,
//			  mobile varchar(40) not null,
//			  DateBirth varchar(50) not null,
//			  Gender varchar(10) not null,
//			  reg_date datetime not null,
//			  log_date datetime not null,
//			  approval_status varchar(10) not null,
//			  approval_key varchar(10),
//            point int not null
//			);
//          
//create table UserNonMember(num int auto_increment primary key, approval_key varchar(15) not null);

}
