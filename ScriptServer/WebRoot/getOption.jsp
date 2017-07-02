<%@page import="com.lym.model.ScriptOption"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.lym.dao.ScriptOptionDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String deviceId = request.getParameter("deviceId");
	String scriptName = request.getParameter("scriptName");
	if (deviceId != null && scriptName != null) {
		Gson gson = new Gson();
		try {
			ScriptOption op = new ScriptOption();
			op.setDeviceId(deviceId);
			op.setScriptName(scriptName);
			ScriptOptionDao sod = new ScriptOptionDao();
			out.print(gson.toJson(sod.getOption(op)));
			return;
		} catch (Exception e) {

		}
	}
%>
<form action="" method="post">
	<input type="text" name="scriptName">
	<input type="text" name="deviceId">
</form>