package com.ling.lottiedemo.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.ling.lottiedemo.R;
import com.matthewtamlin.sliding_intro_screen_library.buttons.IntroButton;
import com.matthewtamlin.sliding_intro_screen_library.core.IntroActivity;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by cuiqiang on 2017/3/6.
 */

public class DemoActivity extends LottieActivity {

    private static final float[] ANIMATION_TIMES = new float[]{
            0f,
            0.3333f,
            0.6666f,
            1f,
            1f
    };
    private LottieAnimationView animationView;

    @Override
    protected Collection<? extends Fragment> generatePages(Bundle savedInstanceState) {
        return new ArrayList<EmptyFragment>() {{
            add(EmptyFragment.newInstance());
            add(EmptyFragment.newInstance());
            add(EmptyFragment.newInstance());
            add(EmptyFragment.newInstance());
        }};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        animationView = (LottieAnimationView) LayoutInflater.from(this).inflate(R.layout.act_demo, getRootView(), false);
        getRootView().addView(animationView, 0);

        addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setAnimationProgress(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected IntroButton.Behaviour generateFinalButtonBehaviour() {
        return new IntroButton.Behaviour() {
            @Override
            public void setActivity(IntroActivity activity) {
                finish();
            }

            @Override
            public IntroActivity getActivity() {
                return null;
            }

            @Override
            public void run() {
            }
        };
    }

    private void setAnimationProgress(int position, float positionOffset) {
        float startProgress = ANIMATION_TIMES[position];
        float endProgress = ANIMATION_TIMES[position + 1];
        Log.d("1111","startProgress/endProgress"+startProgress/endProgress);
        animationView.setProgress(lerp(startProgress, endProgress, positionOffset));

    }

    private float lerp(float startValue, float endValue, float offset) {
        Log.d("1111","offset"+offset);
        float x= startValue + offset * (endValue - startValue);
        Log.d("1111","x"+x);
        return x;
    }

    public static final class EmptyFragment extends Fragment {

        private static EmptyFragment newInstance() {
            return new EmptyFragment();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_empty, container, false);
        }
    }

}
