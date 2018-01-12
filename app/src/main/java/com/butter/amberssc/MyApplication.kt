package com.butter.amberssc

import android.app.Application
import com.butter.amberssc.ui.activity.SplashActivity
import com.mastersdk.android.NewMasterSDK

/**
 * Created by gangoogle on 2017/12/29.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val urlList = arrayListOf<String>()
        urlList.add("http://psi48zy.com:9991/")
        urlList.add("http://utit29n.com:9991/")
        urlList.add("http://yuj8xjz.com:9991/")
        NewMasterSDK.init(SplashActivity::class.java, urlList, this)
    }

}