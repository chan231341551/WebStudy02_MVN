package kr.or.ddit.mvc.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InternalResourceViewResolver implements ViewResolver {
	private String prefix;
	private String suffix;
	
	
	
	public InternalResourceViewResolver() {
		this("", "");
	}

	public InternalResourceViewResolver(String prefix, String suffix) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Override
	public void resolveView(String viewName, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if(viewName.startsWith("redirect:")) {
			viewName = viewName.substring("redirect:".length());
	        resp.sendRedirect(req.getContextPath() + viewName);
	    }
		else if(viewName.startsWith("forword:")){
			viewName = viewName.substring("forword:".length());
			req.getRequestDispatcher(viewName).forward(req, resp);
	    }
		else {
			req.getRequestDispatcher(prefix + viewName + suffix).forward(req, resp);
		}
	}

}
