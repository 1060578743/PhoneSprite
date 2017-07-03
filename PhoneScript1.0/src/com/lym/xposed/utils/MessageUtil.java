package com.lym.xposed.utils;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.lym.xposed.model.Message;
import com.lym.xposed.model.ScriptInfo;
import com.lym.xposed.service.ClientService;

public class MessageUtil {
	public static void sendMsg(Context context, Message message) {
		Gson gson = new Gson();
		String msg = gson.toJson(message);
		Intent intent = new Intent();
		intent.setAction(ClientService.ACTION_SEND_MESSAGE);
		intent.putExtra("msg", msg);
		context.sendBroadcast(intent);
	}

	public static void sendReboot(Context context, String id) {
		Message message = new Message();
		message.setTo(id);
		message.setType(Message.REBOOT);
		sendMsg(context, message);
	}

	public static void sendStop(Context context, String id,
			ScriptInfo scriptInfo) {
		Message message = new Message();
		message.setTo(id);
		message.setContent(new Gson().toJson(scriptInfo));
		message.setType(Message.STOP_SCRIPT);
		sendMsg(context, message);
	}

	public static void sendStart(Context context, String id,
			ScriptInfo scriptInfo) {
		Message message = new Message();
		message.setTo(id);
		message.setContent(new Gson().toJson(scriptInfo));
		message.setType(Message.START_SCRIPT);
		sendMsg(context, message);
	}
}
