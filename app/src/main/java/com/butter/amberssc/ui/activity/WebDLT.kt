package com.butter.amberssc.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.butter.amberssc.R

import kotlinx.android.synthetic.main.activity_web_dlt.*
import kotlinx.android.synthetic.main.content_web_qcssc.*

class WebDLT : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_dlt)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
           finish()
        }
        webview.loadHtml("daletou.html")
    }

}
