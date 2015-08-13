package com.mystudy.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * the class is view of roundview
 * 
 * @author Administrator
 * 
 */
public class RoundView extends View {

	// the constant value is stroke's style
	public static final int STROKE = 0;
	// the constant value is fill's style
	public static final int FILL = 1;
	/**
	 * the roundview's paint
	 */
	private Paint mPaint;

	public Paint getmPaint() {
		return mPaint;
	}

	public void setmPaint(Paint mPaint) {
		this.mPaint = mPaint;
	}

	public int getRoundColor() {
		return roundColor;
	}

	public void setRoundColor(int roundColor) {
		this.roundColor = roundColor;
	}

	public int getRoundProgressColor() {
		return roundProgressColor;
	}

	public void setRoundProgressColor(int roundProgressColor) {
		this.roundProgressColor = roundProgressColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth) {
		this.roundWidth = roundWidth;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public boolean isTextIsDisplayable() {
		return textIsDisplayable;
	}

	public void setTextIsDisplayable(boolean textIsDisplayable) {
		this.textIsDisplayable = textIsDisplayable;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		if (progress >max) {
			this.progress = max;
		} else {
			this.progress = progress;
			postInvalidate();
		}
	}

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * the roundview's color
	 */
	private int roundColor;
	/**
	 * the roundview's progresscolor
	 */
	private int roundProgressColor;
	/**
	 * the roundview's textcolor
	 */
	private int textColor;
	/**
	 * the roundview's textsize
	 */
	private float textSize;
	/**
	 * the roundview's width
	 */
	private float roundWidth;
	/**
	 * the roundview's max progress
	 */
	private int max;
	/**
	 * the roundview's disayable style
	 */
	private boolean textIsDisplayable;
	/**
	 * the roundview's progress
	 */
	private int progress;
	/**
	 * the roundview's center point
	 */
	private int centerX;

	/**
	 * the round view's style
	 */
	private int style;

	/**
	 * the round view's radius
	 */
	private int radius;

	/**
	 * the round inner circle's color
	 */
	private int innerRoundColor;
	/**
	 * the 3 parameter constructor that initial data
	 * 
	 * @param context
	 *            the parameter of context
	 * @param attrs
	 *            the parameter of attributes
	 * @param defStyle
	 *            default style the parameter of default style
	 */
	public RoundView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	/**
	 * initial data
	 * 
	 * @param context
	 *            the parameter of context
	 * @param attrs
	 *            the parameter of attributes
	 */
	private void init(Context context, AttributeSet attrs) {
		mPaint = new Paint();
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.RoundProgressBar);
		roundColor = typedArray.getColor(
				R.styleable.RoundProgressBar_roundColor, Color.RED);
		roundProgressColor = typedArray.getColor(
				R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
		textColor = typedArray.getColor(R.styleable.RoundProgressBar_textColor,
				Color.GREEN);
		textSize = typedArray.getDimension(
				R.styleable.RoundProgressBar_textSize, 15);
		roundWidth = typedArray.getDimension(
				R.styleable.RoundProgressBar_roundWidth, 5);
		max = (int) typedArray.getFloat(R.styleable.RoundProgressBar_max,
				100.0f);
		textIsDisplayable = typedArray.getBoolean(
				R.styleable.RoundProgressBar_textIsDisplayable, true);
		innerRoundColor=typedArray.getColor(R.styleable.RoundProgressBar_innerRoundColor, Color.GRAY);
		typedArray.recycle();
	}

	public RoundView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		DrawCircle(canvas);
		DrawInnerCircle(canvas);
		DrawPercent(canvas);
		DrawArcs(canvas);
		
	}

	/**
	 * draw the view's arcs
	 * 
	 * @param canvas
	 */
	private void DrawArcs(Canvas canvas) {
		mPaint.setShadowLayer(5, 3, 3, 0xffC1FFC1);
		mPaint.setStrokeWidth(roundWidth);
		mPaint.setColor(roundProgressColor);
		RectF oval = new RectF(centerX - radius, centerX - radius, centerX
				+ radius, centerX + radius);
		switch (style) {
		case STROKE:
			mPaint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(oval, 270, 360 * progress / max, false, mPaint);
			break;
		case FILL:
			mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			if (progress != 0)
				canvas.drawArc(oval, 0, 360 * progress / max, true, mPaint); // 根据进度画圆弧
			break;
		}
	}

	/**
	 * draw text's percent
	 * 
	 * @param canvas
	 */
	private void DrawPercent(Canvas canvas) {
		mPaint.setStrokeWidth(0);
		mPaint.setColor(textColor);
		mPaint.setTextSize(textSize);
		mPaint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体
		int percent = (int) (((float) progress / (float) max) * 100);
		float textWidth = mPaint.measureText(percent + "%");
		if (textIsDisplayable && percent != 0 && style == STROKE) {
			canvas.drawText(percent + "%", centerX-textWidth/2 ,
					centerX , mPaint); // 画出进度百分比
		}
	}

	/**
	 * canvas draw circle
	 */
	private void DrawCircle(Canvas canvas) {
		// get the center point
		centerX = getWidth() / 2;
		radius = (int) (centerX - roundWidth / 2);
		mPaint.setColor(roundColor);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(roundWidth);
		canvas.drawCircle(centerX, centerX, radius, mPaint);
		
	}
	
	/**
	 * canvas draw Inner circle
	 */
	private void DrawInnerCircle(Canvas canvas) {
	mPaint.setColor(innerRoundColor);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawCircle(centerX, centerX, radius-roundWidth, mPaint);
		
	}
	
	/**
	 * get your touchable area's angle 
	 */
	private int getAngleABC( Point a, Point b, Point c ) {
		Point ab = new Point(b.x - a.x, b.y - a.y);
		Point cb = new Point(b.x - c.x, b.y - c.y);
	    float dot = (ab.x * cb.x + ab.y * cb.y); // dot product
	    float cross = (ab.x * cb.y - ab.y * cb.x); // cross product
	    float delta = (float) Math.atan2(cross, dot);
	    if((int) Math.toDegrees(delta) < 0) 
	    	return ((int) Math.toDegrees(delta)) + 360;
	    else
	    	return (int) Math.toDegrees(delta);
	}
	
	/**
	 * observe the touchable area's angle  
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			int angle= getAngleABC(new Point(centerX, 0), new Point(centerX, centerX), new Point((int)event.getX(), (int)event.getY()));
		    setProgress((int)(angle/(360/100f)));
			break;

		default:
			break;
		}
		return true;
	}

}
