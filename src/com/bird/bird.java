package com.bird;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
public class bird {

	   public static void main(String[] args) throws IOException {
		   
//	       StringBuilder urlBuilder = new StringBuilder("https://www.nie-ecobank.kr/ecoapi/BgtsInfoService/wms/getBirdsPointWMS"); /* URL */
//	       StringBuilder parameter  = new StringBuilder();
//	       parameter.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=48UV42UKKFW16R243UQT677L92T4CX5H11EMQ6OK");// /Service Key/
//	       parameter.append("&" + URLEncoder.encode("layers","UTF-8")    + "=" + URLEncoder.encode("    mv_map_bgts_birds_point", "UTF-8"));// / 화면에 표출할 레이어명의 나열, 값은 쉼표로 구분 /
//	       parameter.append("&" + URLEncoder.encode("transparent","UTF-8") + "=" + URLEncoder.encode("false", "UTF-8")); //    / 반환 이미지 배경의 투명 여부(true 또는 false[기본값]) / 
//	       parameter.append("&" + URLEncoder.encode("bgcolor","UTF-8")     + "=" + URLEncoder.encode("0xFFFFFF", "UTF-8")); // / 반환 이미지의 배경색(0xRRGGBB) / 
//	       parameter.append("&" + URLEncoder.encode("format","UTF-8")      + "=" + URLEncoder.encode("png", "UTF-8"));// / 반환 이미지 형식(png 또는 jpeg 또는 gif) / 
//	       parameter.append("&" + URLEncoder.encode("srs","UTF-8")         + "=" + URLEncoder.encode("EPSG:5186", "UTF-8"));// / 좌표 체계(산출물을 위한 SRS) / 
//	       parameter.append("&" + URLEncoder.encode("bbox","UTF-8")        + "=" + URLEncoder.encode("314548.9311225004,401742.29949240043,320867.0145135768,409072.0397406582", "UTF-8")); /// 크기(extent)를 정의하는 범위(bounding box) / 
//	       parameter.append("&" + URLEncoder.encode("width","UTF-8")       + "=" + URLEncoder.encode("663", "UTF-8")); //      / 반환 이미지의 너비(픽셀) / 
//	       parameter.append("&" + URLEncoder.encode("height","UTF-8")      + "=" + URLEncoder.encode("768", "UTF-8")); //      / 반환 이미지의 높이(픽셀) */
//	       URL url = new URL(urlBuilder.toString() + parameter.toString());
//	       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	       conn.setRequestMethod("GET");
////	       conn.setRequestProperty("Content-type", "application/json");
//	       System.out.println("Response code: " + conn.getResponseCode());
//	       BufferedReader rd;
//	       if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
////	           rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//	    	   saveImageToFile(conn.getInputStream(), "bird.png");
//	    	   
//	       } else {
//	           rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//	           StringBuilder sb = new StringBuilder();
//	           String line;
//	           while ((line = rd.readLine()) != null) {
//	        	   sb.append(line);
//	    	       conn.disconnect();
//	       }
//	           rd.close();
//	           System.out.println(sb.toString());
//	       }
		   int numberOfImages = 5; // 임시로 5개 지정하여 한개만 있는지 여러개 있는지 테스트
		   for (int i = 0; i < numberOfImages; i++) {
	            getImageAndSaveToFile(i + 1);  // 이미지를 가져와 파일로 저장
	        }
	   }
	   
	   public static void getImageAndSaveToFile(int imageNumber) throws IOException {
		   StringBuilder urlBuilder = new StringBuilder("https://www.nie-ecobank.kr/ecoapi/BgtsInfoService/wms/getBirdsPointWMS"); /* URL */
	       StringBuilder parameter  = new StringBuilder();
	       parameter.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=48UV42UKKFW16R243UQT677L92T4CX5H11EMQ6OK");// /Service Key/
	       parameter.append("&" + URLEncoder.encode("layers","UTF-8")    + "=" + URLEncoder.encode("mv_map_bgts_birds_point", "UTF-8"));// / 화면에 표출할 레이어명의 나열, 값은 쉼표로 구분 /
//	       parameter.append("&" + URLEncoder.encode("layers","UTF-8")    + "=" + URLEncoder.encode("	tbl_opn_tpgrph_evl", "UTF-8"));// / 화면에 표출할 레이어명의 나열, 값은 쉼표로 구분 /
	       parameter.append("&" + URLEncoder.encode("transparent","UTF-8") + "=" + URLEncoder.encode("true", "UTF-8")); //    / 반환 이미지 배경의 투명 여부(true 또는 false[기본값]) / 
	       parameter.append("&" + URLEncoder.encode("bgcolor","UTF-8")     + "=" + URLEncoder.encode("0xFFFFFF", "UTF-8")); // / 반환 이미지의 배경색(0xRRGGBB) / 
	       parameter.append("&" + URLEncoder.encode("format","UTF-8")      + "=" + URLEncoder.encode("png", "UTF-8"));// / 반환 이미지 형식(png 또는 jpeg 또는 gif) / 
	       parameter.append("&" + URLEncoder.encode("srs","UTF-8")         + "=" + URLEncoder.encode("EPSG:5186", "UTF-8"));// / 좌표 체계(산출물을 위한 SRS) / 
	       parameter.append("&" + URLEncoder.encode("bbox","UTF-8")        + "=" + URLEncoder.encode("314548.9311225004,401742.29949240043,320867.0145135768,409072.0397406582", "UTF-8")); /// 크기(extent)를 정의하는 범위(bounding box) / 
	       parameter.append("&" + URLEncoder.encode("width","UTF-8")       + "=" + URLEncoder.encode("663", "UTF-8")); //      / 반환 이미지의 너비(픽셀) / 
	       parameter.append("&" + URLEncoder.encode("height","UTF-8")      + "=" + URLEncoder.encode("768", "UTF-8")); //      / 반환 이미지의 높이(픽셀) */
	       URL url = new URL(urlBuilder.toString() + parameter.toString());
	       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	       conn.setRequestMethod("GET");
	       conn.setRequestProperty("Content-type", "application/json");
	       System.out.println("Response code: " + conn.getResponseCode());
	       
	       BufferedReader rd;
	       
	       if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//	           rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    	   saveImageToFile(conn.getInputStream(), "output" + imageNumber + ".png");
	    	   
	       } else {
	           rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	           StringBuilder sb = new StringBuilder();
	           String line;
	           while ((line = rd.readLine()) != null) {
	        	   sb.append(line);
	       }
	           rd.close();
	           System.out.println(sb.toString());
	       }
	       conn.disconnect();
	    }
	       
	       public static void saveImageToFile(InputStream inputStream, String fileName){
	           try (FileOutputStream fos = new FileOutputStream(fileName)) {
	               byte[] buffer = new byte[1024];
	               int bytesRead;
	               while ((bytesRead = inputStream.read(buffer)) != -1) {
	                   fos.write(buffer, 0, bytesRead);
	               }
	           } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	           System.out.println("Image saved to: " + fileName);
	       
	   }

}
