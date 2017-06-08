package com.vunke.ec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vunke.ec.R;

import java.util.List;
import java.util.Map;

public class PAPAdapter extends RecyclerView.Adapter<PAPAdapter.PAPHolder>{
        private Context context;
        private List<Map<String,Object>> datalist;
        public PAPAdapter(Context context, List<Map<String,Object>> datalist){
            this.context= context;
            this.datalist = datalist;
        }
        @Override
        public PAPHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= null;
            if (viewType == 1){
                 view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_partyaffairspublicity,parent,false);
                PAPHolder holder = new PAPHolder(view);
            }else if (viewType == 2){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_partyaffairspublicity2,parent,false);
            }
            PAPHolder holder = new PAPHolder(view);
            holder.mItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                }
            });
            return holder;
        }

        @Override
        public int getItemViewType(int position) {
            return (int) datalist.get(position).get("type");
        }

        @Override
        public void onBindViewHolder(PAPHolder holder, int position) {
            String title = (String) datalist.get(position).get("title");
            String address = (String) datalist.get(position).get("address");
            String city =(String) datalist.get(position).get("city");
            String createtime = (String)  datalist.get(position).get("createtime");
            holder.pap_recycler_title.setText(title);
            holder.pap_recycler_address.setText(address);
            holder.pap_recycler_city.setText(city);
            holder.pap_recycler_createtime.setText(createtime);
        }

        @Override
        public int getItemCount() {
            return datalist.size();
        }

        public class PAPHolder extends RecyclerView.ViewHolder{
            private TextView pap_recycler_title;
            private TextView pap_recycler_address;
            private TextView pap_recycler_city;
            private TextView pap_recycler_createtime;
            private View mItemView;
            public PAPHolder(View itemView) {
                super(itemView);
                mItemView= itemView;
                pap_recycler_title = (TextView) mItemView.findViewById(R.id.pap_recycler_title);
                pap_recycler_city = (TextView) mItemView.findViewById(R.id.pap_recycler_city);
                pap_recycler_address = (TextView) mItemView.findViewById(R.id.pap_recycler_address);
                pap_recycler_createtime = (TextView) mItemView.findViewById(R.id.pap_recycler_createtime);
            }
        }
    }