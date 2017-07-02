<%@page import="com.lym.dao.DeviceDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.lym.server.*" pageEncoding="UTF-8"%>
<%
	NettyServer.getInstance().stopServer();
	out.println("server is stop...");
%>