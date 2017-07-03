package com.lym.xposed.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lym.xposed.Const;
import com.lym.xposed.R;
import com.lym.xposed.adapter.ScriptAdapter;
import com.lym.xposed.model.Device;
import com.lym.xposed.model.ScriptInfo;
import com.lym.xposed.utils.HttpUtil;

public class ScriptListActivity extends Activity {
	public static Device thisDevice;
	ScriptAdapter mListAdapter;
	ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_script);
		mListAdapter = new ScriptAdapter(this);
		mListView = (ListView) findViewById(R.id.listView);
		update(null);
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public void update(View view) {
		toast("正在更新");
		new Downloder().execute("http://" + Const.HTTP_HOST
				+ "/ScriptServer/getScriptList.jsp");

	}

	public void back(View view) {
		this.finish();
	}

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
					ArrayList<ScriptInfo> scripts = gson.fromJson(result,
							new TypeToken<ArrayList<ScriptInfo>>() {
							}.getType());
					mListAdapter.setScripts(scripts);
					mListView.setAdapter(mListAdapter);
				} catch (Exception e) {
				}
			}
		}

	}
}
