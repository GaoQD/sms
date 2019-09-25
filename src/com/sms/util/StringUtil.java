package com.sms.util;

public class StringUtil {

	/**
	 * 判断字符串是否为空
	 *
	 * @date 2019年9月25日  
	 * @方法名: isEmpty
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
}
