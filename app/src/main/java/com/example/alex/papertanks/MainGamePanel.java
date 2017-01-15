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
    private Tank selected;

    public MainGamePanel(Context context, int displayWidth, int displayHeight) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        initTanks();
    }

    private void initTanks() {
        int tankBitmapSize = displayWidth / 4;
        int indent = tankBitmapSize / 6;

        Bitmap blue = BitmapFactory.decodeResource(getResources(), R.drawable.blue_tank);
        blue = Bitmap.createScaledBitmap(blue, tankBitmapSize, tankBitmapSize, false);
        Bitmap red = BitmapFactory.decodeResource(getResources(), R.drawable.red_tank);
        red = Bitmap.createScaledBitmap(red, tankBitmapSize, tankBitmapSize, false);

        tanks = new Tank[6];
        tanks[0] = new Tank(blue, indent, 0, Team.BLUE);
        tanks[1] = new Tank(red, displayWidth - tankBitmapSize - indent, 0, Team.RED);
        tanks[2] = new Tank(blue, indent, (displayHeight / 2) - (tankBitmapSize / 2), Team.BLUE);
        tanks[3] = new Tank(red, displayWidth - tankBitmapSize - indent, (displayHeight / 2) - (tankBitmapSize / 2), Team.RED);
        tanks[4] = new Tank(blue, indent, displayHeight - tankBitmapSize, Team.BLUE);
        tanks[5] = new Tank(red, displayWidth - tankBitmapSize - indent, displayHeight - tankBitmapSize, Team.RED);

        selected = tanks[0];
        selected.setSelected(true);
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
            selected.handleActionDown((int) event.getX(), (int) event.getY());
        }

        if (action == MotionEvent.ACTION_MOVE) {
            if (selected.isTouched()) {
                selected.move(event.getX() - xTouch, event.getY() - yTouch, tanks);
                xTouch = event.getX();
                yTouch = event.getY();
            }
        }

        if (action == MotionEvent.ACTION_UP) {
            if (selected.isTouched()) {
                selected.setTouched(false);
                selectNextTank();
            }
        }

        return true;
    }

    private void selectNextTank() {
        if (tankCount > 5) tankCount = 0;
        selected.setSelected(false);
        selected = tanks[tankCount];
        selected.setSelected(true);
        tankCount++;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        for (Tank tank : tanks)
            tank.draw(canvas);
    }
}
