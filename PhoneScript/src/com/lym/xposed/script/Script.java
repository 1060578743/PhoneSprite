package com.lym.xposed.script;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lym.xposed.Const;
import com.lym.xposed.aidl.IActivity;
import com.lym.xposed.application.App;
import com.lym.xposed.model.ScriptInfo;
import com.lym.xposed.model.ScriptOption;
import com.lym.xposed.utils.FileUtil;
import com.lym.xposed.utils.HttpUtils;
import com.lym.xposed.utils.LogUtil;

import dalvik.system.DexClassLoader;

public abstract class Script implements Runnable {

	public static Script currentScript;

	public static void loadScript(Context context, ScriptInfo scriptInfo,
			String deviceId) throws Exception {
		if (currentScript != null) {
			if (currentScript.isStart()) {
				currentScript.stop();
			}
		}
		File outfile = context.getDir("dex", 0);
		File scriptPath = context.getDir("script", 0);
		File scriptFile = new File(scriptPath, scriptInfo.getName() + ".apk");
		if (!scriptFile.exists()) {
			// œ¬‘ÿΩ≈±æ
			FileUtil.downloadScript(scriptInfo.getUrl(),
					scriptFile.getAbsolutePath());
		}
		DexClassLoader loader = new DexClassLoader(
				scriptFile.getAbsolutePath(), outfile.getAbsolutePath(), null,
				context.getClassLoader());
		Class<?> scriptClass = loader
				.loadClass("com.lym.xposed.script.ScriptImpl");
		Script script = (Script) scriptClass.newInstance();
		script.setContext(context);
		script.scriptInfo = scriptInfo;
		script.deviceId = deviceId;
		script.downloadOption();
		currentScript = script;
	}

	private Context mContext;
	private boolean mIsStart;
	private Thread mScriptThread;
	private IActivity mTopActivity;
	private String mTopActivityName;
	private ScriptInfo scriptInfo;
	private ScriptOption scriptOption;
	private String deviceId;

	public void downloadOption() {
		ScriptOption scriptOption = new ScriptOption();
		scriptOption.setDeviceId(deviceId);
		scriptOption.setScriptName(scriptInfo.getName());
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("scriptName", scriptInfo.getName());
			map.put("deviceId", deviceId);
			String result = HttpUtils.post("http://" + Const.HTTP_HOST
					+ "/ScriptServer/getOption.jsp", map);
			Gson gson = new Gson();
			this.scriptOption = gson.fromJson(result, ScriptOption.class);
		} catch (Exception e) {
		}

	}

	public Context getContext() {
		return mContext;
	}

	public String getOption() {
		return scriptOption == null ? null : scriptOption.getOption();
	}

	public IActivity getTopActivity() {
		return mTopActivity;
	}

	public String getTopActivityName() {
		return mTopActivityName;
	}

	public View getView() {
		return null;
	}

	public boolean isStart() {
		return mIsStart;
	}

	public void reStart() {

	}

	@Override
	public void run() {
		try {
			scriptMain();
		} catch (InterruptedException e) {
			LogUtil.log(e);
		}
	}

	public void runOnUiThread(Runnable run) {
		App.app.handle.post(run);
	}

	public void saveOption(final String option) {
		toast("±£¥Ê≈‰÷√");
		new Thread() {
			public void run() {
				try {
					Map<String, String> map = new HashMap<String, String>();
					map.put("option", option);
					map.put("scriptName", scriptInfo.getName());
					map.put("deviceId", deviceId);
					HttpUtils
							.post("http://"+Const.HTTP_HOST+"/ScriptServer/setOption.jsp",
									map);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void scriptMain() throws InterruptedException {
		System.out.println("script start");
	}

	public void setContext(Context context) {
		mContext = context;
	}

	public void setTopActivity(String name, IActivity act) {
		this.mTopActivity = act;
		this.mTopActivityName = name;
	}

	public void start() {
		if (mIsStart) {
			return;
		}
		mIsStart = true;
		mScriptThread = new Thread(this);
		mScriptThread.start();
		runOnUiThread(new Runnable() {
			public void run() {
				App.app.drawFloatView(mIsStart);
			}
		});
	}

	public void stop() {
		if (!mIsStart) {
			return;
		}
		mIsStart = false;
		mScriptThread.interrupt();
		runOnUiThread(new Runnable() {
			public void run() {
				App.app.drawFloatView(mIsStart);
			}
		});

	}

	public void toast(String msg) {
		try {
			Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			LogUtil.log(e);
		}
	}

}
