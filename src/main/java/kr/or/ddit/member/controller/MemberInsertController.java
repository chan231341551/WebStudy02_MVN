package kr.or.ddit.member.controller;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.memo.controller.MemoControllerServlet;
import kr.or.ddit.mvc.AbstractController;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

/**
 * Backend controller(command hannler) --> Plain Old Java Object
 */
public class MemberInsertController implements AbstractController {
	
	private static final Logger log = LoggerFactory.getLogger(MemberInsertController.class);
	private MemberService service = new MemberServiceImpl(); // 의존관계 코드
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getMethod();
		RequestMethod requestMethod = RequestMethod.valueOf(method.toUpperCase()); 
		String viewName = null;
		if(requestMethod==RequestMethod.GET) {
			viewName = memberFrom(req, resp);
		}
		else if(requestMethod==RequestMethod.POST) {
			viewName = insert(req, resp);
		}
		else {
			resp.sendError(405,method+"는 지원하지 않음.");
		}
		return viewName;
	}
	
	public String memberFrom(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		return "member/memberForm";
	
	}
	
	public String insert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		log.info("post 온거 확인");
//		1.검증 포함
		
//		command object -> 검증 대상
		MemberVO vo = new MemberVO();
		req.setAttribute("member", vo);
		
//		vo.setMemId(req.getParameter("memId"));
		
		Map<String, String[]> parameterMap = req.getParameterMap();
		try {
			BeanUtils.populate(vo, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		}
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		Boolean valid = ValidationUtils.validate(vo, errors, InsertGroup.class);
		log.info("valid : {}",valid);
		
		String viewName = "";
		if(valid) {
			ServiceResult result = service.createMember(vo);
			
			switch (result) {
			case PKDUPLICATED:
				req.setAttribute("message", "아이디 중복");
				viewName = "member/memberForm";
				break;
			case FAIL:
				req.setAttribute("message", "서버에 문제 있음. 다시 가입하셈");
				viewName = "member/memberForm";
				break;
			default:
				viewName = "redirect:/";
				break;
			}
		}
		else {
			viewName = "member/memberForm";
		}
		return viewName;
	}
}
