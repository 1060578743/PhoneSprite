package com.lym.xposed.utils;


public class HttpUtil {
	public static String get(String url) throws Exception {
		return HttpUtils.get(url, null);
	}


}
