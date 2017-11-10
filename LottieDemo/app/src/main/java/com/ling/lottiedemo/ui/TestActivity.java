package com.ling.lottiedemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.ling.lottiedemo.helper.AnimationListener;
import com.ling.lottiedemo.helper.JsonAnimationHelper;
import com.ling.lottiedemo.R;
import com.ling.lottiedemo.util.UIUtils;

/**
 * Created by cuiqiang on 2017/3/27.
 */

public class TestActivity extends AppCompatActivity {

    private static final float[] ANIMATION_TIMES = new float[]{0f, 1f};
    public static final String ANIM1 = "Walkthrough.json";
    public static final String ANIM2 = "LottieLogo1.json";
    public static final String ANIM3 = "9squares-AlBoardman.json";

    private LottieAnimationView animationView;
    private float mScreenWidth;
    private float mScreenHeight;
    private JsonAnimationHelper mAnimationUtil;
    private int downX;
    private int downY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test);
        initView();
        initData();
    }

    private void initView() {
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        findViewById(R.id.btn_test).setOnClickListener(mTestClickListener);
        findViewById(R.id.btn_test1).setOnClickListener(mTestClickListener1);
    }

    private void initData() {
        mScreenWidth = UIUtils.getScreenWidth();
        mScreenHeight = UIUtils.getScreenHeight();
        mAnimationUtil = new JsonAnimationHelper(animationView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                animationView.setAnimation(ANIM1);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                //判断手滑动方向   横向
                if (Math.abs(downX - moveX) >= Math.abs(downY - moveY)) {
                    if (downX - moveX > 0) {
                        //左滑
                        float position = moveX / mScreenWidth;
                        // setTouchEventLeftX(position);
                    } else {
                        //右滑
                        float position = (moveX - downX) / (mScreenWidth / 3);
                        setTouchEventLeftX(Math.abs(position));
                    }
                } else if (Math.abs(downY - moveY) <= Math.abs(downY - moveY)) {
                    setTouchEventOrientationY();//判断手滑动方向    竖向
                }
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                if ((upX - downX) > (mScreenWidth / 6)) {
                    setTouchEventEnd(1);
                } else if ((upX - downX) > 0) {
                    setTouchEventEnd(0);
                }
                break;
        }
        return true;
    }

    private void setTouchEventEnd(float position) {
        mAnimationUtil.playAnimationByPosition(ANIM1, position, true, new AnimationListener() {
            @Override
            public void onEvent(int type, String des) {

            }
        });
    }

    private void setTouchEventLeftX(float position) {
        Log.d("1111", "position" + position);
        if (position >= 1) position = 1;
        mAnimationUtil.playAnimationByPosition(ANIM1, position, false, new AnimationListener() {
            @Override
            public void onEvent(int type, String des) {

            }
        });
    }

    private void setTouchEventRightX() {

    }

    private void setTouchEventOrientationY() {

    }

    /**
     * 设置动画播放的位置
     *
     * @param position       页面位置
     * @param positionOffset 页面偏移量 0~1f
     */
    private void setAnimationProgress(int position, float positionOffset) {
        float startProgress = ANIMATION_TIMES[position];
        float endProgress = ANIMATION_TIMES[position + 1];
        Log.d("1111", "startProgress/endProgress" + startProgress / endProgress);
        animationView.setProgress(lerp(startProgress, endProgress, positionOffset));
    }

    private float lerp(float startValue, float endValue, float f) {
        Log.d("1111", "f" + f);
        float x = startValue + f * (endValue - startValue);
        Log.d("1111", "x" + x);
        return x;
    }

    View.OnClickListener mTestClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAnimationUtil.playAnimation(ANIM2, 3, false, new AnimationListener() {
                @Override
                public void onEvent(int type, String des) {
                    Log.d("2345", des);
                }

                @Override
                public void onFrameNumber(int frameNumber) {
                    Log.d("2345", "   " + frameNumber);
                }
            });
        }
    };
    View.OnClickListener mTestClickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAnimationUtil.playAnimation(ANIM3, 3, false, new AnimationListener() {
                @Override
                public void onEvent(int type, String des) {
                    Log.d("2345", des);
                }

                @Override
                public void onFrameNumber(int frameNumber) {
                    Log.d("2345", "   " + frameNumber);
                }
            });
        }
    };
}
