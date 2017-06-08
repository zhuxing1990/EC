package com.vunke.ec.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.ec.R;
import com.vunke.ec.adapter.SpaceItemDecoration;
import com.vunke.ec.base.BaseActivity;
import com.vunke.ec.base.Configs;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.mod.ColumnBean;
import com.vunke.ec.network_request.NetWorkRequest;
import com.vunke.ec.util.GlideUtils;
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
 * 益村党务
 */
public class PartyAffairsActivity extends BaseActivity {
    private static final String TAG = "PartyAffairsActivity";
//    private RecyclerView partyaffairs_recycler;
//    private PartyAffairsAdapter adapter;
    private TvRecyclerView partyaffairs_tvrecycler;
    private List<Map<String ,Object>> list;
    private Map<String,Object> map;
    private String[] contentArr = {"益阳科技终止的万亩早稻长势喜人","全国人打农委在想开展脱贫攻坚专题调研","推进“两学一做”学习教育常态化制度化","益阳市启动2017农村法制宣传教育月活动","益阳科技终止的万亩早稻长势喜人","全国人打农委在想开展脱贫攻坚专题调研","推进“两学一做”学习教育常态化制度化","益阳市启动2017农村法制宣传教育月活动","益阳科技终止的万亩早稻长势喜人","全国人打农委在想开展脱贫攻坚专题调研","推进“两学一做”学习教育常态化制度化","益阳市启动2017农村法制宣传教育月活动"};
    private String [] dataTimeArr = {"2017-5-20","2017-5-21","2017-5-22","2017-5-23","2017-5-24","2017-5-25","2017-5-26","2017-5-27","2017-5-28","2017-5-29","2017-5-30","2017-5-31"};
    private int[] imgArr = {R.drawable.partyaffairs_view1,R.drawable.partyaffairs_view2,R.drawable.partyaffairs_view3,R.drawable.partyaffairs_view4,R.drawable.partyaffairs_view1,R.drawable.partyaffairs_view2,R.drawable.partyaffairs_view3,R.drawable.partyaffairs_view4,R.drawable.partyaffairs_view1,R.drawable.partyaffairs_view2,R.drawable.partyaffairs_view3,R.drawable.partyaffairs_view4};

    private String infoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_affairs);
        initView();
        getIntentData();
    }
    private void initView() {
//        partyaffairs_recycler = (RecyclerView) findViewById(R.id.partyaffairs_recycler);
        partyaffairs_tvrecycler = (TvRecyclerView) findViewById(R.id.partyaffairs_tvrecycler);
        GridLayoutManager manager = new GridLayoutManager(mcontext, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        partyaffairs_tvrecycler.setLayoutManager(manager);

        int itemSpace = getResources().
                getDimensionPixelSize(R.dimen.recyclerview_item_space30);
        partyaffairs_tvrecycler.addItemDecoration(new SpaceItemDecoration(itemSpace));
        partyaffairs_tvrecycler.setItemAnimator(new DefaultItemAnimator());
        partyaffairs_tvrecycler.setSelectedScale(1.1f);
        partyaffairs_tvrecycler.setSelectPadding(6,6,6,6);
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
       final  List<ColumnBean.DataBean> dataBean = columnBean.getData();
        if (dataBean!=null){
//            adapter = new PartyAffairsAdapter(mcontext,dataBean);
//            partyaffairs_tvrecycler.setAdapter(adapter);
            adapter2 = new OnlineLearnAdapter2(mcontext,dataBean,partyaffairs_tvrecycler);
            partyaffairs_tvrecycler.setAdapter(adapter2);
            if (adapter2!=null &&adapter2.getItemCount()!=0){
//            partyaffairs_tvrecycler.setItemSelected(adapter.getItemCount());
                Observable.timer(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                partyaffairs_tvrecycler.setItemSelected(0);
                            }
                        });
        }

        }
//        adapter = new OnlineLearnAdapter(mcontext,list);
//        partyaffairs_tvrecycler.setAdapter(adapter);
    }
    private  OnlineLearnAdapter2  adapter2;

    /**
     * Created by zhuxi on 2017/5/20.
     */
    public class OnlineLearnAdapter2 extends RecyclerView.Adapter<OnlineLearnAdapter2.OnlineLearnHolder2> {
        private Context context;
        private List<ColumnBean.DataBean> list;
        private TvRecyclerView tvRecyclerView;
        //    private FlyBorderView flyBorderView;
        public OnlineLearnAdapter2(Context context, List<ColumnBean.DataBean> list, TvRecyclerView partyaffairs_tvrecycler){
//    public OnlineLearnAdapter (Context context, List<Object> list,FlyBorderView flyBorderView){
            this.context = context;
            this.list = list;
            this.tvRecyclerView = partyaffairs_tvrecycler;
        }

        @Override
        public OnlineLearnHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_onlinelearn_item,parent,false);
            final OnlineLearnHolder2 holder = new OnlineLearnHolder2(view);
            holder.onlinelearn_recycler_item_play.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                }
            });
            tvRecyclerView.setOnItemStateListener(new TvRecyclerView.OnItemStateListener() {
                @Override
                public void onItemViewClick(View view, int position) {
                    String videopath= list.get(position).getString6();
                    if (!TextUtils.isEmpty(videopath)){
                        Configs.intent = new Intent(mcontext,VideoPlayActivity.class);
                        Configs.intent.putExtra("infoid",list.get(position).getInfoid());
                        Configs.intent.putExtra("videoPath",videopath);
                        startActivity(Configs.intent);
                    }else{
                        showToast("未找到视频信息");
//                        Configs.intent = new Intent(mcontext,DetailsActivity.class);
//                        Configs.intent.putExtra("infoid",dataBean.get(position).getInfoid());
////                        Configs.intent.putExtra("videoPath",dataBean.get(position).getString6());
//                        startActivity(Configs.intent);
                    }
                }
                @Override
                public void onItemViewFocusChanged(boolean gainFocus, View view, int position) {
                    if (gainFocus){
//                        holder.onlinelearn_recycler_item_play.setVisibility(View.VISIBLE);
                    }else{
//                        holder.onlinelearn_recycler_item_play.setVisibility(View.INVISIBLE);
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder( OnlineLearnHolder2 holder, int position) {
            holder.onlinelearn_recycler_item_text.setText(list.get(position).getTitle());
            String imgurl = list.get(position).getMainpicurl();
            String string6 = list.get(position).getString6();
            if (TextUtils.isEmpty(imgurl)&&TextUtils.isEmpty(string6)){
                WorkLog.i(TAG, "onBindViewHolder: ------------all data is null");
            }else if (TextUtils.isEmpty(string6)&& !TextUtils.isEmpty(imgurl)){
                WorkLog.i(TAG, "onBindViewHolder: ------------mainpicurl has data");
                GlideUtils.getInstance().LoadContextBitmap(context,list.get(position).getMainpicurl(),holder.onlinelearn_recycler_item_img,R.drawable.touming,R.drawable.touming,null);
            }else if (!TextUtils.isEmpty(string6)&& TextUtils.isEmpty(imgurl)){
                WorkLog.i(TAG, "onBindViewHolder: ------------getString6 has data");
                GlideUtils.getInstance().LoadContextBitmap(context,list.get(position).getString6(),holder.onlinelearn_recycler_item_img,R.drawable.touming,R.drawable.touming,GlideUtils.LOAD_GIF);
            }else if (!TextUtils.isEmpty(string6)&& !TextUtils.isEmpty(imgurl)){
                WorkLog.i(TAG, "onBindViewHolder: ------------all data has data");
                GlideUtils.getInstance().LoadContextBitmap(context,list.get(position).getMainpicurl(),holder.onlinelearn_recycler_item_img,R.drawable.touming,R.drawable.touming,null);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class OnlineLearnHolder2 extends RecyclerView.ViewHolder{
            private ImageView onlinelearn_recycler_item_img;
            private TextView onlinelearn_recycler_item_text;
            private ImageView onlinelearn_recycler_item_play;
            private View mItemView;
            public OnlineLearnHolder2(View itemView) {
                super(itemView);
                mItemView = itemView;
                onlinelearn_recycler_item_text = (TextView) mItemView.findViewById(R.id.onlinelearn_recycler_item_text);
                onlinelearn_recycler_item_img = (ImageView) mItemView.findViewById(R.id.onlinelearn_recycler_item_img);
                onlinelearn_recycler_item_play = (ImageView) mItemView.findViewById(R.id.onlinelearn_recycler_item_play);
            }
        }
    }
}
