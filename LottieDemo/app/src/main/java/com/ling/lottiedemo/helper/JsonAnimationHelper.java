package com.ling.lottiedemo.helper;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import com.airbnb.lottie.LottieAnimationView;
import static com.ling.lottiedemo.helper.AnimationListener.ACTION_CANCEL;
import static com.ling.lottiedemo.helper.AnimationListener.ACTION_FINSH;
import static com.ling.lottiedemo.helper.AnimationListener.ACTION_START;

/**
 * Lottie 动画帮助类
 * Created by mhz小志 on 2017/3/30.
 * <p>
 */

public class JsonAnimationHelper {

    private int mPriority = 0;//优先级
    private float currentPosition = 0f;
    private int mFrameRate = 40;
    private int mFrameNumber = 0;
    private LottieAnimationView mLottieAnimationView;

    public JsonAnimationHelper(LottieAnimationView lottieAnimationView) {
        this.mLottieAnimationView = lottieAnimationView;
        mLottieAnimationView.addAnimatorListener(mAnimatorListener);
    }

    public void playAnimationByPosition(String jsonName, float position, boolean isEnd, AnimationListener animationListener) {
        mLottieAnimationView.setAnimation(jsonName);
        if (position < 0) position = 0;
        if (position > 1) position = 1;
        if (isEnd) {
            long duration = (long) ((Math.abs((currentPosition - position)) / 1) * (mLottieAnimationView.getDuration()));
            final ValueAnimator animator = ValueAnimator.ofFloat(currentPosition, position)
                    .setDuration(duration);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float asd = (Float) animator.getAnimatedValue();
                    currentPosition = asd;
                    mLottieAnimationView.setProgress(asd);
                }
            });
            animator.start();
        } else {
            currentPosition = position;
            mLottieAnimationView.setProgress(position);
        }
    }

    private AnimationListener animationListener;

    public boolean playAnimation(String jsonName, int priority, boolean oneShot, final AnimationListener animationListener) {
        this.animationListener = animationListener;
        if (mPriority != 0) {
            if (priority >= mPriority) {
                mPriority = priority;
            } else {
                return false;
            }
        } else {
            if (priority == 0) {
                return false;
            } else {
                mPriority = priority;
            }
        }
        mLottieAnimationView.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long playTime = animation.getCurrentPlayTime();
                int i = (int) (playTime / mFrameRate);
                if (mFrameNumber != i) {
                    Log.d("2345", "            " + playTime);
                    mFrameNumber = i;
                    if (animationListener != null) animationListener.onFrameNumber(mFrameNumber);
                }
            }
        });
        mLottieAnimationView.setAnimation(jsonName);
        mLottieAnimationView.loop(oneShot);
        mLottieAnimationView.playAnimation();
//        mLottieAnimationView.reverseAnimation();// 倒退
//        mLottieAnimationView.cancelAnimation(); //取消
//        mLottieAnimationView.getDuration(); //获取时长
//        mLottieAnimationView.invalidate(); //
//        mLottieAnimationView.pauseAnimation();

        return true;
    }

    Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            mFrameNumber = 0;
            if (animationListener != null)
                animationListener.onEvent(ACTION_START, "animation start");
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mPriority = 0;
            mFrameNumber = 0;
            if (animationListener != null)
                animationListener.onEvent(ACTION_FINSH, "animation finsh");
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            mPriority = 0;
            mFrameNumber = 0;
            if (animationListener != null)
                animationListener.onEvent(ACTION_CANCEL, "animation cancel");
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
}
