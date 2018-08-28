package com.vunke.ec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vunke.ec.R;
import com.vunke.ec.mod.Function2Bean;
import com.vunke.ec.util.GlideUtils;
import com.vunke.ec.view.FlyBorderView;

import java.util.List;

public class GovernmentAffairsAdapter extends RecyclerView.Adapter<GovernmentAffairsAdapter.GovernmentAffairsHolder>{
        private Context context;
        private List<Function2Bean.DataBean> mdatalist;
        private FlyBorderView flyBorderView;
        public GovernmentAffairsAdapter (Context context, FlyBorderView flyBorderView, List<Function2Bean.DataBean> mdatalist){
            this.context = context;
            this.mdatalist =mdatalist;
            this.flyBorderView = flyBorderView;
        }

        public GovernmentAffairsAdapter (Context context, List<Function2Bean.DataBean> mdatalist){
            this.context = context;
            this.mdatalist =mdatalist;
        }
        @Override
        public GovernmentAffairsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_governmentaffairs,parent,false);
            GovernmentAffairsHolder holder = new GovernmentAffairsHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(GovernmentAffairsHolder holder, int position) {
            GlideUtils.getInstance().LoadContextBitmap(context,mdatalist.get(position).getImage(), holder.recycler_governmentaffairs_img,R.drawable.touming,R.drawable.touming,null);

        }

        @Override
        public int getItemCount() {
            return mdatalist.size();
        }

        public class GovernmentAffairsHolder extends RecyclerView.ViewHolder{
            private View mItemView;
            private ImageView recycler_governmentaffairs_img;
            public GovernmentAffairsHolder(View itemView) {
                super(itemView);
                mItemView = itemView;
                recycler_governmentaffairs_img = (ImageView) itemView.findViewById(R.id.recycler_governmentaffairs_img);
            }
        }
    }