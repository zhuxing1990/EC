package com.vunke.ec.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vunke.ec.R;
import com.vunke.ec.base.BaseActivity;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.mod.Function2Bean;
import com.vunke.ec.util.GlideUtils;
import com.vunke.ec.util.SharedPreferencesUtil;
import com.vunke.ec.util.UiUtils;
import com.vunke.ec.view.FlyBorderView;

import java.util.List;

import app.com.tvrecyclerview.TvRecyclerView;

/**
 * 政务
 * Created by zhuxi on 2017/6/9.
 */
public class GovernmentAffairsActivity extends BaseActivity {
    private static final String TAG = "GovernmentAffairsActivity";
    private TvRecyclerView governmentaffair_recycler;
    private TextView governmentaffair_title_text;
    private String infoId;
    private String jsonData;
    private Function2Bean function2Bean;
    private GovernmentAffairsAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_governmentaffair);
        initView();
        getIntentData();
        initListener();
    }

    private void initListener() {
        governmentaffair_recycler.setOnItemStateListener(new TvRecyclerView.OnItemStateListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                Function2Bean.DataBean dataBean = function2Bean.getData().get(position);
                String implement_id = dataBean.getImplement_id();
                String implement_package = dataBean.getImplement_package();
                String implement_address = dataBean.getImplement_address();
                UiUtils.StartAPP(implement_package,implement_address,implement_id,null,mcontext);
            }

            @Override
            public void onItemViewFocusChanged(boolean gainFocus, View view, int position) {

            }
        });
        governmentaffair_recycler.setSelectedScale(1.1f);
        governmentaffair_recycler.setSelectPadding(6,6,6,6);
    }

    private void initView() {
        governmentaffair_recycler = (TvRecyclerView) findViewById(R.id.governmentaffair_recycler);
        LinearLayoutManager linearLayoutmanager = new LinearLayoutManager(mcontext);
        linearLayoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        governmentaffair_recycler.setLayoutManager(linearLayoutmanager);
        governmentaffair_title_text = (TextView) findViewById(R.id.governmentaffair_title_text);
    }
    public void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("infoId")){
            infoId = intent.getStringExtra("infoId");
            WorkLog.i(TAG, "getIntentData: infoId:"+infoId);
        }
        jsonData = SharedPreferencesUtil.getStringValue(mcontext,"jsonData","");
        if (TextUtils.isEmpty(jsonData)) {
            WorkLog.i(TAG, "getIntentData: get jsonData is null");
            return;
        }
        WorkLog.i(TAG, "getIntentData: jsonData:"+jsonData);
        SharedPreferencesUtil.RemoveKey(mcontext,"jsonData");
        try {
            function2Bean = new Gson().fromJson(jsonData,Function2Bean.class);
            if (function2Bean!=null){
                if (!TextUtils.isEmpty(function2Bean.getTitle())){
                    governmentaffair_title_text.setText(function2Bean.getTitle());
                }
                if (function2Bean.getData()!=null){
                    if (function2Bean.getData().size()!=0){

//                        adapter = new FunctionAdapter(mcontext,function_flyborderview,function2Bean.getData());
                        adapter = new GovernmentAffairsAdapter(mcontext,function2Bean.getData());
                        governmentaffair_recycler.setAdapter(adapter);
                    }
                }
            }
        }catch (Exception e ){
            e.printStackTrace();
        }

    }
    public class GovernmentAffairsAdapter extends RecyclerView.Adapter<GovernmentAffairsAdapter.GovernmentAffairsHolder>{
        private Context context;
        private List<Function2Bean.DataBean> mdatalist;
        private  FlyBorderView flyBorderView;
        public GovernmentAffairsAdapter (Context context, FlyBorderView flyBorderView, List<Function2Bean.DataBean> mdatalist){
            this.context = context;
            this.mdatalist =mdatalist;
            this.flyBorderView = flyBorderView;
        }

        public GovernmentAffairsAdapter (Context context, List<Function2Bean.DataBean> mdatalist){
            this.context = context;
            this.mdatalist =mdatalist;
        }
        @Override
        public GovernmentAffairsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_governmentaffairs,parent,false);
            GovernmentAffairsHolder holder = new GovernmentAffairsHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(GovernmentAffairsHolder holder, int position) {
            GlideUtils.getInstance().LoadContextBitmap(context,mdatalist.get(position).getImage(), holder.recycler_governmentaffairs_img,R.drawable.touming,R.drawable.touming,null);

        }

        @Override
        public int getItemCount() {
            return mdatalist.size();
        }

        public class GovernmentAffairsHolder extends RecyclerView.ViewHolder{
            private View mItemView;
            private ImageView recycler_governmentaffairs_img;
             public GovernmentAffairsHolder(View itemView) {
                 super(itemView);
                 mItemView = itemView;
                 recycler_governmentaffairs_img = (ImageView) itemView.findViewById(R.id.recycler_governmentaffairs_img);
             }
         }
    }
}
