<%@page import="com.lym.model.ScriptOption"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.lym.dao.ScriptOptionDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String deviceId = request.getParameter("deviceId");
	String scriptName = request.getParameter("scriptName");
	String option = request.getParameter("option");
	if (option != null && deviceId != null && scriptName != null) {
		Gson gson = new Gson();
		try {
			ScriptOption op = new ScriptOption();
			op.setDeviceId(deviceId);
			op.setScriptName(scriptName);
			op.setOption(option);
			ScriptOptionDao sod = new ScriptOptionDao();
			sod.addOrUpdate(op);
			out.print("ok");
			return;
		} catch (Exception e) {

		}
	}
%>
<form action="" method="post">
	<input type="text" name="scriptName"> <input type="text"
		name="deviceId"> <input type="text" name="option">
</form>