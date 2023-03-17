package kr.or.ddit.memo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.memo.controller.MemoControllerServlet;
import kr.or.ddit.mybatis.MyBatisUtils;
import kr.or.ddit.vo.MemoVO;

public class MemoDAOImpl implements MemoDAO {
	
	private SqlSessionFactory sqlSessionFactory = MyBatisUtils.getsqlSessionFactory();
	
	private static final Logger log = LoggerFactory.getLogger(MemoDAOImpl.class);
	@Override
	public List<MemoVO> selectMemoList() {
		
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			return mapperProxy.selectMemoList();
			
//			return sqlSession.selectList("kr.or.ddit.memo.dao.MemoDAO.selectMemoList");
		}
	}

	@Override
	public int insertMemo(MemoVO memo) {
		
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession(); // 트랜잭션 시작
		){
			// 오타나 오류를 잡아주는 장점이있음.
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			int rowcnt = mapperProxy.insertMemo(memo);
			
			// 오타나 오류를 잡아줄수 없음.
//			int rowcnt = sqlSession.insert("kr.or.ddit.memo.dao.MemoDAO.insertMemo",memo);
			sqlSession.commit();  // 트랜잭션 종료
			return rowcnt;
		
		 }
		
	}

	@Override
	public int updateMemo(MemoVO memo) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			int rowcnt = mapperProxy.updateMemo(memo);
			
//			int rowcnt = sqlSession.update("kr.or.ddit.memo.dao.MemoDAO.updateMemo",memo);
			sqlSession.commit();
			return rowcnt;	
		
		}
	}

	@Override
	public int deleteMemo(int code) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			int rowcnt = mapperProxy.deleteMemo(code);
			
//			int rowcnt = sqlSession.delete("kr.or.ddit.memo.dao.MemoDAO.deleteMemo",code);
			sqlSession.commit();
			return rowcnt;	
		}
	}

}
