package com.sms.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.lizhou.exception.FileFormatException;
import com.lizhou.exception.NullFileException;
import com.lizhou.exception.ProtocolException;
import com.lizhou.exception.SizeException;
import com.lizhou.fileload.FileUpload;
import com.sms.dao.StudentDao;
import com.sms.dao.TeacherDao;
import com.sms.model.StudentInfo;
import com.sms.model.TeacherInfo;

@WebServlet("/PhotoServlet")
public class PhotoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static StudentDao studentDao = new StudentDao();
	private static TeacherDao teacherDao = new TeacherDao();

	public PhotoServlet() {
		super();
	}

	/**
	 * 上传用户头像( 点击上传按钮时触发 )
	 *
	 * @date 2019年9月26日
	 * @方法名: uploadPhoto
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取登录用户的类型
		int sid = request.getParameter("sid") == null ? 0 : Integer.parseInt(request.getParameter("sid"));// 学生id
		int tid = request.getParameter("tid") == null ? 0 : Integer.parseInt(request.getParameter("tid"));// 教师id

		FileUpload fileUpload = new FileUpload(request);
		fileUpload.setFileFormat("jpg");
		fileUpload.setFileFormat("png");
		fileUpload.setFileFormat("gif");
		fileUpload.setFileFormat("jpeg");
		fileUpload.setFileSize(2048);// 2M

		try {
			InputStream uploadInputStream = fileUpload.getUploadInputStream();
			if (sid != 0) {// 学生
				StudentInfo studentInfo = new StudentInfo();
				studentInfo.setId(sid);
				studentInfo.setPhoto(uploadInputStream);
				if (studentDao.setStudentPhoto(studentInfo)) {
					response.getWriter().write("<div id='message'>头像上传成功啦 ~</div>");
				} else {
					response.getWriter().write("<div id='message'>头像上传失败 !</div>");
				}
			}
			if (tid != 0) {
				TeacherInfo teacherInfo = new TeacherInfo();
				teacherInfo.setId(tid);
				teacherInfo.setPhoto(uploadInputStream);
				if (teacherDao.setTeacherPhoto(teacherInfo)) {
					response.getWriter().write("<div id='message'>头像上传成功啦 ~</div>");
				} else {
					response.getWriter().write("<div id='message'>头像上传失败 !</div>");
				}
			}
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			response.getWriter().write("<div id='message'>图片获取失败 !</div>");
		} catch (ProtocolException e) {
			// TODO: handle exception
			response.getWriter().write("<div id='message'>上传协议错误啦 !</div>");
			e.printStackTrace();
		} catch (NullFileException e) {
			// TODO: handle exception
			response.getWriter().write("<div id='message'>请选择指定的图片哟 ~</div>");
			e.printStackTrace();
		} catch (SizeException e) {
			// TODO: handle exception
			response.getWriter().write("<div id='message'>头像大小不能超过2M哟 !</div>");
			e.printStackTrace();
		} catch (FileFormatException e) {
			// TODO: handle exception
			response.getWriter()
					.write("<div id='message'>别闹! 请上传图片呀~ 如格式为: " + fileUpload.getFileFormat() + " 的文件哟 ~</div>");
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO: handle exception
			response.getWriter().write("<div id='message'>图片上传失败 !</div>");
			e.printStackTrace();
		}
	}

	private void getPhoto(HttpServletRequest request, HttpServletResponse response) {
		// 获取登录用户的类型
		int sid = request.getParameter("sid") == null ? 0 : Integer.parseInt(request.getParameter("sid"));// 学生id
		int tid = request.getParameter("tid") == null ? 0 : Integer.parseInt(request.getParameter("tid"));// 教师id

		// 根据用户类型读取头像,并将其显示到页面
		if (sid != 0) {
			StudentInfo studentInfo = new StudentInfo();
//			studentInfo = studentDao
		}
	}
}
