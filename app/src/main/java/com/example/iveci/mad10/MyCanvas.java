package com.example.iveci.mad10;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by iveci on 2017-05-18.
 */

public class MyCanvas extends View {
    Bitmap mBitmap;
    Canvas mCanvas;
    Paint mPaint = new Paint();
    String opt = "";
    public MyCanvas(Context context) {
        super(context);
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaint.setColor(Color.CYAN);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.CYAN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();

        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);

        Bitmap img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap bigImg = Bitmap.createScaledBitmap(img,img.getWidth()*2, img.getHeight()*2,false);
        int cenX = (canvas.getWidth() - bigImg.getWidth())/2;
        int cenY = (canvas.getHeight() - bigImg.getHeight())/2;
        if(opt.equals("rotate"))
            canvas.rotate(45,this.getWidth()/2, this.getHeight()/2);
        else if (opt.equals("move"))
            ;
        else if (opt.equals("scale"))
            ;
        else if (opt.equals("skew"))
            ;
        else if (opt.equals("erase"))
            mBitmap.eraseColor(Color.WHITE);
        else if (opt.equals("open"))
            ;
        invalidate();
        BlurMaskFilter blur = new BlurMaskFilter(100, BlurMaskFilter.Blur.NORMAL);
        paint.setMaskFilter(blur);
        canvas.drawBitmap(bigImg, cenX, cenY, paint);
        /*        canvas.drawBitmap(img, 300, 350, paint);
        Bitmap smallBitmap = Bitmap.createScaledBitmap(img,img.getWidth()/2, img.getHeight()/2,false);
        canvas.drawBitmap(smallBitmap, 400, 350, paint);

        Bitmap bigBitmap = Bitmap.createScaledBitmap(img,img.getWidth()*2, img.getHeight()*2,false);
        canvas.drawBitmap(bigBitmap, 100, 100, paint);
        img.recycle();*/
        float[] array = {
                1, 0, 0, 0, -25f,
                0, 1, 0, 0, -25f,
                0, 0, 1, 0, -25f,
                0, 0, 0, 2, 0
        };
/*
        ColorMatrix colorMatrix = new ColorMatrix(array);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(filter);
        canvas.drawBitmap(bigImg, cenX, cenY, paint);*/

        if(stopX != -1 && stopY != -1) canvas.drawBitmap(img, stopX, stopY, paint);

    }

    float startX = -1, startY = -1, stopX = -1, stopY = -1;

    public void setOptType(String opt){
        this.opt = opt;
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

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int) event.getX();
        int y = (int) event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            startX = event.getX(); startY = event.getY();
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){

        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            stopX = event.getX(); stopY = event.getY();
            invalidate();
        }
        return true;
    }
}
