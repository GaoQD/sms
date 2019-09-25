package com.sms.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
	
	private static Properties properties;
	
	private static InputStream inputStream = DBConfig.class.getResourceAsStream("/db.properties");
	
	private DBConfig() {
		
	}
	
	static {
		try {
			properties = new Properties();
			properties.load(inputStream);
			properties.getProperty("URL");
			properties.getProperty("USERNAME");
			properties.getProperty("PASSWORD");
			properties.getProperty("DRIVER");
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			System.err.println("error : not found the specified configuration file: db.properties");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据库配置
	 *
	 * @date 2019年9月25日  
	 * @方法名: getProperties
	 * @return
	 */
	public static Properties getProperties() {
		return properties;
	}
}
