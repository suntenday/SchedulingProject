package com.suntenday.scheduling.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class StringUtils {

    public static int strToInt(String str) {
        int getInt = -1;
        if (isStrNotEmpty(str)) {
            getInt = Integer.valueOf(str);
        }
        return getInt;
    }

    public static boolean isStrNotEmpty(String str) {
        if (str != null && !"".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStrEmpty(String str) {
        if (str != null && !"".equals(str)) {
            return false;
        } else {
            return true;
        }
    }


    public static String trim(String str) {
        if (isStrNotEmpty(str)) {
            return str.trim();
        } else {
            return str;
        }
    }

    public static JSONObject checkPassword(String password) {
        JSONObject json = new JSONObject();
        try {
            if (isStrNotEmpty(password)) {
                if (password.length() > 16 || password.length() < 6) {
                    json.put("isChecked", false);
                    json.put("msg", "密码长度不能小于6大于16！");
                    return json;
                } else {
                    String regEx = "^[`~!@#$%^&*()+=|{}':;',.<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890]{6,16}$";
//		        	 String regEx="^[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890]{6,16}$";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(password.trim());
                    if (!m.find()) {
                        json.put("isChecked", false);
                        json.put("msg", "密码不符合要求，至少包含数字、字母（区分大小写）、符号中的2种！");
                        return json;
                    }
                    String regEx4 = "(^(?![1234567890]*$))(^(?![qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM]*$))(^(?![`~!@#$%^&*()+=|{}':;',.<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]*$)).{6,}";
//		    		String regEx4="(^(?![1234567890]*$))(^(?![qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM]*$))(^(?![`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]*$)).{6,}";
                    Pattern p1 = Pattern.compile(regEx4);
                    Matcher m1 = p1.matcher(password.trim());
                    if (!m1.find()) {
                        json.put("isChecked", false);
                        json.put("msg", "密码不符合要求，至少包含数字、字母（区分大小写）、符号中的2种！");
                        return json;
                    }
                    json.put("isChecked", true);
                    json.put("msg", "密码成功！");
                }
            } else {
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

    public static JSONObject checkName(Context context, String name, int nameMinLength, int nameMaxLength) {
        JSONObject json = new JSONObject();
        int minLength = 3;
        int maxLength = 16;
        if (nameMinLength < 0) {
            minLength = 3;
        } else {
            minLength = nameMinLength;
        }
        if (nameMaxLength < 0) {
            maxLength = 16;
        } else {
            maxLength = nameMaxLength;
        }
        try {
            if (TextUtils.isEmpty(name) || name.length() < minLength || name.length() > maxLength || !nameFormat(name)) {
                json.put("isChecked", false);
                json.put("msg", "昵称不符合规范，3-16个中英文字符、数字");
                return json;
            } else {
                json.put("isChecked", true);
                json.put("msg", "验证成功");
            }
        } catch (JSONException e) {
        }
        return json;
    }

    public static JSONObject checkEmail(Context context, String email, String password) {
        JSONObject json = new JSONObject();
        try {
            if (!emailFormat(email) || email.length() > 31) {
                json.put("isChecked", false);
                json.put("msg", "邮箱格式不正确");
                return json;
            } else {
                json.put("isChecked", true);
                json.put("msg", "验证成功");
            }
        } catch (JSONException e) {
        }
        return json;
    }

    public static JSONObject checkPassword(Context context, String password, int pwdMinLength, int pwdMaxLength) {
        JSONObject json = new JSONObject();
        int minLength = 6;
        int maxLength = 15;
        if (pwdMinLength < 0) {
            minLength = 6;
        } else {
            minLength = pwdMinLength;
        }
        if (pwdMaxLength < 0) {
            maxLength = 15;
        } else {
            maxLength = pwdMaxLength;
        }
        try {
            if (isStrNotEmpty(password)) {
                if (password.length() > maxLength || password.length() < minLength) {
                    json.put("isChecked", false);
                    json.put("msg", "密码长度不能小于" + minLength + "大于" + maxLength + "！");
                    return json;
                } else {
                    if (!passwordFormat(password)) {
                        json.put("isChecked", false);
                        json.put("msg", "密码不符合要求，至少包含数字、字母（区分大小写）、符号中的2种！");
                        return json;
                    } else {
                        json.put("isChecked", true);
                        json.put("msg", "密码成功！");
                    }
                }
            } else {
                json.put("isChecked", false);
                json.put("msg", "密码不能为空！");
            }
        } catch (JSONException e) {
        }
        return json;
    }

    /**
     * 以字母开头，长度在3~16之间，只能包含字符、数字和下划线（w）
     *
     * @param password
     * @return
     */
    private static boolean passwordFormat(String password) {
        Pattern pattern = Pattern.compile("^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,15}$");
        Matcher mc = pattern.matcher(password);
        return mc.matches();
    }

    public static boolean nameFormat(String name) {
        Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5A-Za-z0-9_]{3,16}$");
        Matcher mc = pattern.matcher(name);
        return mc.matches();
    }

    private static boolean emailFormat(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z\\d]+(\\.[A-Za-z\\d]+)*@([\\dA-Za-z](-[\\dA-Za-z])?)+(\\.{1,2}[A-Za-z]+)+$");
        Matcher mc = pattern.matcher(email);
        return mc.matches();
    }

    public static String phoneFormat(String phone) {
        String resultPhone = phone;
        if (isStrNotEmpty(phone) && phone.length() >= 11) {
            StringBuilder builder = new StringBuilder();
            builder.append(phone.subSequence(0, 3));
            builder.append("****");
            builder.append(phone.subSequence(7, 11));
            resultPhone = builder.toString();
        } else {
            resultPhone = "";
        }
        return resultPhone;
    }

    /**
     * 小数去零
     *
     * @param decimal
     * @return
     */
    public static String decimalKillZeroFormat(String decimal) {
        if (TextUtils.isEmpty(decimal)) {
            return "";
        }
        if (decimal.indexOf(".") > 0) {
            //正则表达
            decimal = decimal.replaceAll("0+?$", "");//去掉后面无用的零
            decimal = decimal.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return decimal;
    }
}
