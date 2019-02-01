package com.mycompany.myapp;
import android.view.View;
import android.view.WindowManager;
import android.graphics.Point;
import android.view.MotionEvent;
import android.animation.PropertyValuesHolder;
import android.animation.ObjectAnimator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.animation.ValueAnimator;
import android.animation.TimeInterpolator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Animator;
import android.view.Display;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import android.content.Context;
import android.widget.LinearLayout;
import android.view.animation.Interpolator;
import android.support.annotation.IntDef;

public class WindowAnimator {
	private static ValueAnimator mAnimator;
    private static TimeInterpolator mDecelerateInterpolator;
    private static float downX;
    private static float downY;
    private static float upX;
    private static float upY;
    private static boolean mClick = false;
    private static int mSlop;
	private static Interpolator mInterpolator;
	private static long mDuration = 800;
	private static int X;
	private static int Y;
	/**
	 * 设置移动贴边及动画
	 **/
	public static void initTouchEvent(final Context context, @MoveType.MOVE_TYPE int mMoveType, final WindowManager windowManager,final WindowManager.LayoutParams mParams, final View view,Interpolator interpolator) {
		X = mParams.x;
		Y = mParams.y;
		mInterpolator = interpolator;
        switch (mMoveType) {
            case MoveType.TYPE_INACTIVE:
                break;
            default:
                view.setOnTouchListener(new View.OnTouchListener() {
						float lastX, lastY, changeX, changeY;
						int newX, newY;
						Point point = new Point();
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							switch (event.getAction()) {
								case MotionEvent.ACTION_DOWN:
									downX = event.getRawX();
									downY = event.getRawY();
									lastX = event.getRawX();
									lastY = event.getRawY();
									cancelAnimator();
									break;
								case MotionEvent.ACTION_MOVE:
									changeX = event.getRawX() - lastX;
									changeY = event.getRawY() - lastY;
									newX = (int) (mParams.x + changeX);
									newY = (int) (mParams.y + changeY);
									mParams.x = newX;
									mParams.y = newY;
									windowManager.updateViewLayout(view,mParams);
									lastX = event.getRawX();
									lastY = event.getRawY();
									break;
								case MotionEvent.ACTION_UP:
									upX = event.getRawX();
									upY = event.getRawY();
									mClick = (Math.abs(upX - downX) > mSlop) || (Math.abs(upY - downY) > mSlop);
									switch (mMoveType) {
										case MoveType.TYPE_SLIDE:
											int startX = mParams.x;
											windowManager.getDefaultDisplay().getSize(point);
											int endX = (startX * 2 + v.getWidth() >	point.x ? point.x - v.getWidth() - 0 : 0);
											mAnimator = ObjectAnimator.ofInt(startX, endX);
											mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
													@Override
													public void onAnimationUpdate(ValueAnimator animation) {
														int x = (int) animation.getAnimatedValue();
														mParams.x = x;
														windowManager.updateViewLayout(view, mParams);
													}
												});
											startAnimator();
											break;
										case MoveType.TYPE_BACK:
											PropertyValuesHolder pvhX = PropertyValuesHolder.ofInt("x", mParams.x,X);
											PropertyValuesHolder pvhY = PropertyValuesHolder.ofInt("y", mParams.y,Y);
											mAnimator = ObjectAnimator.ofPropertyValuesHolder(pvhX, pvhY);
											mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
													@Override
													public void onAnimationUpdate(ValueAnimator animation) {
														int x = (int) animation.getAnimatedValue("x");
														int y = (int) animation.getAnimatedValue("y");
														mParams.x = x;
														mParams.y = y;
														windowManager.updateViewLayout(view, mParams);
													}
												});
											startAnimator();
											break;
										default:
											break;
									}
									break;
								default:
									break;
							}
							return mClick;
						}
					});
        }
    }


    private static void startAnimator() {
        if (mInterpolator == null) {
            if (mDecelerateInterpolator == null) {
                mDecelerateInterpolator = new DecelerateInterpolator();
            }
            mInterpolator = (Interpolator) mDecelerateInterpolator;
        }
        mAnimator.setInterpolator(mInterpolator);
        mAnimator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mAnimator.removeAllUpdateListeners();
					mAnimator.removeAllListeners();
					mAnimator = null;
				}
			});
        mAnimator.setDuration(mDuration).start();
    }

    private static void cancelAnimator() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

	public static class MoveType {
		public static final int TYPE_INACTIVE = 0xAAAAAAAA;
		public static final int TYPE_SLIDE = 0xBBBBBBBB;
		public static final int TYPE_BACK = 0xCCCCCCCC;
		@IntDef({TYPE_INACTIVE, TYPE_SLIDE, TYPE_BACK})
		@Retention(RetentionPolicy.SOURCE)
		@interface MOVE_TYPE {
		}
	}
}
