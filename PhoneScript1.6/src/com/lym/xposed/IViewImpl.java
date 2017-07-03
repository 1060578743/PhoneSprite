package com.lym.xposed;

import java.lang.reflect.Method;

import android.app.Activity;
import android.os.RemoteException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.lym.xposed.aidl.IView;
import com.lym.xposed.utils.ViewUtil;

import de.robv.android.xposed.XposedBridge;

public class IViewImpl extends IView.Stub {

	private Activity activity;
	private View view;

	public IViewImpl(View view, Activity activity) {
		this.view = view;
		this.activity = activity;

	}

	@Override
	public void click() throws RemoteException {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				view.performClick();
			}
		});
	}

	@Override
	public boolean exist() throws RemoteException {
		return view != null;
	}

	@Override
	public int getChildCount() throws RemoteException {
		if (view instanceof ViewGroup) {
			return ((ViewGroup) view).getChildCount();
		}
		return 0;
	}

	@Override
	public int getHeight() throws RemoteException {
		return view.getHeight();
	}

	@Override
	public int getId() throws RemoteException {
		return view.getId();
	}

	@Override
	public String getText() throws RemoteException {
		String txt = null;
		try {
			Method method;
			method = view.getClass().getMethod("getText");
			txt = method.invoke(view).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txt;
	}

	@Override
	public int getWidth() throws RemoteException {
		return view.getWidth();
	}

	@Override
	public int getX() throws RemoteException {
		int[] location = new int[4];
		view.getLocationOnScreen(location);
		return location[0];
	}

	@Override
	public int getY() throws RemoteException {
		int[] location = new int[4];
		view.getLocationOnScreen(location);
		return location[1];
	}

	@Override
	public boolean isVisible() {
		return view.isShown();
	}

	@Override
	public void longClick() throws RemoteException {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				view.performLongClick();
			}
		});
	}

	@Override
	public void scroll(final int dis, final int time) throws RemoteException {
		if (view instanceof ListView) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					((ListView) view).smoothScrollBy(dis, time);
				}
			});
		}

	}

	@Override
	public void setText(final String text) throws RemoteException {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {

				EditText edit = (EditText) view;
				edit.setText(text);
			}
		});
	}

	@Override
	public IView select(String select) throws RemoteException {
		XposedBridge.log(select);
		return new IViewImpl(ViewUtil.select(select, view), activity);
	}

	@Override
	public IView selectWithSplit(String select, String split1, String split2)
			throws RemoteException {
		return new IViewImpl(ViewUtil.select(select, split1, split2, view),
				activity);
	}

	@Override
	public IView selectId(int id) throws RemoteException {
		return selectIdIndex(id, 0);
	}

	@Override
	public IView selectIdIndex(int id, int index) throws RemoteException {
		return new IViewImpl(ViewUtil.selectId(id, index, view), activity);
	}

	@Override
	public IView selectClass(String clz) throws RemoteException {
		return selectClassIndex(clz, 0);
	}

	@Override
	public IView selectClassIndex(String clz, int i) throws RemoteException {
		return new IViewImpl(ViewUtil.selectClass(clz, i, view), activity);
	}

	@Override
	public IView selectText(String text) throws RemoteException {
		return selectTextIndex(text, 0);
	}

	@Override
	public IView selectTextIndex(String text, int i) throws RemoteException {
		return new IViewImpl(ViewUtil.selectText(text, i, view), activity);
	}

	@Override
	public IView selectContains(String text) throws RemoteException {
		return selectContainsIndex(text, 0);
	}

	@Override
	public IView selectContainsIndex(String text, int i) throws RemoteException {
		return new IViewImpl(ViewUtil.selectTextContains(text, i, view),
				activity);
	}

	@Override
	public IView selectChild(int i) throws RemoteException {
		return new IViewImpl(ViewUtil.selectChild(i, view), activity);
	}

	@Override
	public IView selectRes(String res) throws RemoteException {
		int id = activity.getResources().getIdentifier(res, "id",
				activity.getPackageName());
		return selectId(id);
	}

	@Override
	public IView parent() throws RemoteException {
		// TODO Auto-generated method stub
		return new IViewImpl((View) view.getParent(), activity);
	}

}
