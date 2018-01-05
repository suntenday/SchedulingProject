package com.suntenday.scheduling.activity

import android.os.Bundle
import com.suntenday.scheduling.activity.base.BaseActivity
import com.suntenday.scheduling.activity.ui.TypeInViewUi
import com.suntenday.scheduling.bean.EmployeeRuleBean
import com.suntenday.scheduling.manager.DBManager
import com.suntenday.scheduling.utils.ArrayUtils
import com.suntenday.scheduling.utils.StringUtils
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast

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
        if(StringUtils.isStrEmpty(employeeRuleBean.name)){
            toast("请填写姓名")
            return
        }
        val whereArgs = arrayOf(employeeRuleBean.name)
        val employeeRuleBeanList = DBManager.getInstance().queryEmployeeRule(this, "employee_name", whereArgs)
        if (ArrayUtils.isEmpty(employeeRuleBeanList)) {
            DBManager.getInstance().addEmployeeRule(this, employeeRuleBean)
        } else {
            DBManager.getInstance().updateEmployeeRule(this, employeeRuleBean)
        }
        toast("添加成功")
        queryEmployeeRule()
    }

    fun deleteEmployeeRule(employeeRuleBean: EmployeeRuleBean){
        if(employeeRuleBean != null){
            DBManager.getInstance().deleteEmployeeRule(this,employeeRuleBean)
            toast("删除成功")
            queryEmployeeRule()
        }
    }

    fun queryEmployeeRule(){
        employeeRuleList = DBManager.getInstance().queryEmployeeRule(this,null,null)
        TypeInViewUi(this,employeeRuleList).setContentView(this)
    }
}