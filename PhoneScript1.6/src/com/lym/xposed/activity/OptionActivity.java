package com.lym.xposed.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lym.xposed.R;
import com.lym.xposed.model.ScriptInfo;
import com.lym.xposed.script.Script;
import com.lym.xposed.utils.LogUtil;

public class OptionActivity extends Activity {
	//public static Device thisDevice;
	public static ScriptInfo thisScript;
	TextView tv_top_center;
	LinearLayout optionLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		optionLayout = (LinearLayout) findViewById(R.id.optionLayout);
		tv_top_center = (TextView) findViewById(R.id.topCenter);
		tv_top_center.setText(thisScript.getName());
		downloadScript();
	}

	public void downloadScript() {
		new Thread() {
			@Override
			public void run() {
				try {
					Script.loadScript(OptionActivity.this, thisScript,
							ScriptListActivity.thisDevice.getDeviceId());
					OptionActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								optionLayout.removeAllViews();
								optionLayout.addView(Script.currentScript
										.getView());
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

	public void download(View v) {
		downloadScript();

	}

}
