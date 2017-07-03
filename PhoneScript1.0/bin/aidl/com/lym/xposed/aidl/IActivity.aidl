package com.lym.xposed.aidl;
import com.lym.xposed.aidl.IView;
interface IActivity {
	IView getRootView();
	void finish();
	void finishAll();
	
}
