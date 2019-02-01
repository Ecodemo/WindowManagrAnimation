package com.mycompany.myapp;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.util.AttributeSet;
import android.view.animation.CycleInterpolator;

public class FloatNormalView extends LinearLayout {
	long mDuration = 800;
	private Interpolator mInterpolator;
	private WindowManager windowManager;
	private int viewWidth;
	private int viewHeight;
	private WindowManager.LayoutParams mParams;
	
    public FloatNormalView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_normal_view, this);
        View view = findViewById(R.id.ll_float_normal);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        initLayoutParams();
		mInterpolator = new BounceInterpolator();
		WindowAnimator.initTouchEvent(getContext(),WindowAnimator.MoveType.TYPE_SLIDE,windowManager,mParams,this,mInterpolator);
    }

    /**
     * 初始化参数
     */
    private void initLayoutParams() {
        //屏幕宽高
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        mParams = new WindowManager.LayoutParams();
        //总是出现在应用程序窗口之上。
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按,不设置这个flag的话，home页的划屏会有问题
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
			| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        //悬浮窗默认显示的位置
        mParams.gravity = Gravity.START | Gravity.TOP;
        //指定位置
        mParams.x = screenWidth - viewWidth * 2;
        mParams.y = screenHeight / 2 + viewHeight * 2;
        //悬浮窗的宽高
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSPARENT;
        windowManager.addView(this, mParams);
    }

    /**
     * 传入参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }
}
