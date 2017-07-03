package com.lym.xposed.utils;

import java.util.Random;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;

public class StringUtil {
	static Random rd;
	static {
		rd = new Random(System.currentTimeMillis());
	}

	public static String randString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char s = (char) (rd.nextInt('Z' - 'A') + 'A');
			sb.append(s);
		}
		return sb.toString();
	}

	public static String getIntentData(Intent intent) {
		StringBuilder str = new StringBuilder();
		str.append("Intent :" + intent.toString() + " \n{\n");
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Set<String> keys = bundle.keySet();
			for (String key : keys) {
				Object value = bundle.get(key);
				if (value == null) {
					str.append("key[" + key + "] null");
					continue;
				}
				try {
					str.append("key[" + key + "] " + " vlue[" + value + "] "
							+ value.getClass() + "\n");
				} catch (Exception e) {
				}
			}
		}
		str.append("}");
		return str.toString();
	}
}
