package com.suntenday.scheduling.activity.base

import android.app.Activity
import android.os.Bundle
import android.content.pm.PackageManager
import android.os.Build
import android.annotation.SuppressLint
import android.opengl.ETC1.getWidth
import android.opengl.ETC1.getHeight
import android.widget.EditText
import android.view.MotionEvent
import android.view.View
import com.suntenday.scheduling.utils.WindowSoftInputUtils


/**
 * BaseActivity
 * @author zhengli08275
 * @date 2017/9/11 0011.
 */
public open class BaseActivity : Activity() {

    protected var mThis: BaseActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val allowablePermissionRunnables = HashMap<Int, Runnable?>()
    private val disallowablePermissionRunnables = HashMap<Int, Runnable?>()

    /**
     * 请求权限
     * @param id 请求授权的id 唯一标识即可
     * @param permission 请求的权限
     * @param allowableRunnable 同意授权后的操作
     * @param disallowableRunnable 禁止权限后的操作
     */
    @SuppressLint("NewApi")
    fun requestPermission(id: Int, permission: String, allowableRunnable: Runnable?, disallowableRunnable: Runnable?) {
        if (allowableRunnable == null) {
            throw IllegalArgumentException("allowableRunnable == null")
        }

        allowablePermissionRunnables.put(id, allowableRunnable)
        if (disallowableRunnable != null) {
            disallowablePermissionRunnables.put(id, disallowableRunnable)
        }

        //版本判断
        if (Build.VERSION.SDK_INT >= 23) {
            //是否拥有权限
            val checkCallPhonePermission = checkSelfPermission(permission)
            //判断是否能弹出权限对话框
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //弹出对话框接收权限
                requestPermissions(arrayOf(permission), id)
                return
            } else {
                allowableRunnable.run()
            }
        } else {
            allowableRunnable.run()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val allowRun = allowablePermissionRunnables.get(requestCode)
            allowRun?.run()
        } else {
            val disallowRun = disallowablePermissionRunnables.get(requestCode)
            disallowRun?.run()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                v!!.clearFocus()
                //				hideKeyboard(v.getWindowToken());
                WindowSoftInputUtils.hideWindowSoftInput(mThis)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v!!.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v!!.getHeight()
            val right = left + v!!.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false
    }
}