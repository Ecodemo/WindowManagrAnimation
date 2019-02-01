package com.mycompany.myapp;
import android.view.WindowManager;
import android.content.Context;

public class MyWindowManager {

    private FloatNormalView normalView;
    private FloatControlView controlView;
    private static MyWindowManager instance;
    private WindowManager windowManager;
    private MyWindowManager() {
    }

    public static MyWindowManager getInstance() {
        if (instance == null)
            instance = new MyWindowManager();
        return instance;
    }

    /**
     * 判断小悬浮窗是否存在
     *
     * @return
     */
    public boolean isNormalViewExists() {
        return normalView != null;
    }

    /**
     * 判断播放器这个大悬浮窗是否存在
     *
     * @return
     */
    public boolean isControlViewExists() {
        return controlView != null;
    }

    /**
     * 创建小型悬浮窗
     */
    public void createNormalView(Context context) {
        if (normalView == null) {
            normalView = new FloatNormalView(context);
        }
    }


    /**
     * 移除悬浮窗
     *
     * @param context
     */
    public void removeNormalView(Context context) {
        if (normalView != null) {
            windowManager.removeView(normalView);
            normalView = null;
        }
    }

    /**
     * 创建小型悬浮窗
     */
    public void createControlView(Context context) {
        if (controlView == null)
            controlView = new FloatControlView(context);
    }

    /**
     * 移除悬浮窗
     *
     * @param context
     */
    public void removeControlView(Context context) {
        if (controlView != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.removeView(controlView);
            controlView = null;
        }
    }
}
