package com.lym.xposed;

import de.robv.android.xposed.XC_MethodHook;

public abstract class MH extends XC_MethodHook {
	

	@Override
	protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		before(param);
	}

	@Override
	protected void afterHookedMethod(MethodHookParam param) throws Throwable {
		after(param);

	}

	protected abstract void before(MethodHookParam param) throws Throwable;

	protected abstract void after(MethodHookParam param) throws Throwable;
}
