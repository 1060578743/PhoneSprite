package com.lym.xposed;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.lym.xposed.utils.XposedUtil;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Xpose implements IXposedHookLoadPackage {
	ServiceConnectionImpl conn;
	Intent getIntent() {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName("com.lym.xposed",
				"com.lym.xposed.service.ScriptService"));
		return intent;
	}

	void bind(Activity activity) {
		conn = new ServiceConnectionImpl(activity);
		activity.bindService(getIntent(), conn, Context.BIND_AUTO_CREATE);
		
	}

	void unbind(Activity activity) {
		activity.unbindService(conn);
	}

	// 开机加载包的时候会调用
	@Override
	public void handleLoadPackage(final LoadPackageParam lpparam)
			throws Throwable {
		// 去掉root提示
		XposedUtil.removeToast("超级用户");
		XposedBridge.hookAllMethods(Activity.class, "onResume", new MH() {

			@Override
			protected void before(MethodHookParam param) throws Throwable {
				bind((Activity) param.thisObject);
			}

			@Override
			protected void after(MethodHookParam param) throws Throwable {

			}
		});
		XposedBridge.hookAllMethods(Activity.class, "onPause", new MH() {

			@Override
			protected void before(MethodHookParam param) throws Throwable {
				unbind((Activity) param.thisObject);
			}

			@Override
			protected void after(MethodHookParam param) throws Throwable {

			}
		});
	}

}
