/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年7月22日 下午4:27:19
 * @version 1.0
 */
public class CodeUtils {

	/**
	 * 获取日期码
	 * 取一位年份，月份转成代码，日转成代码
	 * 如 2017-07-22 转成 77M
	 * @author eko.zhan at 2017年7月22日 下午4:27:37
	 * @return
	 */
	public static String formatDateCode(String dateCode){
		Map<Integer, String> m = new HashMap<Integer, String>();
		m.put(1, "1");
		m.put(2, "2");
		m.put(3, "3");
		m.put(4, "4");
		m.put(5, "5");
		m.put(6, "6");
		m.put(7, "7");
		m.put(8, "8");
		m.put(9, "9");
		m.put(10, "A");
		m.put(11, "B");
		m.put(12, "C");
		m.put(13, "D");
		m.put(14, "E");
		m.put(15, "F");
		m.put(16, "G");
		m.put(17, "H");
		m.put(18, "I");
		m.put(19, "J");
		m.put(20, "K");
		m.put(21, "L");
		m.put(22, "M");
		m.put(23, "N");
		m.put(24, "O");
		m.put(25, "P");
		m.put(26, "Q");
		m.put(27, "R");
		m.put(28, "S");
		m.put(29, "T");
		m.put(30, "U");
		m.put(31, "V");
		return dateCode.substring(3, 4) + m.get(Integer.parseInt(dateCode.substring(5, 7))) + m.get(Integer.parseInt(dateCode.substring(8, 10)));
	}
	
	/**
	 * 获取料号
	 * @param code
	 * @return
	 */
	public static String getPartNo(String code){
		if (code.length()<11) return "";
		return code.substring(0, 11);
	}
	/**
	 * 获取厂商代码
	 * @param code
	 * @return
	 */
	public static String getVenderCode(String code){
		if (code.length()<15) return "";
		return code.substring(11, 15);
	}
	/**
	 * 获取版本
	 * @param code
	 * @return
	 */
	public static String getVersionNo(String code){
		if (code.length()<17) return "";
		return code.substring(15, 17);
	}
	/**
	 * 获取日期码
	 * @param code
	 * @return
	 */
	public static String getDateCode(String code){
		if (code.length()<20) return "";
		return code.substring(17, 20);
	}
	/**
	 * 获取模具号
	 * @param code
	 * @return
	 */
	public static String getMouldCode(String code){
		if (code.length()<23) return "";
		return code.substring(20, 23);
	}
	/**
	 * 获取流水号
	 * @param cdoe
	 * @return
	 */
	public static String getSerialNo(String code){
		if (code.length()<27) return "";
		return code.substring(23, 27);
	}
}
