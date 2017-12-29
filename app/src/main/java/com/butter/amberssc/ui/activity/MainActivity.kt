package com.butter.amberssc.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.butter.amberssc.R
import com.butter.amberssc.adapter.RCLottertAdapter
import com.butter.amberssc.data.getUrlList
import com.butter.amberssc.model.CaiPiaoUrl
import com.butter.amberssc.model.response.BaseResp
import com.butter.amberssc.model.response.CaiPiaoResponse
import com.butter.amberssc.net.Api
import com.butter.amberssc.net.RetrofitNetHelper
import com.butter.amberssc.ui.customview.LotteryTypePopView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var mContext: Context

    lateinit var mCurrCaipiaoUrl: CaiPiaoUrl
    lateinit var mCaipiaoUrlList: List<CaiPiaoUrl>
    lateinit var mUrlPopView: LotteryTypePopView
    val SIZE = 20
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            mUrlPopView.show(view)
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        init()
    }

    private fun init() {
        mCaipiaoUrlList = getUrlList()
        mCurrCaipiaoUrl = mCaipiaoUrlList[0]
        //设置toolbar title
        supportActionBar?.title = mCurrCaipiaoUrl.name
        mUrlPopView = LotteryTypePopView(mContext, mCaipiaoUrlList)
        //设置监听器
        mUrlPopView.setOnItemClickListener(object : LotteryTypePopView.OnItemClickListener {
            override fun onClick(position: Int) {
                mCurrCaipiaoUrl = mCaipiaoUrlList[position]
                supportActionBar?.title = mCurrCaipiaoUrl.name
                reqquestLottery()
            }
        })
        reqquestLottery()
    }

    private fun reqquestLottery() {
        val callResponse = RetrofitNetHelper.getInstance(mContext)
                .getAPIService(Api::class.java)
                .requestUrl("${mCurrCaipiaoUrl.url}-$SIZE.json")
        RetrofitNetHelper.getInstance(mContext)
                .enqueueCall(callResponse, object : RetrofitNetHelper.RetrofitCallBack<CaiPiaoResponse> {
                    override fun onSuccess(baseResp: BaseResp<CaiPiaoResponse>) {
                        rc_view.layoutManager = LinearLayoutManager(mContext)
                        rc_view.adapter = RCLottertAdapter(mContext, baseResp.data)
                    }

                    override fun onFailure(error: String) {
                        rc_view.adapter = RCLottertAdapter(mContext, arrayListOf<CaiPiaoResponse>())
                        Snackbar.make(fab, error, Snackbar.LENGTH_SHORT).show()
                    }
                })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(mContext, About::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                startActivity(Intent(mContext, DoubleBall50::class.java))
            }
            R.id.nav_gallery -> {
                startActivity(Intent(mContext, DoubleBallKnow::class.java))
            }
            R.id.nav_slideshow -> {
                startActivity(Intent(mContext, DoubleBallHandle::class.java))
            }
            R.id.nav_rules -> {
                startActivity(Intent(mContext, DoubleBallRules::class.java))
            }
            R.id.nav_happy -> {
                startActivity(Intent(mContext, DoubleBallHappy::class.java))
            }
            R.id.nav_cqssc -> {
                startActivity(Intent(mContext, WebQCssc::class.java))
            }
            R.id.nav_dlt -> {
                startActivity(Intent(mContext, WebDLT::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
