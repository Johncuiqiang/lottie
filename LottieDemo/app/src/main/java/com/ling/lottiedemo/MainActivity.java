package com.ling.lottiedemo;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private Button mBtnPlay;
    private Button mBtnPlay2;
    private Button mBtnPlay3;
    private ValueAnimator animator;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setAnimation("Walkthrough.json");
        animationView.loop(false);
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPlay2 = (Button) findViewById(R.id.btn_play2);
        mBtnPlay3 = (Button) findViewById(R.id.btn_play3);
    }

    private void initData() {
        //0到0.3f用了1000ms
        //0.5f到1f用了1000ms相当于最后一段有加速度
        animator = ValueAnimator.ofFloat(0f,0.3f,0.5f,1f);
        animator.setDuration(3000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationView.setProgress((Float) animation.getAnimatedValue());
            }
        });

        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationView.playAnimation();
            }
        });
        mBtnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.start();
            }
        });

        mBtnPlay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TestAct.class));
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        animationView.cancelAnimation();
    }

}
