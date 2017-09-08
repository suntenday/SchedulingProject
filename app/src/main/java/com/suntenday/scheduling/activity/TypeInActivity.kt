package com.suntenday.scheduling.activity

import android.app.Activity
import android.os.Bundle
import com.suntenday.scheduling.activity.ui.TypeInViewUi
import com.suntenday.scheduling.bean.SimpleBean
import org.jetbrains.anko.setContentView
import java.time.DayOfWeek

/**
 * Created by zhengli08275 on 2017/9/8 0008.
 */
class TypeInActivity : Activity() {

    var checkedWeekList: MutableList<SimpleBean<Boolean>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TypeInViewUi(this).setContentView(this)
    }

    fun updateCheckedWeek(checkedWeek: SimpleBean<Boolean>) {
        checkedWeekList.add(checkedWeek)
    }
}