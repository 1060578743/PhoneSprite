package com.lym.xposed.activity;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lym.xposed.R;
import com.lym.xposed.model.ScriptInfo;
import com.lym.xposed.script.Script;
import com.lym.xposed.utils.FileUtil;
import com.lym.xposed.utils.LogUtil;
import com.lym.xposed.utils.UUIDS;

public class RunActivity extends Activity {
	public static ScriptInfo thisScript;
	TextView tv_top_center;
	LinearLayout optionLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run);
		optionLayout = (LinearLayout) findViewById(R.id.optionLayout);
		tv_top_center = (TextView) findViewById(R.id.topCenter);
		if (loadLocalScript()) {
			return;
		}
		loadServerScript();

	}

	private boolean loadLocalScript() {
		String file = getIntent().getStringExtra("filename");
		if (file == null) {
			return false;
		}
		thisScript = new ScriptInfo();
		thisScript.setName("测试");
		thisScript.setUrl(file);
		new Thread() {
			@Override
			public void run() {
				try {
					Script.loadScript(RunActivity.this, thisScript);
					Script.currentScript.start();
				} catch (Exception e) {
				}
			}
		}.start();

		finish();
		return true;
	}

	private void loadServerScript() {
		Gson gson = new Gson();
		if (thisScript == null) {
			// 读取
			String txt = FileUtil.read(new File(getFilesDir(), "run.txt")
					.getPath());
			thisScript = gson.fromJson(txt, ScriptInfo.class);
		} else {
			// 写入
			String txt = gson.toJson(thisScript);
			FileUtil.write(new File(getFilesDir(), "run.txt").getPath(), txt);
		}
		if (thisScript == null) {
			toast("不存在本地脚本。。。");
			finish();
		}

		downloadScript();
		tv_top_center.setText(thisScript.getName());
	}

	public void downloadScript() {
		toast("正在加载脚本。。。");
		new Thread() {
			@Override
			public void run() {
				try {
					Script.loadScript(RunActivity.this, thisScript,
							UUIDS.getUUID());
					Script.currentScript.start();
					Thread.sleep(3000);
					RunActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								optionLayout.removeAllViews();
								optionLayout.addView(Script.currentScript
										.getView());
								RunActivity.this.finish();
							} catch (Exception e) {
								LogUtil.log(e);
							}
						}
					});
				} catch (Exception e) {
					LogUtil.log(e);
				}
			}
		}.start();
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public void back(View v) {
		this.finish();
	}
}
