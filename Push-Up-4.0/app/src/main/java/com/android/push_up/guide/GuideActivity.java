package com.android.push_up.guide;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.push_up.home.HomeActivity;


public class GuideActivity extends Activity {

    private ViewPager guideViewPager;
    private ImageButton imageButton;
    private LinearLayout guideIndicator;
    private ImageView[] imageViews;
    MyPagerAdapter myPagerAdapter;
    boolean isFirstIn = false;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        sharedPreferences = getSharedPreferences("first_pref",MODE_PRIVATE);
        isFirstIn = sharedPreferences.getBoolean("isFirstIn",true);
        if(isFirstIn){
            guideViewPager = (ViewPager) findViewById(R.id.guide_viewPager);
            imageButton = (ImageButton) findViewById(R.id.btnEnter);
            guideIndicator = (LinearLayout) findViewById(R.id.guide_indicator);
            //初始化
            myPagerAdapter = new MyPagerAdapter(this.getApplicationContext());
            imageViews = new ImageView[myPagerAdapter.getCount()];
            for(int i = 0; i < myPagerAdapter.getCount(); i++){
                imageViews[i] = (ImageView) guideIndicator.getChildAt(i);
                imageViews[i].setEnabled(false);
            }
            imageViews[0].setEnabled(true);
            //加载
            guideViewPager.setAdapter(myPagerAdapter);
            guideViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for(int i = 0; i < myPagerAdapter.getCount(); i++){
                        if(i == position){
                            imageViews[i].setEnabled(true);
                        }
                        else{
                            imageViews[i].setEnabled(false);
                        }
                        if(position == myPagerAdapter.getCount()-1){
                            imageButton.setVisibility(View.VISIBLE);//在最后一页显示
                        }
                        else{
                            imageButton.setVisibility(View.GONE);//隐藏
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(GuideActivity.this, HomeActivity.class));
                    GuideActivity.this.finish();
                    sharedPreferences = getSharedPreferences("first_pref",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isFirstIn",false);
                    editor.commit();
                }
            });
        }else{
            startActivity(new Intent(GuideActivity.this, HomeActivity.class));
            GuideActivity.this.finish();
        }

    }

}
