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
			// ������Ļ
			ScreenUtil.wakeUpAndUnlock(context);
			// ������������
			Intent clientService = new Intent(context, ClientService.class);
			context.startService(clientService);
			// �����ű�����
			Intent scriptService = new Intent(context, ScriptService.class);
			context.startService(scriptService);
			// �����ű�
			Intent runActivity = new Intent(context, RunActivity.class);
			runActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(runActivity);
		}

	}

}
