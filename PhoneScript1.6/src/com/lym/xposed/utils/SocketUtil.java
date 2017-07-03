package com.lym.xposed.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SocketUtil {
	Socket socket;
	BufferedReader reader;
	BufferedWriter writer;

	public SocketUtil(Socket socket) {
		this.socket = socket;
	}

	public SocketUtil(String ip, int port) throws IOException {
		socket = new Socket(ip, port);
		socket.setSoTimeout(1000);
	}

	public void sendLine(String str) throws Exception {
		OutputStream out = socket.getOutputStream();
		out.write((str + "\n").getBytes("UTF-8"));
	}

	public String readLine() throws Exception {
		if (reader == null) {
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			reader = new BufferedReader(isr);
		}
		return reader.readLine();
	}

	public void close() {
		try {
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
