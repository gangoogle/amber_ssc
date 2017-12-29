package com.butter.amberssc.net

import android.content.Context
import com.butter.amberssc.constant.SERVER_URL
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.File
import java.util.concurrent.TimeUnit
import android.widget.Toast
import com.butter.amberssc.model.response.BaseResp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by zgyi on 2017-12-28.
 */
class RetrofitNetHelper private constructor(val context: Context) {

    //retrofit请求
    lateinit var mRetrofit: Retrofit
    //默认url
    val baseUrl = SERVER_URL
    //缓存
    private var cache: Cache? = null

    companion object {
        private var mInstance: RetrofitNetHelper? = null

        fun getInstance(context: Context): RetrofitNetHelper {
            if (mInstance == null) {
                synchronized(RetrofitNetHelper::class.java) {
                    if (mInstance == null) {
                        mInstance = RetrofitNetHelper(context)
                    }
                }
            }
            return mInstance!!
        }
    }


    init {
        //logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor(com.butter.amberssc.net.HttpLoggingInterceptor())
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //params interceptor
        val basicParamsInterceptor = BasicParamsInterceptor.Builder().build()
        //url interceptor
        val urlInterceptor = UrlInterceptor()

        //cache
        val cacheFile = File(context.getCacheDir(), "HttpCache")
        cache = Cache(cacheFile, (1024 * 1024 * 100).toLong()) //100Mb

        val mOkhttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(basicParamsInterceptor)
                .addInterceptor(urlInterceptor)
                .retryOnConnectionFailure(true)
                .cache(cache)
                .connectTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .readTimeout(12, TimeUnit.SECONDS)
                .build()
        //build retrofit
        mRetrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkhttpClient)
                .baseUrl(baseUrl)
                .build()
    }

    fun getCache(): Cache? {
        return cache
    }

    @Throws(IOException::class)
    fun clearCache() {
        cache!!.delete()
    }

    fun <D> enqueueCall(call: Call<BaseResp<D>>, retrofitCallBack: RetrofitCallBack<D>?) {

        call.enqueue(object : Callback<BaseResp<D>> {

            override fun onResponse(call: Call<BaseResp<D>>, response: Response<BaseResp<D>>) {
                val resp = response.body()
                if (resp == null) {
                    if (retrofitCallBack != null) {
                        retrofitCallBack!!.onFailure("暂无数据")
                    }
                    return
                }
                if (retrofitCallBack != null) {
                    retrofitCallBack!!.onSuccess(resp)
                }

            }

            override fun onFailure(call: Call<BaseResp<D>>, t: Throwable) {
                //   ToastMaker.makeToast(mContext, "网络错误，请重试！", Toast.LENGTH_SHORT);
                if (retrofitCallBack != null) {
                    retrofitCallBack!!.onFailure(t.toString())
                }
            }
        })
    }

    //异步特殊处理后回调
    interface RetrofitCallBack<D> {
        fun onSuccess(baseResp: BaseResp<D>)
        fun onFailure(error: String)
    }

    //获取相应的APIService对象
    public fun <T> getAPIService(service: Class<T>): T {
        return mRetrofit.create(service)
    }

}