package com.example.iveci.mad10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by iveci on 2017-05-18.
 */

public class MyCanvas extends View {
    Bitmap mBitmap;
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);;
    Boolean stamp = false, blurring = false, color = false, penb = false;
    Canvas mCanvas;
    Paint mPaint = new Paint();
    String opt = "";
    public MyCanvas(Context context) {
        super(context);
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
        this.mPaint.setColor(Color.BLACK);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
        this.mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        if (blurring){
            BlurMaskFilter blur = new BlurMaskFilter(100, BlurMaskFilter.Blur.NORMAL);
            mPaint.setMaskFilter(blur);
        }
        else mPaint.setMaskFilter(null);
        if (color){
            float[] array = {
                    2f, 0f, 0f, 0f, -25f,
                    0f, 2f, 0f, 0f, -25f,
                    0f, 0f, 2f, 0f, -25f,
                    0f, 0f, 0f, 2f, 0f
            };
            ColorMatrix matrix = new ColorMatrix(array);
            mPaint.setColorFilter(new ColorMatrixColorFilter(matrix));
        }
        else mPaint.setColorFilter(null);
        if(penb) mPaint.setStrokeWidth(5);
        else mPaint.setStrokeWidth(3);
        if(mBitmap != null) canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    public void setOptType(String opt){
        this.opt = opt;
    }

    public void clear(){
        mBitmap.eraseColor(Color.WHITE);
        invalidate();
    }

    public void setBlurring(boolean blurring){
        this.blurring = blurring;
    }

    public void setStamp(boolean stamp){
        this.stamp = stamp;
    }

    public void setColoring(boolean color){
        this.color = color;
    }
    public void setB(boolean penb){
        this.penb = penb;
    }
    public boolean Save(String file_name) {
        try {
            FileOutputStream out = new FileOutputStream(file_name);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    int oldX = -1, oldY = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            oldX = X; oldY = Y;
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            if (oldX != -1 && !stamp) {
                mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
                oldX = X;
                oldY = Y;
                invalidate();
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            if (oldX != -1 && !stamp) {
                mCanvas.drawLine(oldX, oldY, X, Y, mPaint);
                invalidate();
            }
            else if (stamp){
                mCanvas.save();
                if (opt.equals("rotate")) {
                    mCanvas.rotate(30,this.getWidth()/2, this.getHeight()/2);
                }
                else if (opt.equals("move")){
                    mCanvas.translate(10,10);
                }
                else if (opt.equals("scale")){
                    mCanvas.scale(1.5f, 1.5f);
                }
                else if (opt.equals("skew")){
                    mCanvas.skew(0.2f,0);
                }
                mCanvas.drawBitmap(bitmap, X, Y, mPaint);
                invalidate();
                mCanvas.restore();
            }
            oldX = -1;
            oldY = -1;
        }
        return true;
    }
}
