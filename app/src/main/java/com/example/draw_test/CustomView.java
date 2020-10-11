package com.example.draw_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends View{
    List<Path> listStrokes = new ArrayList<>();
    Path pathStroke;
    Bitmap memBMP;
    Paint memPaint;
    Canvas memCanvas;
    boolean mBooleanOnTouch = false;

    float start_x;
    float start_y;

    public CustomView(Context context,AttributeSet attrs){
        super(context,attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pathStroke = new Path();
                pathStroke.moveTo(x,y);
                start_x =x;
                start_y =y;
                mBooleanOnTouch = true;
                listStrokes.add(pathStroke);
                break;
            case MotionEvent.ACTION_UP:
                if(mBooleanOnTouch){
                    pathStroke.quadTo(start_x,start_y,x,y);
                    drawStrokes();
                    mBooleanOnTouch = false;
                }
                performClick();
                break;
        }
        return true;
        }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
    void drawStrokes(){
        if(memCanvas ==null){
            memBMP = Bitmap.createBitmap(getWidth(),getHeight(),
                    Bitmap.Config.ARGB_8888);
            memCanvas = new Canvas();
            memCanvas.setBitmap(memBMP);
            memPaint = new Paint();
            memPaint.setAntiAlias(true);
            memPaint.setColor(Color.RED);
            memPaint.setStyle(Paint.Style.STROKE);
            memPaint.setStrokeWidth(5);
        }
        for(Path path: listStrokes){
            memCanvas.drawPath(path,memPaint);
        }
        invalidate();
    }
    Paint paint = new Paint();
    @Override
    protected void onDraw(Canvas canvas){
        if(memBMP!= null){
            canvas.drawBitmap(memBMP,0,0,paint);
        }
    }
}