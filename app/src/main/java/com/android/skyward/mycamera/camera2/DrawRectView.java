package com.android.skyward.mycamera.camera2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.ImageView;

public class DrawRectView extends ImageView{
    private int width;
    private int height;
    private int maskWidth;
    private int maskHeight;
    private Paint linePaint;


    public DrawRectView(Context context, int width) {
        super(context);
        init();
    }

    public DrawRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawRectView(Context context, AttributeSet attrs, int defStyleAttr, int width) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Style.FILL);
        linePaint.setStrokeWidth(8f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int h = Math.abs((height-maskHeight)/2);
        int w = Math.abs((width-maskWidth)/2);
//        Log.i("myLog","w "+ w+" h " +h);

        //画矩形框的四个角
        //左上角
        canvas.drawLine(w,h,w+30,h,linePaint);// _
        canvas.drawLine(w,h,w,h+30,linePaint);// |
        //左下角
        canvas.drawLine(w,h+maskHeight,w,h+maskHeight-30,linePaint);// |
        canvas.drawLine(w,h+maskHeight,w+30,h+maskHeight,linePaint);// _
        //右上角
        canvas.drawLine(w+maskWidth,h,w+maskWidth-30,h,linePaint);// _
        canvas.drawLine(w+maskWidth,h,w+maskWidth,h+30,linePaint);// |
        //右下角
        canvas.drawLine(w+maskWidth,h+maskHeight,w+maskWidth,h+maskHeight-30,linePaint);// |
        canvas.drawLine(w+maskWidth,h+maskHeight,w+maskWidth-30,h+maskHeight,linePaint);// _
    }


    public void PixSize(Integer w, Integer h){
        width = w;
        height = h;
    }
    public void setMaskSize(Integer widths, Integer heights){
        maskWidth = widths;
        maskHeight = heights;
    }

    public int[] getMaskSize(){
        return new MaskSize().size;
    }

    private class MaskSize{
        private final int[] size;
        private MaskSize(){
            this.size = new int[]{maskWidth, maskHeight, width, height};
        }
    }



}