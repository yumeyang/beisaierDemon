package com.solot.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BezierView extends View {
    private static final float POINTWIDTH = 2;

    private Paint mPaint;           // 画笔
    private Path mPath;             // 路径

    private Point startPoint;       // 开始点
    private Point endPoint;         // 结束点
    private Point assistPoint;      // 动点

    public BezierView(Context context) {
        this(context,null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        mPaint = new Paint();
        mPath = new Path();

        // 抗锯齿
        mPaint.setAntiAlias(true);
        // 防抖动
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        // 笔宽
        mPaint.setStrokeWidth(POINTWIDTH);
        // 空心
        mPaint.setStyle(Paint.Style.STROKE);


        startPoint = new Point(-200, 400);
        endPoint = new Point(800, 400);
        assistPoint = new Point(400, 900);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        // 起点
        mPath.moveTo(startPoint.x, startPoint.y);
        // 动点 和 终点
        mPath.quadTo(assistPoint.x, assistPoint.y, endPoint.x, endPoint.y);
        // 画路径
        canvas.drawPath(mPath, mPaint);
        // 画辅助点
        canvas.drawPoint(assistPoint.x, assistPoint.y, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                assistPoint.x = (int) event.getX();
                assistPoint.y = (int) event.getY();
                invalidate();
                break;
        }
        return true;
    }
}