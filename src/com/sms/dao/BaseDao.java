package com.sms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sms.util.DBUtil;

public class BaseDao {
	
	private static Connection conn = DBUtil.getConnection();
	
	/**
	 * 查询数据
	 *
	 * @date 2019年9月25日  
	 * @方法名: query
	 * @param sql
	 * @return
	 */
	public static ResultSet query(String sql) {
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			return preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean update(String sql) {
		try {
			return conn.prepareStatement(sql).executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	
}
