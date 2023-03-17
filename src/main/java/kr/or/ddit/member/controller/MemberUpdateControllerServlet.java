package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.memo.controller.MemoControllerServlet;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberUpdate.do")
public class MemberUpdateControllerServlet extends HttpServlet {
	private MemberService service = new MemberServiceImpl();
	
	private static final Logger log = LoggerFactory.getLogger(MemberUpdateControllerServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO)session.getAttribute("authMember");
		
		MemberVO member = service.retrieveMember(authMember.getMemId());
		
		req.setAttribute("member", member);
		
		String viewName = "member/memberForm";
		new InternalResourceViewResolver("/WEB-INF/views/",".jsp").resolveView(viewName, req, resp);
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		log.info("update post 왔다");
		
		req.setCharacterEncoding("UTF-8");
		
		MemberVO vo = new MemberVO();
		req.setAttribute("member", vo);
		
//		vo.setMemId(req.getParameter("memId"));
		
		Map<String, String[]> parameterMap = req.getParameterMap();
		try {
			BeanUtils.populate(vo, parameterMap); // map 데이터일떄
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		}
		
		
		String viewName = "";
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		Boolean valid = ValidationUtils.validate(vo, errors, UpdateGroup.class);
		
		if(valid) {
			ServiceResult result = service.modifyMember(vo);
			
			switch (result) {
			case INVALIDPASSWORD:
				req.setAttribute("message", "비밀번호 오류");
				viewName = "member/memberForm";
				break;
				
			case FAIL:
				req.setAttribute("message", "서버에 문제 있음. 다시 ");
				viewName = "member/memberForm";
				break;
				
			default:
				viewName = "redirect:/mypage.do";
				break;
			}
		}
		else {
			viewName = "member/memberForm";
			
		}
		
		new InternalResourceViewResolver("/WEB-INF/views/",".jsp").resolveView(viewName, req, resp);
	
	}
	
	
}
