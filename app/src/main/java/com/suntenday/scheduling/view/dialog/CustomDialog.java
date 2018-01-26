package com.suntenday.scheduling.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.suntenday.scheduling.R;
import com.suntenday.scheduling.utils.PixUtils;
import com.suntenday.scheduling.utils.StringUtils;


/**
 * dialog提示框
 *
 * @author suntenday
 */
public class CustomDialog extends AlertDialog implements View.OnClickListener {

	private Context mContext;
	private CustomDialogStyle mStyle;
	private View.OnClickListener mConfirmListener;
	private View.OnClickListener mCancelListener;
	private boolean isCancelDefaul = true;

	private TextView mTitleView;
	private LinearLayout mTitleRule;
	private LinearLayout mContentLinearlayout;
	private TextView mContentView;
	private ImageView mContentImage;
	private Button btnConfirm;
	private Button btnCancel;
	private LinearLayout mBtnRule;

	public CustomDialog(Context context) {
		super(context, R.style.mystyle);
		mContext = context;
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	public CustomDialog(Context context, String title, String content,
	                    View.OnClickListener confirmListener) {
		super(context, R.style.mystyle);
		mContext = context;
		mStyle = new CustomDialogStyle(title, content);
		mConfirmListener = confirmListener;
		isCancelDefaul = true;
	}

	public CustomDialog(Context context, String title, String content,
	                    View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
		super(context, R.style.mystyle);
		mContext = context;
		mStyle = new CustomDialogStyle(title, content);
		mConfirmListener = confirmListener;
		mCancelListener = cancelListener;
		isCancelDefaul = false;
	}

	public CustomDialog(Context context, CustomDialogStyle style,
	                    View.OnClickListener confirmListener) {
		super(context, R.style.mystyle);
		mContext = context;
		mStyle = style;
		mConfirmListener = confirmListener;
		isCancelDefaul = true;
	}

	public CustomDialog(Context context, CustomDialogStyle style,
	                    View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
		super(context, R.style.mystyle);
		mContext = context;
		mStyle = style;
		mConfirmListener = confirmListener;
		mCancelListener = cancelListener;
		isCancelDefaul = false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom, null);
		int padding = mContext.getResources().getDimensionPixelSize(R.dimen.large_spacing) * 2;
		LayoutParams layoutParams = new LayoutParams(PixUtils.getDisplayMetricsWidth(mContext) - padding, LayoutParams.WRAP_CONTENT);
		if (mStyle.getType() > 0) {
			if (mStyle.getType() == CustomDialogStyle.FULL) {
				dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_full, null);
				layoutParams = new LayoutParams(PixUtils.getDisplayMetricsWidth(mContext) - padding, PixUtils.getDisplayMetricsHeight(mContext) - padding * 2);
			}
		}
		this.setContentView(dialogView, layoutParams);
		this.setCanceledOnTouchOutside(mStyle.isCanceledOnTouchOutside());

		// 根据id在布局中找到控件对象
		mTitleView = (TextView) dialogView.findViewById(R.id.dialog_custom_title);
		mTitleRule = (LinearLayout) dialogView.findViewById(R.id.dialog_custom_title_rule_layout);
		mContentLinearlayout = (LinearLayout) dialogView.findViewById(R.id.dialog_custom_content_layout);
		mContentView = (TextView) dialogView.findViewById(R.id.dialog_custom_content_text);
		mContentImage = (ImageView) dialogView.findViewById(R.id.dialog_custom_content_picture);
		btnConfirm = (Button) dialogView.findViewById(R.id.btn_confirm);
		btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
		mBtnRule = (LinearLayout) dialogView.findViewById(R.id.btn_rule_layout);

		if (StringUtils.isStrNotEmpty(mStyle.getTitle())) {
			mTitleView.setText(mStyle.getTitle());
		} else {
			mTitleRule.setVisibility(View.GONE);
		}
		if (mStyle.getContentView() != null) {
			mContentLinearlayout.addView(mStyle.getContentView(),new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			mContentView.setVisibility(View.GONE);
		}else {
			if (mStyle.getContentType() == CustomDialogStyle.DEFAULT) {
				mContentView.setVisibility(View.VISIBLE);
				mContentImage.setVisibility(View.GONE);
				if (!TextUtils.isEmpty(mStyle.getContent())) {
					mContentView.setText(mStyle.getContent());
					mContentView.setGravity(mStyle.getGravity());
				} else {
					mTitleRule.setVisibility(View.GONE);
				}
			} else if (mStyle.getContentType() == CustomDialogStyle.HTMLTEXT) {
				mContentView.setVisibility(View.VISIBLE);
				mContentImage.setVisibility(View.GONE);
				if (!TextUtils.isEmpty(mStyle.getContent())) {
					mContentView.setText(Html.fromHtml(mStyle.getContent().toString()));
				} else {
					mTitleRule.setVisibility(View.GONE);
				}
			} else if (mStyle.getContentType() == CustomDialogStyle.PICTURE) {
				mContentView.setVisibility(View.GONE);
				mContentImage.setVisibility(View.VISIBLE);
				if (mStyle.getContentImage() > 0) {
					mContentImage.setBackgroundResource(mStyle.getContentImage());
				} else {
					mTitleRule.setVisibility(View.GONE);
				}
			}
		}

		if (mStyle.getConfirmColor() > 0) {
			btnConfirm.setTextColor(mStyle.getConfirmColor());
		}
		if (mStyle.getCancelColor() > 0) {
			btnCancel.setTextColor(mStyle.getCancelColor());
		}
		if (StringUtils.isStrNotEmpty(mStyle.getConfirmText())) {
			btnConfirm.setText(mStyle.getConfirmText());
		}
		if (StringUtils.isStrNotEmpty(mStyle.getCancelText())) {
			btnCancel.setText(mStyle.getCancelText());
		}

		if (mConfirmListener != null && mCancelListener != null) {
			btnConfirm.setOnClickListener(this);
			btnCancel.setOnClickListener(this);
		} else if (mCancelListener == null) {
			if (isCancelDefaul) {
				btnConfirm.setOnClickListener(this);
				btnCancel.setOnClickListener(this);
			} else {
				btnConfirm.setOnClickListener(this);
				btnConfirm.setBackgroundResource(R.drawable.full_btn_select);
				btnCancel.setVisibility(View.GONE);
				mBtnRule.setVisibility(View.GONE);
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		if (v == btnConfirm) {
			if(this.mConfirmListener!=null){
				this.mConfirmListener.onClick(null);
			}
			super.dismiss();
		}
		if (v == btnCancel) {
			if (!isCancelDefaul) {
				this.mCancelListener.onClick(null);
			}
			super.dismiss();
		}
	}

}
