package com.lym.model;

/**
 * ��Ϣ��
 * 
 * @author Administrator
 * 
 */
public class Message {
	// �����ֻ�
	public static final int REBOOT = 1;
	// �����ű�
	public static final int START_SCRIPT = 2;
	// ֹͣ�ű�
	public static final int STOP_SCRIPT = 3;
	// �豸����
	public static final int CONNECT = 4;
	// ��Ϣ����
	private int type;

	// ��Ϣ���͵�user
	private String to;

	// ����
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
