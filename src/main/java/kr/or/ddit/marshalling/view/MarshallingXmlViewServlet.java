package kr.or.ddit.marshalling.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@WebServlet("/xmlView.do")
public class MarshallingXmlViewServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		 // native(DataStore.properties)->json: marshalling
	    ObjectMapper mapper = new XmlMapper();
		Enumeration<String> names = req.getAttributeNames();
		Map<String, Object> target = new HashMap<>();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			Object value = req.getAttribute(name);
			target.put(name,value);
		}
		//Object target = req.getAttribute("target");
		//1.Marshalling
		//	      String json = mapper.writeValueAsString(target);
		resp.setContentType("application/xml;charset=UTF-8");
		try(
				PrintWriter out = resp.getWriter();
				){
			//2.serialization
			//	         out.print(json);
			mapper.writeValue(out, target); //마샬링+직렬화
	      }
		
	}
	
}
