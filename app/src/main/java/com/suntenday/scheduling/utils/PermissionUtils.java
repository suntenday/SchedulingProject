package com.suntenday.scheduling.utils;

import com.suntenday.scheduling.activity.base.BaseActivity;
import com.suntenday.scheduling.constants.PermissionConstant;
import com.suntenday.scheduling.view.dialog.CustomDialog;
import com.suntenday.scheduling.view.dialog.CustomDialogStyle;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.view.View;

/**
 * 权限工具类
 * @author zhengli08275
 * @date 2018/1/18 0018 11:59
 */
public class PermissionUtils {
	
	/**
	 * 是否需要权限提示
	 * @return
	 */
	public static boolean isNeedPermission(){
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
			return false;
		}
		return true;
	}
	
	/**
	 * 检测读取手机状态权限
	 * @param context
	 * @param id
	 * @param listener
	 */
	public static void checkPhonePermission(final Context context, int id, final PermissionSimpleListener listener){
		PermissionListener mListener = new PermissionListener() {
			
			@Override
			public void allowable() {
				listener.allowable();
			}
			
			@Override
			public void disallowable() {
				callPermissionDenied(context, PermissionConstant.READ_PHONE_STATE_WARNING);
			}
		};
		checkPhonePermission(context, id, mListener);
	}
	
	public static void checkPhonePermission(Context context, int id, PermissionListener listener){
		checkContext(context, id, Manifest.permission.READ_PHONE_STATE, listener);
	}
	
	/**
	 * 检测定位权限
	 * @param context
	 * @param id
	 * @param listener
	 */
	public static void checkLocationPermission(final Context context, int id, final PermissionSimpleListener listener){
		PermissionListener mListener = new PermissionListener() {
			
			@Override
			public void allowable() {
				listener.allowable();
			}
			
			@Override
			public void disallowable() {
				callPermissionDenied(context, PermissionConstant.ACCESS_COARSE_LOCATION_WARNING);
			}
		};
		checkLocationPermission(context, id, mListener);
	}
	
	public static void checkLocationPermission(Context context, int id, PermissionListener listener){
		checkContext(context, id, Manifest.permission.ACCESS_COARSE_LOCATION, listener);
	}
	
	/**
	 * 检测存储权限
	 * @param context
	 * @param id
	 * @param listener
	 */
	public static void checkStoragePermission(final Context context, int id, final PermissionSimpleListener listener){
		PermissionListener mListener = new PermissionListener() {
			
			@Override
			public void allowable() {
				listener.allowable();
			}
			
			@Override
			public void disallowable() {
				callPermissionDenied(context, PermissionConstant.WRITE_EXTERNAL_STORAGE_WARNING);
			}
		};
		checkStoragePermission(context, id, mListener);
	}
	
	public static void checkStoragePermission(Context context, int id, PermissionListener listener){
		checkContext(context, id, Manifest.permission.WRITE_EXTERNAL_STORAGE, listener);
	}
	
	private static void checkContext(Context context, int id, String permission, PermissionListener listener){
		if(!isNeedPermission()){
			listener.allowable();
		}else{
			if(context instanceof BaseActivity){
				checkPermission((BaseActivity)context, id, permission, listener);
			}else {
				listener.allowable();
			}
		}
	}
	
	private static void checkPermission(BaseActivity context, int id, String permission, final PermissionListener listener){
		((BaseActivity)context).requestPermission(id, permission, new Runnable() {
			@Override
			public void run() {
				listener.allowable();
			}
		}, new Runnable() {
			@Override
			public void run() {
				listener.disallowable();
			}
		});
	}
	
	/**
	 * 通用取消授权提示
	 * @param context
	 */
	public static void callPermissionDenied(final Context context, CharSequence content) {
		CustomDialogStyle style = new CustomDialogStyle(PermissionConstant.PERMISSION_TITLE, content);
		style.setConfirmText(PermissionConstant.PERMISSION_CONFIRM);
		style.setCancelText(PermissionConstant.PERMISSION_CONTINUE);
		final CustomDialog dialog = new CustomDialog(context, style, new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				SystemUtils.showInstalledAppDetails(context, "com.hundsun.InternetSaleTicket");
			}
		});
		dialog.show();
	}
	
	public interface PermissionListener extends PermissionSimpleListener{
		
		public void disallowable();
	}
	
	public interface PermissionSimpleListener{
		
		public void allowable();
		
	}
}
