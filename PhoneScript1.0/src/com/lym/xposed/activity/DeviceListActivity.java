package com.lym.xposed.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lym.xposed.Const;
import com.lym.xposed.R;
import com.lym.xposed.adapter.DeviceAdapter;
import com.lym.xposed.model.Device;
import com.lym.xposed.service.ClientService;
import com.lym.xposed.service.ScriptService;
import com.lym.xposed.utils.HttpUtil;

public class DeviceListActivity extends Activity {
	class Downloder extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				return HttpUtil.get(params[0]);
			} catch (Exception e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					Gson gson = new Gson();
					ArrayList<Device> devices = gson.fromJson(result,
							new TypeToken<ArrayList<Device>>() {
							}.getType());
					mListAdapter.setDevices(devices);
					mListView.setAdapter(mListAdapter);
					return;
				} catch (Exception e) {
				}
			}
			mListAdapter.setDevices(null);
			mListView.setAdapter(mListAdapter);
		}

	}

	private DeviceAdapter mListAdapter;

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device);
		mListAdapter = new DeviceAdapter(this);
		mListView = (ListView) findViewById(R.id.listView);
		startClientService();
		startScriptService();
		refresh(null);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 刷新设备列表
	 * 
	 * @param view
	 */
	public void refresh(View view) {
		toast("正在刷新");
		new Downloder().execute("http://" + Const.HTTP_HOST
				+ "/ScriptServer/getDeviceList.jsp");
	}

	public void startClientService() {
		Intent intent = new Intent(this, ClientService.class);
		// intent.setFlags(flags)
		startService(intent);
	}

	public void startScriptService() {
		Intent intent = new Intent(this, ScriptService.class);
		startService(intent);
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
