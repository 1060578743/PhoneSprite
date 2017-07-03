package com.lym.xposed.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lym.xposed.activity.RunActivity;
import com.lym.xposed.service.ClientService;
import com.lym.xposed.service.ScriptService;
import com.lym.xposed.utils.ScreenUtil;

public class RebootReceiver extends BroadcastReceiver {
	static final String ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION)) {
			// 唤醒屏幕
			ScreenUtil.wakeUpAndUnlock(context);
			// 启动联网服务
			Intent clientService = new Intent(context, ClientService.class);
			context.startService(clientService);
			// 启动脚本服务
			Intent scriptService = new Intent(context, ScriptService.class);
			context.startService(scriptService);
			// 启动脚本
			Intent runActivity = new Intent(context, RunActivity.class);
			runActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(runActivity);
		}

	}

}
