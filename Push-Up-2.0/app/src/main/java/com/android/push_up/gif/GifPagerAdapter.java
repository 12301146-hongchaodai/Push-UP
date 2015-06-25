package com.android.push_up.gif;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 周宇环 on 2015/5/26.
 */
public class GifPagerAdapter extends PagerAdapter {

    private List<View> listGifPager;
    Context context;
    public GifPagerAdapter(Context context){
        this.context = context;
        listGifPager = new ArrayList<View>();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
