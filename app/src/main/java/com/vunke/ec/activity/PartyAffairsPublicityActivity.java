package com.vunke.ec.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.vunke.ec.R;
import com.vunke.ec.adapter.PAPAdapter;
import com.vunke.ec.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.tvrecyclerview.TvRecyclerView;

/**
 * 党务公开
 */
public class PartyAffairsPublicityActivity extends BaseActivity {

    private TvRecyclerView pap_content_recycler;
    private List<Map<String,Object>> datalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_affairs_publicity);
        initView();
        initData();
    }
    private void initView() {
        pap_content_recycler = (TvRecyclerView) findViewById(R.id.pap_content_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        pap_content_recycler.setLayoutManager(manager);

        pap_content_recycler.setOnItemStateListener(new TvRecyclerView.OnItemStateListener() {
            @Override
            public void onItemViewClick(View view, int position) {

            }

            @Override
            public void onItemViewFocusChanged(boolean gainFocus, View view, int position) {

            }
        });
        pap_content_recycler.setSelectedScale(1.0f);
        pap_content_recycler.setSelectPadding(6,6,6,6);

    }
    boolean isBlack= false;
    private void initData() {
        datalist = new ArrayList<>();
        Map<String,Object> map ;
        for (int i = 0; i <10 ; i++) {
            map = new HashMap<>();
            map.put("city","益阳");
            map.put("address","村党委人员");
            map.put("createtime","2017-10-1"+i);
            map.put("title","村支两委换届动员大会");
            isBlack = !isBlack;
            if (isBlack){
                map.put("type",1);
            }else{
                map.put("type",2);
            }
            datalist.add(map);
        }
        PAPAdapter adapter = new PAPAdapter(mcontext,datalist);
        pap_content_recycler.setAdapter(adapter);
    }

//    public class PAPAdapter extends RecyclerView.Adapter<PAPAdapter.PAPHolder>{
//        private Context context;
//        private List<Map<String,Object>> datalist;
//        public PAPAdapter(Context context, List<Map<String,Object>> datalist){
//            this.context= context;
//            this.datalist = datalist;
//        }
//        @Override
//        public PAPHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view= null;
//            if (viewType == 1){
//                 view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_partyaffairspublicity,parent,false);
//                PAPHolder holder = new PAPHolder(view);
//            }else if (viewType == 2){
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_partyaffairspublicity2,parent,false);
//            }
//            PAPHolder holder = new PAPHolder(view);
//            holder.mItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//
//                }
//            });
//            return holder;
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return (int) datalist.get(position).get("type");
//        }
//
//        @Override
//        public void onBindViewHolder(PAPHolder holder, int position) {
//            String title = (String) datalist.get(position).get("title");
//            String address = (String) datalist.get(position).get("address");
//            String city =(String) datalist.get(position).get("city");
//            String createtime = (String)  datalist.get(position).get("createtime");
//            holder.pap_recycler_title.setText(title);
//            holder.pap_recycler_address.setText(address);
//            holder.pap_recycler_city.setText(city);
//            holder.pap_recycler_createtime.setText(createtime);
//        }
//
//        @Override
//        public int getItemCount() {
//            return datalist.size();
//        }
//
//        public class PAPHolder extends RecyclerView.ViewHolder{
//            private TextView pap_recycler_title;
//            private TextView pap_recycler_address;
//            private TextView pap_recycler_city;
//            private TextView pap_recycler_createtime;
//            private View mItemView;
//            public PAPHolder(View itemView) {
//                super(itemView);
//                mItemView= itemView;
//                pap_recycler_title = (TextView) mItemView.findViewById(R.id.pap_recycler_title);
//                pap_recycler_city = (TextView) mItemView.findViewById(R.id.pap_recycler_city);
//                pap_recycler_address = (TextView) mItemView.findViewById(R.id.pap_recycler_address);
//                pap_recycler_createtime = (TextView) mItemView.findViewById(R.id.pap_recycler_createtime);
//            }
//        }
//    }

}
