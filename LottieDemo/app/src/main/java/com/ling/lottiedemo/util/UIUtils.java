package com.ling.lottiedemo.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.ling.lottiedemo.AppApplication;


/**
 * Created by acer on 2016/11/6.
 */

public class UIUtils {

    public static Context getContext() {return AppApplication.getContext();}

    public static Handler getHandler() {
        return AppApplication.getHandler();
    }

    public static int getMainThreadId() {
        return AppApplication.getMainThreadId();
    }

    /*****************************************************************************/

    /**
     * 获取字符串
     */
    public static String getString(int resId) {
        return getContext().getResources().getString(resId);
    }

    /**
     * 获取字符串数组
     */
    public static String[] getStringArray(int resId) {
        return getContext().getResources().getStringArray(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getContext().getResources().getColor(resId);
    }

    /**
     * 颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getContext().getResources().getColorStateList(resId);
    }

    /**
     * 获取尺寸(单位像素)
     */
    public static int getDimen(int resId) {
        return getContext().getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {

        return getContext().getResources().getDrawable(resId);
    }

    /*****************************************************************************/

    /**
     * 获取density
     */
    public static float getDensity() {
        return getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * dp转px
     */
    public static int dp2px(float dp) {
        return (int) (dp * getDensity() + 0.5f);
    }

    /**
     * px转dp
     */
    public static float px2dp(float px) {
        return px / getDensity();
    }

    public static float getScreenWidth(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static float getScreenHeight(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /*****************************************************************************/

    /**
     *填充view
     */
    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    /*****************************************************************************/

    /**
     * 判断当前操作是否在主线程
     */
    public static boolean isRunOnUIThread() {
        return getMainThreadId() == android.os.Process.myTid();
    }

    /**
     * 把任务运行在主线程
     */
    public static void runOnUIThread(Runnable runnable) {
        if(isRunOnUIThread()){
            runnable.run();
        }else{
            getHandler().post(runnable);//TODO post把任务运行在主线程
        }
    }
}
