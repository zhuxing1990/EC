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
 * 党务公开
 */
public class PartyAffairsPublicityActivity extends BaseActivity {
    private static final String TAG = "PartyAffairsPublicityActivity";
    private String infoId;
    private TvRecyclerView pap_content_recycler;
    private List<Map<String, Object>> datalist;
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
//    boolean isBlack= false;
//    private void initData() {
//        datalist = new ArrayList<>();
//        Map<String,Object> map ;
//        for (int i = 0; i <10 ; i++) {
//            map = new HashMap<>();
//            map.put("city","益阳");
//            map.put("address","村党委人员");
//            map.put("createtime","2017-10-1"+i);
//            map.put("title","村支两委换届动员大会");
//            isBlack = !isBlack;
//            if (isBlack){
//                map.put("type",1);
//            }else{
//                map.put("type",2);
//            }
//            datalist.add(map);
//        }
//        PAPAdapter adapter = new PAPAdapter(mcontext,datalist);
//        pap_content_recycler.setAdapter(adapter);
//    }

//    public class PAPAdapter extends RecyclerView.Adapter<PAPAdapter.PAPHolder>{
//        private Context context;
//        private List<Map<String,Object>> datalist;
//        public PAPAdapter(Context context, List<Map<String,Object>> datalist){
//            this.context= context;
//            this.datalist = datalist;
//        }
//        @Override
//        public PAPHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view= null;
//            if (viewType == 1){
//                 view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_partyaffairspublicity,parent,false);
//                PAPHolder holder = new PAPHolder(view);
//            }else if (viewType == 2){
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_partyaffairspublicity2,parent,false);
//            }
//            PAPHolder holder = new PAPHolder(view);
//            holder.mItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//
//                }
//            });
//            return holder;
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return (int) datalist.get(position).get("type");
//        }
//
//        @Override
//        public void onBindViewHolder(PAPHolder holder, int position) {
//            String title = (String) datalist.get(position).get("title");
//            String address = (String) datalist.get(position).get("address");
//            String city =(String) datalist.get(position).get("city");
//            String createtime = (String)  datalist.get(position).get("createtime");
//            holder.pap_recycler_title.setText(title);
//            holder.pap_recycler_address.setText(address);
//            holder.pap_recycler_city.setText(city);
//            holder.pap_recycler_createtime.setText(createtime);
//        }
//
//        @Override
//        public int getItemCount() {
//            return datalist.size();
//        }
//
//        public class PAPHolder extends RecyclerView.ViewHolder{
//            private TextView pap_recycler_title;
//            private TextView pap_recycler_address;
//            private TextView pap_recycler_city;
//            private TextView pap_recycler_createtime;
//            private View mItemView;
//            public PAPHolder(View itemView) {
//                super(itemView);
//                mItemView= itemView;
//                pap_recycler_title = (TextView) mItemView.findViewById(R.id.pap_recycler_title);
//                pap_recycler_city = (TextView) mItemView.findViewById(R.id.pap_recycler_city);
//                pap_recycler_address = (TextView) mItemView.findViewById(R.id.pap_recycler_address);
//                pap_recycler_createtime = (TextView) mItemView.findViewById(R.id.pap_recycler_createtime);
//            }
//        }
//    }

}
