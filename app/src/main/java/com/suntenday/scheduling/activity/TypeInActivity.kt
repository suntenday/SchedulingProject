package com.suntenday.scheduling.activity

import android.app.Activity
import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.suntenday.scheduling.activity.base.BaseActivity
import com.suntenday.scheduling.activity.ui.TypeInViewUi
import com.suntenday.scheduling.bean.EmployeeRuleBean
import com.suntenday.scheduling.bean.SimpleBean
import com.suntenday.scheduling.manager.DBManager
import com.suntenday.scheduling.utils.ArrayUtils
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast
import java.time.DayOfWeek

/**
 * Created by suntenday on 2017/9/8 0008.
 */
class TypeInActivity : BaseActivity() {

    var employeeRuleList:MutableList<EmployeeRuleBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryEmployeeRule()
    }

    fun addEmployeeRule(employeeRuleBean: EmployeeRuleBean) {
        val whereArgs = arrayOf(employeeRuleBean.name)
        val employeeRuleBeanList = DBManager.getInstance().queryEmployeeRule(this, "employee_name", whereArgs)
        if (ArrayUtils.isEmpty(employeeRuleBeanList)) {
            DBManager.getInstance().addEmployeeRule(this, employeeRuleBean)
        } else {
            DBManager.getInstance().updateEmployeeRule(this, employeeRuleBean)
        }
        toast("添加成功")
    }

    fun queryEmployeeRule(){
        val whereArgs = arrayOf("测试")
        employeeRuleList = DBManager.getInstance().queryEmployeeRule(this,null,null)
        TypeInViewUi(this,employeeRuleList).setContentView(this)
    }
}