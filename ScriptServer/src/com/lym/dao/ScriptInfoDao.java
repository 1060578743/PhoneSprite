package com.lym.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.lym.model.ScriptInfo;
import com.lym.util.FileUtil;

public class ScriptInfoDao {
	private static ArrayList<ScriptInfo> scripts;
	private static final String SCRIPT_FILE = "/file/scripts.txt";
	static {
		try {
			read();
		} catch (Exception e) {
		}
	}

	public static void read() {
		System.out.println(SCRIPT_FILE);
		scripts = new ArrayList<ScriptInfo>();
		String allData[] = FileUtil.readAllLines(SCRIPT_FILE);
		Gson gson = new Gson();
		for (String data : allData) {
			if (data == null || "".equals(data) || "null".equals(data)) {
				continue;
			}
			scripts.add(gson.fromJson(data, ScriptInfo.class));
		}
	}

	public static void saveNow() {
		// 保存
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		for (ScriptInfo s : scripts) {
			sb.append(gson.toJson(s));
			sb.append("\n");
		}
		FileUtil.write(SCRIPT_FILE, sb.toString());
	}

	// 列出所有脚本信息
	public List<ScriptInfo> list() {
		return scripts;
	}

	// 添加脚本信息
	public void addOrUpdate(ScriptInfo scriptInfo) {
		synchronized (scripts) {
			scripts.add(scriptInfo);
			saveNow();
		}
	}

	// 删除脚本信息
	public void deleteScriptInfo(ScriptInfo scriptInfo) {
		synchronized (scripts) {
			scripts.remove(scriptInfo);
			saveNow();
		}
	}
}
