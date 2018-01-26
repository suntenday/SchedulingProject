package com.suntenday.scheduling.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import com.suntenday.scheduling.R;


/**
 * 标准弹出框
 * @author zhengli08275
 *
 */
public class SweetDialog extends Dialog{

	private AnimationSet mModalInAnim;
	private AnimationSet mModalOutAnim;
	
	private View mDialogView;
	
	private boolean mCloseFromCancel;
	
	private int layoutId;
	
	public SweetDialog(Context context, int layoutId) {
		super(context, R.style.sweet_dialog);
		this.layoutId = layoutId;
		mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.bounce_in);
		mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.bounce_out);
		mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
		    @Override
		    public void onAnimationStart(Animation animation) {
		
		    }
		
		    @Override
		    public void onAnimationEnd(Animation animation) {
		        mDialogView.setVisibility(View.GONE);
		        mDialogView.post(new Runnable() {
		            @Override
		            public void run() {
		                if (mCloseFromCancel) {
		                	SweetDialog.super.cancel();
		                } else {
		                	SweetDialog.super.dismiss();
		                }
		            }
		        });
		    }
		
		    @Override
		    public void onAnimationRepeat(Animation animation) {
		
		    }
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(layoutId);
	    mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
	}

	@Override
	protected void onStart() {
	    mDialogView.startAnimation(mModalInAnim);
	}

	@Override
	public View findViewById(int id){
		return mDialogView.findViewById(id);
	}
	
	/**
	 * The real Dialog.cancel() will be invoked async-ly after the animation finishes.
	 */
	@Override
	public void cancel() {
	    dismissWithAnimation(true);
	}
	
	/**
	 * The real Dialog.dismiss() will be invoked async-ly after the animation finishes.
	 */
	public void dismissWithAnimation() {
	    dismissWithAnimation(false);
	}
	
	private void dismissWithAnimation(boolean fromCancel) {
	    mCloseFromCancel = fromCancel;
	    mDialogView.startAnimation(mModalOutAnim);
	}
}
