package com.example.iveci.mad10;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by iveci on 2017-05-18.
 */

public class MyCanvas extends View {
    public MyCanvas(Context context) {
        super(context);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(10, 10, 100, 100, paint);

//        paint.setColor(Color.MAGENTA);
    }
}
