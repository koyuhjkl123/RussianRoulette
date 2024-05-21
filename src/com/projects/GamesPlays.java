package com.projects;

import java.sql.DriverManager;

public class GamesPlays extends UserInformation {
	
	public GamesPlays() {
		
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String sql_url_admin = "jdbc:mysql://localhost:3306/sqldb";
			
			setAdminid("root");
			setAdminpwd("1234");
			
			Class.forName(driver);
			con = DriverManager.getConnection(sql_url_admin, getAdminid(), getAdminpwd());
			System.out.println("로그인 성공하셧습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("로그인 실패하셨습니다.");
		}
		
	}
	
	
	public static void main(String[] args) {
		
		GamesPlays g = new GamesPlays();
//		g.UserRegister(); // 회원가입
//		g.UserIdSelect();
		g.UserMenubar();
		
	}

}
