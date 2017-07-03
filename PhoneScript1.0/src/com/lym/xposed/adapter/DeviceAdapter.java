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
import com.lym.xposed.activity.ScriptListActivity;
import com.lym.xposed.model.Device;
import com.lym.xposed.model.ScriptInfo;
import com.lym.xposed.utils.MessageUtil;
import com.lym.xposed.utils.UUIDS;

public class DeviceAdapter extends BaseAdapter {
	class ViewHolder {
		public TextView num;
		public TextView scriptName;
		public TextView state;
		public View reboot;
		public TextView installId;
	}

	private Context mContext;

	public DeviceAdapter(Context mContext) {
		this.mContext = mContext;
	}

	List<Device> devices = new ArrayList<Device>();

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
		for (Device device : devices) {
			if (UUIDS.getUUID().equals(device.getDeviceId())) {
				devices.remove(device);
				devices.add(0, device);
				break;
			}
		}

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.device_list_item, null);
			vh = new ViewHolder();
			vh.num = (TextView) convertView.findViewById(R.id.tv_device_id);
			vh.scriptName = (TextView) convertView
					.findViewById(R.id.tv_script_name);
			vh.state = (TextView) convertView.findViewById(R.id.tv_state);
			vh.reboot = convertView.findViewById(R.id.btn_reboot);
			vh.installId = (TextView) convertView
					.findViewById(R.id.tv_device_install_id);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		try {
			ScriptInfo info = devices.get(position).getScriptInfo();
			String script = info == null ? null : info.getName();
			String installid = devices.get(position).getDeviceId();
			String state = "";
			state += devices.get(position).isOnline() ? "在线 " : "离线 ";
			state += devices.get(position).isRunning() ? "运行中" : "已停止";

			vh.num.setText(String.valueOf(position + 1));
			vh.scriptName.setText(script == null ? "请配置脚本" : script);
			vh.state.setText(state);
			if (position == 0) {
				vh.installId.setText("本机 ");
			} else {
				vh.installId.setText("");
			}
			vh.installId.append(installid == null ? ""
					: (installid.length() > 6 ? installid.substring(0, 6)
							: installid));

			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// 进入脚本列表
					ScriptListActivity.thisDevice = devices.get(position);
					Intent intent = new Intent(mContext,
							ScriptListActivity.class);
					mContext.startActivity(intent);
				}
			});
			vh.reboot.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					reboot(position);
				}
			});
		} catch (Exception e) {

		}
		return convertView;
	}

	// 重启指定手机
	public void reboot(int i) {
		toast("已发送重启指令");
		String id = devices.get(i).getDeviceId();
		MessageUtil.sendReboot(mContext, id);
	}

	public void toast(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		return devices == null ? null : (devices.size() > position ? devices
				.get(position) : null);
	}

	@Override
	public int getCount() {
		return devices == null ? 0 : devices.size();
	}
}
