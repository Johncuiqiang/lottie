package com.ling.lottiedemo.ui;


import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.ling.lottiedemo.R;
import com.ling.lottiedemo.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by cuiqiang on 2017/3/6.
 */

public class OtherActivity extends AppCompatActivity {

    private Button mBtnOne;
    private Button mBtnTwo;
    private Button mBtnThree;
    private Button mBtnFour;
    private ValueAnimator animator;
    private LottieAnimationView animationView;
    private Button mBtnFive;
    private Button mBtnSix;
    private Button mBtnSeven;
    private Button mBtnEight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_other);
        initView();
        initData();
        setClick();
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                File file = FileUtils.copyAssets("11.zip",OtherActivity.this);
                if(file != null && file.exists()) {
                    try {
                        String folder = OtherActivity.this.getCacheDir() + "/lottie";
                        FileUtils.unzipFile(file, folder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void initView() {
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        mBtnOne = (Button) findViewById(R.id.btn_one);
        mBtnTwo = (Button) findViewById(R.id.btn_two);
        mBtnThree = (Button) findViewById(R.id.btn_three);
        mBtnFour = (Button) findViewById(R.id.btn_four);
        mBtnFive = (Button) findViewById(R.id.btn_five);
        mBtnSix = (Button) findViewById(R.id.btn_six);
        mBtnSeven = (Button) findViewById(R.id.btn_seven);
        mBtnEight = (Button) findViewById(R.id.btn_eight);

    }

    private void play(String name){
        animationView.cancelAnimation();
        animationView.setAnimation(name);
        animationView.loop(false);
        animator = ValueAnimator.ofFloat(0f,0.3f,0.5f,1f);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationView.setProgress((Float) animation.getAnimatedValue());
            }
        });
        animationView.playAnimation();

    }


    private void setClick() {
        mBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        mBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play("08.json");
            }
        });
        mBtnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play("05.json");
            }
        });
        mBtnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play("06.json");
            }
        });
        mBtnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play("lottiefiles.com - ATM.json");
            }
        });
        mBtnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play("Spider Loader.json");
            }
        });

        mBtnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play("lottiefiles.com - Im Thirsty.json");
            }
        });

        mBtnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play("lottiefiles.com - Mail Sent.json");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        animationView.cancelAnimation();
    }

    private void loadData() {
        File lottieDir = new File(getCacheDir(), "lottie");
        File unzipFolder = new File(lottieDir,"11");
        final String imageFolder = unzipFolder.getAbsolutePath() + "/images/";
        animationView.setImageAssetsFolder(imageFolder);
        final File jsonFile = new File(unzipFolder, "11.json");
        if(jsonFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(jsonFile);
                LottieComposition.Factory.fromInputStream(OtherActivity.this,fis, new OnCompositionLoadedListener() {
                    @Override
                    public void onCompositionLoaded(LottieComposition composition) {
                        setComposition(composition);
                    }
                });
                animationView.setImageAssetDelegate(new ImageAssetDelegate() {
                    @Override
                    public Bitmap fetchBitmap(LottieImageAsset lottieImageAsset) {
                        String path = imageFolder + lottieImageAsset.getFileName();
                        BitmapFactory.Options opts = new BitmapFactory.Options();
                        opts.inScaled = true;
                        opts.inDensity = 160;
                        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
                        return bitmap;
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void setComposition(LottieComposition composition){
        animationView.setProgress(0);
        animationView.loop(true);
        animationView.setComposition(composition);
        animationView.playAnimation();
    }
}
