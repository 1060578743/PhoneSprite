<%@page import="com.google.gson.Gson"%>
<%@page import="com.lym.dao.DeviceDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	DeviceDao dd = new DeviceDao();
	Gson gson = new Gson();
	out.print(gson.toJson(dd.list()));
%>