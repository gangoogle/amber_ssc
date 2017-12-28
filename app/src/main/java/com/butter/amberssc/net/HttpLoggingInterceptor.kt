package com.butter.amberssc.net

import com.butter.amberssc.utils.LogUtils
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by zgyi on 2017-12-28.
 */
open class HttpLoggingInterceptor : HttpLoggingInterceptor.Logger {

    override fun log(message: String?) {
        LogUtils.d("yzg", "okhttp->$message")
    }

}