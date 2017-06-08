package com.vunke.ec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vunke.ec.R;
import com.vunke.ec.mod.Home_IndexBean;
import com.vunke.ec.util.OnFouchAnim;
import com.vunke.ec.util.UiUtils;
import com.vunke.ec.view.FlyBorderView;

import java.util.List;

/**
 * Created by zhuxi on 2017/5/16.
 */
public class HomeClassifyAdapter extends RecyclerView.Adapter<HomeClassifyAdapter.HomeClassifyHolder> {
    private static final String TAG = "HomeClassifyAdapter";
    private List<Home_IndexBean.IndexBean>  list;
    private Context context;
    private FlyBorderView flyborderview;
    public HomeClassifyAdapter(List<Home_IndexBean.IndexBean> list, Context context, FlyBorderView flyborderview){
        this.context = context;
        this.list = list;
        this.flyborderview = flyborderview;
    }

    @Override
    public HomeClassifyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_home_item,parent,false);
       final HomeClassifyHolder holder = new HomeClassifyHolder(view);
        holder.myView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                OnFouchAnim.showAnim(flyborderview,v,hasFocus,context);
//                if (hasFocus){
//                    OnFouchAnim.scaleBig(v,context);
//                    v.setBackgroundResource(R.drawable.bg_border3);
//                }else{
//                    OnFouchAnim.scaleSmall(v,context);
//                    v.setBackgroundResource(R.drawable.bg_border2);
//                }
            }
        });
        holder.myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                String packageName = list.get(position).getImplement_package();
                String className = list.get(position).getImplement_address();
                String implementId = list.get(position).getImplement_id();
                String jsondata = list.get(position).getJsondata();
                UiUtils.StartAPP(packageName, className, implementId, jsondata,context);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeClassifyHolder holder, int position) {
        String text = list.get(position).getImplement_content();
        holder.home_recyclerview_text.setText(text);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeClassifyHolder extends RecyclerView.ViewHolder{
        private TextView home_recyclerview_text;
        View myView;
        public HomeClassifyHolder(View itemView) {
            super(itemView);
            myView = itemView;
            home_recyclerview_text = (TextView) itemView.findViewById(R.id.recyclerview_home_text);
        }
    }
}
