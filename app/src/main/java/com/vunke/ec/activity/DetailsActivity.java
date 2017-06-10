package com.vunke.ec.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.ec.R;
import com.vunke.ec.base.BaseActivity;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.mod.DetailsBean;
import com.vunke.ec.network_request.NetWorkRequest;
import com.vunke.ec.util.SharedPreferencesUtil;
import com.vunke.ec.util.UiUtils;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 详情页面
 * Created by zhuxi on 2017/5/25.
 */
public class DetailsActivity extends BaseActivity {
    private static final String TAG = "DetailsActivity";
    private WebView details_webview;
//    private RelativeLayout details_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getIntentData();
        initView();
    }
    private int infoid;
    private  DetailsBean detailsBean;
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("infoid")) {
            infoid = intent.getIntExtra("infoid",-1);
        }
        if (infoid>0){
            Log.i(TAG, "getIntentData:  infoid is null");
            return;
        }
        try {

            JSONObject json = new JSONObject();
            json.put("infoId",infoid);
            json.put("userId", SharedPreferencesUtil.getStringValue(mcontext,SharedPreferencesUtil.USER_ID,""));
            json.put("versionCode", UiUtils.getVersionCode(mcontext)+"");
            WorkLog.i(TAG, "getIntentData: json="+json.toString());
            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.FIND_BYI_ID_INFO2).tag(this).params("json", json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, "onSuccess: ------------------------------------------------------------"+s);
                    try {
                        JSONObject js = new JSONObject(s);
                        if (js.has("code")){
                            int code = js.getInt("code");
                            switch (code){
                                case 200:
                                     detailsBean = new Gson().fromJson(s, DetailsBean.class);
                                    break;
                                case 400:
                                    break;
                                case 500:
                                    break;
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    WorkLog.i(TAG, "onError: ----------------------------------------------------------------");
                }

                @Override
                public void onAfter(String s, Exception e) {
                    super.onAfter(s, e);
                    if (detailsBean!=null && detailsBean.getCode().equals("200")){

                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initView() {
        details_webview = (WebView) findViewById(R.id.details_webview);
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

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
            }
        });
        details_webview.setDownloadListener(new MyWebViewDownLoadListener());
        details_webview.loadUrl("http://124.232.136.236:8083/kjmgr/ec/info/toInfoContentView.shtml?infoid="+infoid);
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
        WorkLog.i(TAG, "keyCode:" + keyCode);
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {// && notfy_webView.canGoBack()
            finish();
            // notfy_webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
