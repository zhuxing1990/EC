package com.vunke.ec.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vunke.ec.R;
import com.vunke.ec.adapter.MyFragmentPagerAdapter;
import com.vunke.ec.base.BaseFragementActivity;
import com.vunke.ec.fragement.FutureDevelopmentFragment;
import com.vunke.ec.fragement.HistoricalCultureFragment;
import com.vunke.ec.fragement.OverviewFragment;
import com.vunke.ec.fragement.TodayStyleFragment;
import com.vunke.ec.view.FlyBorderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuxi on 2017/6/9.
 */
public class WalkIntoYiYangActivity extends BaseFragementActivity implements ViewPager.OnPageChangeListener{
    private ViewPager walkintoyiyang_viewpager;
    private List<Fragment> fragmentList;
    private MyFragmentPagerAdapter fragmentPagerAdapter;

    private OverviewFragment fragment1;
    private HistoricalCultureFragment fragment2;
    private TodayStyleFragment fragment3;
    private FutureDevelopmentFragment fragment4;
    private RelativeLayout walkintoyiyang_overview_rl;
    private RelativeLayout walkintoyiyang_historical_culture_rl;
    private RelativeLayout walkintoyiyang_todays_style_rl;
    private RelativeLayout walkintoyiyang_future_development_rl;
    private TextView walkintoyiyang_text1;
    private TextView walkintoyiyang_text2;
    private TextView walkintoyiyang_text3;
    private TextView walkintoyiyang_text4;

    private FlyBorderView walkintoyiyang_flyborderview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkintoyiyang);
        initView();
        initListener();
        initViewPager();
    }


    private void initView() {
        walkintoyiyang_viewpager = (ViewPager) findViewById(R.id.walkintoyiyang_viewpager);
        walkintoyiyang_flyborderview = (FlyBorderView) findViewById(R.id.walkintoyiyang_flyborderview);

        walkintoyiyang_overview_rl = (RelativeLayout) findViewById(R.id.walkintoyiyang_overview_rl);
        walkintoyiyang_historical_culture_rl = (RelativeLayout) findViewById(R.id.walkintoyiyang_historical_culture_rl);
        walkintoyiyang_todays_style_rl = (RelativeLayout) findViewById(R.id.walkintoyiyang_todays_style_rl);
        walkintoyiyang_future_development_rl = (RelativeLayout) findViewById(R.id.walkintoyiyang_future_development_rl);
        walkintoyiyang_overview_rl.setPressed(true);
        walkintoyiyang_text1 = (TextView) findViewById(R.id.walkintoyiyang_text1);
        walkintoyiyang_text2 = (TextView) findViewById(R.id.walkintoyiyang_text2);
        walkintoyiyang_text3 = (TextView) findViewById(R.id.walkintoyiyang_text3);
        walkintoyiyang_text4 = (TextView) findViewById(R.id.walkintoyiyang_text4);
        walkintoyiyang_text1.setText("益阳概况");
        walkintoyiyang_text2.setText("历史文化");
        walkintoyiyang_text3.setText("今日风采");
        walkintoyiyang_text4.setText("未来发展");
    }

    private void initListener() {
        walkintoyiyang_viewpager.addOnPageChangeListener(this);

    }
    private void initViewPager() {
        fragmentList = new ArrayList<>();
        fragment1 = new OverviewFragment();
        fragment2 = new HistoricalCultureFragment();
        fragment3 = new TodayStyleFragment();
        fragment4 = new FutureDevelopmentFragment();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);
        fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        walkintoyiyang_viewpager.setAdapter(fragmentPagerAdapter);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch(position){
            case 0:
                walkintoyiyang_overview_rl.setPressed(true);
                walkintoyiyang_historical_culture_rl.setPressed(false);
                walkintoyiyang_todays_style_rl.setPressed(false);
                walkintoyiyang_future_development_rl.setPressed(false);
                break;
            case 1:
                walkintoyiyang_overview_rl.setPressed(false);
                walkintoyiyang_historical_culture_rl.setPressed(true);
                walkintoyiyang_todays_style_rl.setPressed(false);
                walkintoyiyang_future_development_rl.setPressed(false);
                break;
            case 2:
                walkintoyiyang_overview_rl.setPressed(false);
                walkintoyiyang_historical_culture_rl.setPressed(false);
                walkintoyiyang_todays_style_rl.setPressed(true);
                walkintoyiyang_future_development_rl.setPressed(false);
                break;
            case 3:
                walkintoyiyang_overview_rl.setPressed(false);
                walkintoyiyang_historical_culture_rl.setPressed(false);
                walkintoyiyang_todays_style_rl.setPressed(false);
                walkintoyiyang_future_development_rl.setPressed(true);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
