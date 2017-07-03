package com.lym.xposed.application;

import android.app.Application;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.lym.xposed.R;
import com.lym.xposed.script.Script;
import com.lym.xposed.utils.UUIDS;

public class App extends Application {
	public static App app;
	// 悬浮按钮
	ImageButton floatBtn;
	// 定义浮动窗口布局
	LinearLayout floatView;
	// 创建浮动窗口设置布局参数的对象
	WindowManager windowManager;
	WindowManager.LayoutParams layoutParams;
	Handler mHandler = new Handler();
	public Handler handle = new Handler();

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		UUIDS.buidleID(this).check();
	}

	public void createFloatView() {

		if (floatView != null) {
			return;
		}
		int w = getResources().getDisplayMetrics().widthPixels;
		int h = getResources().getDisplayMetrics().heightPixels;
		layoutParams = new WindowManager.LayoutParams();
		// 通过getApplication获取的是WindowManagerImpl.CompatModeWrapper
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		// 设置window type
		layoutParams.type = LayoutParams.TYPE_PHONE;
		// 设置图片格式，效果为背景透明
		layoutParams.format = PixelFormat.RGBA_8888;
		// 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
		layoutParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
		// 调整悬浮窗显示的停靠位置为左侧置顶
		layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
		// 以屏幕左上角为原点，设置x、y初始值，相对于gravity

		// 设置悬浮窗口长宽数据
		layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		LayoutInflater inflater = LayoutInflater.from(this);
		// 获取浮动窗口视图所在布局
		floatView = (LinearLayout) inflater.inflate(
				R.layout.alert_window_button, null);

		// 添加mFloatLayout
		windowManager.addView(floatView, layoutParams);

		// 浮动窗口按钮
		floatBtn = (ImageButton) floatView
				.findViewById(R.id.alert_window_imagebtn);

		// 测量布局
		floatView.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

		// 设置监听浮动窗口的触摸移动
		floatBtn.setOnTouchListener(new OnTouchListener() {
			float downX, downY;
			boolean isClick;
			float startX, startY;

			public boolean onTouch(View v, MotionEvent event) {
				float rx = event.getRawX();
				float ry = event.getRawY();

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					isClick = true;
					downX = rx;
					downY = ry;
					startX = layoutParams.x;
					startY = layoutParams.y;
					break;
				case MotionEvent.ACTION_MOVE:
					if (Math.abs(rx - downX) > 10 || Math.abs(ry - downY) > 10) {
						isClick = false;
					}
					// getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
					layoutParams.x = (int) (startX - downX + rx);
					// 减25为状态栏的高度
					layoutParams.y = (int) (startY - downY + ry);
					// 刷新
					windowManager.updateViewLayout(floatView, layoutParams);
					break;
				case MotionEvent.ACTION_UP:
					if (isClick) {
						// 判断是启动还是关闭
						scriptSwitch();
					}
					break;
				}
				return false;
			}

		});
		layoutParams.x = w - floatBtn.getWidth();
		layoutParams.y = h / 2;
		windowManager.updateViewLayout(floatView, layoutParams);
	}

	public void drawFloatView(boolean isRunning) {
		if (isRunning) {
			createFloatView();
			floatBtn.setBackgroundResource(R.drawable.circle_red);
		} else {
			floatBtn.setBackgroundResource(R.drawable.circle_cyan);
		}
	}

	public void removeFloatView() {
		if (floatView != null) {
			// 移除悬浮窗口
			windowManager.removeView(floatView);
			floatView = null;
		}
	}

	/**
	 * 脚本开关
	 */
	public void scriptSwitch() {
		if (Script.currentScript != null) {
			if (Script.currentScript.isStart()) {
				Script.currentScript.stop();
			} else {
				Script.currentScript.start();
			}
		}
	}
}
