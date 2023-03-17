package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.commons.IndexController;
import kr.or.ddit.login.controller.LoginProcessController;
import kr.or.ddit.login.controller.LogoutController;
import kr.or.ddit.member.controller.MemberInsertController;
import kr.or.ddit.member.controller.MemberListController;
import kr.or.ddit.member.controller.MemberViewController;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.mvc.view.ViewResolver;
import kr.or.ddit.prod.controller.ProdInsertController;
import kr.or.ddit.prod.controller.ProdListController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DispatcherServlet extends HttpServlet {
	private ViewResolver viewResolver;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		viewResolver = new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		req.setCharacterEncoding("UTF-8");
		
//		String requestURI = req.getRequestURI();
//		requestURI = requestURI.substring(req.getContextPath().length());
		String requestURI = req.getServletPath();
		log.info("requestURI : {}",requestURI);
		AbstractController cotroller = null;
		if("/member/memberList.do".equals(requestURI)) {
			cotroller = new MemberListController();
		}
		else if("/prod/prodList.do".equals(requestURI)) {
			cotroller = new ProdListController();
		}
		else if("/member/memberView.do".equals(requestURI)) {
			cotroller = new MemberViewController();
		}
		else if("/index.do".equals(requestURI)) {
			cotroller = new IndexController();
		}
		else if("/login/memberInsert.do".equals(requestURI)) {
			cotroller = new MemberInsertController();
		}
		else if("/login/loginProcess.do".equals(requestURI)) {
			cotroller = new LoginProcessController();
		}
		else if("/login/logout.do".equals(requestURI)) {
			cotroller = new LogoutController();
		}
		else if("/prod/prodInsert.do".equals(requestURI)) {
			cotroller = new ProdInsertController();
		}
		
		
		if(cotroller == null) {
			resp.sendError(404,requestURI+"는 처리할 수 없는 자원임");
			return;
		}
		
		String viewName = cotroller.process(req, resp);
		if(viewName == null) {
			if(!resp.isCommitted()) {
				resp.sendError(500,"논리적인 뷰 네임은 NULL일수 없음.");
			}
			
		}
		else {
			viewResolver.resolveView(viewName, req, resp);			
		}
		
		
	}
}
