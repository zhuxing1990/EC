package com.vunke.ec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vunke.ec.R;
import com.vunke.ec.mod.Function2Bean;
import com.vunke.ec.util.GlideUtils;
import com.vunke.ec.util.OnFouchAnim;
import com.vunke.ec.util.UiUtils;
import com.vunke.ec.view.FlyBorderView;

import java.util.List;

/**
 * Created by zhuxi on 2017/5/20.
 */
public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FunctionHolder> {
    private static final String TAG = "FunctionAdapter";
    private Context context;
    private FlyBorderView flyBorderView;
//    private List<Map<String,Object>> list;
    private List<Function2Bean.DataBean> list;
    public FunctionAdapter(Context context, FlyBorderView flyBorderView,List<Function2Bean.DataBean> list){
        this.context = context;
        this.flyBorderView =flyBorderView;
        this.list = list;
    }
    @Override
    public FunctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_function_tiem,parent,false);
        final FunctionHolder holder = new FunctionHolder(view);
        holder.mItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                holder.function_item_img.requestFocus();
//                OnFouchAnim.showAnim(flyBorderView,v,hasFocus,context);
                holder.function_item_img.requestFocus();
//                if (hasFocus){
//                    OnFouchAnim.scaleBig(v,context);
//                    v.setBackgroundResource(R.drawable.bg_border4);
//                }else{
//                    OnFouchAnim.scaleSmall(v,context);
//                    v.setBackgroundResource(R.drawable.bg_border2);
//                }
            }
        });
        holder.function_item_img.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                OnFouchAnim.showAnim(flyBorderView,v,hasFocus,context);
            }
        });
        holder.function_item_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
//                if (list.get(position).getImage().equals("qrcode")){
//                    return;
//                }
                if (list.get(position).getImplement_titie().equals("益村二维码"))return;
                String implement_package = list.get(position).getImplement_package();
                String implement_address = list.get(position).getImplement_address();
                String implement_id = list.get(position).getImplement_id();
                UiUtils.StartAPP(implement_package,implement_address,implement_id,null,context);
//
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(FunctionHolder holder, int position) {
//        if (list.get(position).getImage().equals("qrcode")){
//            holder.function_item_img.setImageResource(R.drawable.qr_code);
//        }else{
            GlideUtils.getInstance().LoadContextBitmap(context,list.get(position).getImage(),holder.function_item_img,R.drawable.touming,R.drawable.touming,null);
//        }
        holder.function_item_text.setText(list.get(position).getImplement_titie());
//        holder.function_item_img.setImageResource(img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FunctionHolder extends RecyclerView.ViewHolder{
        private View mItemView;
        private ImageView function_item_img;
        private TextView function_item_text;
        public FunctionHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            function_item_img = (ImageView) itemView.findViewById(R.id.function_item_img);
            function_item_img.setFocusable(true);
            function_item_text = (TextView) itemView.findViewById(R.id.function_item_text);
        }
    }
}
