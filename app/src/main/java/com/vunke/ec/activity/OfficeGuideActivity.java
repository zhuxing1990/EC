package com.vunke.ec.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.ec.R;
import com.vunke.ec.adapter.MyFragmentPagerAdapter;
import com.vunke.ec.adapter.OfficeGuideAdapter;
import com.vunke.ec.base.BaseFragementActivity;
import com.vunke.ec.fragement.OfficeContentFragment;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.mod.getPBlockIdBean;
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
import rx.Subscription;

/**
 * 办事指南
 * Created by zhuxi on 2017/5/22.
 */
public class OfficeGuideActivity extends BaseFragementActivity  implements ViewPager.OnPageChangeListener{
    private static final String TAG = "OfficeGuideActivity";
    private TvRecyclerView officeguide_classify_recycler;

//    private List<String> guide_list;
    private OfficeGuideAdapter adapter;

    private FlyBorderView officeguide_flyborderview;
    private ViewPager officeguide_content_viewpager;
//    private String [] guidetArr = {"人社"};//,"民政","住建","卫计","农业","总工会","教育","团委"

    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragmentList;
    private OfficeContentFragment fragment1,fragment2,fragment3;
    private LinearLayoutManager linearLayoutManager;

    private String infoId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officeguide);
        initView();
        getIntentData();
        ininPBokckId();
//        initRecyclerView();
//        initLinstener();
    }

    private void initView() {
        officeguide_flyborderview = (FlyBorderView) findViewById(R.id.officeguide_flyborderview);
        officeguide_classify_recycler = (TvRecyclerView) findViewById(R.id.officeguide_classify_recycler);
        linearLayoutManager = new LinearLayoutManager(mcontext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        officeguide_classify_recycler.setLayoutManager(linearLayoutManager);
        officeguide_content_viewpager = (ViewPager) findViewById(R.id.officeguide_content_viewpager);
    }
    private void  getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("infoId")){
            infoId = intent.getStringExtra("infoId");
            WorkLog.i(TAG, "getIntentData: infoId:"+infoId);
        }
    }
    private getPBlockIdBean pBlockBean;
    private List<getPBlockIdBean.DataBean> tabList;
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
    private void initListView() {
        WorkLog.i(TAG, "initListView: ------------------");
        if (pBlockBean.getData() != null) {
            tabList = pBlockBean.getData();
            if (tabList != null && tabList.size() != 0) {
                initListener();
                initViewPager();
//                initViewPagerDATA(0);
            }
        }
    }

    private void initListener() {
        adapter = new OfficeGuideAdapter(tabList, mcontext, officeguide_flyborderview);
        officeguide_classify_recycler.setAdapter(adapter);

        officeguide_classify_recycler.setOnItemStateListener(new TvRecyclerView.OnItemStateListener() {
            @Override
            public void onItemViewClick(View view, int position) {

            }

            @Override
            public void onItemViewFocusChanged(boolean gainFocus, View view, int position) {
                if (gainFocus) {
                    view.setBackgroundColor(Color.parseColor("#dfaf5a"));
                    if (fragmentList != null && fragmentList.size() != 0) {
                        if (officeguide_content_viewpager != null) {
                            WorkLog.i(TAG, "onItemViewFocusChanged:  posititon:" + position + " viewpager.item:" + officeguide_content_viewpager.getCurrentItem());
                            if (position <= fragmentList.size() && position != officeguide_content_viewpager.getCurrentItem()) {
                                if (!isDestroyed()){
                                    officeguide_content_viewpager.setCurrentItem(position, false);
                                }
                            } else {
                                WorkLog.i(TAG, "onItemViewFocusChanged:  viewpager.getCurrentItem:" + officeguide_content_viewpager.getCurrentItem());
                            }
                        }
                    }
                } else {
                    view.setBackgroundColor(Color.parseColor("#b97923"));
                }
            }
        });
        officeguide_classify_recycler.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() ==KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
                        officeguide_content_viewpager.requestFocus();
                        int currentItem = officeguide_content_viewpager.getCurrentItem();
                        if (adapter!= null && adapter.getItemCount()!=0){
                            if (currentItem<= tabList.size()){
                                officeguide_classify_recycler.getChildAt(currentItem).setBackgroundColor(Color.parseColor("#dfaf5a"));
                            }
                        }
                        return true;
                    }else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
                        Log.i(TAG, "onKey: left------------------------");
                        if (adapter!= null && adapter.getItemCount()!=0) {
                            if (officeguide_classify_recycler.getSelectedPosition() == tabList.size() - 1) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        });
    }
    private void initViewPager() {
//        List<ColumnBean.DataBean> dataBean = columnBean.getData();
//        if (dataBean!=null){
//            fragmentList = new ArrayList<>();
//            fragment1 = new OfficeContentFragment(dataBean);
////            fragment2 = new OfficeContentFragment(dataBean);
////            fragment3 = new OfficeContentFragment(dataBean);
//            fragmentList.add(fragment1);
////            fragmentList.add(fragment2);
////            fragmentList.add(fragment3);
//            fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
//            officeguide_content_viewpager.setAdapter(fragmentPagerAdapter);
//            officeguide_content_viewpager.addOnPageChangeListener(this);
//            officeguide_content_viewpager.setCurrentItem(0);
////            officeguide_content_viewpager.setOffscreenPageLimit(1);
//        }
        fragmentList = new ArrayList<>();
        for (int i = 0; i <tabList.size() ; i++) {
            fragment1 = new OfficeContentFragment(tabList.get(i).getBlockid(),mcontext);
            fragmentList.add(fragment1);
        }
        fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        officeguide_content_viewpager.setAdapter(fragmentPagerAdapter);
        officeguide_content_viewpager.addOnPageChangeListener(this);
        officeguide_content_viewpager.setCurrentItem(0);
        officeguide_content_viewpager.setOffscreenPageLimit(0);
        officeguide_content_viewpager.setOnKeyListener(null);
    }

//    private void initViewPagerDATA(int position){
//        try {
//            //        OkGo.post(NetWorkRequest.BaseUrl+NetWorkRequest.QUERY_INFO_JSON+infoId).tag(this).execute(new StringCallback() {
//            JSONObject json = new JSONObject();
//            json.put("blockId",tabList.get(0).getBlockid());
//            json.put("userId", SharedPreferencesUtil.getStringValue(mcontext,SharedPreferencesUtil.USER_ID,""));
//            json.put("versionCode", UiUtils.getVersionCode(mcontext)+"");
//            WorkLog.i(TAG, "getIntentData: json="+json.toString());
//            OkGo.post(NetWorkRequest.BaseUrl+NetWorkRequest.FINDBY_BLOCK_ID).tag(this).params("json",json.toString()).execute(new StringCallback() {
//                @Override
//                public void onSuccess(String s, Call call, Response response) {
//                    WorkLog.i(TAG, "onSuccess: ------------------------------------------------------------"+s);
//                    try {
//                        JSONObject js = new JSONObject(s);
//                        if (js.has("code")){
//                            int code = js.getInt("code");
//                            switch (code){
//                                case 200:
//                                    columnBean = new Gson().fromJson(s,ColumnBean.class);
//                                    break;
//                                case 400:
//
//                                    break;
//
//                                case 500:
//
//                                    break;
//                            }
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//                @Override
//                public void onError(Call call, Response response, Exception e) {
//                    super.onError(call, response, e);
//                    WorkLog.i(TAG, "onError: ---------------------------------------------------------------");
//                }
//
//                @Override
//                public void onAfter(String s, Exception e) {
//                    super.onAfter(s, e);
//                    if (columnBean!=null && columnBean.getCode().equals("200")){
//                        initViewPager();
//                    }
//                }
//            });
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }



    private void initRecyclerView() {
//        guide_list = new ArrayList<>();
//        for (int i = 0; i < guidetArr.length; i++) {
//            guide_list.add(guidetArr[i]);
//        }
//        adapter = new OfficeGuideAdapter(guide_list,mcontext,officeguide_flyborderview);
//        officeguide_classify_recycler.setAdapter(adapter);
//        officeguide_classify_recycler.setOnItemStateListener(new TvRecyclerView.OnItemStateListener() {
//            @Override
//            public void onItemViewClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onItemViewFocusChanged(boolean gainFocus, View view, int position) {
//                if (gainFocus) {
//                    view.setBackgroundColor(Color.parseColor("#dfaf5a"));
//                    if (fragmentList != null && fragmentList.size() != 0){
//                        if (officeguide_content_viewpager != null) {
//                            WorkLog.i(TAG, "onItemViewFocusChanged:  posititon:"+position+" viewpager.item:"+officeguide_content_viewpager.getCurrentItem());
//                            if (position <= fragmentList.size()&& position!=officeguide_content_viewpager.getCurrentItem()) {
//                                officeguide_content_viewpager.setCurrentItem(position,false);
//                            }else{
//                                WorkLog.i(TAG, "onItemViewFocusChanged:  viewpager.getCurrentItem:"+officeguide_content_viewpager.getCurrentItem());
//                            }
//                        }
//                    }
//                }else{
//                    view.setBackgroundColor(Color.parseColor("#b97923"));
//                }
//            }
//        });
//        officeguide_classify_recycler.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() ==KeyEvent.ACTION_DOWN){
//                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
//                        officeguide_content_viewpager.requestFocus();
//                        int currentItem = officeguide_content_viewpager.getCurrentItem();
//                        if (adapter!= null && adapter.getItemCount()!=0){
//                            if (currentItem<= guidetArr.length){
//                                officeguide_classify_recycler.getChildAt(currentItem).setBackgroundColor(Color.parseColor("#dfaf5a"));
//                            }
//                        }
//                        return true;
//                    }else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
//                        Log.i(TAG, "onKey: left------------------------");
//                        if (adapter!= null && adapter.getItemCount()!=0) {
//                            if (officeguide_classify_recycler.getSelectedPosition() == guidetArr.length - 1) {
//                                return true;
//                            }
//                        }
//                    }
//                }
//                return false;
//            }
//        });
    }


   private  Subscription subscribe;




    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (subscribe!=null){
//            if (!subscribe.isUnsubscribed())subscribe.unsubscribe();
//        }
    }

    boolean isFirst = true;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (isFirst){
            isFirst =false;
            if (adapter!=null && adapter.getItemCount()!=0){
                View childAt = officeguide_classify_recycler.getChildAt(0);
                childAt.setBackgroundColor(Color.parseColor("#dfaf5a"));
                if (childAt instanceof RelativeLayout){
                    TextView text = (TextView) ((RelativeLayout) childAt).getChildAt(0);
                    text.setTextColor(Color.parseColor("#F0F0F0"));
                }
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        WorkLog.i(TAG, "onPageSelected: position---------------------------------"+ position);
            if (adapter!=null && adapter.getItemCount()!=0){
                if (position<= tabList.size()){
                    for (int i = 0; i <tabList.size() ; i++) {
                        View childAt = officeguide_classify_recycler.getChildAt(i);
                        if (i == position){
                            childAt.setBackgroundColor(Color.parseColor("#dfaf5a"));
                            if (childAt instanceof RelativeLayout){
                                TextView text = (TextView) ((RelativeLayout) childAt).getChildAt(0);
                                text.setTextColor(Color.parseColor("#F0F0F0"));
                            }
                        }else{
                            childAt.setBackgroundColor(Color.parseColor("#b97923"));
                            if (childAt instanceof RelativeLayout){
                                TextView text = (TextView) ((RelativeLayout) childAt).getChildAt(0);
                                text.setTextColor(Color.parseColor("#f0be66"));
                            }
                        }
                    }
                }
        }

    }



    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
