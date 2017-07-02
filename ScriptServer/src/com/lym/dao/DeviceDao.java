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
	// ÿ�α������ݵļ��
	private static final long SAVE_TIME = 1000 * 10;
	private static long nextSaveTime = 0;
	static {
		try {
			read();
		} catch (Exception e) {
		}
	}

	/**
	 * ��ȡ����
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
	 * ��������
	 */
	public static void save() {
		if (System.currentTimeMillis() < nextSaveTime) {
			return;
		}
		nextSaveTime = SAVE_TIME + System.currentTimeMillis();
		save();
	}

	/**
	 * ��������
	 */
	public static void saveNow() {
		// ����
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		for (Device d : devices) {
			sb.append(gson.toJson(d));
			sb.append("\n");
		}
		FileUtil.write(DEVICE_FILE, sb.toString());
	}

	/**
	 * �����豸����������豸
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
	 * ɾ���豸
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
	 * ��ȡ�豸
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
	 * �豸�б�
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
