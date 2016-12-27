package com.example.alex.papertanks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {
    private Paint paint;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, 0, 100, 100, paint);
    }
}
