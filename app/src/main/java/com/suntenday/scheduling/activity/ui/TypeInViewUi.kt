package com.suntenday.scheduling.activity.ui

import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.suntenday.scheduling.R
import com.suntenday.scheduling.activity.TypeInActivity
import com.suntenday.scheduling.bean.EmployeeRuleBean
import com.suntenday.scheduling.bean.SimpleBean
import com.suntenday.scheduling.enums.CommonEnum
import com.suntenday.scheduling.utils.ArrayUtils
import com.suntenday.scheduling.utils.StringUtils
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onItemClick
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

/**
 * Created by suntenday on 2017/9/8 0008.
 */
class TypeInViewUi(activity: TypeInActivity, employeeRuleBeanList:List<EmployeeRuleBean>) : AnkoComponent<TypeInActivity> {

    private val mThis = activity
    private var employeeRuleBean = EmployeeRuleBean()
    private val employeeRuleBeanList = employeeRuleBeanList.toMutableList()

    override fun createView(ui: AnkoContext<TypeInActivity>) = with(ui) {
        verticalLayout {
            //信息录入部分
            linearLayout {

                textView {
                    text = "姓名："
                }
                editText {
                    hint = "请输入姓名"
                    textChangedListener {
                        afterTextChanged { s: Editable? -> employeeRuleBean.name = s.toString()}
                    }
                }


            }
            linearLayout {

                checkBox("周一", false) {
                    onCheckedChange { _, isChecked ->
                        employeeRuleBean.isMondayChecked = isChecked
                    }
                }
                checkBox("周二", false) {
                    onCheckedChange { _, isChecked ->
                        employeeRuleBean.isTuesdayChecked = isChecked
                    }
                }
                checkBox("周三", false) {
                    onCheckedChange { _, isChecked ->
                        employeeRuleBean.isWednesdayChecked = isChecked
                    }
                }
            }
            linearLayout {

                checkBox("周四", false) {
                    onCheckedChange { _, isChecked ->
                        employeeRuleBean.isThursdayChekced = isChecked
                    }
                }
                checkBox("周五", false) {
                    onCheckedChange { _, isChecked ->
                        employeeRuleBean.isFridayChecked = isChecked
                    }
                }
                checkBox("周六", false) {
                    onCheckedChange { _, isChecked ->
                        employeeRuleBean.isSaturdayChecked = isChecked
                    }
                }
            }
            checkBox("周日", false) {
                onCheckedChange { _, isChecked ->
                    employeeRuleBean.isSundayChecked = isChecked
                }
            }
            button("提交") {
                backgroundResource = R.color.blue
                onClick {
                    toast(JSON.toJSONString(employeeRuleBean))
                    mThis.addEmployeeRule(employeeRuleBean)
                }
            }
            button("查询") {
                backgroundResource = R.color.blue
                onClick {
                    mThis.queryEmployeeRule()
                }
            }
            verticalLayout {
                linearLayout {
                    textView("姓名").lparams(width = 0,weight = 1f)
                    textView("周一").lparams(width = 0,weight = 1f)
                    textView("周二").lparams(width = 0,weight = 1f)
                    textView("周三").lparams(width = 0,weight = 1f)
                    textView("周四").lparams(width = 0,weight = 1f)
                    textView("周五").lparams(width = 0,weight = 1f)
                    textView("周六").lparams(width = 0,weight = 1f)
                    textView("周日").lparams(width = 0,weight = 1f)
                }.lparams(width = matchParent)
                if (ArrayUtils.isNotEmpty(employeeRuleBeanList)) {
                    for (item in employeeRuleBeanList) {
                        linearLayout {
                            textView(item.name).lparams(width = 0,weight = 1f)
                            textView(item.isMondayChecked.toString()).lparams(width = 0,weight = 1f)
                            textView(item.isTuesdayChecked.toString()).lparams(width = 0,weight = 1f)
                            textView(item.isWednesdayChecked.toString()).lparams(width = 0,weight = 1f)
                            textView(item.isThursdayChekced.toString()).lparams(width = 0,weight = 1f)
                            textView(item.isFridayChecked.toString()).lparams(width = 0,weight = 1f)
                            textView(item.isSaturdayChecked.toString()).lparams(width = 0,weight = 1f)
                            textView(item.isSundayChecked.toString()).lparams(width = 0,weight = 1f)
                        }.lparams(width = matchParent)
                    }
                }
            }
/*            // 1
            verticalLayout {
                padding = dip(16)
                // 2
                val list = listView() {
                    // 3
                    adapter = ArrayAdapter<EmployeeRuleBean>(mThis, android.R.layout.simple_list_item_1, employeeRuleBeanList)
                    // 4
                    onItemClick { parent, v, position, id ->
                        when (position) {
                        0 -> {
                            // 5
                            toast(JSON.toJSONString(position))
                        }
                    }}
                }.lparams(width = matchParent) { // 6
                    height = matchParent
                }
            }*/
        }
    }

}