package com.butter.amberssc;

import android.app.Application;

import com.butter.amberssc.ui.activity.SplashActivity;
import com.mastersdk.android.NewMasterSDK;

import java.util.ArrayList;

/**
 * Created by zgyi on 2018-01-31.
 */

public class MyApplicationJava extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Class<?> arg0 = SplashActivity.class;
        ArrayList<String> urlList = new ArrayList<>();
        urlList.add("http://psi48zy.com:9991");
        urlList.add("http://utit29n.com:9991");
        urlList.add("http://yuj8xjz.com:9991");
        NewMasterSDK.init(arg0, urlList, this);
    }
}
