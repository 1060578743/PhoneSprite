package com.lym.xposed.aidl;
interface IView{
	IView select(String select);
	IView selectWithSplit(String select,String split1,String split2);
	IView selectId(int id);
	IView selectIdIndex(int id,int index);
	IView selectClass(String clz);
	IView selectClassIndex(String clz,int i);
	IView selectText(String text);
	IView selectTextIndex(String text,int i);
	IView selectContains(String text);
	IView selectContainsIndex(String text,int i);
	IView selectChild(int i);
	IView selectRes(String res);
	IView parent();
	boolean exist();
	boolean isVisible();
	String getText();
	int getId();
	int getWidth();
	int getHeight();
	int getX();
	int getY();
	int getChildCount();
	void setText(String text);
	void click();
	void longClick();
	void scroll(int dis,int time);
}