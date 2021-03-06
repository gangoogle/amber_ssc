package com.butter.amberssc.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.butter.amberssc.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        context = this
        tv_show.setOnViewLoadCompleteListener {
            startActivity(Intent(context, MainActivity::class.java))
            finish()
        }
    }


}
