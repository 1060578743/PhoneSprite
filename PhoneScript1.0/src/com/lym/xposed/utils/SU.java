package com.lym.xposed.utils;

import com.lym.xposed.utils.ShellUtil.CommandResult;

public class SU {
	public static void startMainActivity() {
		startActivity("com.lym.script/com.lym.activity.MainActivity");
	}

	public static void startActivity(String name) {
		ShellUtil.execCommand("am start -n " + name, true);
	}

	public static void stopPackage(String pkg) {
		ShellUtil.execCommand("am force-stop " + pkg, true);
	}

	public static void startInput() {
		ShellUtil.execCommand(new String[] {
				"ime set com.lym.script/com.lym.service.InputService",
				"ime enable com.lym.script/com.lym.service.InputService" },
				true);
	}

	public static void stopInput() {
		ShellUtil
				.execCommand(
						new String[] { "ime disable com.lym.script/com.lym.service.InputService" },
						true);
	}

	public static void refreshPic(String picFileName) throws Exception {
		ShellUtil
				.execCommand(
						new String[] { "am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://"
								+ picFileName }, true);
	}

	public static void mv(String fromFile, String toFile) throws Exception {
		ShellUtil.execCommand(
				new String[] { "mv " + fromFile + " " + toFile }, true);
	}

	public static void cp(String fromFile, String toFile) throws Exception {
		ShellUtil.execCommand(
				new String[] { "cp " + fromFile + " " + toFile }, true);
	}

	public static void rm(String file) throws Exception {
		ShellUtil.execCommand(new String[] { "rm " + file }, true);
	}

	public static void startScriptService() {
		ShellUtil
				.execCommand(
						new String[] {
								"settings put secure enabled_accessibility_services com.lym.script/com.lym.service.ScriptService",
								"settings put secure accessibility_enabled 1" },
						true);
	}

	public static void stopScriptService() {
		ShellUtil.execCommand(
				new String[] { "settings put secure accessibility_enabled 0" },
				true);
	}

	public static String getTopActivity() throws Exception {
		CommandResult result = ShellUtil.execCommand(
				new String[] { "dumpsys activity |grep 'mFocusedActivity'" },
				true);
		return result.successMsg;
	}

	public static void reboot() {
		ShellUtil.execCommand(new String[] { "reboot" }, true);
	}
}
