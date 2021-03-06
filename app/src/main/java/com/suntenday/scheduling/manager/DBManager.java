package com.suntenday.scheduling.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.suntenday.scheduling.bean.EmployeeRuleBean;
import com.suntenday.scheduling.enums.CommonEnum;
import com.suntenday.scheduling.utils.LogUtils;
import com.suntenday.scheduling.utils.StringUtils;

/**
 * 数据库管理
 *
 * @author suntenday
 */
public class DBManager {
    private static final String LOG_TAG = DBManager.class.getSimpleName();

    private static DBHelper mDBHelper = null;

    private class DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "suntenday_scheduling";
        private static final int DB_VERSION = 1;

        public static final String EMPLOYEE_RULE_TABLE_NAME = "employee_rule_table";
        public static final String EMPLOYEE_SCHEDULING_TABLE_NAME = "employee_scheduling_table";

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DB_VERSION);
        }

        // 数据库第一次被创建时调用
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE IF NOT EXISTS " + EMPLOYEE_RULE_TABLE_NAME + "(" +
                    "employee_name text primary key, " +
                    "employee_monday_checked text, " +
                    "employee_tuesday_checked text, " +
                    "employee_wednesday_checked text, " +
                    "employee_thursday_checked text, " +
                    "employee_friday_checked text, " +
                    "employee_saturday_checked text, " +
                    "employee_sunday_checked text " +
                    ")"
            );

            db.execSQL("CREATE TABLE IF NOT EXISTS " + EMPLOYEE_SCHEDULING_TABLE_NAME + "(" +
                    "scheduling_id integer primary key autoincrement," +
                    "scheduling_date text," +
                    "scheduling_day integer," +
                    "scheduling_employee_name text" +
                    ")"
            );

        }

        // 数据库版本不同时调用，主要用于升级数据库。
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private static class SingletonHolder {
        public static final DBManager INSTANCE = new DBManager();
    }

    public static DBManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private DBManager() {

    }

    private DBHelper getDBHelper(Context context) {
        synchronized (DBManager.class) {
            if (mDBHelper == null) {
                mDBHelper = new DBHelper(context);
            }

            return mDBHelper;
        }
    }

    public void addEmployeeRule(Context context, EmployeeRuleBean data) {
        ArrayList<EmployeeRuleBean> listData = new ArrayList<>();
        listData.add(data);
        addEmployeeRuleList(context, listData);
    }

    public void addEmployeeRuleList(Context context, ArrayList<EmployeeRuleBean> listData) {
        SQLiteDatabase db = getDBHelper(context).getWritableDatabase();

        try {
            db.beginTransaction();
            for (int i = 0; i < listData.size(); i++) {
                EmployeeRuleBean employeeRuleData = listData.get(i);
                ContentValues values = new ContentValues();
                values.put("employee_name", employeeRuleData.getName());
                values.put("employee_monday_checked", CommonEnum.getKey(employeeRuleData.isMondayChecked()));
                values.put("employee_tuesday_checked", CommonEnum.getKey(employeeRuleData.isTuesdayChecked()));
                values.put("employee_wednesday_checked", CommonEnum.getKey(employeeRuleData.isWednesdayChecked()));
                values.put("employee_thursday_checked", CommonEnum.getKey(employeeRuleData.isThursdayChekced()));
                values.put("employee_friday_checked", CommonEnum.getKey(employeeRuleData.isFridayChecked()));
                values.put("employee_saturday_checked", CommonEnum.getKey(employeeRuleData.isSaturdayChecked()));
                values.put("employee_sunday_checked", CommonEnum.getKey(employeeRuleData.isSundayChecked()));
                db.insert(DBHelper.EMPLOYEE_RULE_TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtils.logError(LOG_TAG, "addEmployeeRuleList, exception:" + e.getLocalizedMessage() + ";db=" + db);
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void updateEmployeeRule(Context context, EmployeeRuleBean employeeRuleData) {
        SQLiteDatabase db = getDBHelper(context).getWritableDatabase();

        if (db != null) {
            ContentValues values = new ContentValues();
            values.put("employee_monday_checked", CommonEnum.getKey(employeeRuleData.isMondayChecked()));
            values.put("employee_tuesday_checked", CommonEnum.getKey(employeeRuleData.isTuesdayChecked()));
            values.put("employee_wednesday_checked", CommonEnum.getKey(employeeRuleData.isWednesdayChecked()));
            values.put("employee_thursday_checked", CommonEnum.getKey(employeeRuleData.isThursdayChekced()));
            values.put("employee_friday_checked", CommonEnum.getKey(employeeRuleData.isFridayChecked()));
            values.put("employee_saturday_checked", CommonEnum.getKey(employeeRuleData.isSaturdayChecked()));
            values.put("employee_sunday_checked", CommonEnum.getKey(employeeRuleData.isSundayChecked()));
            String whereClause = "employee_name=?";
            String[] whereArgs = {String.valueOf(employeeRuleData.getName())};

            db.update(DBHelper.EMPLOYEE_RULE_TABLE_NAME, values, whereClause, whereArgs);
        } else {
            LogUtils.logError(LOG_TAG, "updateEmployeeRule, db=null");
        }
    }

    public void deleteEmployeeRule(Context context, EmployeeRuleBean employeeRuleData) {
        SQLiteDatabase db = getDBHelper(context).getWritableDatabase();

        if (db != null) {
            String whereClause = "employee_name=?";
            String[] whereArgs = {String.valueOf(employeeRuleData.getName())};

            db.delete(DBHelper.EMPLOYEE_RULE_TABLE_NAME, whereClause, whereArgs);
        } else {
            LogUtils.logError(LOG_TAG, "deleteEmployeeRule, db=null");
        }
    }

    public ArrayList<EmployeeRuleBean> queryEmployeeRule(Context context, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getDBHelper(context).getWritableDatabase();

        ArrayList<EmployeeRuleBean> listData = new ArrayList<>();

        try {
            if (StringUtils.isStrNotEmpty(whereClause)) {
                whereClause = whereClause + "=?";
            }
            Cursor cursor = db.query(DBHelper.EMPLOYEE_RULE_TABLE_NAME,
                    null, whereClause, whereArgs, null, null, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                EmployeeRuleBean employeeRuleData = new EmployeeRuleBean(cursor);
                if (StringUtils.isStrNotEmpty(employeeRuleData.getName())) {
                    listData.add(employeeRuleData);
                }
            }

            cursor.close();
        } catch (Exception e) {
            LogUtils.logError(LOG_TAG, "queryEmployeeRule, exception:" + e.getLocalizedMessage() + ";db=" + db);
            e.printStackTrace();
        }

        return listData;
    }

    public void addSchedulingList(Context context, String date, HashMap<Integer, String> listData) {
        SQLiteDatabase db = getDBHelper(context).getWritableDatabase();
        try {
            db.beginTransaction();
            for (int i = 1; i <= 31; i++) {
                String employeeName = listData.get(i);
                if(StringUtils.isStrNotEmpty(employeeName)) {
                    ContentValues values = new ContentValues();
                    values.put("scheduling_date", date);
                    values.put("scheduling_day", i);
                    values.put("scheduling_employee_name", employeeName);
                    db.insert(DBHelper.EMPLOYEE_SCHEDULING_TABLE_NAME, null, values);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtils.logError(LOG_TAG, "addSchedulingList, exception:" + e.getLocalizedMessage() + ";db=" + db);
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void deleteSchedulingList(Context context, String date, HashMap<Integer, String> listData) {
        SQLiteDatabase db = getDBHelper(context).getWritableDatabase();
        try {
            db.beginTransaction();
            String whereClause = "scheduling_date=?";
            String[] whereArgs = {date};
            db.delete(DBHelper.EMPLOYEE_SCHEDULING_TABLE_NAME, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtils.logError(LOG_TAG, "addSchedulingList, exception:" + e.getLocalizedMessage() + ";db=" + db);
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void updateSchedulingList(Context context, String date, HashMap<Integer, String> listData) {
        deleteSchedulingList(context, date, listData);
        SQLiteDatabase db = getDBHelper(context).getWritableDatabase();
        try {
            db.beginTransaction();
            for (int i = 1; i <= 31; i++) {
                String employeeName = listData.get(i);
                if(StringUtils.isStrNotEmpty(employeeName)) {
                    ContentValues values = new ContentValues();
                    values.put("scheduling_date", date);
                    values.put("scheduling_day", i);
                    values.put("scheduling_employee_name", employeeName);
                    db.insert(DBHelper.EMPLOYEE_SCHEDULING_TABLE_NAME, null, values);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtils.logError(LOG_TAG, "addSchedulingList, exception:" + e.getLocalizedMessage() + ";db=" + db);
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public HashMap<Integer, String>  querySchedulingList(Context context, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getDBHelper(context).getWritableDatabase();

        HashMap<Integer, String> listData = new HashMap<>();

        try {
            if (StringUtils.isStrNotEmpty(whereClause)) {
                whereClause = whereClause + "=?";
            }
            Cursor cursor = db.query(DBHelper.EMPLOYEE_SCHEDULING_TABLE_NAME,
                    null, whereClause, whereArgs, null, null, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int day = cursor.getInt(cursor.getColumnIndex("scheduling_day"));
                String employeeName = cursor.getString(cursor.getColumnIndex("scheduling_employee_name"));
                if (StringUtils.isStrNotEmpty(employeeName)) {
                    listData.put(day, employeeName);
                }
            }

            cursor.close();
        } catch (Exception e) {
            LogUtils.logError(LOG_TAG, "querySchedulingList, exception:" + e.getLocalizedMessage() + ";db=" + db);
            e.printStackTrace();
        }

        return listData;
    }
}
