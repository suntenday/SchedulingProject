package com.suntenday.scheduling.utils;

import android.util.Log;

public final class LogUtils
{
	private static boolean mIsEnabled = false;
	
	public static void enableLog(boolean enable) {
		mIsEnabled = enable;
	}
	
	public static void logError(String tag, String error) {
		if (mIsEnabled)
			Log.e(tag, error);
	}
	
	public static void logInfo(String tag, String info) {
		if (mIsEnabled)
			Log.i(tag, info);
	}

}
