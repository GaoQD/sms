package com.sms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sms.dao.AdminDao;
import com.sms.dao.StudentDao;
import com.sms.dao.TeacherDao;
import com.sms.model.AdminInfo;
import com.sms.model.StudentInfo;
import com.sms.model.TeacherInfo;
import com.sms.util.StringUtil;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static AdminDao adminDao = new AdminDao();
	private static TeacherDao teacherDao = new TeacherDao();
	private static StudentDao studentDao = new StudentDao();

	public LoginServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		
		String method = request.getParameter("method");
		
		if ("login".equals(method)) {
			login(request, response);
		} else if ("loginOut".equals(method)) {
			loginOut(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		doGet(request, response);
	}
	
	/**
	 * 验证用户登录表单信息
	 *
	 * @date 2019年9月26日
	 * @方法名: login
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取用户登录类型
		int userType = Integer.valueOf(request.getParameter("userType"));
		// 获取用户提交的登录表单信息
		String name = request.getParameter("userName");
		String password = request.getParameter("userPassword");
		// 获取验证码信息
		String verifiCode = request.getParameter("verificationCode");
		String verificationCode = request.getSession().getAttribute("verifiCode").toString();

		if (StringUtil.isEmpty(verifiCode) || !verifiCode.equalsIgnoreCase(verificationCode)) {
			response.getWriter().write("vcodeError");
			return;
		}

		switch (userType) {
		case 1: {// 管理员
			AdminInfo adminInfo = new AdminInfo();
			adminInfo = adminDao.getUserInfo(name, password);
			if (adminInfo == null) {
				response.getWriter().write("loginError");
				return;
			}
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", adminInfo);
			session.setAttribute("userType", userType);
			response.getWriter().write("loginSuccess");
			break;
		}
		case 2: {// 学生
			StudentInfo studentInfo = new StudentInfo();
			studentInfo = studentDao.getUserInfo(name, password);
			if (studentInfo == null) {
				response.getWriter().print("loginError");
				return;
			}
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", studentInfo);
			session.setAttribute("userType", userType);
			response.getWriter().print("loginSuccess");
			break;
		}
		case 3: {// 教师
			TeacherInfo teacherInfo = new TeacherInfo();
			teacherInfo = teacherDao.getUserInfo(name, password);
			if (teacherInfo == null) {
				response.getWriter().print("loginError");
			}
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", teacherInfo);
			session.setAttribute("userType", userType);
			response.getWriter().print("loginSuccess");
			break;
		}
		}
	}
	
	/**
	 * 注销用户信息
	 *
	 * @date 2019年9月26日  
	 * @方法名: loginOut
	 * @param request
	 * @param response
	 */
	private void loginOut(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().removeAttribute("userInfo");
		request.getSession().removeAttribute("userType");
		
		try {
			response.sendRedirect("index.jsp");
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
