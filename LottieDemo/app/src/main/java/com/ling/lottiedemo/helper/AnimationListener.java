package com.ling.lottiedemo.helper;

/**
 * Created by mhz小志 on 2017/3/30.
 */

public abstract class AnimationListener {

    public static final int ACTION_FINSH = 0x1001;
    public static final int ACTION_START = 0x1002;
    public static final int ACTION_CANCEL = 0x1003;

    public static final int ACTION_TYPE_EYE = 0x2001;
    public static final int ACTION_TYPE_SOUND = 0x2002;
    public static final int ACTION_TYPE_WALK = 0x2003;
    public static final int ACTION_TYPE_BODY = 0x2004;

    public abstract void onEvent(int type, String des);

    public void onFrameNumber(int frameNumber) {
    }
}
