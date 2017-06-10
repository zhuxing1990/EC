package com.vunke.ec.fragement;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vunke.ec.R;
import com.vunke.ec.adapter.SpaceItemDecoration;
import com.vunke.ec.util.OnFouchAnim;
import com.vunke.ec.view.ScaleRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 益阳概况
 * Created by zhuxi on 2017/6/9.
 */
public class OverviewFragment extends Fragment {
    private ScaleRecyclerView overviewyiyang_recycler;
    private List<String> list;
    private OverviewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview,null);
        initView(view);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        list = new ArrayList<>();
        list.add("行政区划");
        list.add("自然地理");
        list.add("益阳人口");
        list.add("民族教宗");
        list.add("县区概况");
        list.add("益阳印象");
        list.add("图说益阳");
        list.add("视频益阳");
        adapter = new OverviewAdapter(getContext(),list);
        overviewyiyang_recycler.setAdapter(adapter);
//        overviewyiyang_recycler.setItemSelected(0);
    }

    private void initView(View view) {
        overviewyiyang_recycler = (ScaleRecyclerView) view.findViewById(R.id.overviewyiyang_recycler);
        GridLayoutManager gridlayoutmanager = new GridLayoutManager(getContext(),2);
        overviewyiyang_recycler.setLayoutManager(gridlayoutmanager);
        gridlayoutmanager.setOrientation(GridLayoutManager.VERTICAL);
        int itemSpace = getResources().
                getDimensionPixelSize(R.dimen.recyclerview_item_space8);
        overviewyiyang_recycler.addItemDecoration(new SpaceItemDecoration(itemSpace));
//        overviewyiyang_recycler.setSelectedScale(1.1f);
//        overviewyiyang_recycler.setSelectPadding(6,6,6,6);

    }

    public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.OverviewHolder>{
        private Context context;
        private List<String> mdatalist;
        public OverviewAdapter ( Context context, List<String> mdatalist){
            this.context = context;
            this.mdatalist = mdatalist;
        }

        @Override
        public OverviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_overview_item,parent,false);
            final OverviewHolder holder = new OverviewHolder(view);
            holder.mItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                  OnFouchAnim.showAnim2(v,hasFocus,context);
                    if (hasFocus){
                        holder.recycler_overview_item_layout.setBackgroundColor(Color.parseColor("#64b7e9"));
                    }else{
                        holder.recycler_overview_item_layout.setBackgroundColor(Color.parseColor("#53bde0"));
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(OverviewHolder holder, int position) {
            holder.recycler_overview_item_text.setText(mdatalist.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return mdatalist.size();
        }

        public class OverviewHolder extends RecyclerView.ViewHolder{
            private View mItemView;
            private TextView recycler_overview_item_text;
            private RelativeLayout recycler_overview_item_layout;
            public OverviewHolder(View itemView) {
                super(itemView);
                mItemView = itemView;
                recycler_overview_item_text = (TextView) mItemView.findViewById(R.id.recycler_overview_item_text);
                recycler_overview_item_layout = (RelativeLayout) mItemView.findViewById(R.id.recycler_overview_item_layout);

            }
        }
    }
}
