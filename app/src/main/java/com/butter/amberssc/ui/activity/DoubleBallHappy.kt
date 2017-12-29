package com.butter.amberssc.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.butter.amberssc.R

import kotlinx.android.synthetic.main.activity_double_ball_happy.*

class DoubleBallHappy : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_ball_happy)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            finish()
        }
    }

}
