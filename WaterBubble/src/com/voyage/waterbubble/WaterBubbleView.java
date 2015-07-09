package com.voyage.waterbubble;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class WaterBubbleView extends View {

	/**
	 * 初始半径
	 */
	private final int RADIUS = 60;
	/**
	 * 初始透明度
	 */
	private final int ALPHA = 255;
	private Canvas canvas;
	private Paint paint;
	/**
	 * 颜色数组
	 */
	private final int colors[] = new int[] { Color.RED, Color.BLUE,
			Color.GREEN, Color.YELLOW, Color.BLACK, Color.CYAN, Color.GRAY,
			Color.GRAY, Color.MAGENTA };
	/**
	 * 圆心X坐标
	 */
	private int cx;
	/**
	 * 圆心Y坐标
	 */
	private int cy;
	/**
	 * 圆的半径
	 */
	private int radius = RADIUS;
	/**
	 * 运行状态
	 */
	private boolean isRunning = false;
	/**
	 * 透明度
	 */
	private int alpha = ALPHA;
	/**
	 * 实现动画效果
	 */
	private Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			alpha -= 8;
			paint.setAlpha(alpha);
			radius += 5;
			if (alpha <= 10) {
				isRunning = false;
				radius = RADIUS;
				alpha = ALPHA;
			}
			invalidate();
			return true;
		}
	});

	public WaterBubbleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		canvas = new Canvas();
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (isRunning) {
			System.out.println("isRun, draw");
			canvas.drawCircle(cx, cy, radius, paint);
			handler.sendEmptyMessageDelayed(0, 20);
		} else {
			System.out.println("noRun, change");
			paint.setColor(colors[(int) (Math.random() * colors.length)]);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (!isRunning) {
				isRunning = true;
			}
			cx = (int) event.getX();
			cy = (int) event.getY();
			handler.sendEmptyMessage(0);
			break;
		}
		return super.onTouchEvent(event);
	}

}
