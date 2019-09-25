package com.sms.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	
	private static Connection conn;
	
	private static String URL = DBConfig.getProperties().getProperty("URL");
	private static String USER = DBConfig.getProperties().getProperty("USERNAME");
	private static String PASSWORD = DBConfig.getProperties().getProperty("PASSWORD");
	private static String DRIVER = DBConfig.getProperties().getProperty("DRIVER");
	
	private DBUtil() {
		
	}
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("error : fail to initialize the driver of database !\n");
			throw new ExceptionInInitializerError(e);
		}
	}
	
	/**
	 * 获取数据库连接
	 *
	 * @date 2019年9月25日  
	 * @方法名: getConnection
	 * @return
	 */
	public static Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(URL,USER,PASSWORD);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return conn;
	}
}
