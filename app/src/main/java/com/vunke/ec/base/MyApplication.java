package com.vunke.ec.base;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.vunke.ec.util.UiUtils;

/**
 * Created by zhuxi on 2017/3/29.
 */
public class MyApplication extends Application{
    private static final String TAG = "MyApplication";
    private String userId="";
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.init(this);
        UiUtils.queryUserInfo(getApplicationContext());
//        UserInfoUtil.initUserInfo(getApplicationContext());
//        UserInfoUtil.registerBoradcastReceiver(getApplicationContext(), new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (intent != null) {
//                    String action = intent.getAction();
//                    if (action.equals(UserInfoUtil.LOAD_USER_INFO_ACTION)) {
//                        userId = intent.getStringExtra("userID");//用户ID
//                        WorkLog.i(TAG, "initData: userID:" + userId);
//                        SharedPreferencesUtil.setStringValue(getApplicationContext(),SharedPreferencesUtil.USER_ID,userId);
//                        unregisterReceiver(this);
//                    }
//                }
//            }
//        });
    }
}
