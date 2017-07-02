<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.lym.server.*" pageEncoding="UTF-8"%>
<%
	NettyServer.getInstance().startServer();
	out.println("server is start...");
%>