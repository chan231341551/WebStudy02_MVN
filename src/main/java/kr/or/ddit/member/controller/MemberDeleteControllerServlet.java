package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;


@WebServlet("/member/memberDelete.do")
public class MemberDeleteControllerServlet extends HttpServlet {
	
	private static final Logger log = LoggerFactory.getLogger(MemberDeleteControllerServlet.class);
	private MemberService service = new MemberServiceImpl();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1.
		HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO)session.getAttribute("authMember");
		String memId = authMember.getMemId();
		
		String memPass = req.getParameter("memPass");
		
		MemberVO inputDate = new MemberVO();
		inputDate.setMemId(memId);
		inputDate.setMemPass(memPass);
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		Boolean valid = ValidationUtils.validate(inputDate, errors, DeleteGroup.class);
		
		String viewName = null;
		if(valid) {
			ServiceResult result = service.removeMember(inputDate);
			
			switch (result) {
			case INVALIDPASSWORD:
				session.setAttribute("message", "비번 오류");
				viewName = "redirect://mypage.do";
				break;
			case FAIL:
				session.setAttribute("message", "서버 오류");
				viewName = "redirect://mypage.do";
				break;
			default:
				session.invalidate();
				viewName = "redirect:/";
				break;
			}
		}
		else {
			session.setAttribute("message", "아이디나 비밀번호 누락");
			viewName = "redirect:/mypage.do";
		}
		
		new InternalResourceViewResolver("/WEB-INF/views/",".jsp").resolveView(viewName, req, resp);
		
		
		
		
		
		/*HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO)session.getAttribute("authMember");
		req.setAttribute("member", authMember);
		String memPass = req.getParameter("memPass");
		ServiceResult result = null;
		if(memPass.equals(authMember.getMemPass())) {
			result = service.removeMember(authMember);
		}
		else {
			result = ServiceResult.INVALIDPASSWORD;
		}
		
		String viewName = "";
		switch (result) {
			case INVALIDPASSWORD:
				req.setAttribute("message", "비밀번호 오류");
				viewName = "member/memberView";
				break;
			case PKDUPLICATED:
				req.setAttribute("message", "아이디 중복");
				viewName = "member/memberView";
				break;
			case FAIL:
				req.setAttribute("message", "서버에 문제 있음. 다시 가입하셈");
				viewName = "member/memberView";
				break;
			default:
				session.invalidate();
				viewName = "redirect:/";
					
				break;
		}
		
		new InternalResourceViewResolver("/WEB-INF/views/",".jsp").resolveView(viewName, req, resp);*/
	}
}
