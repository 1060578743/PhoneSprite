package com.lym.xposed.script;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.lym.xposed.aidl.IView;
import com.lym.xposed.utils.LogUtil;
import com.lym.xposed.utils.SU;
import com.lym.xposed.utils.ViewUtil;
import com.lym.xposed.utils.ViewUtil.Selector;

public class ScriptImpl extends Script {
	public void waitActivity(String act) throws Exception {
		waitActivity(act, 10 * 1000);
	}

	public void waitActivity(String act, long timeout) throws Exception {
		long end = System.currentTimeMillis() + timeout;
		do {
			String top = getTopActivityName();
			if (top != null) {
				if (top.contains(act)) {
					return;
				}
			}
			Thread.sleep(1000);
			if (System.currentTimeMillis() > end) {
				throw new Exception("wait activity time out");
			}
		} while (true);
	}

	private IView waitId(int id) throws Exception {
		return waitId(id, 10 * 1000);
	}

	private IView waitId(int id, long timeout) throws Exception {
		long end = System.currentTimeMillis() + timeout;
		do {
			IView view = select(new Selector().selectId(id));
			if (view.exist()) {
				return view;
			}
			Thread.sleep(1000);
			if (System.currentTimeMillis() > end) {
				throw new Exception("wait activity time out");
			}
		} while (true);
	}

	IView select(ViewUtil.Selector select) throws Exception {
		IView root = getTopActivity().getRootView();
		return root.select(select.toString());
	}

	void attention() throws Exception {
		SU.stopPackage("com.baidu.mbaby");
		Thread.sleep(1000);
		SU.startActivity("com.baidu.mbaby/.activity.search.NewSearchActivity");
		waitActivity("NewSearchActivity");
		Thread.sleep(1000);
		// 输入
		IView edit = select(new Selector().selectId(baby.id.key_word));
		edit.setText(getOption());
		Thread.sleep(1000);
		// 搜索
		IView btn = select(new Selector().selectId(baby.id.app_search_button));
		btn.click();
		Thread.sleep(500);
		// 等待搜索结果点击第一个用户
		IView first_user = waitId(baby.id.first_layout);
		if (first_user.exist()) {
			first_user.click();
		}
		// 等待用户界面
		waitActivity("UserArticleListActivity");
		// 等待粉丝按钮
		IView fans_linear = waitId(baby.id.fans_linear);
		if (fans_linear.exist()) {
			fans_linear.click();
		}
		waitActivity("UserFansListActivity");
		Thread.sleep(1000);
		// 遍历列表
		do {
			IView list = select(new Selector().selectId(baby.id.list));
			int count = list.getChildCount();
			for (int i = 0; i < count; i++) {
				// 判断是否满足条件，满足则关注
				IView child = list.selectChild(i);
				IView infoVIew = child.selectId(baby.id.fans_user_info);
				String info = infoVIew.getText();
				if (info.contains("孕5周") || info.contains("孕6周")
						|| info.contains("孕7周") || info.contains("孕8周")
						|| info.contains("孕9周")) {
					IView btnAttention = child.selectChild(0).selectChild(2)
							.selectChild(0);
					if (btnAttention.isVisible()) {
						btnAttention.click();
						Thread.sleep(1000);
					}
				}
			}
			// 滚动
			try {
				list.scroll(list.getHeight(), 2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Thread.sleep(3000);
			// 判断是否结束
		} while (true);
	}

	@Override
	public void scriptMain() throws InterruptedException {
		do {
			try {
				attention();
			} catch (Exception e) {
				LogUtil.log(e);
				if (e instanceof InterruptedException) {
					break;
				}
			}
			Thread.sleep(1000);
		} while (isStart());

	}

	@Override
	public View getView() {
		LinearLayout layout = new LinearLayout(getContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		final EditText edit_user = new EditText(getContext());
		edit_user.setLayoutParams(params);
		edit_user.setHint("请输入要关注的用户名字");
		layout.addView(edit_user);
		Button btnSave = new Button(getContext());
		btnSave.setLayoutParams(params);
		btnSave.setText("保存");
		btnSave.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String user = edit_user.getText().toString();
				saveOption(user);
			}
		});
		layout.addView(btnSave);
		final Handler downLoadHandler = new Handler();
		new Thread() {
			public void run() {
				downLoadHandler.post(new Runnable() {

					public void run() {
						edit_user.setText(getOption());
					}
				});
			};
		}.start();

		return layout;
	}

	@Override
	public void stop() {
		super.stop();
	}
}
