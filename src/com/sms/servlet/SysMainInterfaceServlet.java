package com.sms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SysMainInterfaceServlet")
public class SysMainInterfaceServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public SysMainInterfaceServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String method = request.getParameter("method");
		if ("toMainView".equals(method)) {
			toMainView(request, response);
		}
	}
	
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		doGet(request, response);
	}
	
	/**
	 * 经滤器拦截后将请求转发到系统主页面
	 *
	 * @date 2019年9月27日  
	 * @方法名: toMainView
	 * @param request
	 * @param response
	 */
	private void toMainView(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/view/system/main.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
