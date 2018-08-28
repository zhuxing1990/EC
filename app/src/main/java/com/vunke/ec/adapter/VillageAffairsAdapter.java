package com.vunke.ec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vunke.ec.R;
import com.vunke.ec.mod.ColumnBean;

import java.util.List;

public class VillageAffairsAdapter extends RecyclerView.Adapter<VillageAffairsAdapter.VillageAffairsHolder>{
        private Context context;
        private List<ColumnBean.DataBean> datalist;
        public VillageAffairsAdapter(Context context, List<ColumnBean.DataBean> datalist){
            this.context= context;
            this.datalist = datalist;
        }
        @Override
        public VillageAffairsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= null;
            if (viewType == 1){
                 view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_governmentaffairspublicity,parent,false);
                VillageAffairsHolder holder = new VillageAffairsHolder(view);
            }else if (viewType == 2){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_governmentaffairspublicity2,parent,false);
            }
            VillageAffairsHolder holder = new VillageAffairsHolder(view);
            holder.mItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                }
            });
            return holder;
        }

        @Override
        public int getItemViewType(int position) {
            return datalist.get(position).getAdapterType();
        }

        @Override
        public void onBindViewHolder(VillageAffairsHolder holder, int position) {
            String title = (String) datalist.get(position).getTitle();
            String address = (String) datalist.get(position).getString2();
            String city =(String) datalist.get(position).getString1();
            String createtime = (String)  datalist.get(position).getCreatetime();
            holder.pap_recycler_title.setText(title);
            holder.pap_recycler_address.setText(address);
            holder.pap_recycler_city.setText(city);
            holder.pap_recycler_createtime.setText(createtime);
        }

        @Override
        public int getItemCount() {
            return datalist.size();
        }

        public class VillageAffairsHolder extends RecyclerView.ViewHolder{
            private TextView pap_recycler_title;
            private TextView pap_recycler_address;
            private TextView pap_recycler_city;
            private TextView pap_recycler_createtime;
            private View mItemView;
            public VillageAffairsHolder(View itemView) {
                super(itemView);
                mItemView= itemView;
                pap_recycler_title = (TextView) mItemView.findViewById(R.id.pap_recycler_title);
                pap_recycler_city = (TextView) mItemView.findViewById(R.id.pap_recycler_city);
                pap_recycler_address = (TextView) mItemView.findViewById(R.id.pap_recycler_address);
                pap_recycler_createtime = (TextView) mItemView.findViewById(R.id.pap_recycler_createtime);
            }
        }
    }
