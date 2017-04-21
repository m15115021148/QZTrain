package com.sitemap.qingzangtrain.view;


import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

/**
 * 自定义进度条（水平方向）
 * Description:
 * @author chenhao
 * @date   2016-11-18
 */
public class HorizontalProgressView extends View{
	/**
	 * 画笔对象
	 */
	private Paint mPaint;

	/**
	 * 滚动圆半径
	 */
	private float mRadius ;

	/**
	 * 滚动开始位置
	 */
	private float startX;

	/**
	 * 滚动结束位置
	 */
	private float endX;

	/**
	 * 控件所处位置y
	 */
	private float mY;

	/**
	 * 控件滚动时，所处位置x坐标
	 */
	private float mX1,mX2,mX3;


	/**
	 * 属性动画对象
	 */
	private ValueAnimator animator1,animator2,animator3;
	/**
	 * 属性动画执行时间
	 */
	private long ValueTime = 2000;

	/**
	 * 动画是否开启
	 */
	private boolean isStart=false;

	public HorizontalProgressView(Context context) {
		this(context, null);
	}

	public HorizontalProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.WHITE);// 画笔颜色
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		setmRadius(w/35);
		startX=3*getmRadius();
		endX=w-3*getmRadius();
		mX1=startX;
		mX2=startX;
		mX3=startX;
		mY=h/2;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
							int bottom) {
		if(changed==false&&isStart==true){
			start();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setAlpha(getmAlpha(mX1));
		canvas.drawCircle(mX1, mY, getmRadius(), mPaint);
		mPaint.setAlpha(getmAlpha(mX2));
		canvas.drawCircle(mX2, mY, getmRadius(), mPaint);
		mPaint.setAlpha(getmAlpha(mX3));
		canvas.drawCircle(mX3, mY, getmRadius(), mPaint);
	}

	/**
	 * 开始动画
	 */
	public void start() {
		if(isStart==false){
			isStart=true;
			return;
		}
		animator1 = ValueAnimator.ofFloat(startX, endX);//返回值在这两个值之间，不包括两边
		animator1.setDuration(ValueTime);
		animator1.setRepeatCount(Animation.INFINITE);// 无限循环
		animator1.setInterpolator(new AccelerateDecelerateInterpolator());//先加速后减速
		animator1.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value =  (Float) animation.getAnimatedValue();
				mX1=value;
				invalidate();
			}
		});

		animator2 = ValueAnimator.ofFloat(startX, endX);//返回值在这两个值之间，不包括两边
		animator2.setDuration(ValueTime);
		animator2.setStartDelay(160);
		animator2.setRepeatCount(Animation.INFINITE);// 无限循环
		animator2.setInterpolator(new AccelerateDecelerateInterpolator());//先加速后减速
		animator2.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value =  (Float) animation.getAnimatedValue();
				mX2=value;
			}
		});

		animator3 = ValueAnimator.ofFloat(startX, endX);//返回值在这两个值之间，不包括两边
		animator3.setDuration(ValueTime);
		animator3.setStartDelay(320);
		animator3.setRepeatCount(Animation.INFINITE);// 无限循环
		animator3.setInterpolator(new AccelerateDecelerateInterpolator());//先加速后减速
		animator3.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value =  (Float) animation.getAnimatedValue();
				mX3=value;
			}
		});

//		因为动画是异步，所以基本上是同一时间开启
		animator1.start();
		animator2.start();
		animator3.start();

	}

	/**
	 * 关闭动画
	 */
	public void stop() {
		if (animator1.isRunning()) {
			animator1.cancel();
		}
		if (animator2.isRunning()) {
			animator2.cancel();
		}
		if (animator3.isRunning()) {
			animator3.cancel();
		}

		if(isStart==true){
			isStart=false;
		}

	}


	/**
	 * 根据当前位置获取透明度值
	 * @return 透明度,取值范围为0~255，数值越小越透明
	 */
	private int getmAlpha(float currentX) {
		return (int)((1-currentX/endX)*255)+20;
	}


	public float getmRadius() {
		return mRadius;
	}

	/**
	 * 设置滚动球的半径大小
	 * @param mRadius
	 */
	public void setmRadius(float mRadius) {
		this.mRadius = mRadius;
	}

}
