package com.ling.lottiedemo.ui;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.ling.lottiedemo.R;

public class MainActivity extends AppCompatActivity {

    private Button mBtnPlay;
    private Button mBtnPlayFast;
    private Button mBtnPlayDemo;
    private Button mBtnPlayTest;
    private ValueAnimator animator;
    private LottieAnimationView animationView;
    private Button mBtnPlayOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPlayFast = (Button) findViewById(R.id.btn_play_fast);
        mBtnPlayDemo = (Button) findViewById(R.id.btn_play_demo);
        mBtnPlayTest = (Button) findViewById(R.id.btn_play_test);
        mBtnPlayOther = (Button) findViewById(R.id.btn_play_other);
    }

    private void initData() {
        //0到0.3f用了1000ms
        //0.5f到1f用了1000ms相当于最后一段有加速度
        animationView.setAnimation("Walkthrough.json");
        animationView.loop(false);
        animator = ValueAnimator.ofFloat(0f,0.3f,0.5f,1f);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationView.setProgress((Float) animation.getAnimatedValue());
            }
        });
        mBtnPlay.setOnClickListener(playListener);
        mBtnPlayFast.setOnClickListener(playFastListener);
        mBtnPlayDemo.setOnClickListener(demoListener);
        mBtnPlayTest.setOnClickListener(testListener);
        mBtnPlayOther.setOnClickListener(otherListener);
    }

    /**
     * 播放json动画
     */
    private View.OnClickListener playListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            animationView.playAnimation();
        }
    };

    /**
     * 加速度
     */
    private View.OnClickListener playFastListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            animator.start();
        }
    };

    /**
     * viewpager界面下的交互demo
     */
    private View.OnClickListener demoListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, DemoActivity.class));
        }
    };

    /**
     * 跳转交互界面
     */
    private View.OnClickListener testListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, TestActivity.class));
        }
    };


    private View.OnClickListener otherListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, OtherActivity.class));
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        animationView.cancelAnimation();
    }

}
