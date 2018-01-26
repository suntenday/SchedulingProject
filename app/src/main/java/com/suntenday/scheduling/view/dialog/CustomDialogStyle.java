package com.suntenday.scheduling.view.dialog;

import android.view.Gravity;
import android.view.View;

import java.io.Serializable;

/**
 * dialog自定义样式
 *
 * @author suntenday
 */
public class CustomDialogStyle implements Serializable {

	private static final long serialVersionUID = 8765015488637326929L;

	public static int CUSTOM = 0;
	public static int FULL = 1;

	public static int DEFAULT = 0;
	public static int HTMLTEXT = 1;
	public static int PICTURE = 2;

	private String title = "";    //标题
	private CharSequence content = "";    //文字内容
	private int gravity = Gravity.LEFT;    //文字内容布局
	private String confirmText = "";    //确认按钮文字
	private String cancelText = "";    //取消按钮文字
	private int confirmColor = -1;    //确认按钮颜色
	private int cancelColor = -1;    //取消按钮颜色
	private boolean CanceledOnTouchOutside = false;
	private int type = -1;    //是否固定高度
	private int contentType = -1;    //内容类型
	private int contentImage = -1;    //图片内容
	private View contentView;

	public CustomDialogStyle(String title, CharSequence content) {
		this.title = title;
		this.content = content;
		this.type = CUSTOM;
		this.contentType = DEFAULT;
	}

	public CustomDialogStyle(String title, CharSequence content, int type, int contentType) {
		this.title = title;
		this.content = content;
		this.type = type;
		this.contentType = contentType;
	}

	public CustomDialogStyle(String title, View view) {
		this.title = title;
		this.contentView = view;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CharSequence getContent() {
		return content;
	}

	public void setContent(CharSequence content) {
		this.content = content;
	}

	public int getGravity() {
		return gravity;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public String getConfirmText() {
		return confirmText;
	}

	public void setConfirmText(String confirmText) {
		this.confirmText = confirmText;
	}

	public String getCancelText() {
		return cancelText;
	}

	public void setCancelText(String cancelText) {
		this.cancelText = cancelText;
	}

	public int getConfirmColor() {
		return confirmColor;
	}

	public void setConfirmColor(int confirmColor) {
		this.confirmColor = confirmColor;
	}

	public int getCancelColor() {
		return cancelColor;
	}

	public void setCancelColor(int cancelColor) {
		this.cancelColor = cancelColor;
	}

	public boolean isCanceledOnTouchOutside() {
		return CanceledOnTouchOutside;
	}

	public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
		CanceledOnTouchOutside = canceledOnTouchOutside;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public int getContentImage() {
		return contentImage;
	}

	public void setContentImage(int contentImage) {
		this.contentImage = contentImage;
	}

	public View getContentView() {
		return contentView;
	}

	public void setContentView(View contentView) {
		this.contentView = contentView;
	}
}
