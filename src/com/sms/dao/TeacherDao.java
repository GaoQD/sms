package com.sms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sms.model.Paging;
import com.sms.model.TeacherInfo;
import com.sms.util.DBUtil;
import com.sms.util.StringUtil;

public class TeacherDao extends BaseDao {

	/**
	 * 获取用户信息
	 *
	 * @date 2019年9月26日
	 * @方法名: getUserInfo
	 * @param name
	 * @param password
	 * @return
	 */
	public TeacherInfo getUserInfo(String name, String password) {
		String sql = "select id,classID,tno,name,password,sex,email,mobile,photo from user_teacher where name='" + name
				+ "'  and password='" + password + "'";

		try (ResultSet rs = query(sql)) {
			if (rs.next()) {
				TeacherInfo teacherInfo = new TeacherInfo();
				teacherInfo.setId(rs.getInt("id"));
				teacherInfo.setClassID(rs.getInt("classID"));
				teacherInfo.setTno(rs.getString("tno"));
				teacherInfo.setName(rs.getString("name"));
				teacherInfo.setPassword(rs.getString("password"));
				teacherInfo.setSex(rs.getString("sex"));
				teacherInfo.setEmail(rs.getString("email"));
				teacherInfo.setMobile(rs.getString("mobile"));
				teacherInfo.setPhoto(rs.getBinaryStream("photo"));

				return teacherInfo;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加老师
	 *
	 * @date 2019年9月26日
	 * @方法名: addTeacher
	 * @param teacherInfo
	 * @return
	 */
	public boolean addTeacher(TeacherInfo teacherInfo) {
		String sql = null;
		if (!StringUtil.isEmpty(teacherInfo.toString())) {
			sql = "insert into user_teacher(classID,tno,name,password,sex,email,mobile) values('"
					+ teacherInfo.getClassID() + "','" + teacherInfo.getTno() + "','" + teacherInfo.getName() + "' , '"
					+ teacherInfo.getPassword() + "' , '" + teacherInfo.getSex() + "' , '" + teacherInfo.getEmail()
					+ "' , '" + teacherInfo.getMobile() + "')";
		}
		return update(sql);
	}

	/**
	 * 获取教师列表
	 *
	 * @date 2019年9月26日  
	 * @方法名: getTeacherList
	 * @param teacherInfo
	 * @param paging
	 * @return
	 */
	public List<TeacherInfo> getTeacherList(TeacherInfo teacherInfo, Paging paging) {
		List<TeacherInfo> list = new ArrayList<TeacherInfo>();
		String sql = "select * from user_teacher ";

		if (!StringUtil.isEmpty(teacherInfo.getName())) {
			sql += " and name like '%" + teacherInfo.getName() + "%' ";
		}
		if (teacherInfo.getClassID() != 0) {
			sql += " and classID = " + teacherInfo.getClassID();
		}
		if (teacherInfo.getId() != 0) {
			sql += " and id = " + teacherInfo.getId();// 约束用户权限
		}
		sql += " limit " + paging.getPageStart() + "," + paging.getPageSize();// 分页查询

		try (ResultSet resultSet = query(sql.replaceFirst("and", "where"))) {
			while (resultSet.next()) {
				TeacherInfo teacherInfo2 = new TeacherInfo();
				teacherInfo2.setClassID(resultSet.getInt("classID"));
				teacherInfo2.setId(resultSet.getInt("id"));
				teacherInfo2.setTno(resultSet.getString("tno"));
				teacherInfo2.setName(resultSet.getString("name"));
				teacherInfo2.setSex(resultSet.getString("sex"));
				teacherInfo2.setEmail(resultSet.getString("email"));
				teacherInfo2.setMobile(resultSet.getString("mobile"));
				list.add(teacherInfo2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取教师列表的条数
	 *
	 * @date 2019年9月27日  
	 * @方法名: getTeacherListNum
	 * @param teacherInfo
	 * @return
	 */
	public int getTeacherListNum(TeacherInfo teacherInfo) {
		int num = 0;
		String sql = "select count(*) as num from user_teacher ";

		if (!StringUtil.isEmpty(teacherInfo.getName())) {
			sql += "and name like '%" + teacherInfo.getName() + "%' ";
		}
		if (teacherInfo.getClassID() != 0) {
			sql += "and classID = " + teacherInfo.getClassID();
		}
		if (teacherInfo.getId() != 0) {
			sql += " and id = " + teacherInfo.getId();// 约束用户权限
		}

		try (ResultSet resultSet = query(sql.replaceFirst("and", "where"))) {
			while (resultSet.next()) {
				num = resultSet.getInt("num");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return num;
	}
	
	
	/**
	 * 更新教师信息
	 *
	 * @date 2019年9月26日  
	 * @方法名: editTeacherInfo
	 * @param teacherInfo
	 * @return
	 */
	public boolean editTeacherInfo(TeacherInfo teacherInfo) {
		// 为防止异常:Data truncation: Truncated incorrect DOUBLE value,使用 ' , ' 代替 ' and ' ~
		String sql = "update user_teacher set name='" + teacherInfo.getName() + "' ,  sex='" + teacherInfo.getSex()
				+ "' ,  email='" + teacherInfo.getEmail() + "' ,  mobile='" + teacherInfo.getMobile() + "' ,  classID='"
				+ teacherInfo.getClassID() + "' where id= " + teacherInfo.getId();
		return update(sql);		
	}
	
	/**
	 * 删除教师
	 *
	 * @date 2019年9月26日  
	 * @方法名: deleteTeacher
	 * @param ids
	 * @return
	 */
	public boolean deleteTeacher(String ids) {
		String sql = "delete from user_teacher where id in ( " + ids + " )";
		return update(sql);
	}
	
	/**
	 * 获取教师信息
	 *
	 * @date 2019年9月26日  
	 * @方法名: getTeacherInfoById
	 * @param id
	 * @return
	 */
	public TeacherInfo getTeacherInfoById(int id) {
		String sql = "select * from user_teacher where id=" + id;
		TeacherInfo teacherInfo = null;
		
		try (ResultSet rs = query(sql)){
			if (rs.next()) {
				teacherInfo = new TeacherInfo();
				teacherInfo.setId(rs.getInt("id"));
				teacherInfo.setClassID(rs.getInt("classID"));
				teacherInfo.setTno(rs.getString("tno"));
				teacherInfo.setName(rs.getString("name"));
				teacherInfo.setPassword(rs.getString("password"));
				teacherInfo.setSex(rs.getString("sex"));
				teacherInfo.setEmail(rs.getString("email"));
				teacherInfo.setMobile(rs.getString("mobile"));
				teacherInfo.setPhoto(rs.getBinaryStream("photo"));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return teacherInfo;
	}
	
	/**
	 * 更新用户头像
	 *
	 * @date 2019年9月26日  
	 * @方法名: setTeacherPhoto
	 * @param teacherInfo
	 * @return
	 */
	public boolean setTeacherPhoto(TeacherInfo teacherInfo) {
		Connection conn = DBUtil.getConnection();
		String sql = "update user_teacher set photo = ? where id = ?";
		
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
			preparedStatement.setBinaryStream(1, teacherInfo.getPhoto());
			preparedStatement.setInt(2, teacherInfo.getId());
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return update(sql);
	}
	
	/**
	 * 修改密码
	 *
	 * @date 2019年9月26日  
	 * @方法名: modifyPassword
	 * @param teacherInfo
	 * @param newPassword
	 * @return
	 */
	public boolean modifyPassword(TeacherInfo teacherInfo,String newPassword) {
		String sql = "update user_teacher set password = '" + newPassword + "' where id = '" + teacherInfo.getId() + "'";
		return update(sql);
	}
}
