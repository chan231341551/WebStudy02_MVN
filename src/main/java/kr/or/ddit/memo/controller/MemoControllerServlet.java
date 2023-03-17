package kr.or.ddit.memo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import kr.or.ddit.memo.dao.DataBaseMemoDAOImpl;
import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.memo.dao.MemoDAOImpl;
import kr.or.ddit.vo.MemoVO;
import oracle.net.ns.DataDescriptorPacket;

@WebServlet("/memo")
public class MemoControllerServlet extends HttpServlet {
	
	private static final Logger log = LoggerFactory.getLogger(MemoControllerServlet.class);
	
//	private MemoDAO dao = DataBaseMemoDAOImpl.getInstance();
//	private MemoDAO dao = new DataBaseMemoDAOImpl();
	private MemoDAO dao = new MemoDAOImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//	1. 요청 분석
		String accept = req.getHeader("Accept");
		log.info("accept header : {}",accept);
		if(accept.contains("xml")) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}
//	2. 모델 확보
		List<MemoVO> memoList = dao.selectMemoList();
//	3. 모델 공유
		req.setAttribute("memoList", memoList);
		
//	4. 뷰 선택 
		String path = "";
		if(accept.contains("json")) {
			path = "/jsonView.do";
		}
		
//	5. 뷰 이동
		req.getRequestDispatcher(path).forward(req, resp);
		
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Post-Redirect-Get : PRG pattern
		req.setCharacterEncoding("UTF-8");
	
//		2. 모델 확보
		MemoVO memo = getMemoFromRequest(req);
		int cnt = dao.insertMemo(memo);
		
//		3. 뷰 선택 , 뷰 이동 
		resp.sendRedirect(req.getContextPath()+"/memo");
	
	
	}
	
	private MemoVO getMemoFromRequest(HttpServletRequest req) throws IOException {
//		1. 요청 분석
		MemoVO memo = null;
		String contentType = req.getContentType();
		if(contentType.contains("json")) {
			try(
				BufferedReader br = req.getReader();	// 역직렬화 , body content read용 입력 스트림	
			){
				memo = new ObjectMapper().readValue(br, MemoVO.class);
				System.out.println(memo);
				return memo;
			}	
		}
		else if(contentType.contains("xml")) {
			try(
				BufferedReader br = req.getReader();	// 역직렬화 , body content read용 입력 스트림	
			){
				memo = new XmlMapper().readValue(br, MemoVO.class);
				return memo;
			}	
		}
		else {
			String writer = req.getParameter("writer");
			String date = req.getParameter("date");
			String content = req.getParameter("content");
			memo = new MemoVO();
			memo.setContent(content);
			memo.setWriter(writer);
			memo.setDate(date);
		}
		return memo;
		
//		MemoVO vo = new MemoVO();
		
		/*try(
			BufferedReader br =  req.getReader();	// 역직렬화
		){
			String tmp = null;
			StringBuffer strJson = new StringBuffer();
			while((tmp = br.readLine()) != null) {
				strJson.append(tmp);
			}
			ObjectMapper mapper = new ObjectMapper(); //언마샬링
			vo = mapper.readValue(strJson.toString(), MemoVO.class);
		}catch (IOException e) {
		}
		return vo;*/
		
		
		
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	
	
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	
	
	}
}
