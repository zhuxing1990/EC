package com.vunke.ec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vunke.ec.R;
import com.vunke.ec.adapter.FunctionAdapter;
import com.vunke.ec.base.BaseActivity;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.mod.Function2Bean;
import com.vunke.ec.util.SharedPreferencesUtil;
import com.vunke.ec.view.FlyBorderView;

import java.util.List;
import java.util.Map;

/**
 * 首页2
 * Created by zhuxi on 2017/5/20.
 */
public class Function2Activity extends BaseActivity {
    private static final String TAG = "Function2Activity";
    private RecyclerView function_recyclerview;
    private FlyBorderView function_flyborderview;
    private List<Map<String,Object>> list;
    private Map<String ,Object > map;
    private FunctionAdapter adapter;
    private String infoId;
    private String jsonData;
    private TextView function_titie_text;
    private Function2Bean function2Bean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function2);
        initView();
        getIntentData();
    }
    private void initView() {
        function_flyborderview = (FlyBorderView) findViewById(R.id.function_flyborderview);
        function_titie_text = (TextView) findViewById(R.id.function_titie_text);
        function_recyclerview = (RecyclerView) findViewById(R.id.function_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        function_recyclerview.setLayoutManager(linearLayoutManager);


    }
//    private  Function2Bean.DataBean bean;
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
                   function_titie_text.setText(function2Bean.getTitle());
               }
               if (function2Bean.getData()!=null){
                   if (function2Bean.getData().size()!=0){
//                       bean = new Function2Bean.DataBean();
//                       bean.setImage("qrcode");
//                       bean.setImplement_titie("益村二维码");
//                       function2Bean.getData().add(bean);
                       adapter = new FunctionAdapter(mcontext,function_flyborderview,function2Bean.getData());
                       function_recyclerview.setAdapter(adapter);
                   }
               }
           }
        }catch (Exception e ){
            e.printStackTrace();
        }

    }
//    private void initData(int infoId) {
//        userId = SharedPreferencesUtil.getStringValue(mcontext,SharedPreferencesUtil.USER_ID,"");
//        if (infoId==-1){
//            showToast("执行ID错误");
//            return;
//        }
//        //json = {“versioCode”,”xx”,”userId”:”id”,”infoId”:”infoid”}
//        try {
//            JSONObject json = new JSONObject();
//            json.put("versionCode","1");
//            json.put("userId",userId);
//            json.put("infoId",infoId);
//            WorkLog.i(TAG, "initData: json="+json.toString());
//            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.FIND_BYI_ID_INFO2).tag(this).params("json", json.toString()).execute(new StringCallback() {
//                @Override
//                public void onSuccess(String s, Call call, Response response) {
//                    WorkLog.i(TAG, "onSuccess: ------------------------------------------------------------"+s);
//                    try{
//                        JSONObject js = new JSONObject(s);
//                        if (js.has("code")){
//                            int code = js.getInt("code");
//                            switch (code){
//                                case 200:
//
//                                    break;
//                                case 400:
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
//                    WorkLog.i(TAG, "onError: ---------------------------------------------------------------");
//                }
//
//                @Override
//                public void onAfter(String s, Exception e) {
//                    super.onAfter(s, e);
//                    WorkLog.i(TAG, "onAfter: ");
//
//                }
//            });
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }





}
