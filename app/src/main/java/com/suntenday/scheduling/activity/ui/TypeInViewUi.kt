package com.suntenday.scheduling.activity.ui

import android.text.Editable
import android.text.TextUtils
import android.view.Gravity
import com.suntenday.scheduling.R
import com.suntenday.scheduling.activity.TypeInActivity
import com.suntenday.scheduling.bean.EmployeeRuleBean
import com.suntenday.scheduling.utils.ArrayUtils
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

/**
 * Created by suntenday on 2017/9/8 0008.
 */
class TypeInViewUi(activity: TypeInActivity, employeeRuleBeanList: List<EmployeeRuleBean>) : AnkoComponent<TypeInActivity> {

    private val mThis = activity
    private var employeeRuleBean = EmployeeRuleBean()
    private val employeeRuleBeanList = employeeRuleBeanList.toMutableList()

    override fun createView(ui: AnkoContext<TypeInActivity>) = with(ui) {
        verticalLayout {

            linearLayout {
                backgroundColor = resources.getColor(R.color.royal_blue)
                imageView {
                    imageResource = R.drawable.header_back
                    onClick {
                        mThis.finish()
                    }
                }.lparams(width = 0, weight = 1f, height = wrapContent) {
                    gravity = Gravity.CENTER
                    setPadding(dip(0), dip(5), dip(0), dip(5))
                }

                textView("录入") {
                    textColor = resources.getColor(R.color.white)
                    textSize = resources.getDimension(R.dimen.wex_text_size)
                    gravity = Gravity.CENTER
                }.lparams(width = 0, weight = 10f) {
                    gravity = Gravity.CENTER
                }
            }.lparams(width = matchParent)

            //信息录入部分
            linearLayout {

                textView {
                    text = "姓名："
                }.lparams {
                    setMargins(dip(10), dip(5), dip(10), dip(5))
                }
                editText {
                    hint = "请输入姓名"
                    textChangedListener {
                        afterTextChanged { s: Editable? -> employeeRuleBean.name = s.toString() }
                    }
                }.lparams {
                    setMargins(dip(5), dip(5), dip(10), dip(5))
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
                checkBox("周四", false) {
                    onCheckedChange { _, isChecked ->
                        employeeRuleBean.isThursdayChekced = isChecked
                    }
                }
            }
            linearLayout {

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
                checkBox("周日", false) {
                    onCheckedChange { _, isChecked ->
                        employeeRuleBean.isSundayChecked = isChecked
                    }
                }
            }
            button("提交") {
                backgroundResource = R.drawable.selector_button_blue_corner
                textColor = resources.getColor(R.color.white)
                onClick {
                    mThis.addEmployeeRule(employeeRuleBean)
                }
            }.lparams(width = matchParent) {
                setMargins(dip(10), dip(5), dip(10), dip(5))
            }

            textView("*点击员工姓名可删除员工信息").lparams {
                setMargins(5, 5, 5, 5)
            }
            scrollView {
                verticalLayout {
                    linearLayout {
                        backgroundColor = resources.getColor(R.color.royal_blue)
                    }.lparams(width = matchParent, height = 1)
                    linearLayout {
                        linearLayout {
                            backgroundColor = resources.getColor(R.color.royal_blue)
                        }.lparams(width = 1, height = matchParent)
                        textView("姓名") {
                            gravity = Gravity.CENTER
                            setPadding(0, 8, 0, 8)
                        }.lparams(width = 0, weight = 1.5f)
                        linearLayout {
                            backgroundColor = resources.getColor(R.color.royal_blue)
                        }.lparams(width = 1, height = matchParent)
                        textView("周一") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout {
                            backgroundColor = resources.getColor(R.color.royal_blue)
                        }.lparams(width = 1, height = matchParent)
                        textView("周二") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout {
                            backgroundColor = resources.getColor(R.color.royal_blue)
                        }.lparams(width = 1, height = matchParent)
                        textView("周三") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout {
                            backgroundColor = resources.getColor(R.color.royal_blue)
                        }.lparams(width = 1, height = matchParent)
                        textView("周四") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout {
                            backgroundColor = resources.getColor(R.color.royal_blue)
                        }.lparams(width = 1, height = matchParent)
                        textView("周五") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout {
                            backgroundColor = resources.getColor(R.color.royal_blue)
                        }.lparams(width = 1, height = matchParent)
                        textView("周六") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout {
                            backgroundColor = resources.getColor(R.color.royal_blue)
                        }.lparams(width = 1, height = matchParent)
                        textView("周日") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout {
                            backgroundColor = resources.getColor(R.color.royal_blue)
                        }.lparams(width = 1, height = matchParent)
                    }.lparams(width = matchParent)
                    linearLayout {
                        backgroundColor = resources.getColor(R.color.royal_blue)
                    }.lparams(width = matchParent, height = 1)
                    if (ArrayUtils.isNotEmpty(employeeRuleBeanList)) {
                        for (item in employeeRuleBeanList) {
                            linearLayout {
                                textView(item.name) {
                                    singleLine = true
                                    ellipsize = TextUtils.TruncateAt.END
                                    gravity = Gravity.CENTER
                                    setPadding(0, 8, 0, 8)
                                    onClick {
                                        alert("员工:" + item.name, "是否要删除该员工信息?") {
                                            yesButton { mThis.deleteEmployeeRule(item) }
                                            noButton { }
                                        }.show()
                                    }
                                }.lparams(width = 0, weight = 1.5f)
                                linearLayout {
                                    backgroundColor = resources.getColor(R.color.royal_blue)
                                }.lparams(width = 1, height = matchParent)
                                textView(if (item.isMondayChecked) "有空" else "没空") {
                                    gravity = Gravity.CENTER
                                    if (item.isMondayChecked) {
                                        textColor = resources.getColor(R.color.crimson)
                                    }
                                }.lparams(width = 0, weight = 1f)
                                linearLayout {
                                    backgroundColor = resources.getColor(R.color.royal_blue)
                                }.lparams(width = 1, height = matchParent)
                                textView(if (item.isTuesdayChecked) "有空" else "没空") {
                                    gravity = Gravity.CENTER
                                    if (item.isTuesdayChecked) {
                                        textColor = resources.getColor(R.color.crimson)
                                    }
                                }.lparams(width = 0, weight = 1f)
                                linearLayout {
                                    backgroundColor = resources.getColor(R.color.royal_blue)
                                }.lparams(width = 1, height = matchParent)
                                textView(if (item.isWednesdayChecked) "有空" else "没空") {
                                    gravity = Gravity.CENTER
                                    if (item.isWednesdayChecked) {
                                        textColor = resources.getColor(R.color.crimson)
                                    }
                                }.lparams(width = 0, weight = 1f)
                                linearLayout {
                                    backgroundColor = resources.getColor(R.color.royal_blue)
                                }.lparams(width = 1, height = matchParent)
                                textView(if (item.isThursdayChekced) "有空" else "没空") {
                                    gravity = Gravity.CENTER
                                    if (item.isThursdayChekced) {
                                        textColor = resources.getColor(R.color.crimson)
                                    }
                                }.lparams(width = 0, weight = 1f)
                                linearLayout {
                                    backgroundColor = resources.getColor(R.color.royal_blue)
                                }.lparams(width = 1, height = matchParent)
                                textView(if (item.isFridayChecked) "有空" else "没空") {
                                    gravity = Gravity.CENTER
                                    if (item.isFridayChecked) {
                                        textColor = resources.getColor(R.color.crimson)
                                    }
                                }.lparams(width = 0, weight = 1f)
                                linearLayout {
                                    backgroundColor = resources.getColor(R.color.royal_blue)
                                }.lparams(width = 1, height = matchParent)
                                textView(if (item.isSaturdayChecked) "有空" else "没空") {
                                    gravity = Gravity.CENTER
                                    if (item.isSaturdayChecked) {
                                        textColor = resources.getColor(R.color.crimson)
                                    }
                                }.lparams(width = 0, weight = 1f)
                                linearLayout {
                                    backgroundColor = resources.getColor(R.color.royal_blue)
                                }.lparams(width = 1, height = matchParent)
                                textView(if (item.isSundayChecked) "有空" else "没空") {
                                    gravity = Gravity.CENTER
                                    if (item.isSundayChecked) {
                                        textColor = resources.getColor(R.color.crimson)
                                    }
                                }.lparams(width = 0, weight = 1f)
                                linearLayout {
                                    backgroundColor = resources.getColor(R.color.royal_blue)
                                }.lparams(width = 1, height = matchParent)
                            }.lparams(width = matchParent)
                            linearLayout {
                                backgroundColor = resources.getColor(R.color.royal_blue)
                            }.lparams(width = matchParent, height = 1)
                        }
                    }
                }
            }.lparams(width = matchParent, height = matchParent)
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