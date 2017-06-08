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

public class OfficeContentAdapter extends RecyclerView.Adapter<OfficeContentAdapter.OfficeContent2Holder>{
        private Context context;
        private List<ColumnBean.DataBean> mdatalist;

        public OfficeContentAdapter(Context context, List<ColumnBean.DataBean> list){
            this.context = context;
            this.mdatalist = list;
        }
        @Override
        public OfficeContent2Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_officecontent_item,parent,false);
            OfficeContent2Holder holder = new OfficeContent2Holder(view);
            holder.mItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
//                OnFouchAnim.showAnim(flyBorderView,v,hasFocus,context);
                    if (hasFocus){

                    }else{

                    }
                }
            });
            holder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(OfficeContent2Holder holder, int position) {
            holder.officecontent_item_text.setText(mdatalist.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return mdatalist.size();
        }

        public class OfficeContent2Holder extends RecyclerView.ViewHolder{
            private TextView officecontent_item_text;
            private View mItemView;
            public OfficeContent2Holder(View itemView) {
                super(itemView);
                mItemView = itemView;
                officecontent_item_text = (TextView) mItemView.findViewById(R.id.officecontent_item_text);
            }
        }
    }