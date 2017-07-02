package com.lym.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.lym.model.Device;
import com.lym.util.FileUtil;

public class DeviceDao {
	private static ArrayList<Device> devices;
	public static final String DEVICE_FILE = "/file/devices.txt";
	// 每次保存数据的间隔
	private static final long SAVE_TIME = 1000 * 10;
	private static long nextSaveTime = 0;
	static {
		try {
			read();
		} catch (Exception e) {
		}
	}

	/**
	 * 读取数据
	 */
	public static void read() {
		devices = new ArrayList<Device>();
		String allData[] = FileUtil.readAllLines(DEVICE_FILE);
		Gson gson = new Gson();
		for (String data : allData) {
			if (data == null || "".equals(data) || "null".equals(data)) {
				continue;
			}
			devices.add(gson.fromJson(data, Device.class));
		}
	}

	/**
	 * 保存数据
	 */
	public static void save() {
		if (System.currentTimeMillis() < nextSaveTime) {
			return;
		}
		nextSaveTime = SAVE_TIME + System.currentTimeMillis();
		save();
	}

	/**
	 * 立即保存
	 */
	public static void saveNow() {
		// 保存
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		for (Device d : devices) {
			sb.append(gson.toJson(d));
			sb.append("\n");
		}
		FileUtil.write(DEVICE_FILE, sb.toString());
	}

	/**
	 * 更新设备，或者添加设备
	 * 
	 * @param device
	 */
	public void addOrUpdate(Device device) {
		System.out.println("file:" + new File(DEVICE_FILE).getAbsolutePath().toString());
		if (device == null) {
			return;
		}
		synchronized (devices) {
			for (Device d : devices) {
				if (device.equals(d)) {
					delete(d);
					break;
				}
			}
			devices.add(device);
			save();
		}
	}

	/**
	 * 删除设备
	 * 
	 * @param device
	 */
	public void delete(Device device) {
		synchronized (devices) {
			devices.remove(device);
			saveNow();
		}

	}

	/**
	 * 获取设备
	 * 
	 * @param id
	 * @return
	 */
	public Device getDevice(String id) {
		synchronized (devices) {
			for (Device device : devices) {
				if (device != null) {
					if (id.equals(device.getDeviceId())) {
						return device;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 设备列表
	 * 
	 * @return
	 */
	public List<Device> list() {
		return devices;
	}

	public int size() {
		return devices.size();
	}
}
