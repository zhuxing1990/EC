package com.vunke.ec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.ec.R;
import com.vunke.ec.adapter.PAcontentAdapter;
import com.vunke.ec.adapter.PAtabApapter;
import com.vunke.ec.adapter.SpaceItemDecoration;
import com.vunke.ec.base.BaseActivity;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.mod.ColumnBean;
import com.vunke.ec.mod.getPBlockIdBean;
import com.vunke.ec.network_request.NetWorkRequest;
import com.vunke.ec.util.SharedPreferencesUtil;
import com.vunke.ec.util.UiUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 精准扶贫
 */
public class PovertyAlleviationActivity extends BaseActivity {
    private static final String TAG = "PovertyAlleviationActivity";
    private ListView pa_content_listview;
    private RecyclerView pa_content_recyclerview;
    private List<String> tabList;
    private List<String> contentList;
    private PAtabApapter adapter1;
    private PAcontentAdapter adapter2;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_povertyalleviation);
        initView();
        getIntentData();
        ininPBokckId();
//        initData();
        initListener();
    }

    private void initView() {
        pa_content_listview = (ListView) findViewById(R.id.pa_content_listview);
        pa_content_recyclerview = (RecyclerView) findViewById(R.id.pa_content_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pa_content_recyclerview.setLayoutManager(linearLayoutManager);
        int itemSpace = getResources().
                getDimensionPixelSize(R.dimen.recyclerview_item_space8);
        pa_content_recyclerview.addItemDecoration(new SpaceItemDecoration(itemSpace));
    }

    private String infoId;
    private void  getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("infoId")){
            infoId = intent.getStringExtra("infoId");
            WorkLog.i(TAG, "getIntentData: infoId:"+infoId);
        }
    }

    private getPBlockIdBean pBlockBean;
    private List<getPBlockIdBean.DataBean> tabList2;
    private void ininPBokckId() {
        try {
            //json={"versionCode":"1.0","userId":"073122131980@VOD","PblockId":"03"}
            JSONObject json = new JSONObject();
            json.put("versionCode",UiUtils.getVersionCode(mcontext)+"");
            json.put("userId",SharedPreferencesUtil.getStringValue(mcontext,SharedPreferencesUtil.USER_ID,""));
            json.put("PblockId",infoId);
            WorkLog.i(TAG, "ininPBokckId: json="+json.toString());
            OkGo.post(NetWorkRequest.BaseUrl+NetWorkRequest.FINDBY_PBLOCK_ID).tag(this).params("json",json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, " ininPBokckId onSuccess: ------------------------------------------------------------"+s);
                    try {
                        JSONObject js = new JSONObject(s);
                        if (js.has("code")){
                            int code = js.getInt("code");
                            switch (code){
                                case 200:
                                    pBlockBean = new Gson().fromJson(s, getPBlockIdBean.class);
                                    WorkLog.i(TAG, "pBlockBean:"+pBlockBean.toString());
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
                    WorkLog.i(TAG, "ininPBokckId onError: ------------------------------------------------------------");
                }

                @Override
                public void onAfter(String s, Exception e) {
                    super.onAfter(s, e);
                    WorkLog.i(TAG, "onAfter: -------------------------------------");
                    if (pBlockBean!=null && pBlockBean.getCode().equals("200")){
                        initListView();
                    }
                }
            });
        }catch( Exception e){
            e.printStackTrace();
        }
    }
    private void initData() {
//        tabList = new ArrayList<>();
//        tabList.add("扶贫公示");
////        tabList.add("扶贫政策");
////        tabList.add("爱心帮扶");
////        tabList.add("爱心足迹");
//        adapter1 = new PAtabApapter(mcontext,tabList);
//        pa_content_listview.setAdapter(adapter1);
    }
    private void initListView() {
        WorkLog.i(TAG, "initListView: ------------------");
        if (pBlockBean.getData()!=null){
            tabList2 = new ArrayList<>();
            Observable.from(pBlockBean.getData()).filter(new Func1<getPBlockIdBean.DataBean, Boolean>() {
                @Override
                public Boolean call(getPBlockIdBean.DataBean dataBean) {
                    return dataBean.getBlockid().equals("0204")|dataBean.getBlockid().equals("0205");
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<getPBlockIdBean.DataBean>() {
                @Override
                public void onCompleted() {
                    WorkLog.i(TAG, "initListView: pBlockBean:"+pBlockBean.toString());
                    try {
                    if (tabList2!=null&&tabList2.size()!=0 ){
                        adapter1 = new PAtabApapter(mcontext,tabList2);
                        pa_content_listview.setAdapter(adapter1);
                        initRecyclerViewData(tabList2.get(0).getBlockid());
                    }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    this.unsubscribe();
                }

                @Override
                public void onError(Throwable e) {
                    this.unsubscribe();
                }

                @Override
                public void onNext(getPBlockIdBean.DataBean dataBean) {
                    tabList2.add(dataBean);
                    WorkLog.i(TAG, "onNext: dataBean:"+dataBean.getBlockname());
                }
            });
        }


        pa_content_listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WorkLog.i(TAG, "onItemSelected: -------------------");
                try {
                    if (isFirst){
                        pa_content_listview.getChildAt(position).setBackgroundColor(mcontext.getResources().getColor(R.color.color_yellow2));
                        isFirst = false;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private boolean isFirst = true;

    private ColumnBean columnBean;
    private void initRecyclerViewData(String blockId){
        try {
            //        OkGo.post(NetWorkRequest.BaseUrl+NetWorkRequest.QUERY_INFO_JSON+infoId).tag(this).execute(new StringCallback() {
            JSONObject json = new JSONObject();
            json.put("blockId",blockId);
            json.put("userId", SharedPreferencesUtil.getStringValue(mcontext,SharedPreferencesUtil.USER_ID,""));
            json.put("versionCode", UiUtils.getVersionCode(mcontext)+"");
            WorkLog.i(TAG, "getIntentData: json="+json.toString());
            OkGo.post(NetWorkRequest.BaseUrl+NetWorkRequest.FINDBY_BLOCK_ID).tag(this).params("json",json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, "getIntentData onSuccess: ------------------------------------------------------------"+s);
                    try {
                        JSONObject js = new JSONObject(s);
                        if (js.has("code")){
                            int code = js.getInt("code");
                            switch (code){
                                case 200:
                                    columnBean = new Gson().fromJson(s,ColumnBean.class);
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
                    WorkLog.i(TAG, "getIntentData onError: ---------------------------------------------------------------");
                }

                @Override
                public void onAfter(String s, Exception e) {
                    super.onAfter(s, e);
                    if (columnBean!=null && columnBean.getCode().equals("200")){
                        initRecyclerView(0);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initRecyclerView(int position) {
        if (position == 0){
            List<ColumnBean.DataBean> dataBean = columnBean.getData();
            if (dataBean!=null){
                adapter2 = new PAcontentAdapter(mcontext,dataBean);
                pa_content_recyclerview.setAdapter(adapter2);
            }
        }
//        else{
//            pa_content_recyclerview.setAdapter(null);
//        }
//        int datasize = 1+position;
//        contentList = new ArrayList<>();
//        for (int i = 0; i <datasize ; i++) {
//            contentList.add("关于印发《泉交河镇推进精准扶贫进一步帮扶工作责任方案》 通知");
//            contentList.add("赫山去安监局深入对口帮扶贫团村开展调研走访");
//        }
//        adapter2 = new PAcontentAdapter(mcontext,contentList);
//        pa_content_recyclerview.setAdapter(adapter2);

    }
    private void initListener() {
        pa_content_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initRecyclerView(position);
                initRecyclerViewData(tabList2.get(position).getBlockid());
                try{
                    for (int i = 0; i <tabList2.size() ; i++) {
                        if (i == position){
                            pa_content_listview.getChildAt(position).setBackgroundColor(mcontext.getResources().getColor(R.color.color_yellow2));
                        }else{
                            pa_content_listview.getChildAt(i).setBackgroundResource(R.drawable.bg_border2);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
        pa_content_listview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
                        pa_content_recyclerview.requestFocus();
                        return true;
                    }
                }
                return false;
            }
        });
    }


}
