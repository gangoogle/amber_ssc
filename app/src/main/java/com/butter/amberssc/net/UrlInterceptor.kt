package com.butter.amberssc.net

import okhttp3.Interceptor
import android.os.Looper
import android.text.TextUtils
import com.butter.amberssc.utils.LogUtils
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.functions.Func1


/**
 * Created by zgyi on 2017-12-28.
 */
class UrlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): okhttp3.Response {
        val request = chain?.request()


        val response = chain?.proceed(request)
        val requestUrl = response?.request()?.url()?.uri()?.path
        if (!TextUtils.isEmpty(requestUrl)) {
            if (requestUrl!!.contains("sampleUrl")) {
                if (Looper.myLooper() == null) {
                    Looper.prepare()
                }
                createObservable("现在请求的是登录接口")
            }
        }
        return response!!
    }

    private fun createObservable(msg: String) {
        Observable.just(msg).map(Func1<String, String> { s -> s })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Action1 {
                    LogUtils.d("net", "拦截到url $it")
                })
    }

}