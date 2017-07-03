package com.lym.xposed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.RemoteException;

import com.lym.xposed.aidl.IActivity;
import com.lym.xposed.aidl.IView;
import com.lym.xposed.utils.LogUtil;

public class IActivityImpl extends IActivity.Stub {
	Activity activity;

	public IActivityImpl(Activity activity) {
		super();
		this.activity = activity;
	}

	@SuppressLint("NewApi")
	@Override
	public void finishAll() throws RemoteException {
		try {
			activity.finishAffinity();
		} catch (Exception e) {
			LogUtil.log(e);
		}
	}

	@Override
	public IView getRootView() throws RemoteException {
		return new IViewImpl(activity.getWindow().getDecorView(), activity);
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void finish() throws RemoteException {
		try {
			activity.finish();
		} catch (Exception e) {
			LogUtil.log(e);
		}
	}

}
