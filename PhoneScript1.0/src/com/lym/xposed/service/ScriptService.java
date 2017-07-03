package com.lym.xposed.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;

import com.google.gson.Gson;
import com.lym.xposed.activity.RunActivity;
import com.lym.xposed.aidl.IActivity;
import com.lym.xposed.aidl.IScriptService;
import com.lym.xposed.model.Message;
import com.lym.xposed.model.ScriptInfo;
import com.lym.xposed.script.Script;
import com.lym.xposed.utils.SU;

public class ScriptService extends Service {
	private BroadcastReceiver mBroadcastReceiver;

	public void registerReceiver() {
		mBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String msg = intent.getStringExtra("msg");
				Gson gson = new Gson();
				Message message = gson.fromJson(msg, Message.class);
				switch (message.getType()) {
				case Message.REBOOT: {
					SU.reboot();
					break;
				}
				case Message.START_SCRIPT: {
					ScriptInfo info = gson.fromJson(message.getContent(),
							ScriptInfo.class);
					RunActivity.thisScript = info;
					Intent run = new Intent(ScriptService.this,
							RunActivity.class);
					run.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					ScriptService.this.startActivity(run);
					break;
				}
				case Message.STOP_SCRIPT: {
					Script.currentScript.stop();
					break;
				}
				}
			}
		};
		registerReceiver(mBroadcastReceiver, new IntentFilter(
				ClientService.ACTION_RECEIVE_MESSAGE));
	}

	@Override
	public void onCreate() {
		super.onCreate();
		registerReceiver();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new IScriptService.Stub() {

			@Override
			public void onConnect(String name, IActivity activity)
					throws RemoteException {
				if (Script.currentScript != null) {
					Script.currentScript.setTopActivity(name, activity);
				}
			}
		};
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}
}
