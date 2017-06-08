package com.vunke.ec.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.vunke.ec.R;
import com.vunke.ec.adapter.HomeClassifyAdapter;
import com.vunke.ec.base.BaseActivity;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.mod.Home_IndexBean;
import com.vunke.ec.network_request.NetWorkRequest;
import com.vunke.ec.util.GlideUtils;
import com.vunke.ec.util.NetUtils;
import com.vunke.ec.util.OnFouchAnim;
import com.vunke.ec.util.SharedPreferencesUtil;
import com.vunke.ec.util.UiUtils;
import com.vunke.ec.view.FlyBorderView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 首页
 */
public class HomeActivity extends BaseActivity implements View.OnFocusChangeListener,View.OnKeyListener,View.OnClickListener, SurfaceHolder.Callback, android.media.MediaPlayer.OnPreparedListener, android.media.MediaPlayer.OnCompletionListener, android.media.MediaPlayer.OnErrorListener{
    private static final String TAG = "HomeActivity";
    private FlyBorderView home_flyborderview;
    private RelativeLayout home_ct_view1,home_ct_view2,home_ct_view3;
    private RelativeLayout home_fc_view1,home_fc_view2,home_fc_view3,home_fc_view4;
    private RelativeLayout home_rd_view1,home_rd_view2,home_rd_view3;
    private RelativeLayout home_video_rl;
    private TextView home_title;
    private ImageView home_ct_img1,home_ct_img2,home_ct_img3;
    private ImageView home_fc_img1,home_fc_img2,home_fc_img3,home_fc_img4;
    private ImageView home_rd_img1,home_rd_img2,home_rd_img3;

    private RecyclerView home_recyclerview;

    private List<Home_IndexBean.IndexBean> listdata;
    private HomeClassifyAdapter adapter;
    private String userId;
    private Home_IndexBean home_indexBean;



    // 获取视频文件地址
//    String videoPath = "http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4";
    String videoPath = "";
    private SurfaceView home_surfaceview;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (NetUtils.isNetConnected(mcontext)) {
//            showToast("网络已经连接");
        } else {
            showToast("网络未连接,请检查网络状态");
            finish();
            return;
        }
        setContentView(R.layout.activity_home);
        userId = SharedPreferencesUtil.getStringValue(mcontext,SharedPreferencesUtil.USER_ID,"");
        WorkLog.i(TAG, "onCreate: userId:"+userId);
        initView();
        initListener();
        initData();

    }

    private void initData() {
        if (TextUtils.isEmpty(userId)) {
            userId = "null";
            WorkLog.e(TAG, "initData: userId:" + userId);
        }
        int versionCode = UiUtils.getVersionCode(getApplicationContext());
        WorkLog.e(TAG, "initData: versionCode:" + versionCode);
        try {
            //1.1.2接口入参 json = {“versionCode”,”xx”,”userId”:”id”}
            JSONObject json = new JSONObject();
            json.put("versionCode", versionCode);
            json.put("userId", userId);
            WorkLog.i(TAG, "initData: json=" + json.toString());
            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.HOME_DATE2).tag(this).params("json", json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, "onSuccess: ------------------------------------------"+s);
                    try {
                        JSONObject js = new JSONObject(s);
                        if (js.has("code")){
                            int code = js.getInt("code");
                            switch(code){
                                case 200:
                                    Gson gson = new Gson();
                                    home_indexBean = gson.fromJson(s, Home_IndexBean.class);
                                    break;
                                case 400:

                                    break;
                                case 500:

                                    break;


                                default:
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
                    WorkLog.i(TAG, "onError: ----------------------------------------------");
                }

                @Override
                public void onAfter(String s, Exception e) {
                    super.onAfter(s, e);
                    if (home_indexBean != null && home_indexBean.getCode().equals("200")) {
                        if (home_indexBean.getIndex() != null && home_indexBean.getIndex().size() != 0) {
                            initNetData(home_indexBean);
                        }
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initNetData(Home_IndexBean home_indexBean) {
        Observable.from(home_indexBean.getIndex()).filter(new Func1<Home_IndexBean.IndexBean, Boolean>() {
            @Override
            public Boolean call(Home_IndexBean.IndexBean indexBean) {
                  return indexBean.getMode_type().equals("1.5");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Home_IndexBean.IndexBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Home_IndexBean.IndexBean indexBean) {
                        WorkLog.i(TAG, "图片地址" + indexBean.getImplement_content());
                        videoPath = indexBean.getImplement_content();
                        initVideo();
                    }
                });
        Observable.from(home_indexBean.getIndex())
                .filter(new Func1<Home_IndexBean.IndexBean, Boolean>() {
                    @Override
                    public Boolean call(Home_IndexBean.IndexBean indexBean) {
                        return (indexBean.getMode_type().equals("1.0")) || indexBean.getMode_type().equals("1.1") || indexBean.getMode_type().equals("1.4");
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Home_IndexBean.IndexBean>() {
                    @Override
                    public void call(Home_IndexBean.IndexBean indexBean) {
                        WorkLog.i(TAG, "图片地址" + indexBean.getImplement_content());
                        String ImgContent = indexBean.getImplement_content();
                        switch(indexBean.getIndex_id()){
                            case "1":
                                GlideUtils.getInstance().LoadContextBitmap(HomeActivity.this,ImgContent,home_ct_img1,R.drawable.touming,R.drawable.touming,null);
                                break;
                            case "2":
                                GlideUtils.getInstance().LoadContextBitmap(HomeActivity.this,ImgContent,home_ct_img2,R.drawable.touming,R.drawable.touming,null);
                                break;
                            case "3":
                                GlideUtils.getInstance().LoadContextBitmap(HomeActivity.this,ImgContent,home_ct_img3,R.drawable.touming,R.drawable.touming,null);
                                break;
                            case "4":
                                GlideUtils.getInstance().LoadContextBitmap(HomeActivity.this,ImgContent,home_rd_img1,R.drawable.touming,R.drawable.touming,null);
                                break;
                            case "5":
                                GlideUtils.getInstance().LoadContextBitmap(HomeActivity.this,ImgContent,home_rd_img2,R.drawable.touming,R.drawable.touming,null);
                                break;
                            case "6":
                                GlideUtils.getInstance().LoadContextBitmap(HomeActivity.this,ImgContent,home_rd_img3,R.drawable.touming,R.drawable.touming,null);
                                break;
                            case "7":
                                GlideUtils.getInstance().LoadContextBitmap(HomeActivity.this,ImgContent,home_fc_img1,R.drawable.touming,R.drawable.touming,null);
                                break;
                            case "8":
                                GlideUtils.getInstance().LoadContextBitmap(HomeActivity.this,ImgContent,home_fc_img3,R.drawable.touming,R.drawable.touming,null);
                                break;
                            case "9":
                                GlideUtils.getInstance().LoadContextBitmap(HomeActivity.this,ImgContent,home_fc_img2,R.drawable.touming,R.drawable.touming,null);
                                break;
                            case "10":
                                GlideUtils.getInstance().LoadContextBitmap(HomeActivity.this,ImgContent,home_fc_img4,R.drawable.touming,R.drawable.touming,null);
                                break;
                            default:
                                break;
                        }
//                        GlideUtils.getInstance().LoadContextBitmap(HomeActivity.this,ImgContent,home_ct_img1,);

                    }
                });
        listdata = new ArrayList<>();
        Observable.from(home_indexBean.getIndex())
                .filter(new Func1<Home_IndexBean.IndexBean, Boolean>() {
                    @Override
                    public Boolean call(Home_IndexBean.IndexBean indexBean) {
                        return ((indexBean.getMode_type().equals("1.2")) || (indexBean.getMode_type().equals("1.3")));
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Home_IndexBean.IndexBean>() {
                    @Override
                    public void onCompleted() {
                        initRecyclerView(listdata);
                    }

                    @Override
                    public void onError(Throwable e) {
                        this.unsubscribe();
                    }

                    @Override
                    public void onNext(Home_IndexBean.IndexBean indexBean) {
                        WorkLog.i(TAG, "文字" + indexBean.getImplement_content());
                        String textContext  = indexBean.getImplement_content();
                        if (indexBean.getIndex_id().equals("0")){
                            home_title.setText(textContext);
                        }else{
                            listdata.add(indexBean);
                        }

                    }
                });
    }

    private void initRecyclerView(List<Home_IndexBean.IndexBean> list) {
       if (!list.isEmpty()&& list.size()!=0){
           adapter = new HomeClassifyAdapter(list,mcontext,home_flyborderview);
           home_recyclerview.setAdapter(adapter);
       }
    }

    private void initView() {
        home_surfaceview = (SurfaceView) findViewById(R.id.home_surfaceview);
        home_flyborderview = (FlyBorderView) findViewById(R.id.home_flyborderview);
        home_title = (TextView) findViewById(R.id.home_title);

        home_ct_view1 = (RelativeLayout) findViewById(R.id.home_ct_view1);
        home_ct_view2 = (RelativeLayout) findViewById(R.id.home_ct_view2);
        home_ct_view3 = (RelativeLayout) findViewById(R.id.home_ct_view3);


        home_fc_view1 = (RelativeLayout) findViewById(R.id.home_fc_view1);
        home_fc_view2 = (RelativeLayout) findViewById(R.id.home_fc_view2);
        home_fc_view3 = (RelativeLayout) findViewById(R.id.home_fc_view3);
        home_fc_view4 = (RelativeLayout) findViewById(R.id.home_fc_view4);

        home_rd_view1 = (RelativeLayout) findViewById(R.id.home_rd_view1);
        home_rd_view2 = (RelativeLayout) findViewById(R.id.home_rd_view2);
        home_rd_view3 = (RelativeLayout) findViewById(R.id.home_rd_view3);


        home_ct_img1 = (ImageView) findViewById(R.id.home_ct_img1);
        home_ct_img2 = (ImageView) findViewById(R.id.home_ct_img2);
        home_ct_img3 = (ImageView) findViewById(R.id.home_ct_img3);

        home_fc_img1 = (ImageView) findViewById(R.id.home_fc_img1);
        home_fc_img2 = (ImageView) findViewById(R.id.home_fc_img2);
        home_fc_img3 = (ImageView) findViewById(R.id.home_fc_img3);
        home_fc_img4 = (ImageView) findViewById(R.id.home_fc_img4);

        home_rd_img1 = (ImageView) findViewById(R.id.home_rd_img1);
        home_rd_img2 = (ImageView) findViewById(R.id.home_rd_img2);
        home_rd_img3 = (ImageView) findViewById(R.id.home_rd_img3);

        home_video_rl = (RelativeLayout) findViewById(R.id.home_video_rl);

        home_recyclerview = (RecyclerView) findViewById(R.id.home_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        home_recyclerview.setItemAnimator(new DefaultItemAnimator());
        home_recyclerview.setLayoutManager(linearLayoutManager);
    }

    private void initListener() {
        home_ct_view1.requestFocus();
        home_ct_view1.setOnFocusChangeListener(this);
        home_ct_view2.setOnFocusChangeListener(this);
        home_ct_view3.setOnFocusChangeListener(this);
        home_ct_view1.setFocusable(true);
        home_ct_view2.setFocusable(true);
        home_ct_view3.setFocusable(true);

        home_fc_view1.setOnFocusChangeListener(this);
        home_fc_view2.setOnFocusChangeListener(this);
        home_fc_view3.setOnFocusChangeListener(this);
        home_fc_view4.setOnFocusChangeListener(this);
        home_fc_view1.setFocusable(true);
        home_fc_view2.setFocusable(true);
        home_fc_view3.setFocusable(true);
        home_fc_view4.setFocusable(true);

        home_rd_view1.setOnFocusChangeListener(this);
        home_rd_view2.setOnFocusChangeListener(this);
        home_rd_view3.setOnFocusChangeListener(this);
        home_rd_view1.setFocusable(true);
        home_rd_view2.setFocusable(true);
        home_rd_view3.setFocusable(true);

        home_flyborderview.setFocusable(true);

        home_ct_view1.setOnKeyListener(this);
        home_ct_view2.setOnKeyListener(this);
        home_fc_view1.setOnKeyListener(this);
        home_fc_view3.setOnKeyListener(this);
        home_rd_view2.setOnKeyListener(this);



        home_ct_view1.setOnClickListener(this);
        home_ct_view2.setOnClickListener(this);
        home_ct_view3.setOnClickListener(this);

        home_fc_view1.setOnClickListener(this);
        home_fc_view2.setOnClickListener(this);
        home_fc_view3.setOnClickListener(this);
        home_fc_view4.setOnClickListener(this);

        home_rd_view1.setOnClickListener(this);
        home_rd_view2.setOnClickListener(this);
        home_rd_view3.setOnClickListener(this);

        home_video_rl.setOnFocusChangeListener(this);
        home_video_rl.setFocusable(true);


    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.home_video_rl){
            OnFouchAnim.showAnim3(home_flyborderview, v, hasFocus, mcontext);
            return;
        }
        OnFouchAnim.showAnim(home_flyborderview, v, hasFocus, mcontext);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
            switch(v.getId()){
                case R.id.home_ct_view1:
//                    if (isKeyDown(event)) {
//                        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
//                            home_fc_view1.requestFocus();
//                            return  true;
//                        }else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
//                            return true;
//                        }
//                    }
                    break;
                case R.id.home_ct_view2:
//                    if (isKeyDown(event)) {
//                        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
//                            home_fc_view3.requestFocus();
//                            return  true;
//                        }
//                    }
                    break;
                case R.id.home_fc_view1:
//                    if (isKeyDown(event)) {
//                        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
//                            home_ct_view1.requestFocus();
//                            return  true;
//                        }
//                    }
                    break;
                case R.id.home_fc_view3:
//                    if (isKeyDown(event)) {
//                        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
//                            home_ct_view2.requestFocus();
//                            return  true;
//                        }
//                    }
                    break;
                case R.id.home_rd_view2:
//                    if (isKeyDown(event)){
//                        if (keyCode == KeyEvent.KEYCODE_DPAD_UP){
//                            Log.i(TAG, "onKey: ");
//                            return true;
//                        }
//                    }
                    break;
                default:
                break;
            }
        return false;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.home_ct_view1:
                StartUp(1);
                break;
            case R.id.home_ct_view2:
                StartUp(2);
                break;
            case R.id.home_ct_view3:
                StartUp(3);
                break;

            case R.id.home_fc_view1:
                StartUp(4);
                break;
            case R.id.home_fc_view2:
                StartUp(5);
                break;
            case R.id.home_fc_view3:
                StartUp(6);
                break;
            case R.id.home_fc_view4:
                StartUp(7);
                break;

            case R.id.home_rd_view1:
                StartUp(8);
                break;
            case R.id.home_rd_view2:
                StartUp(9);
                break;
            case R.id.home_rd_view3:
                StartUp(home_indexBean.getIndex().size() -1);
                break;
        }
    }
    /**
     * 开始启动应用
     * @param position
     */
//    private void StartUp2(int position) {
//        if (BeanHasData(home_indexBean)) {
//            WorkLog.i(TAG, "StartUp2: "+home_indexBean.getIndex().get(position).toString());
//            String packageName = home_indexBean.getIndex().get(position).getImplement_package();
//            String className = home_indexBean.getIndex().get(position).getImplement_address();
//            String implementId = home_indexBean.getIndex().get(position).getImplement_id();
////            UiUtils.StartAPP(packageName, className, implementId, mcontext);
//        }
////        if (position>0&position<=5||position==home_indexBean.getIndex().size()-1){
////            UploadTimes(position);
////        }
//    }
    /**
     * 开始启动应用
     * @param position
     */
    private void StartUp(int position) {
        if (BeanHasData(home_indexBean)) {
            WorkLog.i(TAG, "StartUp1: "+home_indexBean.getIndex().get(position).toString());
            String packageName = home_indexBean.getIndex().get(position).getImplement_package();
            String className = home_indexBean.getIndex().get(position).getImplement_address();
            String implementId = home_indexBean.getIndex().get(position).getImplement_id();
            String jsondata = home_indexBean.getIndex().get(position).getJsondata();
            UiUtils.StartAPP(packageName, className, implementId,jsondata, mcontext);
        }
//        if (position>0&position<=5||position==home_indexBean.getIndex().size()-1){
//            UploadTimes(position);
//        }
    }
    /**
     *  上传推荐位点击次数
     * @param position
     */
    private void UploadTimes(int position) {
        WorkLog.i(TAG,"UploadTimes: position:"+position);
        try {
            String statisticsId = home_indexBean.getIndex().get(position).getIndex_id();
            JSONObject jsonItem = new JSONObject();
            jsonItem.put("frequency", 1);//点击次数
            jsonItem.put("statisticsId", statisticsId);
            JSONArray jsArray = new JSONArray();
            jsArray.put(jsonItem);
            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("versionCode", UiUtils.getVersionCode(mcontext)+"");
            json.put("item", jsArray);
            WorkLog.i(TAG, "UploadTimes: json=" + json.toString());
//            String jieko = "http://196.168.0.123:8080/kjmgr/intf/StatisticsRbit.shtml";
            OkGo.post(NetWorkRequest.BaseUrl + NetWorkRequest.STATISTICES_RBIT2).tag(this).params("json", json.toString()).execute(new StringCallback() {
                //            OkGo.post(jieko).tag(this).params("json", json.toString()).execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    WorkLog.i(TAG, "onSuccess: upload times success" + s);
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    WorkLog.e(TAG, "onError: upload times error");
                }
            });
//            json={"item":[{"statisticsId":"4","frequency":"1"}],"versionCode":"xx","userId":"id"}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @NonNull
    private boolean BeanHasData(Home_IndexBean bean) {
        try {
            if (bean == null) {
                WorkLog.i(TAG, "BeanHasData: bean is null");
                return false;
            }
            if (!bean.getCode().equals("200")) {
                WorkLog.i(TAG, "BeanHasData: bean.getcode not is 200");
                return false;
            }
            if (bean.getIndex().size() == 0 && bean.getIndex().isEmpty()) {
                WorkLog.i(TAG, "BeanHasData: bean.getIndex is null");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     *   判断按键是否是在按下的状态
     *   ture 按下   false 弹起
     * @param event
     * @return
     */
    private boolean isKeyDown(KeyEvent event) {
        return event.getAction() == KeyEvent.ACTION_DOWN;
    }
    /**
     *  记录按下返回键的时间
     */
    private long back_time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - back_time >= 2000) {
                showToast("再按一次退出");
                back_time = System.currentTimeMillis();
                return false;
            } else {
//                this.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化视频
     */
    private void initVideo() {
        Observable.timer(100, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                videoPlay();
            }
        });
    }
    /**
     * 播放视频
     */
    private void videoPlay() {
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                WorkLog.i(TAG, "initVideo: mediaPlayer is playing,stopVideo and release mediaplayer");
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置播放的视频源
            if (videoPath.startsWith("http://")) {
                WorkLog.i(TAG, "initVideo: get videopath is network video");
                mediaPlayer.setDataSource(mcontext, Uri.parse(videoPath));
            } else {
                try {
                    File file = new File(videoPath);
                    if (!file.exists()) {
                        WorkLog.i(TAG, "initVideo: get videofile not exists");
                        showToast("获取播放视频文件失败或者文件不存在");
                        finish();
                        return;
                    } else {
                        WorkLog.i(TAG, "initVideo: get videopath is local video");
                        mediaPlayer.setDataSource(file.getAbsolutePath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            mediaPlayer.setDisplay(home_surfaceview.getHolder());
            WorkLog.i(TAG, "initVideo: loading video");
            mediaPlayer.prepareAsync();
//            mediaPlayer.prepare();//prepare之后自动播放
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        } catch (Exception e) {
            WorkLog.i(TAG, "initVideo: loading video error");
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer = null;
            }
            initVideo();
            e.printStackTrace();
        }
    }
    private boolean startPlay = true;
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        WorkLog.i(TAG, "surfaceCreated: surfaceView创建");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        WorkLog.i(TAG, "surfaceChanged: surfaceView发生改变");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        WorkLog.i(TAG, "surfaceDestroyed: surfaceView 被销毁");
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // 在播放完毕被回调
        WorkLog.i(TAG, "onCompletion: video play completion");
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        replay();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        WorkLog.i(TAG, "onError: 播放错误");
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer = null;
        }
        initVideo();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        WorkLog.i(TAG, "onPrepared: video load success,start play video");
        mediaPlayer.setDisplay(home_surfaceview.getHolder());
        mediaPlayer.start();
        // 按照初始位置播放
        if (VideoProgess!=-1){
            mediaPlayer.seekTo(VideoProgess);
            VideoProgess = -1;
        }else{
            mediaPlayer.seekTo(0);
        }
    }
    /**
     * 停止播放视频
     */
    private void videoStop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 重新开始播放
     */
    protected void replay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            showToast("重新播放");
            return;
        }
        initVideo();
    }
    private int VideoProgess = -1;
    /**
     * Activity 生命周期  暂停
     */
    @Override
    protected void onPause() {
        super.onPause();
        try{
//            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                WorkLog.i(TAG, "onPause: video Pause1");
//                mediaPlayer.pause();
//            }else if(mediaPlayer != null && !mediaPlayer.isPlaying()){
//                WorkLog.i(TAG, "onPause:  video Pause2");
//                mediaPlayer.pause();
//            }
            if (mediaPlayer!=null){
                VideoProgess = mediaPlayer.getCurrentPosition();
            }
            videoStop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Activity 生命周期   重新开始
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (startPlay) {
            startPlay = false;
            return;
        }
        videoPlay();
//        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
//            WorkLog.i(TAG, "onResume: video restart");
//            mediaPlayer.start();
//        }
    }
    /**
     * Activity 生命周期   销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoStop();
    }
}
