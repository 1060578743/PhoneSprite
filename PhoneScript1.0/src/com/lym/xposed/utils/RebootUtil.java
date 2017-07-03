package com.lym.xposed.utils;

import java.util.Timer;
import java.util.TimerTask;


public class RebootUtil {
	private static RebootUtil rebootUtil;
	private Timer rebootTimer;

	public synchronized static RebootUtil instance() {
		if (rebootUtil == null) {
			rebootUtil = new RebootUtil();
		}
		return rebootUtil;
	}

	private RebootUtil() {
		rebootTimer = new Timer();
	}

	public void rebootDelay(long delay) {
		cancelReboot();
		rebootTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				SU.reboot();
			}
		}, delay);
	}

	public void cancelReboot() {
		rebootTimer.cancel();
	}
}
