package com.butter.amberssc.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.butter.amberssc.R

import kotlinx.android.synthetic.main.activity_double_ball_rules.*

class DoubleBallRules : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_double_ball_rules)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            finish()
        }
    }

}
