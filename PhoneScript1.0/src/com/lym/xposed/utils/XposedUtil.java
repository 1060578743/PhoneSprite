package com.lym.xposed.utils;

import com.lym.xposed.MH;

import android.content.Context;
import android.widget.Toast;
import de.robv.android.xposed.XposedBridge;

public class XposedUtil {
	public static void removeToast(final String contains) throws Throwable {
		XposedBridge.hookMethod(Toast.class.getMethod("makeText",
				Context.class, CharSequence.class, int.class), new MH() {

			@Override
			protected void before(MethodHookParam param) throws Throwable {
				String text = param.args[1].toString();
				if (text.contains(contains)) {
					param.setResult(null);
				}
			}

			@Override
			protected void after(MethodHookParam param) throws Throwable {
			}
		});
	}
}
