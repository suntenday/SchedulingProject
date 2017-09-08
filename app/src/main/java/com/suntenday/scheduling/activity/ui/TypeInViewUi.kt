package com.suntenday.scheduling.activity.ui

import android.view.View
import android.widget.Toast
import com.suntenday.scheduling.R
import com.suntenday.scheduling.activity.TypeInActivity
import com.suntenday.scheduling.bean.SimpleBean
import com.suntenday.scheduling.enums.CommonEnum
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by zhengli08275 on 2017/9/8 0008.
 */
class TypeInViewUi(activity:TypeInActivity):AnkoComponent<TypeInActivity>{

    private val mThis = activity
    private var checkedWeekMap = mapOf<String, String>(Pair("周一", CommonEnum.NO.key), Pair("周二", CommonEnum.NO.key), Pair("周三", CommonEnum.NO.key), Pair("周四", CommonEnum.NO.key), Pair("周五", CommonEnum.NO.key), Pair("周六", CommonEnum.NO.key), Pair("周日", CommonEnum.NO.key)).toMutableMap()

    override fun createView(ui: AnkoContext<TypeInActivity>) = with(ui) {
        verticalLayout {
            //信息录入部分
            linearLayout {

                textView {
                    text = "姓名："
                }
                editText {
                    hint = "请输入姓名"
                }

                checkBox("周一",false){
                    onCheckedChange { _, isChecked ->
                        checkedWeekMap[text.toString()] = CommonEnum.getKey(isChecked)
                    }
                }
                checkBox("周二",false){
                    onCheckedChange { _, isChecked ->
                        checkedWeekMap[text.toString()] = CommonEnum.getKey(isChecked)
                    }
                }
                checkBox("周三",false){
                    onCheckedChange { _, isChecked ->
                        checkedWeekMap[text.toString()] = CommonEnum.getKey(isChecked)
                    }
                }
                checkBox("周四",false){
                    onCheckedChange { _, isChecked ->
                        checkedWeekMap[text.toString()] = CommonEnum.getKey(isChecked)
                    }
                }
                checkBox("周五",false){
                    onCheckedChange { _, isChecked ->
                        checkedWeekMap[text.toString()] = CommonEnum.getKey(isChecked)
                    }
                }
                checkBox("周六",false){
                    onCheckedChange { _, isChecked ->
                        checkedWeekMap[text.toString()] = CommonEnum.getKey(isChecked)
                    }
                }
                checkBox("周日",false){
                    onCheckedChange { _, isChecked ->
                        checkedWeekMap[text.toString()] = CommonEnum.getKey(isChecked)
                    }
                }


            }
            button("提交"){
                backgroundResource = R.color.blue
                onClick {
                    Toast.makeText(mThis, checkedWeekMap.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}