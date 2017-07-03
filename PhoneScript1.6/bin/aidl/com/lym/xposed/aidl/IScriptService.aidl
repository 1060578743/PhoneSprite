package com.lym.xposed.aidl;
import com.lym.xposed.aidl.IActivity;
interface IScriptService{
	void onConnect(String name,in IActivity activity);
}