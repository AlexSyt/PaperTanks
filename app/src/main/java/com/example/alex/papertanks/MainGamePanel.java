package com.example.alex.papertanks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private int displayWidth;
    private int displayHeight;
    private float xTouch;
    private float yTouch;
    private int tankCount;
    private Tank[] tanks;
    private Tank SELECTED;

    public MainGamePanel(Context context, int displayWidth, int displayHeight) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        initTanks();
    }

    private void initTanks() {
        int tankBitmapWidth = 300;
        int tankBitmapHeight = 300;
        int indent = 50;

        Bitmap blue = BitmapFactory.decodeResource(getResources(), R.drawable.blue_tank);
        blue = Bitmap.createScaledBitmap(blue, tankBitmapWidth, tankBitmapHeight, false);
        Bitmap red = BitmapFactory.decodeResource(getResources(), R.drawable.red_tank);
        red = Bitmap.createScaledBitmap(red, tankBitmapWidth, tankBitmapHeight, false);

        tanks = new Tank[6];
        tanks[0] = new Tank(blue, indent, 0, Team.BLUE);
        tanks[1] = new Tank(red, displayWidth - tankBitmapWidth - indent, 0, Team.RED);
        tanks[2] = new Tank(blue, indent, (displayHeight / 2) - (tankBitmapHeight / 2), Team.BLUE);
        tanks[3] = new Tank(red, displayWidth - tankBitmapWidth - indent, (displayHeight / 2) - (tankBitmapHeight / 2), Team.RED);
        tanks[4] = new Tank(blue, indent, displayHeight - tankBitmapHeight, Team.BLUE);
        tanks[5] = new Tank(red, displayWidth - tankBitmapWidth - indent, displayHeight - tankBitmapHeight, Team.RED);

        SELECTED = tanks[0];
        SELECTED.setSelected(true);
        tankCount = 1;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // do something
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            xTouch = event.getX();
            yTouch = event.getY();
            for (Tank tank : tanks) tank.handleActionDown((int) event.getX(), (int) event.getY());
        }

        if (action == MotionEvent.ACTION_MOVE) {
            Tank selected = getTouchedTank();
            if (selected != null && selected.isTouched()) {
                selected.move(event.getX() - xTouch, event.getY() - yTouch);
                xTouch = event.getX();
                yTouch = event.getY();
            }
        }

        if (action == MotionEvent.ACTION_UP) {
            Tank selected = getTouchedTank();
            if (selected != null && selected.isTouched())
                selected.setTouched(false);
        }

        return true;
    }

    private Tank getTouchedTank() {
        Tank selected = null;
        for (Tank tank : tanks) {
            selected = tank;
            if (selected.isTouched())
                break;
        }
        return selected;
    }

    private void selectNextTank() {
        if (tankCount > 5) tankCount = 0;
        SELECTED.setSelected(false);
        SELECTED = tanks[tankCount];
        SELECTED.setSelected(true);
        tankCount++;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        for (Tank tank : tanks)
            tank.draw(canvas);
    }
}
