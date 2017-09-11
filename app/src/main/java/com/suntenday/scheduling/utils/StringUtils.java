package com.suntenday.scheduling.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class StringUtils {
	
	public static int strToInt(String str){
		int getInt = -1;
		if(isStrNotEmpty(str)){
			getInt = Integer.valueOf(str);
		}
		return getInt;
	}
	
	public static boolean isStrNotEmpty(String str){
		if(str != null && !"".equals(str)){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isStrEmpty(String str){
		if(str != null && !"".equals(str)){
			return false;
		}else{
			return true;
		}
	}
	
	public static JSONObject checkPassword(String password){
		JSONObject json = new JSONObject();
		try {
			if(isStrNotEmpty(password)){
			if(password.length()>16 || password.length()<6){
					json.put("isChecked", false);
					json.put("msg", "密码长度不能小于6大于16！");
					return json;
		        }else {
		        	 String regEx="^[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890]{6,16}$";
		        	 Pattern p=Pattern.compile(regEx);  
		    		 Matcher m=p.matcher(password.trim());
		    		if(!m.find()){
		    			json.put("isChecked", false);
						json.put("msg", "密码不符合要求，至少包含数字、字母（区分大小写）、符号中的2种！");
				        return json;
		    		}
		    		String regEx4="(^(?![1234567890]*$))(^(?![qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM]*$))(^(?![`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]*$)).{6,}";
		    		Pattern p1=Pattern.compile(regEx4);  
		    		Matcher m1=p1.matcher(password.trim());
		    		if(!m1.find()){
		    			json.put("isChecked", false);
						json.put("msg", "密码不符合要求，至少包含数字、字母（区分大小写）、符号中的2种！");
				        return json;
		    		}
		    		json.put("isChecked", true);
					json.put("msg", "密码成功！");
		        }
			}else{
				json.put("isChecked", false);
				json.put("msg", "密码不能为空");
				return json;
			}
			json.put("isChecked", false);
			json.put("msg", "密码内容不符合要求,6~16位字符，至少包含数字、字母（区分大小写）、符号中的2种！");
		} catch (JSONException e) {
		}
		return json;
	}
}
