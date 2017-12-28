package com.butter.amberssc.ui.customview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.butter.amberssc.R
import com.butter.amberssc.adapter.RCLotteryNameAdapter
import com.butter.amberssc.model.CaiPiaoUrl
import kotlinx.android.synthetic.main.view_lottery_url_pop.view.*

/**
 * Created by zgyi on 2017-12-28.
 */

class LotteryTypePopView(val context: Context, val caipiaoUrls: List<CaiPiaoUrl>) {
    lateinit var mPopview: PopupWindow
    lateinit var mView: View

    init {
        initPop()
        setData()

    }

    private fun initPop() {
        val screenWidth = getScreenWH(context)[0]
        val screenHeight = getScreenWH(context)[1]
        mView = View.inflate(context, R.layout.view_lottery_url_pop, null)
        mPopview = PopupWindow(mView, screenWidth/2, (screenHeight/1.5).toInt(), true)
        val dw = ColorDrawable(Color.TRANSPARENT)
        mPopview?.setBackgroundDrawable(dw)
        mPopview?.animationStyle = R.style.UrlPopAnimStyle
    }

    private fun setData() {
        mView?.rc_lottery_url.layoutManager = LinearLayoutManager(context)
        mView?.rc_lottery_url?.adapter = RCLotteryNameAdapter(context, caipiaoUrls)
    }

    fun show(view: View) {
        if (mPopview != null) {
            mPopview.showAsDropDown(view, -mPopview.width, -mPopview.height)
        }
    }

    private fun getScreenWH(context: Context): Array<Int> {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width
        val heigh = wm.defaultDisplay.height
        return arrayOf(width, heigh)
    }

}