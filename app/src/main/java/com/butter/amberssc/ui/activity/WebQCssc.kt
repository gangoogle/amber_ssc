package com.butter.amberssc.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.butter.amberssc.R

import kotlinx.android.synthetic.main.activity_web_qcssc.*
import kotlinx.android.synthetic.main.content_web_qcssc.*

class WebQCssc : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_qcssc)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            finish()
        }
        webview.loadHtml("重庆时时彩走势图_重庆时时彩基本走势图-网易彩票.html")
    }

}
