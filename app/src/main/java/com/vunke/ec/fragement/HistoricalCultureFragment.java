package com.vunke.ec.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vunke.ec.R;

/**
 * 历史文化
 * Created by zhuxi on 2017/6/9.
 */
public class HistoricalCultureFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historicalculture,null);
        return view;

    }
}
