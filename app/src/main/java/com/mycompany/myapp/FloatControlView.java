package com.mycompany.myapp;
import android.widget.LinearLayout;
import android.content.Context;
import android.view.WindowManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Gravity;
import android.graphics.PixelFormat;

public class FloatControlView extends LinearLayout
{
    /**
     * 用于更新悬浮窗的位置
     */
    private WindowManager windowManager;
    /**
     * 悬浮窗的参数
     */
    private WindowManager.LayoutParams mParams;
	public FloatControlView(Context context)
	{
		super(context);
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater.from(context).inflate(R.layout.float_control_view, this);
        initLayoutParams();
	}
	
    /**
     * 初始化参数
     */
    private void initLayoutParams() {
        mParams = new WindowManager.LayoutParams();
        //总是出现在应用程序窗口之上。
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按,不设置这个flag的话，home页的划屏会有问题
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
			| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        //悬浮窗默认显示的位置
        mParams.gravity = Gravity.CENTER;
        //指定位置
        mParams.x = 0;
        mParams.y = 0;
        //悬浮窗的宽高
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSPARENT;
        windowManager.addView(this, mParams);
    }
}
