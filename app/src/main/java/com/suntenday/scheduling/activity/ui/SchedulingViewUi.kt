package com.suntenday.scheduling.activity.ui

import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.suntenday.scheduling.R
import com.suntenday.scheduling.activity.SchedulingActivity
import com.suntenday.scheduling.utils.CalendarUtils
import com.suntenday.scheduling.utils.StringUtils
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.HashMap

/**
 * Created by suntenday on 2017/9/8 0008.
 */
class SchedulingViewUi(activity: SchedulingActivity, year: Int, month: Int, employeeList: HashMap<Int, String>) : AnkoComponent<SchedulingActivity> {

    val mThis = activity
    val mEmployeeList = employeeList
    var selectedYear = year
    var selectedMonth = month
    var mCalendarView = HashMap<Int, LinearLayout>()

    override fun createView(ui: AnkoContext<SchedulingActivity>) = with(ui) {
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

                textView("排班") {
                    textColor = resources.getColor(R.color.white)
                    textSize = resources.getDimension(R.dimen.wex_text_size)
                    gravity = Gravity.CENTER
                }.lparams(width = 0, weight = 10f) {
                    gravity = Gravity.CENTER
                }
            }.lparams(width = matchParent)
            verticalLayout {
                linearLayout {
                    linearLayout {
                        textView(selectedYear.toString() + "年 >") {
                            textColor = resources.getColor(R.color.white)
                            textSize = resources.getDimension(R.dimen.wer_text_size)
                            backgroundDrawable = resources.getDrawable(R.drawable.shape_bg_blue_5r)
                            onClick {
                                val years = listOf("2017", "2018")
                                selector("选择年份", years) { ds, i ->
                                    selectedYear = StringUtils.strToInt("${years[i]}")
                                    text = selectedYear.toString() + "年 >"
                                    mThis.queryScheduling(selectedYear, selectedMonth)
                                }
                            }
                            setPadding(dip(10), dip(5), dip(10), dip(5))
                        }
                        gravity = Gravity.CENTER
                    }.lparams(width = 0, weight = 1f) {
                        setPadding(dip(5), dip(5), dip(5), dip(5))
                    }
                    linearLayout {
                        textView(selectedMonth.toString() + "月 >") {
                            textColor = resources.getColor(R.color.white)
                            textSize = resources.getDimension(R.dimen.wer_text_size)
                            backgroundDrawable = resources.getDrawable(R.drawable.shape_bg_blue_5r)
                            onClick {
                                val months = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
                                selector("选择月份", months) { ds, i ->
                                    selectedMonth = StringUtils.strToInt("${months[i]}")
                                    text = selectedMonth.toString() + "月 >"
                                    mThis.queryScheduling(selectedYear, selectedMonth)
                                }
                            }
                            setPadding(dip(10), dip(5), dip(10), dip(5))
                        }.lparams {
                            gravity = Gravity.CENTER
                            setPadding(dip(5), dip(5), dip(5), dip(5))
                        }
                    }.lparams(width = 0, weight = 1f) {
                        setPadding(dip(5), dip(5), dip(5), dip(5))
                    }
                    gravity = Gravity.CENTER
                }.lparams(width = matchParent) {
                    gravity = Gravity.CENTER
                }

                linearLayout {
                    button {
                        if (mEmployeeList != null && mEmployeeList.size > 0) {
                            text = "已排班"
                            isEnabled = false
                        } else {
                            text = "排班"
                            isEnabled = true
                        }
                        textColor = resources.getColor(R.color.white)
                        backgroundDrawable = resources.getDrawable(R.drawable.selector_button_blue_corner)
                        onClick {
                            mThis.addScheduling(selectedYear, selectedMonth)
                        }
                    }.lparams(width = 0, weight = 1f) {
                        setMargins(dip(15), dip(0), dip(15), dip(5))
                        gravity = Gravity.CENTER
                    }

                    if (mEmployeeList != null && mEmployeeList.size > 0) {
                        button("重新排班") {
                            textColor = resources.getColor(R.color.white)
                            backgroundDrawable = resources.getDrawable(R.drawable.shape_bg_crimson_5r)
                            onClick {
                                mThis.updateScheduling(selectedYear, selectedMonth)
                            }
                        }.lparams(width = 0, weight = 1f) {
                            setMargins(dip(15), dip(0), dip(15), dip(5))
                            gravity = Gravity.CENTER
                        }
                    }
                }.lparams(width = matchParent)
            }

            scrollView {
                verticalLayout {

                    linearLayout {
                        backgroundColor = resources.getColor(R.color.royal_blue)
                    }.lparams(width = matchParent, height = 1)

                    linearLayout {
                        linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                        textView("周日") {
                            gravity = Gravity.CENTER
                            textColor = resources.getColor(R.color.crimson)
                        }.lparams(width = 0, weight = 1f)
                        linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                        textView("周一") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                        textView("周二") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                        textView("周三") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                        textView("周四") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                        textView("周五") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                        linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                        textView("周六") {
                            gravity = Gravity.CENTER
                            textColor = resources.getColor(R.color.crimson)
                        }.lparams(width = 0, weight = 1f)
                        linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                        gravity = Gravity.CENTER
                    }.lparams(width = matchParent)

                    linearLayout {
                        backgroundColor = resources.getColor(R.color.royal_blue)
                    }.lparams(width = matchParent, height = 1)

                    for(viewIndex in 1..5) {
                        val week = linearLayout {
                            linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                            textView("") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                            linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                            textView("") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                            linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                            textView("") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                            linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                            textView("") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                            linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                            textView("") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                            linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                            textView("") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                            linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                            textView("") { gravity = Gravity.CENTER }.lparams(width = 0, weight = 1f)
                            linearLayout { backgroundColor = resources.getColor(R.color.royal_blue) }.lparams(width = 1, height = matchParent)
                        }.lparams(width = matchParent)

                        linearLayout {
                            backgroundColor = resources.getColor(R.color.royal_blue)
                        }.lparams(width = matchParent, height = 1)
                        mCalendarView.put(viewIndex, week)
                    }

                    val monthDay = mThis.getMonthDay(selectedYear, selectedMonth)
                    var index = 1
                    var weekIndex = 1
                    var weekView = mCalendarView[1]
                    for (i in 1..monthDay) {
                        val date = selectedYear.toString() + "-" + selectedMonth.toString() + "-" + i
                        val week = CalendarUtils.getWeek(date, CalendarUtils.DATE_FORMAT)
                        if (i == 1) {
                            if ("星期一" == week) {
                                index = 2
                            } else if ("星期二" == week) {
                                index = 3
                            } else if ("星期三" == week) {
                                index = 4
                            } else if ("星期四" == week) {
                                index = 5
                            } else if ("星期五" == week) {
                                index = 6
                            } else if ("星期六" == week) {
                                index = 7
                            } else if ("星期日" == week) {
                                index = 1
                            }
                            val name = mEmployeeList[i]
                            var trueIndex = index + index - 1
                            if (StringUtils.isStrNotEmpty(name)) {
                                (mCalendarView[1]!!.getChildAt(trueIndex) as TextView).text = name
                            } else {
                                (mCalendarView[1]!!.getChildAt(trueIndex) as TextView).text = i.toString()
                            }
                            if (index < 7) {
                                index++
                            } else {
                                index = 1
                            }
                        } else {
                            val name = mEmployeeList[i]
                            if (index == 1) {
//                                if (weekIndex == 1) {
//                                    weekView = week2
//                                } else if (weekIndex == 2) {
//                                    weekView = week3
//                                } else if (weekIndex == 3) {
//                                    weekView = week4
//                                } else if (weekIndex == 4) {
//                                    weekView = week5
//                                }
                                weekIndex++
                                weekView = mCalendarView[weekIndex]
                            }

                            var trueIndex = index + index - 1
                            if (StringUtils.isStrNotEmpty(name)) {
                                (weekView!!.getChildAt(trueIndex) as TextView).text = name
                            } else {
                                (weekView!!.getChildAt(trueIndex) as TextView).text = i.toString()
                            }

                            if (index < 7) {
                                index++
                            } else {
                                index = 1
                            }
                        }
                    }
                }
            }.lparams(width = matchParent, height = matchParent)
        }
    }
}