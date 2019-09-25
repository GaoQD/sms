package com.sms.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sms.model.Paging;
import com.sms.model.StudentInfo;
import com.sms.util.DBUtil;
import com.sms.util.StringUtil;

public class StudentDao extends BaseDao{

	/**
	 * 获取用户信息
	 * @param name
	 * @param password
	 * @return
	 */
	public StudentInfo getUserInfo(String name,String password) {
		String sql = "select id,classID,sno,name,password,sex,email,mobile,photo from user_student where name='" + name + "'  and password='" + password + "'";
		
		try (ResultSet rs = query(sql)){
			if (rs.next()) {
				StudentInfo studentInfo = new StudentInfo();
				studentInfo.setId(rs.getInt("id"));
				studentInfo.setClassID(rs.getInt("classID"));
				studentInfo.setSno(rs.getString("sno"));
				studentInfo.setName(rs.getString("name"));
				studentInfo.setPassword(rs.getString("password"));
				studentInfo.setSex(rs.getString("sex"));
				studentInfo.setEmail(rs.getString("email"));
				studentInfo.setMobile(rs.getString("mobile"));
				studentInfo.setPhoto(rs.getBinaryStream("photo"));

				return studentInfo;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加学生信息
	 * @param studentInfo
	 * @return
	 */
	public boolean addStudent(StudentInfo studentInfo) {
		String sql = "";
		if (!StringUtil.isEmpty(studentInfo.toString())) {
			sql = "insert into user_student(classID,sno,name,password,sex,email,mobile) values('"
					+ studentInfo.getClassID() + "','" + studentInfo.getSno() + "','" + studentInfo.getName() + "' , '"
					+ studentInfo.getPassword() + "' , '" + studentInfo.getSex() + "' , '" + studentInfo.getEmail()
					+ "' , '" + studentInfo.getMobile() + "')";
		}
		return update(sql);
	}
	
	/**
	 * 获取学生列表
	 * @param studentInfo
	 * @param paging
	 * @return
	 */
	public List<StudentInfo> getStudentList(StudentInfo studentInfo,Paging paging) {
		List<StudentInfo> list = new ArrayList<StudentInfo>();
		String sql = "select * from user_student ";

		if (!StringUtil.isEmpty(studentInfo.getName())) {
			sql += " and name like '%" + studentInfo.getName() + "%' ";
		}
		if (studentInfo.getClassID() != 0) {
			sql += " and classID = " + studentInfo.getClassID();
		}
		if (studentInfo.getId() != 0) {
			sql += " and id = " + studentInfo.getId();// 约束用户权限
		}
		sql += " limit " + paging.getPageStart() + "," + paging.getPageSize();// 分页查询

		try (ResultSet resultSet = query(sql.replaceFirst("and", "where"))) {
			while (resultSet.next()) {
				StudentInfo studentInfo2 = new StudentInfo();
				studentInfo2.setClassID(resultSet.getInt("classID"));
				studentInfo2.setId(resultSet.getInt("id"));
				studentInfo2.setSno(resultSet.getString("sno"));
				studentInfo2.setName(resultSet.getString("name"));
				studentInfo2.setSex(resultSet.getString("sex"));
				studentInfo2.setEmail(resultSet.getString("email"));
				studentInfo2.setMobile(resultSet.getString("mobile"));
				list.add(studentInfo2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取学生列表的条数
	 * @param studentInfo
	 * @return
	 */
	public int getStudentListNum(StudentInfo studentInfo) {
		int num = 0;
		String sql = "select count(*) as num from user_student ";
		
		if (!StringUtil.isEmpty(studentInfo.getName())) {
			sql += "and name like '%" + studentInfo.getName() + "%' ";
		}
		
		if (studentInfo.getClassID() != 0) {
			sql += "and classID = " + studentInfo.getClassID();
		}
		
		if (studentInfo.getId() != 0) {
			sql += " and id = " + studentInfo.getId();// 约束用户权限
		}

		try (ResultSet rs = query(sql.replaceFirst("and", "where"))){
			while(rs.next()) {
				num = rs.getInt("num");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 更新学生信息
	 * @param studentInfo
	 * @return
	 */
	public boolean editStudentInfo(StudentInfo studentInfo) {

		// 为防止异常:Data truncation: Truncated incorrect DOUBLE value,使用 ' , ' 代替 ' and ' !
		String sql = "update user_student set name='" + studentInfo.getName() + "' ,  sex='" + studentInfo.getSex()
				+ "' ,  email='" + studentInfo.getEmail() + "' ,  mobile='" + studentInfo.getMobile() + "' ,  classID='"
				+ studentInfo.getClassID() + "' where id= " + studentInfo.getId();
		return update(sql);
	}
	
	/**
	 * 删除学生
	 * @param ids
	 * @return
	 */
	public boolean deleteStudent(String ids) {
		String sql = "delete from user_student where id in (" + ids + " )";
		return update(sql);
	}
	
	/**
	 * 获取学生信息
	 * @param id
	 * @return
	 */
	public StudentInfo getStudentInfoById(int id) {
		String sql = "select * from user_student where id=" + id;
		StudentInfo studentInfo = null;
		
		try (ResultSet rs = query(sql)){
			if (rs.next()) {
				studentInfo = new StudentInfo();
				studentInfo.setId(rs.getInt("id"));
				studentInfo.setClassID(rs.getInt("classID"));
				studentInfo.setSno(rs.getString("sno"));
				studentInfo.setName(rs.getString("name"));
				studentInfo.setPassword(rs.getString("password"));
				studentInfo.setSex(rs.getString("sex"));
				studentInfo.setEmail(rs.getString("email"));
				studentInfo.setMobile(rs.getString("mobile"));
				studentInfo.setPhoto(rs.getBinaryStream("photo"));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return studentInfo;
	}
	
	public boolean setStudentPhoto(StudentInfo studentInfo) {
		Connection conn = DBUtil.getConnection();
		String sql = "update user_student set photo=? where id = ?";
		
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
			preparedStatement.setBinaryStream(1, studentInfo.getPhoto());
			preparedStatement.setInt(2, studentInfo.getId());
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return update(sql);
	}
	
	public boolean modifyPassword(StudentInfo studentInfo,String newPassword) {
		String sql = "update user_student set password = '" + newPassword + "' where id = '" + studentInfo.getId() + "'";
		return update(sql);
	}
}
