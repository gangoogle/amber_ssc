package com.butter.amberssc.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.butter.amberssc.R
import kotlinx.android.synthetic.main.activity_double_ball50.*


class DoubleBall50 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_ball50)
        webview.loadHtml("双色球走势图_福彩双色球红球篮球冷热基本走势图-网易彩票.html")

        fab.setOnClickListener { view ->
            finish()
        }
    }
}
