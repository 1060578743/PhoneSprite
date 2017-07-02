package com.lym.xposed.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

public class ScreenUtil {
	public static void wakeUpAndUnlock(Context context) {
		KeyguardManager km = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		// 获取电源管理器对象
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		// 初始化键盘锁
		KeyguardManager.KeyguardLock keyLock = km.newKeyguardLock("unLock");
		// 解锁
		keyLock.disableKeyguard();

		// 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
		PowerManager.WakeLock wakeLock = pm.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// 点亮屏幕
		wakeLock.acquire();
		// 释放
		wakeLock.release();
	}

}
