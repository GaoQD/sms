package com.sms.util;

public class StringUtil {

	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 *
	 * @date 2019��9��25��  
	 * @������: isEmpty
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
