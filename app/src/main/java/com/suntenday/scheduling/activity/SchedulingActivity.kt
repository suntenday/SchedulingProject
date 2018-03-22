package com.suntenday.scheduling.activity

import android.app.Activity
import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.suntenday.scheduling.activity.ui.SchedulingViewUi
import org.jetbrains.anko.setContentView
import com.suntenday.scheduling.utils.ArrayUtils
import com.suntenday.scheduling.utils.CalendarUtils
import com.suntenday.scheduling.bean.EmployeeRuleBean
import com.suntenday.scheduling.enums.CommonEnum
import com.suntenday.scheduling.enums.WeekDBEnum
import com.suntenday.scheduling.manager.DBManager
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by zhengli08275 on 2017/9/8 0008.
 */
class SchedulingActivity : Activity() {

    private var weekNameStoreMap = HashMap<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryScheduling()
    }

    private fun queryScheduling() {
        queryScheduling(CalendarUtils.getYear(), CalendarUtils.getMonth())
    }

    /**
     * 获取排班信息
     */
    fun queryScheduling(year: Int, month: Int) {
        val whereArgs = arrayOf(year.toString() + "-" + month.toString())
        weekNameStoreMap = DBManager.getInstance().querySchedulingList(this, "scheduling_date", whereArgs)
        SchedulingViewUi(this, year, month, weekNameStoreMap).setContentView(this)
    }

    /**
     * 排班
     */
    fun addScheduling(year: Int, month: Int) {
        weekNameStoreMap.clear()
//        weekNameStoreMap = querySchedulingEmployee(year, month)
        weekNameStoreMap = querySchedulingEmployeeNew(year, month)
        DBManager.getInstance().addSchedulingList(this, year.toString() + "-" + month.toString(), weekNameStoreMap)
        SchedulingViewUi(this, year, month, weekNameStoreMap).setContentView(this)
    }

    /**
     * 更新排班
     */
    fun updateScheduling(year: Int, month: Int) {
        weekNameStoreMap.clear()
//        weekNameStoreMap = querySchedulingEmployee(year, month)
        weekNameStoreMap = querySchedulingEmployeeNew(year, month)
        DBManager.getInstance().updateSchedulingList(this, year.toString() + "-" + month.toString(), weekNameStoreMap)
        SchedulingViewUi(this, year, month, weekNameStoreMap).setContentView(this)
    }

    /**
     * 排班
     */
    @Deprecated("容易出现短期重复排班")
    private fun querySchedulingEmployee(year: Int, month: Int): HashMap<Int, String> {
        val weekStoreMap = HashMap<String, ArrayList<Int>?>()
        var monthDay = getMonthDay(year, month)
        //初始化月份星期的日期
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
        monthDay = checkWeekStoreMap(weekStoreMap, monthDay)
        var employeeRuleList = DBManager.getInstance().queryEmployeeRule(this, null, null)
        System.out.println("weekNameStoreMap.size=" + weekNameStoreMap.size)
        System.out.println("monthDay=" + monthDay)

        //循环员工从排班日期池取排班日期
        while (weekNameStoreMap.size < monthDay) {
            Collections.shuffle(employeeRuleList)
            for (employee in employeeRuleList) {
                val day = getDayForEmployee(employee, weekStoreMap)
                if (day > 0) {
                    updateMap(weekStoreMap, day)
                    weekNameStoreMap.put(day, employee.name)
                }
            }
        }
        System.out.println("weekNameStoreMap2.size=" + weekNameStoreMap.size)
        return weekNameStoreMap
    }

    private var employeeRuleListStore: MutableList<EmployeeRuleBean> = ArrayList()
//    var employeeRuleListStoreForWeek = HashMap<String, ArrayList<EmployeeRuleBean>?>()

    private fun querySchedulingEmployeeNew(year: Int, month: Int): HashMap<Int, String> {
//        val weekStoreMap = HashMap<String, ArrayList<Int>?>()
        var monthDayList:MutableList<Int> = getMonthDayList(year, month)
        if (ArrayUtils.isEmpty(employeeRuleListStore)) {
            employeeRuleListStore = DBManager.getInstance().queryEmployeeRule(this, null, null)
        }
        System.out.println("employeeRuleListStore.size=" + employeeRuleListStore.size)
        while (monthDayList.isNotEmpty()) {

            var monthDayListTemp = confluenceMonthDay(monthDayList, year, month)
            System.out.println("employeeRuleListStore.size=" + employeeRuleListStore.size)
            monthDayList.clear()
            monthDayList.addAll(monthDayListTemp)
        }

        System.out.println("weekNameStoreMap2.size=" + weekNameStoreMap.size)
        return weekNameStoreMap
    }

    private fun confluenceMonthDay(monthDayList:List<Int>,year: Int, month: Int):MutableList<Int>{
        var monthDayListTemp:MutableList<Int> = ArrayList()
        for (i in monthDayList){
            val date = year.toString() + "-" + month + "-" + i
            //日期所属星期
            val week = CalendarUtils.getWeek(date, CalendarUtils.DATE_FORMAT)
            var employee = getEmployeesForWeek(week)
            if(employee != null) {
                weekNameStoreMap.put(i, employee.name)
            }else{
                monthDayListTemp.add(i)
            }
        }
        return monthDayListTemp
    }

    /**
     * 获取某星期可安排员工
     */
    private fun getEmployee(week:String): MutableList<EmployeeRuleBean>?{
        var employeeList:MutableList<EmployeeRuleBean> = ArrayList()

        if(employeeRuleListStore.isEmpty()){
            employeeRuleListStore = DBManager.getInstance().queryEmployeeRule(this, null, null)
            //TODO 此处会有重复数据的问题，重复第二轮的时候如果是正好在周中排，那么第二轮就很有可能在一周里与已经排班的重复
//            employeeRuleListStore.addAll(employeeRuleListStore.size, employeeRuleListStoreTemp)
//            employeeList = employeeRuleListStoreTemp
        }
        employeeRuleListStore.forEach {
            if(getDaysForEmployee(it, week)){
                employeeList.add(it)
            }
        }
//        for(employee in employeeRuleListStore){
//            if(getDaysForEmployee(employee, week)){
//                employeeList.add(employee)
//            }
//        }
        return employeeList
    }

    private fun getEmployeesForWeek(week: String): EmployeeRuleBean? {
        //TODO 先整理每周每个星期可安排的员工池
        //TODO 当员工数不足时查询DB获取对应星期的员工形成新员工池，直到全部日期都分配

        var employeeRuleListStoreItemForWeek = getEmployee(week)
//        if (ArrayUtils.isEmpty(employeeRuleListStoreItemForWeek)) {
//            var whereArgs = arrayOf(CommonEnum.YES.key)
//            employeeRuleListStoreItemForWeek = DBManager.getInstance().queryEmployeeRule(this, WeekDBEnum.getDBName(week), whereArgs)
//        }
        if(ArrayUtils.isNotEmpty(employeeRuleListStoreItemForWeek)) {
            val random = Random()
            val randomNum = random.nextInt(employeeRuleListStoreItemForWeek!!.size)
            var employeeRuleStoreItemForWeek = employeeRuleListStoreItemForWeek[randomNum]
            if (ArrayUtils.isNotEmpty(employeeRuleListStore)) {
                employeeRuleListStore.remove(employeeRuleStoreItemForWeek)
            }
            return employeeRuleStoreItemForWeek
        }
        return null
    }

    private fun checkWeekStoreMap(weekStoreMap: HashMap<String, ArrayList<Int>?>, monthDay: Int): Int {
        val key = arrayOf("星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日")
        var recordMonthDay = monthDay
        for (i in 0..6) {
            var key = key[i]
            var weekStore = weekStoreMap[key]
            var whereArgs = arrayOf(CommonEnum.YES.key)
            var employeeRuleList = DBManager.getInstance().queryEmployeeRule(this, WeekDBEnum.getDBName(key), whereArgs)
            System.out.println(i.toString() + "|key" + key + "|employeeRuleList=" + JSON.toJSONString(employeeRuleList))
            if (ArrayUtils.isEmpty(employeeRuleList)) {
                recordMonthDay -= weekStore!!.size
                System.out.println("weekStore!!.size=" + weekStore!!.size)
                System.out.println("change_monthDay=" + monthDay)
                weekStoreMap.remove(key)
            }
        }
        return recordMonthDay
    }

    private fun updateMap(weekStoreMap: HashMap<String, ArrayList<Int>?>, removeSeed: Int?) {
        val key = arrayOf("星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日")
//        for (i in 0..6) {
//            val weekStore = weekStoreMap[key[i]]
//            if (weekStore != null) {
//                var result = weekStore as MutableCollection<Int>
//                result.remove(removeSeed)
//            }
//        }

        key.forEach {
            if (it != null) {
                var result = it as MutableCollection<Int>
                result.remove(removeSeed)
            }
        }
    }

    /**
     * 获取月份总天数
     */
    fun getMonthDay(year: Int, month: Int): Int {
        return CalendarUtils.getMonthDays(year, month)
    }

    fun getMonthDayList(year: Int, month: Int): MutableList<Int>{
        var monthDayList: MutableList<Int> = ArrayList()
        var monthDays = CalendarUtils.getMonthDays(year, month)
        for (i in 1..monthDays) {
            monthDayList.add(i)
        }
        return monthDayList
    }

    /**
     * 安排日期对应值班的员工
     */
    private fun getDayForEmployee(employee: EmployeeRuleBean, weekStoreMap: HashMap<String, ArrayList<Int>?>): Int {
        val daysForEmployee = getDaysForEmployee(employee, weekStoreMap)
        if (ArrayUtils.isEmpty(daysForEmployee)) {
            return -1
        }
        val random = Random()
        val randomNum = random.nextInt(daysForEmployee!!.size)
        return daysForEmployee!![randomNum]
    }

    /**
     * 获取日期可安排的员工
     */
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

    private fun getDaysForEmployee(employee: EmployeeRuleBean, week: String): Boolean {
        val daysForEmployee = arrayListOf<Int>().toMutableList()
        if (employee.isMondayChecked) {
            if("星期一" == week){
                return true
            }
        }
        if (employee.isTuesdayChecked) {
            if("星期二" == week){
                return true
            }
        }
        if (employee.isWednesdayChecked) {
            if("星期三" == week){
                return true
            }
        }
        if (employee.isThursdayChekced) {
            if("星期四" == week){
                return true
            }
        }
        if (employee.isFridayChecked) {
            if("星期五" == week){
                return true
            }
        }
        if (employee.isSaturdayChecked) {
            if("星期六" == week){
                return true
            }
        }
        if (employee.isSundayChecked) {
            if("星期日" == week){
                return true
            }
        }
        return false
    }

}