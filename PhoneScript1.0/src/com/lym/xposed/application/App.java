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
	// ������ť
	ImageButton floatBtn;
	// ���帡�����ڲ���
	LinearLayout floatView;
	// ���������������ò��ֲ����Ķ���
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
		// ͨ��getApplication��ȡ����WindowManagerImpl.CompatModeWrapper
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		// ����window type
		layoutParams.type = LayoutParams.TYPE_PHONE;
		// ����ͼƬ��ʽ��Ч��Ϊ����͸��
		layoutParams.format = PixelFormat.RGBA_8888;
		// ���ø������ڲ��ɾ۽���ʵ�ֲ���������������������ɼ����ڵĲ�����
		layoutParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
		// ������������ʾ��ͣ��λ��Ϊ����ö�
		layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
		// ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ�������gravity

		// �����������ڳ�������
		layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		LayoutInflater inflater = LayoutInflater.from(this);
		// ��ȡ����������ͼ���ڲ���
		floatView = (LinearLayout) inflater.inflate(
				R.layout.alert_window_button, null);

		// ���mFloatLayout
		windowManager.addView(floatView, layoutParams);

		// �������ڰ�ť
		floatBtn = (ImageButton) floatView
				.findViewById(R.id.alert_window_imagebtn);

		// ��������
		floatView.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

		// ���ü����������ڵĴ����ƶ�
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
					// getRawX�Ǵ���λ���������Ļ�����꣬getX������ڰ�ť������
					layoutParams.x = (int) (startX - downX + rx);
					// ��25Ϊ״̬���ĸ߶�
					layoutParams.y = (int) (startY - downY + ry);
					// ˢ��
					windowManager.updateViewLayout(floatView, layoutParams);
					break;
				case MotionEvent.ACTION_UP:
					if (isClick) {
						// �ж����������ǹر�
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
			// �Ƴ���������
			windowManager.removeView(floatView);
			floatView = null;
		}
	}

	/**
	 * �ű�����
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
