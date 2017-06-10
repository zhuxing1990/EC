package com.vunke.ec.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.vunke.ec.R;
import com.vunke.ec.view.FlyBorderView;

/**
 * Created by zhuxi on 2017/5/16.
 */
public class OnFouchAnim {
    public static void showAnim(FlyBorderView mv, View v, boolean hasFocus, Context mcontext) {
        if (hasFocus) {
            v.bringToFront();
            mv.bringToFront();
            scaleBig(v, mcontext);
            mv.setVisibility(View.VISIBLE);
            mv.setTvScreen(true);
            mv.setFocusView(v, 1.1f);
        } else {
            scaleSmall(v, mcontext);
            mv.setVisibility(View.INVISIBLE);
        }
    }
    public static void showAnim2( View v, boolean hasFocus, Context mcontext){
        v.bringToFront();
        if (hasFocus){
            scaleBig2(v, mcontext);
        }else{
            scaleSmall2(v, mcontext);
        }
    }
    public static void showAnim3(FlyBorderView mv, View v, boolean hasFocus, Context mcontext){
        if (hasFocus) {
            mv.setVisibility(View.VISIBLE);
            mv.setTvScreen(true);
            mv.setFocusView(v, 1.04f);
            mv.bringToFront();
        } else {
            mv.setVisibility(View.INVISIBLE);
        }
    }
    public static void showAnim4(FlyBorderView mv, View v, boolean hasFocus, Context mcontext){
        if (hasFocus) {
            mv.setVisibility(View.VISIBLE);
            mv.setTvScreen(true);
            mv.setFocusView(v, 1.4f);
            mv.bringToFront();
        } else {
            mv.setVisibility(View.INVISIBLE);
        }
    }
    public static void scaleSmall(View v, Context mcontext) {
        Animation animation = AnimationUtils.loadAnimation(mcontext, R.anim.anim_scale_small);
        v.startAnimation(animation);
    }

    public static void scaleBig(View v, Context mcontext) {
        Animation animation = AnimationUtils.loadAnimation(mcontext, R.anim.anim_scale_big);
        v.startAnimation(animation);
    }
    public static void scaleSmall2(View v, Context mcontext) {
        Animation animation = AnimationUtils.loadAnimation(mcontext, R.anim.anim_scale_small2);
        v.startAnimation(animation);
    }

    public static void scaleBig2(View v, Context mcontext) {
        Animation animation = AnimationUtils.loadAnimation(mcontext, R.anim.anim_scale_big2);
        v.startAnimation(animation);
    }

}
