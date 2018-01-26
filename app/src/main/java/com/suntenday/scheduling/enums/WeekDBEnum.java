package com.suntenday.scheduling.enums;

/**
 * WeekDBEnum
 *
 * @author suntenday
 * @date 2017/9/27 0027.
 */

public enum WeekDBEnum {

    MONDAY("星期一", "employee_monday_checked"), TUESDAY("星期二", "employee_tuesday_checked"),
    WEDNESDAY("星期三", "employee_wednesday_checked"), THURSDAY("星期四", "employee_thursday_checked"),
    FRIDAY("星期五", "employee_friday_checked"), SATURDAY("星期六", "employee_saturday_checked"),
    SUNDAY("星期日", "employee_sunday_checked");

    private String weekName;

    private String dbName;

    private WeekDBEnum(String weekName, String dbName) {
        this.weekName = weekName;
        this.dbName = dbName;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public static String getWeekKey(String dbName) {
        for (WeekDBEnum valueEnum : WeekDBEnum.values()) {
            if(valueEnum.getDbName().equals(dbName)){
                return valueEnum.getWeekName();
            }
        }
        return "";
    }

    public static String getDBName(String weekName) {
        for (WeekDBEnum valueEnum : WeekDBEnum.values()) {
            if (valueEnum.getWeekName().equals(weekName)) {
                return valueEnum.getDbName();
            }
        }
        return "";
    }
}
