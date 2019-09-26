package com.sms.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sms.dao.ClazzDao;
import com.sms.model.ClazzInfo;
import com.sms.model.Paging;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet("/ClazzManagementServlet")
public class ClazzManagementServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static ClazzDao clazzDao = new ClazzDao();

	public ClazzManagementServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");

		String method = request.getParameter("method");

		if ("toClassListView".equals(method)) {
			classListView(request, response);
		} else if ("getClassList".equals(method)) {
			getClassList(request, response);
		} else if ("addClass".equals(method)) {
			addClass(request, response);
		} else if ("deleteClass".equals(method)) {
			deleteClass(request, response);
		} else if ("editClass".equals(method)) {
			editClass(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		doGet(request, response);
	}
	
	/**
	 * 将请求转发到班级信息页面
	 *
	 * @date 2019年9月26日
	 * @方法名: classListView
	 * @param request
	 * @param response
	 */
	private void classListView(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/view/class/classList.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 获取班级列表信息
	 *
	 * @date 2019年9月26日
	 * @方法名: getClassList
	 * @param request
	 * @param response
	 */
	private void getClassList(HttpServletRequest request, HttpServletResponse response) {
		// 获取用户查询条件
		String className = request.getParameter("className");

		// 利用三目算法解决无page..属性的页面响应出错问题( page,rows: 需与classList.jsp中的变量名保持一致哟 ~ )
		int currentPage = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
		int pageSize = request.getParameter("rows") == null ? 999 : Integer.valueOf(request.getParameter("rows"));

		// 获取分页后的班级列表
		ClazzInfo clazzInfo = new ClazzInfo();
		clazzInfo.setName(className);
		List<ClazzInfo> clazzList = clazzDao.getClazzList(clazzInfo, new Paging(currentPage, pageSize));

		// 获取班级列表的条数
		int totalNum = clazzDao.getClazzListNum(clazzInfo);

		/*
		 * 将List转化为JSON数据
		 */
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("total", totalNum);// total与classList.jsp中的变量名需保持一致哟 ~
		datas.put("rows", clazzList);
		try {
			// 解决学生信息管理页面中班级名称下拉框无数据显示问题
			String fromStuInfoPage = request.getParameter("from");
			if ("combox".equals(fromStuInfoPage)) {
				response.getWriter().write(JSONArray.fromObject(clazzList).toString());
			} else {
				// 在班级信息管理页面中显示班级列表信息
				response.getWriter().write(JSONObject.fromObject(datas).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加班级
	 *
	 * @date 2019年9月26日
	 * @方法名: addClass
	 * @param request
	 * @param response
	 */
	private void addClass(HttpServletRequest request, HttpServletResponse response) {
		// 获取班级的添加信息
		String className = request.getParameter("name");
		String classIntroduce = request.getParameter("introduce");

		ClazzInfo clazzInfo = new ClazzInfo();
		clazzInfo.setName(className);
		clazzInfo.setIntroduce(classIntroduce);

		if (clazzDao.addClass(clazzInfo)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除班级
	 *
	 * @date 2019年9月26日
	 * @方法名: deleteClass
	 * @param request
	 * @param response
	 */
	private void deleteClass(HttpServletRequest request, HttpServletResponse response) {
		int classId = Integer.valueOf(request.getParameter("classid"));

		if (clazzDao.deleteClass(classId)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改班级信息
	 *
	 * @date 2019年9月26日  
	 * @方法名: editClass
	 * @param request
	 * @param response
	 */
	private void editClass(HttpServletRequest request, HttpServletResponse response) {
		// 获取修改内容
		String classId = request.getParameter("id");
		String name = request.getParameter("name");
		String introduce = request.getParameter("introduce");
		
		ClazzInfo clazzInfo = new ClazzInfo();
		clazzInfo.setId(Integer.valueOf(classId));
		clazzInfo.setName(name);
		clazzInfo.setIntroduce(introduce);
		
		if (clazzDao.editClassInfo(clazzInfo)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
