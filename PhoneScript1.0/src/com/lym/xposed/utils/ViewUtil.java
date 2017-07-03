package com.lym.xposed.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtil {

	public static class Selector {
		public static final String SPLIT_1 = ">>";
		public static final String SPLIT_2 = "::";
		private StringBuilder selector = new StringBuilder();

		private String split_1, split_2;

		public Selector() {
			this.split_1 = SPLIT_1;
			this.split_2 = SPLIT_2;
		}

		public Selector(String split_1, String split_2) {
			this.split_1 = split_1;
			this.split_2 = split_2;
		}

		public Selector child(int i) {
			this.selector.append(SPLIT_1 + "child" + SPLIT_2 + i);
			return this;
		}

		@Override
		public String toString() {
			return selector.toString();
		}

		public String getSplit_1() {
			return split_1;
		}

		public String getSplit_2() {
			return split_2;
		}

		public Selector selectClass(String clz) {
			return selectClass(clz, 0);
		}

		public Selector selectClass(Class<?> clz) {
			return selectClass(clz.getName(), 0);
		}

		public Selector selectClass(Class<?> clz, int index) {
			return selectClass(clz.getName(), index);
		}

		public Selector selectClass(String clz, int index) {
			this.selector.append(SPLIT_1 + "class" + SPLIT_2 + clz + SPLIT_2
					+ index);
			return this;
		}

		public Selector selectContains(String regx) {
			return selectContains(regx, 0);
		}

		public Selector selectContains(String regx, int index) {
			this.selector.append(SPLIT_1 + "contains" + SPLIT_2 + regx
					+ SPLIT_2 + index);
			return this;
		}

		public Selector selectId(int id) {
			return selectId(id, 0);
		}

		public Selector selectId(int id, int index) {
			this.selector.append(SPLIT_1 + "id" + SPLIT_2 + id + SPLIT_2
					+ index);
			return this;
		}

		public Selector selectText(String regx) {
			return selectText(regx, 0);
		}

		public Selector selectText(String regx, int index) {
			this.selector.append(SPLIT_1 + "text" + SPLIT_2 + regx + SPLIT_2
					+ index);
			return this;
		}
	}

	/**
	 * ������������View
	 * 
	 * @param result
	 * @param clz
	 * @param root
	 */
	public static void findViewsByClass(ArrayList<View> result, String clz,
			View root) {
		if (root.getClass().getName().equals(clz)) {
			result.add(root);
		}
		if (root instanceof ViewGroup) {
			int count = ((ViewGroup) root).getChildCount();
			for (int i = 0; i < count; i++) {
				View child = ((ViewGroup) root).getChildAt(i);
				findViewsByClass(result, clz, child);
			}
		}
	}

	/**
	 * ����id����View
	 * 
	 * @param result
	 * @param id
	 * @param root
	 */
	public static void findViewsById(ArrayList<View> result, int id, View root) {
		if (root.getId() == id) {
			result.add(root);
		}
		if (root instanceof ViewGroup) {
			int count = ((ViewGroup) root).getChildCount();
			for (int i = 0; i < count; i++) {
				View child = ((ViewGroup) root).getChildAt(i);
				findViewsById(result, id, child);
			}
		}
	}

	/**
	 * ����������ʽƥ�����View
	 * 
	 * @param result
	 * @param pattern
	 *            ������ʽ��Pattern
	 * @param isContainsMode
	 *            �Ƿ��ǰ���ģʽ
	 * @param root
	 */
	public static void findViewsByText(ArrayList<View> result, Pattern pattern,
			boolean isContaisMode, View root) {
		Matcher m = pattern.matcher(getViewText(root));
		if (isContaisMode) {
			if (m.find()) {
				result.add(root);
			}
		} else {
			if (m.matches()) {
				result.add(root);
			}
		}
		if (root instanceof ViewGroup) {
			int count = ((ViewGroup) root).getChildCount();
			for (int i = 0; i < count; i++) {
				View child = ((ViewGroup) root).getChildAt(i);
				findViewsByText(result, pattern, isContaisMode, child);
			}
		}
	}

	/**
	 * ����������ʽƥ�����View������ǰ���ģʽ�����ַ�����������ô������View�Ƿ����������ʽ���ı�����
	 * 
	 * @param result
	 * @param text
	 *            ������ʽ
	 * @param isContainsMode
	 *            �Ƿ��ǰ���ģʽ
	 * @param root
	 */
	public static void findViewsByText(ArrayList<View> result, String text,
			boolean isContaisMode, View root) {
		Pattern pattern = Pattern.compile(text);
		findViewsByText(result, pattern, isContaisMode, root);
	}

	/**
	 * ��ȡView��Text
	 * 
	 * @param view
	 * @return
	 */
	public static String getViewText(View view) {
		String txt = "";
		try {
			Method method = view.getClass().getMethod("getText");
			txt = method.invoke(view).toString();
		} catch (Exception e) {
			LogUtil.log(e);
		}
		return txt;
	}

	public static View select(Selector selector, View root) {
		return select(selector.toString(), selector.getSplit_1(),
				selector.getSplit_2(), root);
	}

	public static View select(String text, String split_1, String split_2,
			View root) {
		View result = root;
		String selectors[] = text.split(split_1);
		for (String selector : selectors) {
			if (selector == null || "".equals(selector)) {
				continue;
			}
			String kvi[] = selector.split(split_2);
			String k = kvi[0];
			String v = kvi.length > 1 ? kvi[1] : null;
			// ���������3��ô��3���������֣����������2����ô��2���������֣���������Ϊ0
			String i = kvi.length > 2 ? kvi[2] : null;
			if (k.equals("child")) {
				result = selectChild(v == null ? 0 : Integer.parseInt(v),
						result);
				continue;
			}
			if ("id".equals(k)) {
				result = selectId(Integer.parseInt(v),
						i == null ? 0 : Integer.parseInt(i), root);
				continue;
			}
			if ("text".equals(k)) {
				result = selectText(v, i == null ? 0 : Integer.parseInt(i),
						root);
				continue;
			}
			if ("contains".equals(k)) {
				result = selectTextContains(v,
						i == null ? 0 : Integer.parseInt(i), root);
				continue;
			}
			if ("class".equals(k)) {
				result = selectClass(v, i == null ? 0 : Integer.parseInt(i),
						root);
				continue;
			}
			// ��ƥ����߱��ʽ���󣬷��ؿ�
			return null;
		}
		return result;
	}

	/**
	 * ����5��ѡ������index����ʡ�ԣ�ʡ�Է��ص�1��ƥ�����View
	 * 
	 * child::index
	 * 
	 * text::regx::index
	 * 
	 * contains::regx::index
	 * 
	 * id::id::index
	 * 
	 * class::class::index
	 * 
	 * ���磺>>child::1>>text::regx::1>>contains::regx::1>>id::0::0>>class::
	 * textView::0
	 * 
	 * @param text
	 * @param root
	 * @return
	 */

	public static View select(String text, View root) {
		return select(text, Selector.SPLIT_1, Selector.SPLIT_2, root);
	}

	/**
	 * ѡ���(i+1)����View
	 * 
	 * @param i
	 * @param root
	 * @return
	 */
	public static View selectChild(int i, View root) {
		if (root instanceof ViewGroup) {
			int count = ((ViewGroup) root).getChildCount();
			if (count > i) {
				return ((ViewGroup) root).getChildAt(i);
			}
		}
		return null;
	}

	/**
	 * ����class���ҵ�(i+1)��ƥ�����View
	 * 
	 * @param clz
	 * @param i
	 * @param root
	 * @return
	 */
	public static View selectClass(String clz, int i, View root) {
		ArrayList<View> views = new ArrayList<View>();
		findViewsByClass(views, clz, root);
		if (views.size() > i) {
			return views.get(i);
		}
		return null;
	}

	/**
	 * ����class���ҵ�1��ƥ�����View
	 * 
	 * @param clz
	 * @param root
	 * @return
	 */
	public static View selectClass(String clz, View root) {
		return selectClass(clz, 0, root);
	}

	/**
	 * ����id���ҵڣ�i+1����ƥ�����View
	 * 
	 * @param id
	 * @param i
	 * @param root
	 * @return
	 */
	public static View selectId(int id, int i, View root) {
		ArrayList<View> views = new ArrayList<View>();
		findViewsById(views, id, root);
		if (views.size() > i) {
			return views.get(i);
		}
		return null;

	}

	/**
	 * ����id���ҵ�һ��ƥ�����View
	 * 
	 * @param id
	 * @param root
	 * @return
	 */
	public static View selectId(int id, View root) {
		return selectId(id, 0, root);
	}

	/**
	 * ����������ʽ���ҵ�(i+1)��ƥ���ַ�������View
	 * 
	 * @param regx
	 * @param root
	 * @return
	 */
	public static View selectText(String regx, int i, View root) {
		ArrayList<View> views = new ArrayList<View>();
		findViewsByText(views, regx, false, root);
		if (views.size() > i) {
			return views.get(i);
		}
		return null;
	}

	/**
	 * ����������ʽ���ҵ�1��ƥ���ַ�������View
	 * 
	 * @param regx
	 * @param root
	 * @return
	 */
	public static View selectText(String regx, View root) {
		return selectText(regx, 0, root);
	}

	/**
	 * ����������ʽ���ҵ�(i+1)������������ʽ�ַ�������View
	 * 
	 * @param regx
	 * @param i
	 * @param root
	 * @return
	 */
	public static View selectTextContains(String regx, int i, View root) {
		ArrayList<View> views = new ArrayList<View>();
		findViewsByText(views, regx, true, root);
		if (views.size() > i) {
			return views.get(i);
		}
		return null;
	}

	/**
	 * ����������ʽ���ҵ�1������������ʽ�ַ�������View
	 * 
	 * @param regx
	 * @param root
	 * @return
	 */
	public static View selectTextContains(String regx, View root) {
		return selectTextContains(regx, 0, root);
	}
}
