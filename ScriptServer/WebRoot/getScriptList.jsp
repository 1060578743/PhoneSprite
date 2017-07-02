<%@page import="com.google.gson.Gson"%>
<%@page import="com.lym.dao.ScriptInfoDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ScriptInfoDao sid = new ScriptInfoDao();
	Gson gson = new Gson();
	out.print(gson.toJson(sid.list()));
%>