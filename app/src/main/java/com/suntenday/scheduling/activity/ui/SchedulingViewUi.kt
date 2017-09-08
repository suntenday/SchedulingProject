package com.suntenday.scheduling.activity.ui

import android.app.Activity
import com.suntenday.scheduling.activity.SchedulingActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout

/**
 * Created by zhengli08275 on 2017/9/8 0008.
 */
class SchedulingViewUi(activity: SchedulingActivity):AnkoComponent<SchedulingActivity>{

    val mThis = activity

    override fun createView(ui: AnkoContext<SchedulingActivity>) = with(ui) {
        linearLayout {

        }
    }
}