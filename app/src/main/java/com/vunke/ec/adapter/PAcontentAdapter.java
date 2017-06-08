package com.vunke.ec.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vunke.ec.R;
import com.vunke.ec.activity.DetailsActivity;
import com.vunke.ec.base.Configs;
import com.vunke.ec.mod.ColumnBean;
import com.vunke.ec.util.OnFouchAnim;

import java.util.List;

public class PAcontentAdapter extends RecyclerView.Adapter<PAcontentAdapter.PAcontentHolder>{
        private List<ColumnBean.DataBean> mlist;
        private Context context;
        public PAcontentAdapter(Context context, List<ColumnBean.DataBean> mlist){
            this.context = context;
            this.mlist = mlist;
        }
        @Override
        public PAcontentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_povertyalleviation_item,parent,false);
            final PAcontentHolder holder = new PAcontentHolder(view);
            holder.mItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus){
                    OnFouchAnim.scaleBig(v,context);
                        v.setBackgroundResource(R.drawable.bg_border3);
                    }else{
                        OnFouchAnim.scaleSmall(v,context);
                        v.setBackgroundColor(context.getResources().getColor(R.color.color_black3));
                    }
                }
            });
            holder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    Configs.intent = new Intent(context, DetailsActivity.class);
                    Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Configs.intent.putExtra("infoid",mlist.get(adapterPosition).getInfoid());
                    context.startActivity(Configs.intent);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(PAcontentHolder holder, int position) {
            holder.pa_recyclerview_item_content.setText(mlist.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }

        public class PAcontentHolder extends RecyclerView.ViewHolder{
            private View mItemView;
            private TextView pa_recyclerview_item_content;
            public PAcontentHolder(View itemView) {
                super(itemView);
                mItemView = itemView;
                pa_recyclerview_item_content = (TextView) mItemView.findViewById(R.id.pa_recyclerview_item_content);
            }
        }
    }