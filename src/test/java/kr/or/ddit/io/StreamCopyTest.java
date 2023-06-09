package kr.or.ddit.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class StreamCopyTest {
	private File targetFile;
	private File destFile;
	
	@Before
	public void setUp() {
		// 1. 복사할 파일의 경로와 복사하고 싶은 경로를 변수로 지정
		targetFile = new File("D:/contents/movies/target.mp4");
		destFile = new File("d:/target.mp4");
		
		
		
	}
	
	//@Test // 2 ~ 4 초
	public void copyTest1(){
		// 2. 입출력 스트림 객체를 통해 바이트단위마다 읽고 씀
		try(
				FileInputStream fis = new FileInputStream(targetFile);
				FileOutputStream fos = new FileOutputStream(destFile);
				
				
		){ 
	        
	        
	        int readData = -1;
	        // 3. 파일의 데이터가 없을떄까지 읽어들이고  읽어들은 데이터들은 지정한 경로값이 있는 변수에 쓴다.
	        while ((readData = fis.read()) != -1) {
	        	fos.write(readData);
	        }
		}catch (IOException e) {
			// TODO: handle exception
		}
        // 4. 자원 반환
   }
	
	//@Test // 0.00001 초
	public void copyTest2(){
		// 2. 입출력 스트림 객체를 통해 바이트단위마다 읽고 씀
		try(
				FileInputStream fis = new FileInputStream(targetFile);
				FileOutputStream fos = new FileOutputStream(destFile);
				
				
		){ 
	        byte[] buffer = new byte[1024];
	        
	        int length = -1;
	        int count = 1;
	        // 3. 파일의 데이터가 없을떄까지 읽어들이고  읽어들은 데이터들은 지정한 경로값이 있는 변수에 쓴다.
	        while ((length = fis.read(buffer)) != -1) {
	        	if(count++ == 1) {
	        		Arrays.fill(buffer, (byte)0);
	        	}
	        	fos.write(buffer,0,length);
	        }
		}catch (IOException e) {
			// TODO: handle exception
		}
        // 4. 자원 반환
   }
	
	@Test // 0.00001 초
	public void copyTest3() throws IOException{
		// 2. 입출력 스트림 객체를 통해 바이트단위마다 읽고 씀
		try(
				FileInputStream fis = new FileInputStream(targetFile);
				FileOutputStream fos = new FileOutputStream(destFile);
				
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				
		){ 
	        
	        int tmp = -1;
	        // 3. 파일의 데이터가 없을떄까지 읽어들이고  읽어들은 데이터들은 지정한 경로값이 있는 변수에 쓴다.
	        while ((tmp = bis.read()) != -1) {
	        	bos.write(tmp);
	        }
		}
       
   }
}

	

