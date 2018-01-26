package com.suntenday.scheduling.bean;

import android.database.Cursor;

import com.suntenday.scheduling.constants.CommonConstatns;
import com.suntenday.scheduling.enums.CommonEnum;

import java.util.Map;

/**
 * 员工规则对象
 *
 * @author suntenday
 * @date 2017-12-19 10:57
 */
public class EmployeeRuleBean {

    private String name;
    private boolean isMondayChecked;
    private boolean isTuesdayChecked;
    private boolean isWednesdayChecked;
    private boolean isThursdayChekced;
    private boolean isFridayChecked;
    private boolean isSaturdayChecked;
    private boolean isSundayChecked;

    public EmployeeRuleBean() {
    }

    public EmployeeRuleBean(String name, boolean isMondayChecked, boolean isTuesdayChecked, boolean isWednesdayChecked,
                            boolean isThursdayChekced, boolean isFridayChecked, boolean isSaturdayChecked, boolean isSundayChecked) {
        this.name = name;
        this.isMondayChecked = isMondayChecked;
        this.isTuesdayChecked = isTuesdayChecked;
        this.isWednesdayChecked = isWednesdayChecked;
        this.isThursdayChekced = isThursdayChekced;
        this.isFridayChecked = isFridayChecked;
        this.isSaturdayChecked = isSaturdayChecked;
        this.isSundayChecked = isSundayChecked;
    }

    public EmployeeRuleBean(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndex("employee_name"));
        this.isMondayChecked = CommonEnum.getValue(cursor.getString(cursor.getColumnIndex("employee_monday_checked")));
        this.isTuesdayChecked = CommonEnum.getValue(cursor.getString(cursor.getColumnIndex("employee_tuesday_checked")));
        this.isWednesdayChecked = CommonEnum.getValue(cursor.getString(cursor.getColumnIndex("employee_wednesday_checked")));
        this.isThursdayChekced = CommonEnum.getValue(cursor.getString(cursor.getColumnIndex("employee_thursday_checked")));
        this.isFridayChecked = CommonEnum.getValue(cursor.getString(cursor.getColumnIndex("employee_friday_checked")));
        this.isSaturdayChecked = CommonEnum.getValue(cursor.getString(cursor.getColumnIndex("employee_saturday_checked")));
        this.isSundayChecked = CommonEnum.getValue(cursor.getString(cursor.getColumnIndex("employee_sunday_checked")));
    }

    public EmployeeRuleBean(Map<Integer, String> excelItemMap) {
        if(excelItemMap.size()>7) {
            this.name = excelItemMap.get(0);
            this.isMondayChecked = CommonConstatns.YES.equals(excelItemMap.get(1));
            this.isTuesdayChecked = CommonConstatns.YES.equals(excelItemMap.get(2));
            this.isWednesdayChecked = CommonConstatns.YES.equals(excelItemMap.get(3));
            this.isThursdayChekced = CommonConstatns.YES.equals(excelItemMap.get(4));
            this.isFridayChecked = CommonConstatns.YES.equals(excelItemMap.get(5));
            this.isSaturdayChecked = CommonConstatns.YES.equals(excelItemMap.get(6));
            this.isSundayChecked = CommonConstatns.YES.equals(excelItemMap.get(7));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMondayChecked() {
        return isMondayChecked;
    }

    public void setMondayChecked(boolean mondayChecked) {
        isMondayChecked = mondayChecked;
    }

    public boolean isTuesdayChecked() {
        return isTuesdayChecked;
    }

    public void setTuesdayChecked(boolean tuesdayChecked) {
        isTuesdayChecked = tuesdayChecked;
    }

    public boolean isWednesdayChecked() {
        return isWednesdayChecked;
    }

    public void setWednesdayChecked(boolean wednesdayChecked) {
        isWednesdayChecked = wednesdayChecked;
    }

    public boolean isThursdayChekced() {
        return isThursdayChekced;
    }

    public void setThursdayChekced(boolean thursdayChekced) {
        isThursdayChekced = thursdayChekced;
    }

    public boolean isFridayChecked() {
        return isFridayChecked;
    }

    public void setFridayChecked(boolean fridayChecked) {
        isFridayChecked = fridayChecked;
    }

    public boolean isSaturdayChecked() {
        return isSaturdayChecked;
    }

    public void setSaturdayChecked(boolean saturdayChecked) {
        isSaturdayChecked = saturdayChecked;
    }

    public boolean isSundayChecked() {
        return isSundayChecked;
    }

    public void setSundayChecked(boolean sundayChecked) {
        isSundayChecked = sundayChecked;
    }
}
