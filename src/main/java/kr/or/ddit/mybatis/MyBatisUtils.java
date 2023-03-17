package kr.or.ddit.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtils {
	private static SqlSessionFactory sqlSessionFactory;
	static{
		
		String configPath = "kr/or/ddit/mybatis/mybatis-config.xml";
		try(
			Reader reader = Resources.getResourceAsReader(configPath);
		
		){
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static SqlSessionFactory getsqlSessionFactory() {
		return sqlSessionFactory;
	}
}
