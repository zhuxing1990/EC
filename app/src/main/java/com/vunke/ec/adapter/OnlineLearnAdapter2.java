package com.vunke.ec.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vunke.ec.R;
import com.vunke.ec.activity.VideoPlayActivity;
import com.vunke.ec.base.Configs;
import com.vunke.ec.log.WorkLog;
import com.vunke.ec.mod.ColumnBean;
import com.vunke.ec.util.GlideUtils;
import com.vunke.ec.util.UiUtils;

import java.util.List;

import app.com.tvrecyclerview.TvRecyclerView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
     * Created by zhuxi on 2017/5/20.
     */
    public class OnlineLearnAdapter2 extends RecyclerView.Adapter<OnlineLearnAdapter2.OnlineLearnHolder2> {
    private static final String TAG = "OnlineLearnAdapter2";
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
                        Configs.intent = new Intent(context,VideoPlayActivity.class);
                        Configs.intent.putExtra("infoid",list.get(position).getInfoid());
                        Configs.intent.putExtra("videoPath",videopath);
                        context.startActivity(Configs.intent);
                    }else{
                        UiUtils.showToast("未找到视频信息",context);
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
//            if (TextUtils.isEmpty(imgurl)&&TextUtils.isEmpty(string6)){
//                WorkLog.i(TAG, "onBindViewHolder: ------------all data is null");
//            }else if (TextUtils.isEmpty(string6)&& !TextUtils.isEmpty(imgurl)){
//                WorkLog.i(TAG, "onBindViewHolder: ------------mainpicurl has data");
//                GlideUtils.getInstance().LoadContextBitmap(context,list.get(position).getMainpicurl(),holder.onlinelearn_recycler_item_img,R.drawable.touming,R.drawable.touming,null);
//            }else if (!TextUtils.isEmpty(string6)&& TextUtils.isEmpty(imgurl)){
//                WorkLog.i(TAG, "onBindViewHolder: ------------getString6 has data");
//                GlideUtils.getInstance().LoadContextBitmap(context,list.get(position).getString6(),holder.onlinelearn_recycler_item_img,R.drawable.touming,R.drawable.touming,GlideUtils.LOAD_GIF);
//            }else 
            if (!TextUtils.isEmpty(string6)&& !TextUtils.isEmpty(imgurl)){
                WorkLog.i(TAG, "onBindViewHolder: ------------all data has data");
                GlideUtils.getInstance().LoadContextBitmap(context,list.get(position).getMainpicurl(),holder.onlinelearn_recycler_item_img,R.drawable.touming,R.drawable.touming,null);
            }

//            getVideoBitmap(holder.onlinelearn_recycler_item_img,list.get(position).getString6());
        }
        private void getVideoBitmap(final ImageView imageView, final String path){
//            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, 500);
//            imageView.setImageBitmap(bitmap);

        Observable.unsafeCreate(new Observable.OnSubscribe<Bitmap>() {

                @Override
                public void call(Subscriber<? super Bitmap> subscriber) {
                    Bitmap bitmap = UiUtils.createVideoThumbnail(path, 255, 138);
                    subscriber.onNext(bitmap);
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Bitmap>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                        }
                    });


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
