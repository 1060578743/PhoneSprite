package com.lym.xposed.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtil {
	public static void downloadScript(String urlStr, String savePath) {
		System.out.println("download script");
		URL url = null;
		HttpURLConnection conn = null;
		FileOutputStream out = null;
		InputStream in = null;
		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3 * 1000);
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			in = conn.getInputStream();
			out = new FileOutputStream(savePath);
			FileUtil.cpyStream(in, out);
		} catch (Exception e) {
			LogUtil.log(e);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
			try {
				out.close();
			} catch (Exception e) {
			}
		}

	}

	public static String[] readAllLines(String file) {
		String all[] = read(file).split("\n");
		return all;
	}

	public static void writeAllLines(String file, String[] all) {
		StringBuffer sb = new StringBuffer();
		for (String string : all) {
			sb.append(string);
			sb.append("\n\r");
		}
		write(file, sb.toString());
	}

	public static String read(String file) {
		File f = new File(file);
		if (!f.exists()) {
			return null;
		}
		InputStream in = null;
		StringBuffer result = new StringBuffer();
		try {
			in = new FileInputStream(f);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
		} catch (Exception e) {
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		return result.toString().trim();
	}

	public static void append(String file, String content) throws Exception {
		RandomAccessFile rf = new RandomAccessFile(file, "rw");
		rf.seek(rf.length()); // 将指针移动到文件末尾
		rf.write((content + "\n\r").getBytes("utf-8"));
		rf.close();// 关闭文件流
	}

	public static void write(String file, String content) {
		File f = new File(file);
		OutputStream out = null;
		try {
			out = new FileOutputStream(f);
			out.write(content.getBytes());
		} catch (Exception e) {
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

	public static void createDir(String filename) {
		File file = new File(filename);
		if (file.getParentFile().exists()) {
			return;
		}
		file.getParentFile().mkdirs();
	}

	public static void cpyStream(InputStream in, OutputStream out)
			throws Exception {
		byte buff[] = new byte[1024];
		int len = 0;
		while ((len = in.read(buff)) != -1) {
			out.write(buff, 0, len);
		}
	}
}
