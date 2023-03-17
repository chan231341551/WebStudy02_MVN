package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.AbstractController;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.vo.MemberVO;


public class MemberViewController implements AbstractController {
	
	private MemberService service = new MemberServiceImpl();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String who = req.getParameter("who");
		System.out.println("who : " + who );
		
		if(StringUtils.isBlank(who)) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
//		2.
		MemberVO member = service.retrieveMember(who);
//		3.
		req.setAttribute("member", member);
		System.out.println("member : "+member);
//		4.
		String viewName = "member/memberView";
		
		
		return viewName;
	}
}
