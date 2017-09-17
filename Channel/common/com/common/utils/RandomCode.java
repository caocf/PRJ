package com.common.utils;

import java.util.Date;
import java.util.Random;

public class RandomCode {
	private static String randomString = "23456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ"; // 图片上的字符串  除去易混淆字符0、1、I，大小写o不影响判断

	/**
	 * 生成验证码
	 * 
	 * @param length
	 * @return
	 */
	public static String genRandomCode(int length) {
		return genRandomCode(randomString, length);
	}

	/**
	 * 使用种子生成验证码
	 * 
	 * @param seedstring
	 *            验证码种子
	 * @param length
	 * @return
	 */
	public static String genRandomCode(String seedstring, int length) {
		String code = "";
		Random random = new Random(new Date().getTime());
		for (int i = 0; i < length; i++) {
			code += String.valueOf(seedstring.charAt(random.nextInt(seedstring
					.length())));
		}
		return code;
	}
}
