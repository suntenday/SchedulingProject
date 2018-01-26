package com.suntenday.scheduling.utils;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * 多种像素处理及获取屏幕像素方式
 * @author suntenday
 *
 */
public class PixUtils {

	/**
	 * @param @param  context
	 * @param @return
	 * @return int
	 * @throws
	 * @Title: getWindowScreenWidth
	 * @Description: 获取屏幕宽度
	 */
	@SuppressWarnings("deprecation")
	public static int getWindowScreenWidth(Context context) {
		if(context instanceof Activity){
			return ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
		}else{
			return getDisplayMetricsWidth(context);
		}
	}

	public static int getDisplayMetricsWidth(Context context) {
		DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
		return mDisplayMetrics.widthPixels;
	}
	
	public static int getDeviceWidth(Context context) 
	{
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
	
	public static int getDefaultDisplayMetricsWidth(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		if(context instanceof Activity){
			((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm); // //将当前窗口的一些信息放在DisplayMetrics类中
			return dm.widthPixels;// 获取分辨率宽度
		}else{
			return getDisplayMetricsWidth(context);
		}
	}

	/**
	 * @param @param  context
	 * @param @return
	 * @return int
	 * @throws
	 * @Title: getWindowScreenHeight
	 * @Description: 获取屏幕高度
	 */
	@SuppressWarnings("deprecation")
	public static int getWindowScreenHeight(Context context) {
		if(context instanceof Activity){
			return ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
		}else{
			return getDisplayMetricsHeight(context);
		}
	}

	public static int getDisplayMetricsHeight(Context context) {
		DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
		return mDisplayMetrics.heightPixels;
	}

	public static int getDeviceHeight(Context context) 
	{
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
	
	public static int getDefaultDisplayMetricsHeight(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		if(context instanceof Activity){
			((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm); // //将当前窗口的一些信息放在DisplayMetrics类中
			return dm.heightPixels;// 获取分辨率宽度
		}else{
			return getDisplayMetricsHeight(context);
		}
	}
	
	/**
	 * 获取View的宽度
	 * @param v
	 * @return
	 */
    @SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void doViewWidth(final View view, final GlobalLayoutListener listener){
		//增加整体布局监听
		ViewTreeObserver vto = view.getViewTreeObserver();  
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener(){ 
			@Override 
		    public void onGlobalLayout() { 
		    	if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
		    		view.getViewTreeObserver().removeGlobalOnLayoutListener(this);   
		    	}else{
		    		view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
		    	}
		    	listener.doMeasuredSize(view.getMeasuredWidth());
		    } 
		});
	}
	
	/**
	 * 获取View的高度
	 * @param v
	 * @return
	 */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
	public static void doViewHeight(final View view, final GlobalLayoutListener listener){
		//增加整体布局监听
		ViewTreeObserver vto = view.getViewTreeObserver();  
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener(){ 
			@Override 
		    public void onGlobalLayout() { 
		    	if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
		    		view.getViewTreeObserver().removeGlobalOnLayoutListener(this);   
		    	}else{
		    		view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
		    	}
		    	listener.doMeasuredSize(view.getMeasuredHeight()); 
		    } 
		});
	}
    
    public interface GlobalLayoutListener{
    	public void doMeasuredSize(int measuredSize);
    }
	
	/**
	 * 根据手机的分辨率�?dp 的单�?转成�?px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率�?px(像素) 的单�?转成�?dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 * @param fontScale （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
	
	/**
	 * 获取状态栏高度
	 * @param activity
	 * @return
	 */
	public static int getTopBarHeight(Activity activity)
	{
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;

		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = activity.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}

	/**
	 * 获得文件后缀名
	 *
	 * @param filename 传过来的路径
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot);
			}
		}
		return filename;
	}
	

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context the context
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();

        } else {
            mResources = context.getResources();
        }
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }
}
