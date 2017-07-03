package com.lym.xposed.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

public class ScreenUtil {
	public static void wakeUpAndUnlock(Context context) {
		KeyguardManager km = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		// ��ȡ��Դ����������
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		// ��ʼ��������
		KeyguardManager.KeyguardLock keyLock = km.newKeyguardLock("unLock");
		// ����
		keyLock.disableKeyguard();

		// ��ȡPowerManager.WakeLock����,����Ĳ���|��ʾͬʱ��������ֵ,������LogCat���õ�Tag
		PowerManager.WakeLock wakeLock = pm.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// ������Ļ
		wakeLock.acquire();
		// �ͷ�
		wakeLock.release();
	}

}
