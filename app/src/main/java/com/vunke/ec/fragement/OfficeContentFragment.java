package com.vunke.ec.fragement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.ec.R;
import com.vunke.ec.activity.DetailsActivity;
import com.vunke.ec.adapter.OfficeContentAdapter;
import com.vunke.ec.adapter.SpaceItemDecoration;
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
 * Created by zhuxi on 2017/5/23.
 */
@SuppressLint("ValidFragment")
public class OfficeContentFragment extends Fragment {
    private static final String TAG = "OfficeContentFragment";
//    private List<ColumnBean.DataBean> dataBean;
//    public OfficeContentFragment(List<ColumnBean.DataBean> dataBean){
//        this.dataBean = dataBean;
//        WorkLog.i(TAG, "OfficeContentFragment: dataBean:"+dataBean.toString());
//    }
    private Context context;
    private String BlockId;
    public OfficeContentFragment(String blockId,Context context){
        this.context = context;
        BlockId = blockId;
    }
    private String blockId;
    private TvRecyclerView fragement_officecontent_recycler;
//    private List<String> mlsit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WorkLog.i(TAG, "onCreateView: ----------------------------");
        View view = inflater.inflate(R.layout.fragement_officecontent,null);
        initView(view);
        setData(BlockId,context);
        return view;
    }
    private void initView(View view) {
        fragement_officecontent_recycler = (TvRecyclerView) view.findViewById(R.id.fragement_officecontent_recycler);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.supportsPredictiveItemAnimations();
        fragement_officecontent_recycler.setLayoutManager(manager);
        int itemSpace = getResources().
                getDimensionPixelSize(R.dimen.recyclerview_item_space30);
        fragement_officecontent_recycler.addItemDecoration(new SpaceItemDecoration(itemSpace));
        fragement_officecontent_recycler.setItemAnimator(new DefaultItemAnimator());

        fragement_officecontent_recycler.setSelectPadding(6,6, 6, 6);
//            mlsit = new ArrayList<>();
//        for (int i = 0; i <contentArr.length; i++) {
//            mlsit.add(contentArr[i]);
//        }

    }
    private void initListener(final List<ColumnBean.DataBean> dataBean) {
        WorkLog.i(TAG, "initListener: -----------------------------");
        fragement_officecontent_recycler.setOnItemStateListener(new TvRecyclerView.OnItemStateListener() {
            @Override
            public void onItemViewClick(View view, int position) {
//                Configs.intent = new Intent(getActivity(), OffficeNoticeActivity.class);
//                Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(Configs.intent);
                Configs.intent = new Intent(getActivity(), DetailsActivity.class);
                Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Configs.intent.putExtra("infoid",dataBean.get(position).getInfoid());
                startActivity(Configs.intent);
            }

            @Override
            public void onItemViewFocusChanged(boolean gainFocus, View view, int position) {
//                WorkLog.i(TAG, "onFocusChange: -------------------------------");
            }
        });
        if (adpater!=null &&adpater.getItemCount()!=0) {
            Observable.timer(500, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            fragement_officecontent_recycler.setItemSelected(0);
                        }
                    });
        }
    }
    private ColumnBean columnBean;
    public void setData(String blockId, final Context context) {
        try {
            //        OkGo.post(NetWorkRequest.BaseUrl+NetWorkRequest.QUERY_INFO_JSON+infoId).tag(this).execute(new StringCallback() {
            JSONObject json = new JSONObject();
            json.put("blockId",blockId);
            json.put("userId", SharedPreferencesUtil.getStringValue(context,SharedPreferencesUtil.USER_ID,""));
            json.put("versionCode", UiUtils.getVersionCode(context)+"");
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
                    WorkLog.i(TAG, "onAfter: -----------------------------------------------------");
                    if (columnBean!=null && columnBean.getCode().equals("200")){
                        initData(columnBean.getData(),context);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private OfficeContentAdapter adpater;
    private void initData(List<ColumnBean.DataBean> dataBean,Context context) {
        WorkLog.i(TAG, "initData: ----------------------------");
        if (dataBean !=null&&dataBean.size()!=0){
            adpater = new OfficeContentAdapter(context,dataBean);
            fragement_officecontent_recycler.setAdapter(adpater);
            initListener(dataBean);
        }
    }

//    @Override
//    protected void lazyLoad() {
//
//    }
}
