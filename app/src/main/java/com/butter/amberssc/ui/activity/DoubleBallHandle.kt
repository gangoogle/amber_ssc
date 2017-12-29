package com.butter.amberssc.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.butter.amberssc.R

import kotlinx.android.synthetic.main.activity_double_ball_handle.*
import kotlinx.android.synthetic.main.content_double_ball_handle.*

class DoubleBallHandle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_ball_handle)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
           finish()
        }
        webview.loadHtml("双色球数据分析：和值分布及走势图.html")
    }

}
