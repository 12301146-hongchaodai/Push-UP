package com.android.push_up.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.push_up.application.SysApplication;
import com.android.push_up.count.DietPlanActivity;
import com.android.push_up.count.MyPlanActivity;
import com.android.push_up.count.PlanCountActivity;
import com.android.push_up.guide.R;
import com.android.push_up.share.ShareActivity;


public class MeFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View meView = inflater.inflate(R.layout.fragment_me,container,false);
        sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);

        meView.findViewById(R.id.tvMakePlan).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView textView = (TextView) v;
                if(event.getAction() == MotionEvent.ACTION_DOWN){//按下事件
                    textView.setBackgroundColor(0x10000000);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    meView.findViewById(R.id.tvMakePlan).setBackgroundColor(0xfff);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    textView.setBackgroundColor(0xfff);
                    startActivity(new Intent(getActivity(), PlanCountActivity.class));
                    return false;
                }
                return false;
            }
        });

        meView.findViewById(R.id.tvExercisePlan).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView textView = (TextView) v;
                if(event.getAction() == MotionEvent.ACTION_DOWN){//按下事件
                    textView.setBackgroundColor(0x10000000);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    meView.findViewById(R.id.tvExercisePlan).setBackgroundColor(0xfff);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    textView.setBackgroundColor(0xfff);
                    startActivity(new Intent(getActivity(), MyPlanActivity.class));
                    return false;
                }
                return false;
            }
        });

        meView.findViewById(R.id.tvDietPlan).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView textView = (TextView) v;
                if(event.getAction() == MotionEvent.ACTION_DOWN){//按下事件
                    textView.setBackgroundColor(0x10000000);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    meView.findViewById(R.id.tvDietPlan).setBackgroundColor(0xfff);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    textView.setBackgroundColor(0xfff);
                    startActivity(new Intent(getActivity(), DietPlanActivity.class));
                    return false;
                }
                return false;
            }
        });

        meView.findViewById(R.id.tvMatter).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView textView = (TextView) v;
                if(event.getAction() == MotionEvent.ACTION_DOWN){//按下事件
                    textView.setBackgroundColor(0x10000000);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    meView.findViewById(R.id.tvMatter).setBackgroundColor(0xfff);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    textView.setBackgroundColor(0xfff);
                    startActivity(new Intent(getActivity(),MatterActivity.class));
                    return false;
                }
                return false;
            }
        });

        meView.findViewById(R.id.tvTakePhotoAndShare).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView textView = (TextView) v;
                if(event.getAction() == MotionEvent.ACTION_DOWN){//按下事件
                    textView.setBackgroundColor(0x10000000);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    meView.findViewById(R.id.tvTakePhotoAndShare).setBackgroundColor(0xfff);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    textView.setBackgroundColor(0xfff);
                    startActivity(new Intent(getActivity(), ShareActivity.class));
                    return false;
                }
                return false;
            }
        });

        meView.findViewById(R.id.tvLogout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView textView = (TextView) v;
                if(event.getAction() == MotionEvent.ACTION_DOWN){//按下事件
                    textView.setBackgroundColor(0x10000000);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_CANCEL){
                    meView.findViewById(R.id.tvLogout).setBackgroundColor(0xfff);
                    return false;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    textView.setBackgroundColor(0xfff);
                    //点击退出当前应用
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loginState",false);
                    editor.commit();
                    sharedPreferences = getActivity().getSharedPreferences("first_pref",Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("isFirstIn",true);
                    editor.commit();
                    SysApplication.getInstance().exit();
                    return false;
                }
                return false;
            }
        });

        return meView;
    }
}