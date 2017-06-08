package com.vunke.ec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.ec.R;
import com.vunke.ec.adapter.PartyAffairsAdapter;
import com.vunke.ec.adapter.SpaceItemDecoration;
import com.vunke.ec.base.BaseActivity;
import com.vunke.ec.base.Configs;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.mod.ColumnBean;
import com.vunke.ec.network_request.NetWorkRequest;
import com.vunke.ec.util.SharedPreferencesUtil;
import com.vunke.ec.util.UiUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import app.com.tvrecyclerview.TvRecyclerView;
import okhttp3.Call;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 村务公开
 */
public class VillageAffairsActivity extends BaseActivity {
    private static final String TAG = "VillageAffairsActivity";
    //    private RecyclerView partyaffairs_recycler;
    private PartyAffairsAdapter adapter;
    private TvRecyclerView villageaffairs_tvrecycler;
    private List<Map<String ,Object>> list;
    private Map<String,Object> map;
    private String[] contentArr = {"益阳科技终止的万亩早稻长势喜人","全国人打农委在想开展脱贫攻坚专题调研","推进“两学一做”学习教育常态化制度化","益阳市启动2017农村法制宣传教育月活动","益阳科技终止的万亩早稻长势喜人","全国人打农委在想开展脱贫攻坚专题调研","推进“两学一做”学习教育常态化制度化","益阳市启动2017农村法制宣传教育月活动","益阳科技终止的万亩早稻长势喜人","全国人打农委在想开展脱贫攻坚专题调研","推进“两学一做”学习教育常态化制度化","益阳市启动2017农村法制宣传教育月活动"};
    private String [] dataTimeArr = {"2017-5-20","2017-5-21","2017-5-22","2017-5-23","2017-5-24","2017-5-25","2017-5-26","2017-5-27","2017-5-28","2017-5-29","2017-5-30","2017-5-31"};
    private int[] imgArr = {R.drawable.partyaffairs_view1,R.drawable.partyaffairs_view2,R.drawable.partyaffairs_view3,R.drawable.partyaffairs_view4,R.drawable.partyaffairs_view1,R.drawable.partyaffairs_view2,R.drawable.partyaffairs_view3,R.drawable.partyaffairs_view4,R.drawable.partyaffairs_view1,R.drawable.partyaffairs_view2,R.drawable.partyaffairs_view3,R.drawable.partyaffairs_view4};

    private String infoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_affairs);
        initView();
        getIntentData();
    }
    private void initView() {

//        partyaffairs_recycler = (RecyclerView) findViewById(R.id.partyaffairs_recycler);
        villageaffairs_tvrecycler = (TvRecyclerView) findViewById(R.id.villageaffairs_tvrecycler);
        GridLayoutManager manager = new GridLayoutManager(mcontext, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        villageaffairs_tvrecycler.setLayoutManager(manager);

        int itemSpace = getResources().
                getDimensionPixelSize(R.dimen.recyclerview_item_space30);
        villageaffairs_tvrecycler.addItemDecoration(new SpaceItemDecoration(itemSpace));
//        partyaffairs_tvrecycler.setItemAnimator(new DefaultItemAnimator());
        villageaffairs_tvrecycler.setOnItemStateListener(new TvRecyclerView.OnItemStateListener() {
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
        villageaffairs_tvrecycler.setSelectedScale(1.1f);
        villageaffairs_tvrecycler.setSelectPadding(6,6,6,6);
    }
    private ColumnBean columnBean;
    private void  getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("infoId")){
            infoId = intent.getStringExtra("infoId");
            WorkLog.i(TAG, "getIntentData: infoId:"+infoId);
        }
        try {
            JSONObject json = new JSONObject();
            json.put("blockId",infoId+"");
            json.put("userId", SharedPreferencesUtil.getStringValue(mcontext,SharedPreferencesUtil.USER_ID,""));
            json.put("versionCode", UiUtils.getVersionCode(mcontext)+"");
            WorkLog.i(TAG, "getIntentData: json="+json.toString());
            OkGo.post(NetWorkRequest.BaseUrl+NetWorkRequest.FINDBY_BLOCK_ID).tag(this).params("json",json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, "onSuccess: ------------------------------------------------------------"+s);
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
                    WorkLog.i(TAG, "onError: ---------------------------------------------------------------");
                }

                @Override
                public void onAfter(String s, Exception e) {
                    super.onAfter(s, e);
                    WorkLog.i(TAG, "onAfter: "+columnBean.getCode().equals("200"));
                    if (columnBean!=null && columnBean.getCode().equals("200")){
                        initRecyclerView();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
//        list = new ArrayList<>();
//        for (int i = 0; i <contentArr.length ; i++) {
//            map = new HashMap<>();
//            map.put("content",contentArr[i]);
//            map.put("img",imgArr[i]);
//            map.put("time",dataTimeArr[i]);
//            list.add(map);
//        }
        List<ColumnBean.DataBean> dataBean = columnBean.getData();
        if (dataBean!=null){
            adapter = new PartyAffairsAdapter(mcontext,dataBean);
            villageaffairs_tvrecycler.setAdapter(adapter);
        }
        if (adapter!=null &&adapter.getItemCount()!=0){
//            villageaffairs_tvrecycler.setItemSelected(adapter.getItemCount());
            Observable.timer(500, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            villageaffairs_tvrecycler.setItemSelected(0);
                        }
                    });
        }
//        adapter = new OnlineLearnAdapter(mcontext,list);
//        partyaffairs_tvrecycler.setAdapter(adapter);
    }

}
