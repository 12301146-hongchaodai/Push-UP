package com.android.push_up.gif;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.android.push_up.application.SysApplication;
import com.android.push_up.guide.R;
import com.ant.liao.GifView;

public class GifActivity extends ActionBarActivity implements GestureDetector.OnGestureListener{

    private GestureDetector gestureDetector = null;
    private ViewFlipper viewFlipper = null;
    private int[] layoutId = {R.layout.gif1,R.layout.gif2,R.layout.gif3,R.layout.gif4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        SysApplication.getInstance().addActivity(this);
        gestureDetector = new GestureDetector(this);
        viewFlipper = (ViewFlipper) findViewById(R.id.gifViewFlipper);

        //加载gif图
        View gifView1 = LayoutInflater.from(this).inflate(layoutId[0],null);
        GifView gifView = (GifView) gifView1.findViewById(R.id.gif1);
        gifView.setGifImage(R.drawable.gif1);
        gifView.setGifImageType(GifView.GifImageType.COVER);

        View gifView2 = LayoutInflater.from(this).inflate(layoutId[1],null);
        gifView = (GifView) gifView2.findViewById(R.id.gif2);
        gifView.setGifImage(R.drawable.gif2);
        gifView.setGifImageType(GifView.GifImageType.COVER);

        View gifView3 = LayoutInflater.from(this).inflate(layoutId[2],null);
        gifView = (GifView) gifView3.findViewById(R.id.gif3);
        gifView.setGifImage(R.drawable.gif3);
        gifView.setGifImageType(GifView.GifImageType.COVER);

        View gifView4 = LayoutInflater.from(this).inflate(layoutId[3],null);
        gifView = (GifView) gifView4.findViewById(R.id.gif4);
        gifView.setGifImage(R.drawable.gif4);
        gifView.setGifImageType(GifView.GifImageType.COVER);

        viewFlipper.addView(gifView1,new ViewFlipper.LayoutParams(ViewFlipper.LayoutParams.MATCH_PARENT, ViewFlipper.LayoutParams.MATCH_PARENT));
        viewFlipper.addView(gifView2,new ViewFlipper.LayoutParams(ViewFlipper.LayoutParams.MATCH_PARENT, ViewFlipper.LayoutParams.MATCH_PARENT));
        viewFlipper.addView(gifView3,new ViewFlipper.LayoutParams(ViewFlipper.LayoutParams.MATCH_PARENT, ViewFlipper.LayoutParams.MATCH_PARENT));
        viewFlipper.addView(gifView4,new ViewFlipper.LayoutParams(ViewFlipper.LayoutParams.MATCH_PARENT, ViewFlipper.LayoutParams.MATCH_PARENT));

    }



    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //滑动屏幕切换页面
        if(e1.getX()-e2.getX() > 100){
            if(viewFlipper.getDisplayedChild()==layoutId.length-1){//最后一个layout
                viewFlipper.stopFlipping();
                return false;
            }
            else{
                this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
                this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_in));
                this.viewFlipper.showNext();
                return true;
            }
        }
        else if(e1.getX()-e2.getX() < -100){
            if(viewFlipper.getDisplayedChild()==0){//最后一个layout
                viewFlipper.stopFlipping();
                return false;
            }
            else{
                this.viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_out));
                this.viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_in));
                this.viewFlipper.showPrevious();
            }
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

}
