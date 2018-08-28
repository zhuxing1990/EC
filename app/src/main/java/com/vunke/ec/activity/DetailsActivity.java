package com.vunke.ec.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.vunke.ec.R;
import com.vunke.ec.base.BaseActivity;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.view.ScrollWebView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 详情页面
 * Created by zhuxi on 2017/5/25.
 */
public class DetailsActivity extends BaseActivity {
    private static final String TAG = "DetailsActivity";
    private ScrollWebView details_webview;
//    private RelativeLayout details_layout;
    private ImageView details_title_godown;
    private ImageView details_title_goup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getIntentData();
        initView();
        initWebView();
        initListener();
        initTimeOut();
    }

    private int infoid;
//    private  DetailsBean detailsBean;
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("infoid")) {
            infoid = intent.getIntExtra("infoid",-1);
        }
        if (infoid<0){
            Log.i(TAG, "getIntentData:  infoid is null");
        }
//        try {
//
//            JSONObject json = new JSONObject();
//            json.put("infoId",infoid);
//            json.put("userId", SharedPreferencesUtil.getStringValue(mcontext,SharedPreferencesUtil.USER_ID,""));
//            json.put("versionCode", UiUtils.getVersionCode(mcontext)+"");
//            WorkLog.i(TAG, "getIntentData: json="+json.toString());
//            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.FIND_BYI_ID_INFO2).tag(this).params("json", json.toString()).execute(new StringCallback() {
//                @Override
//                public void onSuccess(String s, Call call, Response response) {
//                    WorkLog.i(TAG, "onSuccess: ------------------------------------------------------------"+s);
//                    try {
//                        JSONObject js = new JSONObject(s);
//                        if (js.has("code")){
//                            int code = js.getInt("code");
//                            switch (code){
//                                case 200:
//                                    detailsBean = new Gson().fromJson(s, DetailsBean.class);
//                                    break;
//                                case 400:
//                                    break;
//                                case 500:
//                                    break;
//                            }
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(Call call, Response response, Exception e) {
//                    super.onError(call, response, e);
//                    WorkLog.i(TAG, "onError: ----------------------------------------------------------------");
//                }
//
//                @Override
//                public void onAfter(String s, Exception e) {
//                    super.onAfter(s, e);
//                    if (detailsBean!=null && detailsBean.getCode().equals("200")){
//
//                    }
//                }
//            });
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    private void initView() {
        details_webview = (ScrollWebView) findViewById(R.id.details_webview);
        details_title_godown = (ImageView) findViewById(R.id.details_title_godown);
        details_title_goup = (ImageView) findViewById(R.id.details_title_goup);
    }
    private void initWebView() {

//        details_layout = (RelativeLayout) findViewById(R.id.details_layout);
        WebSettings settings = details_webview.getSettings();
        // 支持js
        settings.setJavaScriptEnabled(true);
        // 设置字符编码
        settings.setDefaultTextEncodingName("GBK");
        // 启用支持javascript
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        settings.setBuiltInZoomControls(true);
        settings.setLightTouchEnabled(true);
        settings.setSupportZoom(true);
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 不使用缓存，只从网络获取数据.
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // settings.setLoadWithOverviewMode(true);
        // 支持JS交互
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        details_webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                details_webview.requestFocus();
                WorkLog.i(TAG, "网页加载中");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                WorkLog.i(TAG, "网页加载结束");
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        details_webview.setVisibility(View.VISIBLE);
                    }
                }, 200);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//在2.3上面不加这句话，可以加载出页面，在4.0上面必须要加入，不然出现白屏
                return true;
            }
        });
        details_webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 1) { // 加载中
                    WorkLog.i(TAG, "网页加载进度：" + newProgress);
                    details_webview.requestFocus();
                    // notfy_webView.setVisibility(View.GONE);
                } else if (newProgress == 100) { // 网页加载完成
                    WorkLog.i(TAG, "网页加载进度：" + newProgress);
                    details_webview.requestFocus();
                    // notfy_webView.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }
//            @Override
//            public void onShowCustomView(View view, CustomViewCallback callback) {
//                super.onShowCustomView(view, callback);
//            }
//
//            @Override
//            public void onHideCustomView() {
//                super.onHideCustomView();
//            }
        });
        details_webview.setDownloadListener(new MyWebViewDownLoadListener());
        details_webview.loadUrl("http://124.232.136.236:8083/kjmgr/ec/info/toInfoContentView.shtml?infoid="+infoid);
    }

    private boolean isPageEnd = false;
    private boolean isPageTop = true;
    private void initListener() {

        details_webview.setOnScrollChangeListener(new ScrollWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
                details_title_goup.setVisibility(View.VISIBLE);
                details_title_godown.setVisibility(View.INVISIBLE);
                isPageEnd = true;
                isPageTop = false;
            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
                details_title_goup.setVisibility(View.INVISIBLE);
                details_title_godown.setVisibility(View.VISIBLE);
;               isPageEnd = false;
                isPageTop = true;
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                if (!isPageEnd){
                    details_title_godown.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private Observable<Long> observable;
    private Subscriber<Long> subscriber;
    private  Subscription subscribe2;
    private void initTimeOut() {
        final long endtime = 5000;
        observable = Observable.interval(endtime,2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                this.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                this.unsubscribe();
            }

            @Override
            public void onNext(Long aLong) {
                if (aLong != 0) {
                    if (details_webview.getVisibility() == View.VISIBLE) {
                        if (isPageTop && !isPageEnd) {
                            details_webview.scrollBy(0, 1);
                        } else if (isPageEnd) {
                            this.unsubscribe();
                        }
                    }
                } else {

                }
            }
        };
        subscribe2 = observable.subscribe(subscriber);
    }






    private void stopTimerOut() {
        if (subscribe2!=null) {
            subscribe2.unsubscribe();
        }
        subscribe2 = null;
        observable = null;
        subscriber = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe2!=null&&!subscribe2.isUnsubscribed()){
            subscribe2.unsubscribe();
        }
    }

    /**
     * WebView 点击下载监听
     *
     * @author zhuxi
     */
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            WorkLog.i(TAG, "onKey:ACTION_DOWN -----------------------------------");
            if (subscribe2 != null) {
                stopTimerOut();
            }
            initTimeOut();
        }
        WorkLog.i(TAG, "keyCode:" + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK && details_webview.canGoBack()) {  //表示按返回键
            if (details_webview.getUrl().contains("http://124.232.136.236:8083/kjmgr/ec/info/toInfoContentView.shtml")){
                return false;
            }
            details_webview.goBack();   //后退
            //webview.goForward();//前进
            return true;    //已处理
        }
//        else if(keyCode == KeyEvent.KEYCODE_BACK){
//            return false;
//        }
        return super.onKeyDown(keyCode, event);

    }

}
