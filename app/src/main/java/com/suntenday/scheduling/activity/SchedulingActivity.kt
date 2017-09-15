package com.suntenday.scheduling.activity

import android.app.Activity
import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.suntenday.scheduling.activity.ui.SchedulingViewUi
import org.jetbrains.anko.setContentView
import com.suntenday.scheduling.utils.ArrayUtils
import com.suntenday.scheduling.utils.CalendarUtils
import com.suntenday.scheduling.bean.EmployeeRuleBean
import com.suntenday.scheduling.manager.DBManager
import org.jetbrains.anko.toast
import java.util.*


/**
 * Created by zhengli08275 on 2017/9/8 0008.
 */
class SchedulingActivity : Activity() {

    var weekNameStoreMap = HashMap<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryScheduling()
    }

    private fun queryScheduling() {
        queryScheduling(CalendarUtils.getYear(),CalendarUtils.getMonth())
    }

    fun queryScheduling(year: Int, month: Int) {
        val whereArgs = arrayOf(year.toString() + "-" + month.toString())
        weekNameStoreMap = DBManager.getInstance().querySchedulingList(this, "scheduling_date", whereArgs)
        SchedulingViewUi(this, year, month, weekNameStoreMap).setContentView(this)
    }

    fun addScheduling(year: Int, month: Int){
        weekNameStoreMap.clear()
        weekNameStoreMap = querySchedulingEmployee(year, month)
        DBManager.getInstance().addSchedulingList(this, year.toString() + "-" + month.toString(), weekNameStoreMap)
        SchedulingViewUi(this, year, month, weekNameStoreMap).setContentView(this)
    }

    fun updateScheduling(year: Int, month: Int){
        weekNameStoreMap.clear()
        weekNameStoreMap = querySchedulingEmployee(year, month)
        DBManager.getInstance().updateSchedulingList(this, year.toString() + "-" + month.toString(), weekNameStoreMap)
        SchedulingViewUi(this, year, month, weekNameStoreMap).setContentView(this)
    }

    private fun querySchedulingEmployee(year: Int, month: Int):HashMap<Int, String>{
        val weekStoreMap = HashMap<String, ArrayList<Int>?>()
        val monthDay = getMonthDay(year, month)
        for (i in 1..monthDay) {
            val date = year.toString() + "-" + month + "-" + i
            val week = CalendarUtils.getWeek(date, CalendarUtils.DATE_FORMAT)
            var weekDays: ArrayList<Int>? = arrayListOf<Int>()
            weekDays = weekStoreMap[week]
            if (ArrayUtils.isEmpty(weekDays)) {
                weekDays = arrayListOf<Int>()
            }
            weekDays?.add(i)
            weekStoreMap.put(week, weekDays)
        }

        var employeeRuleList = DBManager.getInstance().queryEmployeeRule(this, null, null)
        Collections.shuffle(employeeRuleList)
        for (employee in employeeRuleList) {
            val day = getDayForEmployee(employee, weekStoreMap)
            if (day > 0) {
                updateMap(weekStoreMap, day)
                weekNameStoreMap.put(day, employee.name)
            }
        }
        return weekNameStoreMap
    }

    private fun updateMap(weekStoreMap: HashMap<String, ArrayList<Int>?>, removeSeed: Int?) {
        val key = arrayOf("星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日")
        for (i in 0..6) {
            var result = weekStoreMap[key[i]] as MutableCollection<Int>
            result.remove(removeSeed)
        }
    }

    fun getMonthDay(year: Int, month: Int): Int {
        return CalendarUtils.getMonthDays(year, month)
    }

    private fun getDayForEmployee(employee: EmployeeRuleBean, weekStoreMap: HashMap<String, ArrayList<Int>?>): Int {
        val daysForEmployee = getDaysForEmployee(employee, weekStoreMap)
        if (ArrayUtils.isEmpty(daysForEmployee)) {
            return -1
        }
        val random = Random()
        val randomNum = random.nextInt(daysForEmployee!!.size)
        return daysForEmployee!![randomNum]

    }

    private fun getDaysForEmployee(employee: EmployeeRuleBean, weekStoreMap: HashMap<String, ArrayList<Int>?>): List<Int>? {
        val daysForEmployee = arrayListOf<Int>().toMutableList()
        if (employee.isMondayChecked) {
            daysForEmployee.addAll(0, weekStoreMap["星期一"] as Collection<Int>)
        }
        if (employee.isTuesdayChecked) {
            daysForEmployee.addAll(daysForEmployee.size, weekStoreMap["星期二"] as Collection<Int>)
        }
        if (employee.isWednesdayChecked) {
            daysForEmployee.addAll(daysForEmployee.size, weekStoreMap["星期三"] as Collection<Int>)
        }
        if (employee.isThursdayChekced) {
            daysForEmployee.addAll(daysForEmployee.size, weekStoreMap["星期四"] as Collection<Int>)
        }
        if (employee.isFridayChecked) {
            daysForEmployee.addAll(daysForEmployee.size, weekStoreMap["星期五"] as Collection<Int>)
        }
        if (employee.isSaturdayChecked) {
            daysForEmployee.addAll(daysForEmployee.size, weekStoreMap["星期六"] as Collection<Int>)
        }
        if (employee.isSundayChecked) {
            daysForEmployee.addAll(daysForEmployee.size, weekStoreMap["星期日"] as Collection<Int>)
        }
        return daysForEmployee
    }

}