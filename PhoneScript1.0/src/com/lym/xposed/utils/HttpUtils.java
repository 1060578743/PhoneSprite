package com.lym.xposed.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @ClassName: HttpUtils
 * @Description: Http 请求工具类
 * @author lixk
 * @date 2017年3月31日 下午6:21:20
 * @version [1.0, 2017年3月31日]
 * @since version 1.0
 */
public class HttpUtils {

	/**
	 * 
	 * @Title: get
	 * @Description: 向服务器发起get请求
	 * @param url
	 *            请求地址
	 * @param args
	 *            请求参数
	 * @return 字符串格式的服务器响应数据
	 * @throws IOException
	 */
	public static String get(String url, Map<String, String> args)
			throws IOException {
		// 请求服务器的地址
		StringBuilder fullHostUrl = new StringBuilder(url).append("?");

		// 如果参数不空，循环遍历参数
		if (args != null) {
			for (Entry<String, String> entry : args.entrySet()) {
				String key = entry.getKey(); // 参数名称
				String value = entry.getValue(); // 参数值
				fullHostUrl
						.append(key)
						.append("=")
						.append(URLEncoder.encode(String.valueOf(value),
								"UTF-8"));
				// 参数结尾加连接符
				fullHostUrl.append("&");
			}
		}
		URL host = new URL(fullHostUrl.toString());
		HttpURLConnection connection = HttpURLConnection.class.cast(host
				.openConnection());
		connection.setConnectTimeout(3000);
		connection.setReadTimeout(3000);
		// 设置为GET请求
		connection.setRequestMethod("GET");
		// 禁用缓存
		connection.setUseCaches(false);
		// 设置请求头参数
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Charsert", "UTF-8");
		// 通过输入流来读取服务器响应
		int resultCode = connection.getResponseCode();
		StringBuilder response = new StringBuilder();
		if (resultCode == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			// 关闭输入流
			br.close();
		} else {
			response.append(resultCode);
		}
		return response.toString();
	}

	/**
	 * 
	 * @Title: post
	 * @Description: 向服务器发起post请求
	 * @param url
	 *            请求地址
	 * @param args
	 *            请求参数
	 * @return 字符串格式的服务器响应数据
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> args)
			throws IOException {
		// 请求服务器的地址
		URL host = new URL(url);
		HttpURLConnection connection = HttpURLConnection.class.cast(host
				.openConnection());
		connection.setConnectTimeout(3000);
		connection.setReadTimeout(3000);
		// 设置为POST请求
		connection.setRequestMethod("POST");
		// 发送POST请求必须设置如下两行
		connection.setDoOutput(true);
		connection.setDoInput(true);
		// 禁用缓存
		connection.setUseCaches(false);
		// 设置请求头参数
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Charsert", "UTF-8");
		// 获取数据输出流
		DataOutputStream dos = new DataOutputStream(
				connection.getOutputStream());

		// 如果参数不空，循环遍历参数
		if (args != null) {
			for (Entry<String, String> entry : args.entrySet()) {
				String key = entry.getKey(); // 参数名称
				Object value = entry.getValue(); // 参数值

				dos.write((key + "=" + URLEncoder.encode(String.valueOf(value),
						"UTF-8")).getBytes());
				// 参数结尾加连接符
				dos.write("&".getBytes());
			}
		}
		// 关闭输出流
		dos.flush();
		dos.close();

		// 通过输入流来读取服务器响应
		int resultCode = connection.getResponseCode();
		StringBuilder response = new StringBuilder();
		if (resultCode == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			// 关闭输入流
			br.close();
		} else {
			response.append(resultCode);
		}
		return response.toString();
	}

	public static void main(String[] args) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("transtype", "translang");
		map.put("textFileName", "java并发编程的艺术.pdf");
		map.put("photoName", "照片.jpg");
		System.out.println(HttpUtils.post("http://localhost/test", map));
	}

}
