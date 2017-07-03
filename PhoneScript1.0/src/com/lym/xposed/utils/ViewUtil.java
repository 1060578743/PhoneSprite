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
	 * 工具类名查找View
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
	 * 根据id查找View
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
	 * 根据正则表达式匹配查找View
	 * 
	 * @param result
	 * @param pattern
	 *            正则表达式的Pattern
	 * @param isContainsMode
	 *            是否是包含模式
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
	 * 根据正则表达式匹配查找View，如果是包含模式，即字符串包含，那么根据子View是否包含正则表达式的文本查找
	 * 
	 * @param result
	 * @param text
	 *            正则表达式
	 * @param isContainsMode
	 *            是否是包含模式
	 * @param root
	 */
	public static void findViewsByText(ArrayList<View> result, String text,
			boolean isContaisMode, View root) {
		Pattern pattern = Pattern.compile(text);
		findViewsByText(result, pattern, isContaisMode, root);
	}

	/**
	 * 获取View的Text
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
			// 如果长度是3那么第3部分是数字，如果长度是2，那么第2部分是数字，否则数字为0
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
			// 不匹配或者表达式错误，返回空
			return null;
		}
		return result;
	}

	/**
	 * 包括5种选择器，index可以省略，省略返回第1个匹配的子View
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
	 * 例如：>>child::1>>text::regx::1>>contains::regx::1>>id::0::0>>class::
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
	 * 选择第(i+1)个子View
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
	 * 根据class查找第(i+1)个匹配的子View
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
	 * 根据class查找第1个匹配的子View
	 * 
	 * @param clz
	 * @param root
	 * @return
	 */
	public static View selectClass(String clz, View root) {
		return selectClass(clz, 0, root);
	}

	/**
	 * 根据id查找第（i+1）个匹配的子View
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
	 * 根据id查找第一个匹配的子View
	 * 
	 * @param id
	 * @param root
	 * @return
	 */
	public static View selectId(int id, View root) {
		return selectId(id, 0, root);
	}

	/**
	 * 根据正则表达式查找第(i+1)个匹配字符串的子View
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
	 * 根据正则表达式查找第1个匹配字符串的子View
	 * 
	 * @param regx
	 * @param root
	 * @return
	 */
	public static View selectText(String regx, View root) {
		return selectText(regx, 0, root);
	}

	/**
	 * 根据正则表达式查找第(i+1)个包含正则表达式字符串的子View
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
	 * 根据正则表达式查找第1个包含正则表达式字符串的子View
	 * 
	 * @param regx
	 * @param root
	 * @return
	 */
	public static View selectTextContains(String regx, View root) {
		return selectTextContains(regx, 0, root);
	}
}
