package com.vunke.ec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vunke.ec.R;
import com.vunke.ec.mod.getPBlockIdBean;
import com.vunke.ec.view.FlyBorderView;

import java.util.List;

/**
 * Created by zhuxi on 2017/5/16.
 */
public class OfficeGuideAdapter extends RecyclerView.Adapter<OfficeGuideAdapter.OfficeGuideHolder> {
    private static final String TAG = "OfficeGuideAdapter";
//    private List<String> list;
    private List<getPBlockIdBean.DataBean> list;
    private Context context;
    private FlyBorderView flyborderview;

    public OfficeGuideAdapter(List<getPBlockIdBean.DataBean> list, Context context, FlyBorderView flyborderview){
        this.context = context;
        this.list = list;

        this.flyborderview = flyborderview;
    }

    @Override
    public OfficeGuideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_officeguide_item,parent,false);
       final OfficeGuideHolder holder = new OfficeGuideHolder(view);
        holder.mItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                OnFouchAnim.showAnim(flyBorderView,v,hasFocus,context);
//                if (hasFocus){
//                    v.setBackgroundColor(Color.parseColor("#dfaf5a"));
//                    OnFouchAnim.scaleBig(v,context);
//                    v.setBackgroundResource(R.drawable.bg_border4);
//                }else{
//                    v.setBackgroundColor(Color.parseColor("#b97923"));
//                    OnFouchAnim.scaleSmall(v,context);
//                    v.setBackgroundResource(R.drawable.bg_border2);
//                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(OfficeGuideHolder holder, int position) {
        String text = list.get(position).getBlockname();
        holder.recyclerview_officeguide_item_text.setText(text);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OfficeGuideHolder extends RecyclerView.ViewHolder{
        private TextView recyclerview_officeguide_item_text;
        View mItemView;
        public OfficeGuideHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            recyclerview_officeguide_item_text = (TextView) itemView.findViewById(R.id.recyclerview_officeguide_item_text);
        }
    }
}
