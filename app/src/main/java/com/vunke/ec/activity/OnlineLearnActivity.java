package com.vunke.ec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.ec.R;
import com.vunke.ec.adapter.OnlineLearnAdapter;
import com.vunke.ec.adapter.SpaceItemDecoration;
import com.vunke.ec.base.BaseActivity;
import com.vunke.ec.base.Configs;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.mod.DetailsBean;
import com.vunke.ec.network_request.NetWorkRequest;
import com.vunke.ec.util.SharedPreferencesUtil;
import com.vunke.ec.util.UiUtils;
import com.vunke.ec.view.FlyBorderView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.com.tvrecyclerview.TvRecyclerView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 在线学习
 */
public class OnlineLearnActivity extends BaseActivity {
    private static final String TAG = "OnlineLearnActivity";
    private List<Object> list;
    private FlyBorderView onlinelearn_flyborderview;
    private TvRecyclerView onlinelearn_recyclerview2;
    private OnlineLearnAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_learn);
        initView();
        getIntentData();
    }
    private void initView() {
        onlinelearn_flyborderview = (FlyBorderView) findViewById(R.id.onlinelearn_flyborderview);
        onlinelearn_recyclerview2 = (TvRecyclerView) findViewById(R.id.onlinelearn_recyclerview2);
        GridLayoutManager manager = new GridLayoutManager(mcontext, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        onlinelearn_recyclerview2.setLayoutManager(manager);

        int itemSpace = getResources().
                getDimensionPixelSize(R.dimen.recyclerview_item_space30);
        onlinelearn_recyclerview2.addItemDecoration(new SpaceItemDecoration(itemSpace));

        onlinelearn_recyclerview2.setOnItemStateListener(new TvRecyclerView.OnItemStateListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                Configs.intent = new Intent(mcontext,VideoPlayActivity.class);
                startActivity(Configs.intent);
            }

            @Override
            public void onItemViewFocusChanged(boolean gainFocus, View view, int position) {

            }
        });
        onlinelearn_recyclerview2.setSelectedScale(1.1f);
        onlinelearn_recyclerview2.setSelectPadding(6,6,6,6);
    }
    private int infoid;
    private DetailsBean detailsBean;
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("infoid")) {
            infoid = intent.getIntExtra("infoid", -1);
        }
        if (infoid > 0) {
            Log.i(TAG, "getIntentData:  infoid is null");
            return;
        }
        try {

            JSONObject json = new JSONObject();
            json.put("infoId", infoid);
            json.put("userId", SharedPreferencesUtil.getStringValue(mcontext, SharedPreferencesUtil.USER_ID, ""));
            json.put("versionCode", UiUtils.getVersionCode(mcontext) + "");
            WorkLog.i(TAG, "getIntentData: json=" + json.toString());
            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.FIND_BYI_ID_INFO2).tag(this).params("json", json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, "onSuccess: ------------------------------------------------------------" + s);
                    try {
                        JSONObject js = new JSONObject(s);
                        if (js.has("code")) {
                            int code = js.getInt("code");
                            switch (code) {
                                case 200:
                                    detailsBean = new Gson().fromJson(s, DetailsBean.class);
                                    break;
                                case 400:
                                    break;
                                case 500:
                                    break;
                            }
                        }
                    } catch (Exception e) {
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
                    if (detailsBean != null && detailsBean.getCode().equals("200")) {
                        initRecyclerView();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {

        list = new ArrayList<>();
        for (int i = 0; i <40 ; i++) {
            list.add("益村视频"+i);
        }
        adapter = new OnlineLearnAdapter(mcontext,list);
        onlinelearn_recyclerview2.setAdapter(adapter);
    }





}
