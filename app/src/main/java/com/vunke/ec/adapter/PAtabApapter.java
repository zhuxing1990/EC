package com.vunke.ec.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vunke.ec.R;
import com.vunke.ec.mod.getPBlockIdBean;

import java.util.List;

public class PAtabApapter extends DefaultAdapter<getPBlockIdBean.DataBean>{
        private Context context;
        public PAtabApapter(Context context, List<getPBlockIdBean.DataBean> list ) {
            super(list);
            this.context = context;
        }

        @Override
        protected BaseHolder getHolder() {

            return new PAtabViewHolder();
        }
        public class PAtabViewHolder extends BaseHolder<getPBlockIdBean.DataBean>{
            private TextView pa_list_item_text;

            @Override
            protected View initView() {
//                View view =View.inflate(context,R.layout.listview_povertyalleviation_item,null);
                View view = LayoutInflater.from(context).inflate(R.layout.listview_povertyalleviation_item,null);
                pa_list_item_text = (TextView) view.findViewById(R.id.pa_list_item_text);

                return view;
            }

            @Override
            protected void refreshView(getPBlockIdBean.DataBean data, int position, ViewGroup parent) {
                pa_list_item_text.setText(data.getBlockname());
                pa_list_item_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        }
    }
