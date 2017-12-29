package com.butter.amberssc.ui.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by zgyi on 2017/4/26.
 */

public class DelayLoadJsWebView extends WebView {
    private Context mContext;
    private static final int LOADJS = 0;
    //webview是否已经加载完成html
    private boolean webviewIsload;
    //是否有js方法需要调用
    private boolean isHasJsLoad;
    //缓冲js方法数据
    private JsonData jsLoadCache;
    //记录是否已经loadJs成功
    private boolean isLoadJsOk;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != this && msg.what == LOADJS) {
                if (msg.obj != null) {
                    JsonData data = (JsonData) msg.obj;
                    DelayLoadJsWebView.this.loadUrl("javascript:" + data.methodName + "('" + data.jsonData + "')");
                }
            }
        }
    };

    public DelayLoadJsWebView(Context context) {
        super(context);
        mContext = context;
        initSetting();
    }


    public DelayLoadJsWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initSetting();
    }

    public DelayLoadJsWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initSetting();
    }

    private void initSetting() {
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setBuiltInZoomControls(false);
        this.getSettings().setDomStorageEnabled(false);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        this.getSettings().setSupportZoom(false);
        this.clearCache(true);
        this.setWebChromeClient(new WebChromeClient());
        this.setHorizontalScrollBarEnabled(false);//水平不显示
        this.setVerticalScrollBarEnabled(false); //垂直不显示
        this.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webviewIsload = true;
                if (!isLoadJsOk && isHasJsLoad && jsLoadCache != null) {
                    isLoadJsOk = true;
                    Message message = new Message();
                    message.what = LOADJS;
                    message.obj = jsLoadCache;
                    handler.sendMessage(message);
                }
            }
        });
    }

    /**
     * 加载html
     *
     * @param fileName
     */
    public void loadHtml(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            Toast.makeText(mContext, "url不能为空", Toast.LENGTH_SHORT).show();
        }
        this.loadUrl("file:///android_asset/" + fileName);
    }

    /**
     * 加载html中的js方法
     *
     * @param jsMethodName js方法名
     * @param jsonStr      json字符串数据
     */
    public void loadJavaScript(String jsMethodName, String jsonStr) {
        if (TextUtils.isEmpty(jsMethodName) || TextUtils.isEmpty(jsonStr)) {
            return;
        }
        JsonData jsonData = new JsonData(jsMethodName, jsonStr);
        if (webviewIsload) {
            isLoadJsOk = true;
            Message message = new Message();
            message.what = LOADJS;
            message.obj = jsonData;
            handler.sendMessage(message);
        } else {
            isHasJsLoad = true;
            jsLoadCache = jsonData;
        }
    }

    /**
     * js方法加数据的实体类
     */
    public class JsonData {
        //java script方法名
        public String methodName;
        //json 数据
        public String jsonData;

        public JsonData(String methodName, String jsonData) {
            this.methodName = methodName;
            this.jsonData = jsonData;
        }
    }

    /**
     * 销毁webview并且清空webView
     */
    public void destoryClearCache() {
        this.clearCache(true);
        this.clearHistory();
        this.destroy();
    }

}
