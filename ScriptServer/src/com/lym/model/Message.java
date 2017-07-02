package com.lym.model;

/**
 * 消息类
 * 
 * @author Administrator
 * 
 */
public class Message {
	// 重启手机
	public static final int REBOOT = 1;
	// 启动脚本
	public static final int START_SCRIPT = 2;
	// 停止脚本
	public static final int STOP_SCRIPT = 3;
	// 设备连接
	public static final int CONNECT = 4;
	// 消息类型
	private int type;

	// 消息发送到user
	private String to;

	// 内容
	private String content;

	public String getContent() {
		return content;
	}

	public String getTo() {
		return to;
	}

	public int getType() {
		return type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setType(int type) {
		this.type = type;
	}

}
