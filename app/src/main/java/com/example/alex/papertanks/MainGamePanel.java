package com.example.alex.papertanks;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Point displaySize;
    private PointF touchLocation;
    private int tankCount;
    private Tank[] tanks;
    private Tank selected;

    public MainGamePanel(Context context, Point displaySize) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        this.displaySize = displaySize;
        touchLocation = new PointF();
        initTanks();
    }

    private void initTanks() {
        int tankBitmapWidth = displaySize.x / 5;
        int indent = tankBitmapWidth / 6;

        Bitmap blue = BitmapFactory.decodeResource(getResources(), R.drawable.blue_tank);
        blue = Bitmap.createScaledBitmap(blue, tankBitmapWidth, tankBitmapWidth, false);
        Bitmap red = BitmapFactory.decodeResource(getResources(), R.drawable.red_tank);
        red = Bitmap.createScaledBitmap(red, tankBitmapWidth, tankBitmapWidth, false);

        tanks = new Tank[6];
        tanks[0] = new Tank(blue, indent, 0, Team.BLUE);
        tanks[1] = new Tank(red, displaySize.x - tankBitmapWidth - indent, 0, Team.RED);
        tanks[2] = new Tank(blue, indent, (displaySize.y / 2) - (tankBitmapWidth / 2), Team.BLUE);
        tanks[3] = new Tank(red, displaySize.x - tankBitmapWidth - indent, (displaySize.y / 2) - (tankBitmapWidth / 2), Team.RED);
        tanks[4] = new Tank(blue, indent, displaySize.y - tankBitmapWidth, Team.BLUE);
        tanks[5] = new Tank(red, displaySize.x - tankBitmapWidth - indent, displaySize.y - tankBitmapWidth, Team.RED);

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
            touchLocation.x = event.getX();
            touchLocation.y = event.getY();
            selected.handleActionDown((int) event.getX(), (int) event.getY());
        }

        if (action == MotionEvent.ACTION_MOVE) {
            if (selected.isTouched()) {
                selected.move(event.getX() - touchLocation.x, event.getY() - touchLocation.y, tanks);
                touchLocation.x = event.getX();
                touchLocation.y = event.getY();
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
