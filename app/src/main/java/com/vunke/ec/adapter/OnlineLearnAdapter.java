package com.vunke.ec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vunke.ec.R;
import com.vunke.ec.util.OnFouchAnim;

import java.util.List;

/**
 * Created by zhuxi on 2017/5/20.
 */
public class OnlineLearnAdapter extends RecyclerView.Adapter<OnlineLearnAdapter.OnlineLearnHolder> {
    private Context context;
    private List<Object> list;
//    private FlyBorderView flyBorderView;
    public OnlineLearnAdapter (Context context, List<Object> list){
//    public OnlineLearnAdapter (Context context, List<Object> list,FlyBorderView flyBorderView){
        this.context = context;
        this.list = list;
//        this.flyBorderView = flyBorderView;
    }

    @Override
    public OnlineLearnHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_onlinelearn_item,parent,false);
        final OnlineLearnHolder holder = new OnlineLearnHolder(view);
        holder.mItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                OnFouchAnim.showAnim(flyBorderView,v,hasFocus,context);
                if (hasFocus){
                    OnFouchAnim.scaleBig(v,context);
                    v.setBackgroundResource(R.drawable.bg_border4);
                }else{
                    OnFouchAnim.scaleSmall(v,context);
                    v.setBackgroundResource(R.drawable.bg_border2);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder( OnlineLearnHolder holder, int position) {
            holder.onlinelearn_recycler_item_text.setText(list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OnlineLearnHolder extends RecyclerView.ViewHolder{
        private ImageView onlinelearn_recycler_item_img;
        private TextView onlinelearn_recycler_item_text;
        private View mItemView;
        public OnlineLearnHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            onlinelearn_recycler_item_text = (TextView) mItemView.findViewById(R.id.onlinelearn_recycler_item_text);
            onlinelearn_recycler_item_img = (ImageView) mItemView.findViewById(R.id.onlinelearn_recycler_item_img);
        }
    }
}
