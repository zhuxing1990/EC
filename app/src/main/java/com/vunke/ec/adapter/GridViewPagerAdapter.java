package com.vunke.ec.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.com.tvrecyclerview.TvRecyclerView;

/**
 * 实现ViewPager页卡切换的适配器
 * @author Administrator
 *
 */
public class GridViewPagerAdapter extends PagerAdapter{
	private List<TvRecyclerView> array;
	private Context context;
	/**
	 * 供外部调用（new）的方法
	 * @param context  上下文
	 * @param array    添加的序列对象
	 */
	public GridViewPagerAdapter(Context context, List<TvRecyclerView> array) {
		this.array=array;
		this.context = context;
	}
	@Override
	public int getCount() {
		return array.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(array.get(position));
		return array.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}


}
