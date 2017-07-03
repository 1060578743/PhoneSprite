package com.lym.xposed.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lym.xposed.R;
import com.lym.xposed.activity.OptionActivity;
import com.lym.xposed.activity.ScriptListActivity;
import com.lym.xposed.model.ScriptInfo;
import com.lym.xposed.utils.MessageUtil;

public class ScriptAdapter extends BaseAdapter {

	class ViewHolder {
		public TextView num;
		public View option;
		public TextView scriptName;
		public View start;
		public View stop;
	}

	private Context mContext;

	private List<ScriptInfo> scripts;

	public ScriptAdapter(Context mContext) {
		this.mContext = mContext;
		scripts = new ArrayList<ScriptInfo>();
		android .os.FileUtils.setPermissions("", 1, 1, 1);
	}

	@Override
	public int getCount() {
		return scripts == null ? 0 : scripts.size();
	}

	@Override
	public Object getItem(int position) {
		return scripts == null ? null : (scripts.size() > position ? scripts
				.get(position) : null);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<ScriptInfo> getScripts() {
		return scripts;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.script_list_item, null);
			vh = new ViewHolder();
			vh.num = (TextView) convertView.findViewById(R.id.tv_script_id);
			vh.scriptName = (TextView) convertView
					.findViewById(R.id.tv_script_name);
			vh.option = convertView.findViewById(R.id.btn_option);
			vh.start = convertView.findViewById(R.id.btn_start);
			vh.stop = convertView.findViewById(R.id.btn_stop);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.num.setText(String.valueOf(position + 1));
		String script = scripts.get(position).getName();
		vh.scriptName.setText(script == null ? "" : script);
		vh.start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startScript(position);
			}
		});

		vh.stop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				stopScript(position);
			}
		});

		vh.option.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				option(position);
			}
		});
		return convertView;
	}

	/**
	 * ≈‰÷√
	 * 
	 * @param i
	 */
	public void option(int i) {
		OptionActivity.thisScript = scripts.get(i);
		Intent intent = new Intent(mContext, OptionActivity.class);
		mContext.startActivity(intent);
	}

	public void setScripts(List<ScriptInfo> scripts) {
		this.scripts = scripts;
	}

	/**
	 * ‘∂≥Ã∆Ù∂ØΩ≈±æ
	 * 
	 * @param i
	 */
	public void startScript(int i) {
		String id = ScriptListActivity.thisDevice.getDeviceId();
		MessageUtil.sendStart(mContext, id, scripts.get(i));
	}

	/**
	 * ‘∂≥ÃÕ£÷πΩ≈±æ
	 * 
	 * @param i
	 */
	public void stopScript(int i) {
		String id = ScriptListActivity.thisDevice.getDeviceId();
		MessageUtil.sendStop(mContext, id, scripts.get(i));
	}

	public void toast(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}
}
