package com.android.push_up.guide;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 周宇环 on 2015/5/21.
 */
public class MyPagerAdapter extends PagerAdapter{
    private List<View> listViewPager;
    Context context;

    public MyPagerAdapter(Context context){
        this.context = context;
        View viewPager1 = LayoutInflater.from(context).inflate(R.layout.guide1,null);
        View viewPager2 = LayoutInflater.from(context).inflate(R.layout.guide2,null);
        View viewPager3 = LayoutInflater.from(context).inflate(R.layout.guide3,null);
        View viewPager4 = LayoutInflater.from(context).inflate(R.layout.guide4,null);
        listViewPager = new ArrayList<View>();
        listViewPager.add(viewPager1);
        listViewPager.add(viewPager2);
        listViewPager.add(viewPager3);
        listViewPager.add(viewPager4);
    }

    @Override
    public int getCount() {
        return listViewPager.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(listViewPager.get(position));
        return listViewPager.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(listViewPager.get(position));
    }
}
