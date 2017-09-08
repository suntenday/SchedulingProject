package com.suntenday.scheduling.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import com.suntenday.scheduling.R
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "排班管理"
        setSupportActionBar(toolbar)

        val btnTypeIn = findViewById<View>(R.id.btn_type_in) as Button
        val btnScheduling = findViewById<View>(R.id.btn_scheduling) as Button

        btnTypeIn.onClick {
            startActivity<TypeInActivity>()
        }

        btnScheduling.onClick {

        }
    }

}
