package com.vunke.ec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.ec.R;
import com.vunke.ec.adapter.PAPAdapter;
import com.vunke.ec.base.BaseActivity;
import com.vunke.ec.base.Configs;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.mod.ColumnBean;
import com.vunke.ec.network_request.NetWorkRequest;
import com.vunke.ec.util.SharedPreferencesUtil;
import com.vunke.ec.util.UiUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import app.com.tvrecyclerview.TvRecyclerView;
import okhttp3.Call;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 党务公开
 */
public class PartyAffairsPublicityActivity extends BaseActivity {
    private static final String TAG = "PartyAffairsPublicityActivity";
    private String infoId;
    private TvRecyclerView pap_content_recycler;
//    private List<Map<String, Object>> datalist;
    private TextView pap_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_affairs_publicity);
        initView();
        getIntentData();
//        initData();
    }

    private void initView() {
        pap_title = (TextView) findViewById(R.id.pap_title);
        pap_content_recycler = (TvRecyclerView) findViewById(R.id.pap_content_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        pap_content_recycler.setLayoutManager(manager);

        pap_content_recycler.setOnItemStateListener(new TvRecyclerView.OnItemStateListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                Configs.intent = new Intent(mcontext, DetailsActivity.class);
//                Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Configs.intent.putExtra("infoid",columnBean.getData().get(position).getInfoid());
                startActivity(Configs.intent);
            }

            @Override
            public void onItemViewFocusChanged(boolean gainFocus, View view, int position) {

            }
        });
        pap_content_recycler.setSelectedScale(1.0f);
        pap_content_recycler.setSelectPadding(6, 6, 6, 6);

    }

    private ColumnBean columnBean;

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("infoId")) {
            infoId = intent.getStringExtra("infoId");
            WorkLog.i(TAG, "getIntentData: infoId:" + infoId);
        }
        try {
            JSONObject json = new JSONObject();
            json.put("blockId", infoId + "");
            json.put("userId", SharedPreferencesUtil.getStringValue(mcontext, SharedPreferencesUtil.USER_ID, ""));
            json.put("versionCode", UiUtils.getVersionCode(mcontext) + "");
            WorkLog.i(TAG, "getIntentData: json=" + json.toString());
            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.FINDBY_BLOCK_ID).tag(this).params("json", json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, "onSuccess: ------------------------------------------------------------" + s);
                    try {
                        JSONObject js = new JSONObject(s);
                        if (js.has("code")) {
                            int code = js.getInt("code");
                            switch (code) {
                                case 200:
                                    columnBean = new Gson().fromJson(s, ColumnBean.class);
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
                    WorkLog.i(TAG, "onError: ---------------------------------------------------------------");
                }

                @Override
                public void onAfter(String s, Exception e) {
                    super.onAfter(s, e);
                    WorkLog.i(TAG, "onAfter: " + columnBean.getCode().equals("200"));
                    if (columnBean != null && columnBean.getCode().equals("200")) {
                        pap_title.setText(columnBean.getBlockNmae());
                        initRecyclerView();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isBlack = false;

    private void initRecyclerView() {
        final List<ColumnBean.DataBean> dataBean = columnBean.getData();
        if (dataBean != null) {
            for (int i = 0; i < dataBean.size(); i++) {
                isBlack = !isBlack;
                if (isBlack) {
                    dataBean.get(i).setAdapterType(1);
                } else {
                    dataBean.get(i).setAdapterType(2);
                }
            }
            PAPAdapter adapter = new PAPAdapter(mcontext, dataBean);
            pap_content_recycler.setAdapter(adapter);
            if (adapter != null && adapter.getItemCount() != 0) {
//            partyaffairs_tvrecycler.setItemSelected(adapter.getItemCount());
                Observable.timer(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                pap_content_recycler.setItemSelected(0);
                            }
                        });
            }
        }
    }

}
