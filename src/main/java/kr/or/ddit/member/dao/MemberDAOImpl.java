package kr.or.ddit.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.memo.controller.MemoControllerServlet;
import kr.or.ddit.mybatis.MyBatisUtils;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;

public class MemberDAOImpl implements MemberDAO {
	private SqlSessionFactory sqlSessionFactory = MyBatisUtils.getsqlSessionFactory();
	private static final Logger log = LoggerFactory.getLogger(MemoControllerServlet.class);

	@Override
	public int insertMember(MemberVO member) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				log.info("mapperProxy : {}",mapperProxy);
				int rowcnt = mapperProxy.insertMember(member);
				sqlSession.commit();
				return rowcnt;
				
			}
	}
	
	@Override
	public int selectTotalRecord(PagingVO<MemberVO> pagingVO) { // 페이징처리할떄 밑에 메소드랑 한세트
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				return mapperProxy.selectTotalRecord(pagingVO);
				
			}
	}
	@Override
	public List<MemberVO> selectMemberList(PagingVO<MemberVO> pagingVO) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				log.info("mapperProxy : {}",mapperProxy);
				return mapperProxy.selectMemberList(pagingVO);
				
			}
	
	}

	@Override
	public MemberVO selectMember(String memId) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				return mapperProxy.selectMember(memId);
				
			}
		
	}

	@Override
	public int updateMember(MemberVO member) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				log.info("mapperProxy : {}",mapperProxy);
				int rowcnt = mapperProxy.updateMember(member);
				sqlSession.commit();
				return rowcnt;
				
			}
	}

	@Override
	public int deleteMember(String memId) {
		try(
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){
				MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
				log.info("mapperProxy : {}",mapperProxy);
				int rowcnt = mapperProxy.deleteMember(memId);
				sqlSession.commit();
				return rowcnt;
				
			}
	}

}