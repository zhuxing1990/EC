package com.vunke.ec.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vunke.ec.R;
import com.vunke.ec.activity.OnlineLearnActivity;
import com.vunke.ec.base.Configs;
import com.vunke.ec.mod.ColumnBean;
import com.vunke.ec.util.GlideUtils;
import com.vunke.ec.util.OnFouchAnim;

import java.util.List;

/**
 * 党务设配器
 * Created by zhuxi on 2017/5/22.
 */
public class PartyAffairsAdapter extends RecyclerView.Adapter<PartyAffairsAdapter.PartyAffairsHolder>{
    private  List<ColumnBean.DataBean> list;
    private Context context;
    public PartyAffairsAdapter(Context context, List<ColumnBean.DataBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public PartyAffairsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_partyaffairs,parent,false);
        PartyAffairsHolder holder = new PartyAffairsHolder(view);
        holder.mItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                OnFouchAnim.showAnim(flyBorderView,v,hasFocus,context);
                if (hasFocus){
                    OnFouchAnim.scaleBig(v,context);
                    v.setBackgroundResource(R.drawable.bg_border5);
                }else{
                    OnFouchAnim.scaleSmall(v,context);
                    v.setBackgroundResource(R.drawable.bg_border2);
                }
            }
        });
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configs.intent = new Intent(context,OnlineLearnActivity.class);
                Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Configs.intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(PartyAffairsHolder holder, int position) {
        ColumnBean.DataBean dataBean = list.get(position);
        if (TextUtils.isEmpty(dataBean.getMainpicurl())){
            holder.recycler_partyaffairs_img.setImageResource(R.drawable.partyaffairs_view1);
        }else{
            GlideUtils.getInstance().LoadContextBitmap(context,dataBean.getMainpicurl(),holder.recycler_partyaffairs_img,R.drawable.touming,R.drawable.touming,null);
        }

        holder.recycler_partyaffairs_content.setText(dataBean.getTitle());
        holder.recycler_partyaffairs_time.setText(dataBean.getCreatetime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PartyAffairsHolder extends RecyclerView.ViewHolder {
        private View mItemView;
        private ImageView recycler_partyaffairs_img;
        private TextView recycler_partyaffairs_time;
        private TextView recycler_partyaffairs_content;
        public PartyAffairsHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            recycler_partyaffairs_img = (ImageView) mItemView.findViewById(R.id.recycler_partyaffairs_img);
            recycler_partyaffairs_time = (TextView) mItemView.findViewById(R.id.recycler_partyaffairs_time);
            recycler_partyaffairs_content = (TextView) mItemView.findViewById(R.id.recycler_partyaffairs_content);

        }
    }
}
