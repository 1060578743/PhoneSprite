package com.lym.xposed.model;


public class Device {
	// �豸id
	private String deviceId;
	// �豸���еĽű�
	private ScriptInfo scriptInfo;
	// �Ƿ�����
	private boolean online;
	// �Ƿ�����
	private boolean running;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		return true;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public ScriptInfo getScriptInfo() {
		return scriptInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
		return result;
	}

	public boolean isOnline() {
		return online;
	}

	public boolean isRunning() {
		return running;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void setScriptInfo(ScriptInfo scriptInfo) {
		this.scriptInfo = scriptInfo;
	}

}
