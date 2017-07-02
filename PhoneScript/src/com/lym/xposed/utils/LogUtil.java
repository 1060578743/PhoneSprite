package com.lym.xposed.utils;

public class LogUtil {
	public static void log(Exception e) {
		e.printStackTrace();
	}

	public static void log(String log) {
		System.out.println("hello log:" + log);
	}
}
