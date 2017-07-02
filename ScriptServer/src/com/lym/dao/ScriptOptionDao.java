package com.lym.dao;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.lym.model.ScriptOption;
import com.lym.util.FileUtil;

public class ScriptOptionDao {
	private static final String OPTION_FILE = "/file/options.txt";
	private static List<ScriptOption> options;
	static {
		try {
			read();
		} catch (Exception e) {
		}
	}

	public static void read() {
		options = new ArrayList<ScriptOption>();
		String allData[] = FileUtil.readAllLines(OPTION_FILE);
		Gson gson = new Gson();
		for (String data : allData) {
			if (data == null || "".equals(data) || "null".equals(data)) {
				continue;
			}
			options.add(gson.fromJson(data, ScriptOption.class));
		}
	}

	public static void saveNow() {
		// 保存
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		for (ScriptOption option : options) {
			sb.append(gson.toJson(option));
			sb.append("\n");
		}
		FileUtil.write(OPTION_FILE, sb.toString());
	}

	// 更新或者添加脚本配置
	public void addOrUpdate(ScriptOption option) {
		synchronized (options) {

			for (ScriptOption o : options) {
				if (o.equals(option)) {
					options.remove(o);
					break;
				}
			}
			options.add(option);
			saveNow();
		}

	}

	public ScriptOption getOption(ScriptOption option) {
		synchronized (options) {
			for (ScriptOption o : options) {
				if (o.equals(option)) {
					return o;
				}

			}
		}
		return null;
	}
}
