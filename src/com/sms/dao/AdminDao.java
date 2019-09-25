package com.sms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sms.model.AdminInfo;

public class AdminDao extends BaseDao{
	
	/**
	 * 获取用户信息
	 *
	 * @date 2019年9月25日  
	 * @方法名: getUserInfo
	 * @param name
	 * @param password
	 * @return
	 */
	public AdminInfo getUserInfo(String name,String password) {
		String sql = "select id,name,password,status from user_Admin where name='" + name + "'  and password='" + password + "'";
		try (ResultSet rs = query(sql)) {
			if (rs.next()) {
				AdminInfo adminInfo = new AdminInfo();
				adminInfo.setId(rs.getString("id"));
				adminInfo.setStatus(rs.getInt("status"));
				adminInfo.setName(rs.getString("name"));
				adminInfo.setPassword(rs.getString("password"));
				return adminInfo;
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 修改密码
	 *
	 * @date 2019年9月25日  
	 * @方法名: modifyPassword
	 * @param adminInfo
	 * @param newPassword
	 * @return
	 */
	public boolean modifyPassword(AdminInfo adminInfo,String newPassword) {
		String sql = "update user_admin set password= '" + newPassword + "' where id='" + adminInfo.getId() + "'";
		return update(sql);
	}
	
}
