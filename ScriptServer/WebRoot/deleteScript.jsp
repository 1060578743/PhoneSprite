<%@page import="com.lym.model.*,com.lym.dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>删除脚本</title>
<link rel="stylesheet" href="css/style.css" media="screen"
	type="text/css" />
</head>
<%
	request.setCharacterEncoding("UTF-8");
	String scriptName = request.getParameter("scriptName");
	String scriptUrl = request.getParameter("scriptUrl");
	if (scriptName != null) {
		ScriptInfo scriptInfo = new ScriptInfo();
		scriptInfo.setName(scriptName);
		scriptInfo.setUrl(scriptUrl);
		ScriptInfoDao sid = new ScriptInfoDao();
		sid.deleteScriptInfo(scriptInfo);
		out.print("<script>alert('删除成功！');</script>");
	}
%>
<body>
	<div class="container">
		<form id="contact" action="" method="post">
			<h3>删除脚本</h3>
			<h4>请将脚本名字填好提交即可</h4>
			<fieldset>
				<input placeholder="脚本名字" type="text" tabindex="1" name="scriptName"
					required autofocus>
			</fieldset>
			<!-- 
			<fieldset>
				<input placeholder="Your Email Address" type="email" tabindex="2"
					required>
			</fieldset>
			<fieldset>
				<input placeholder="Your Phone Number" type="tel" tabindex="3"
					required>
			</fieldset>
			<fieldset>
				<input placeholder="脚本地址 http://" type="url" tabindex="4"
					name="scriptUrl" required>
			</fieldset>
			 -->
			<!--  
			<fieldset>
				<textarea placeholder="Type your Message Here...." tabindex="5"
					required></textarea>
			</fieldset>
			 -->
			<fieldset>
				<button name="submit" type="submit" id="contact-submit"
					data-submit="...Sending">提交</button>
			</fieldset>
		</form>

	</div>
	<div style="text-align: center; clear: both">
		<script src="/gg_bd_ad_720x90.js" type="text/javascript"></script>
		<script src="/follow.js" type="text/javascript"></script>
	</div>
</body>
</html>