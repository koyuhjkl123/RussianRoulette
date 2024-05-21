package com.bird;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class bird2 {
	Connection con;

	public bird2() {
	      try {
	            String driver = "com.mysql.cj.jdbc.Driver";
	            String sql_url_admin = "jdbc:mysql://localhost:3306/sqldb";
	            String user = "root";
	            String pwd = "1234";

	            Class.forName(driver);
	            con = DriverManager.getConnection(sql_url_admin, user, pwd);
	            System.out.println("로그인 성공하셧습니다.");
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("로그인 실패하셨습니다.");
	        }
	    }

	    public String getImageUrl(int imageNumber) {
	        return "https://www.nie-ecobank.kr/ecoapi/BgtsInfoService/wms/getBirdsPointWMS?image=" + imageNumber;
	    }

	    public byte[] getImageData(String imageUrl) throws IOException {
	        try (InputStream inputStream = new URL(imageUrl).openStream()) {
	            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	            int nRead;
	            byte[] data = new byte[16384];
	            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	                buffer.write(data, 0, nRead);
	            }
	            buffer.flush();
	            return buffer.toByteArray();
	        }
	    }

	    public void insertImage(Connection connection, String imageUrl) {
	    	  try {
	              byte[] imageData = getImageData(imageUrl);
	              if (imageData != null) {
	                  String insertSQL = "INSERT INTO bird(image_path) VALUES (?)";
	                  try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
	                      preparedStatement.setBytes(1, imageData);
	                      preparedStatement.executeUpdate();
	                      System.out.println("Image data inserted successfully.");
	                  }
	              } else {
	                  System.out.println("Failed to get image data from URL: " + imageUrl);
	              }
	          } catch (SQLException | IOException e) {
	              e.printStackTrace();
	          }
	      }

	    public void retrieveAndSaveImages(Connection connection) throws SQLException, IOException {
	        String selectSQL = "SELECT image_path FROM bird";
	        try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(selectSQL)) {

	            int imageNumber = 1;
	            while (resultSet.next()) {
	                byte[] imageData = resultSet.getBytes("image_path");
	                saveImageToFile(imageData, "output" + imageNumber + ".png");
	                imageNumber++;
	            }
	        }
	    }

	    public void saveImageToFile(byte[] imageData, String fileName) throws IOException {
	        try (FileOutputStream fos = new FileOutputStream(fileName)) {
	            fos.write(imageData);
	        }
	        System.out.println("Image saved to: " + fileName);
	    }

	    public static void main(String[] args) {
	    	bird2 bird = new bird2();
	    	try {
	            bird.insertImage(bird.con, bird.getImageUrl(1));
	            bird.retrieveAndSaveImages(bird.con);
	        } catch (SQLException | IOException e) {
	            e.printStackTrace();
	        }
	    }
	}