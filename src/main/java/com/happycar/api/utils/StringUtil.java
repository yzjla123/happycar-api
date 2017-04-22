package com.happycar.api.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class StringUtil {
	private final static String key = "is2obxom7vboya3h5pf4fikby3nponie";
	
	public static boolean matchChi(String str, int min, int max) {
		return Pattern.matches("^[A-z-\\d\\-\\u4e00-\\u9fa5]{" + min + "," + max + "}$", str);
	}

	public static boolean checkPassword(String password){
		return Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$", password);
	}
	public static boolean isMobile(String mobile){
		return Pattern.matches("^1\\d{10}$", mobile);
	}
	public static String toString(int[] array, String split) {
		String str = "";
		for (int i : array) {
			str += i + split;
		}
		return str.substring(0, str.lastIndexOf(split));
	}

	public static String toString(String[] array, String split) {
		String str = "";
		for (String i : array) {
			str += i + split;
		}
		return str.substring(0, str.lastIndexOf(split));
	}

	public static int[] toIntArray(String str, String splitChar) {
		String[] tmp = str.split(splitChar);
		int[] nums = new int[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			nums[i] = Integer.valueOf(tmp[i]);
		}
		return nums;
	}

	public static boolean notIn(String val, String[] strs) {
		boolean flag = false;
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equals(val)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 从序列中找到一个不相等的字符串
	 * 
	 * @param val
	 * @param strs
	 * @return
	 */
	public static String notEquals(String val, String[] strs) {
		for (String str : strs) {
			if (!val.equals(str))
				return str;
		}
		return val;
	}

	/**
	 * 从序列中找到一个相等的字符串
	 * 
	 * @param val
	 * @param strs
	 * @return
	 */
	public static boolean equals(String val, String... strs) {
		for (String str : strs) {
			if (val.equals(str))
				return true;
		}
		return false;
	}

	public static boolean isNull(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		}
		return false;
	}
	public static boolean isNotNull(String str) {
		if (str == null || str.trim().equals("")) {
			return false;
		}
		return true;
	}
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	/**
	 * 功能：生成签名结果
	 * 
	 * @param sArray
	 *            要签名的数组
	 * @param key
	 *            安全校验码
	 * @return 签名结果字符串
	 */
	public static String BuildMysign(Map sArray) {
		String prestr = CreateLinkString(sArray); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		prestr = prestr +"&"+ key; // 把拼接后的字符串再与安全校验码直接连接起来
		String mysign = MD5Util.md5(prestr);
		return mysign;
	}

	/**
	 * 功能：除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map ParaFilter(Map sArray) {
		List keys = new ArrayList(sArray.keySet());
		Map sArrayNew = new HashMap();

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) sArray.get(key);

			if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}

			sArrayNew.put(key, value);
		}

		return sArrayNew;
	}

	/**
	 * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String CreateLinkString(Map params) {
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = params.get(key)==null?"":params.get(key).toString();

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}
	
	/**
	 * 验证码 随机生成四位数验证码
	 * @return
	 */
	public static String verifyCode(){
		String randCode = "";
		Random random = new Random();
		while(true){
			int r=random.nextInt(10);
			if(r==0) continue;
			randCode += r;
			if(randCode.length()>=4) break;
		}
		return randCode;
	}
}
