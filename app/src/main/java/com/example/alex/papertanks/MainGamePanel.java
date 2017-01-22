package com.example.alex.papertanks;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
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

    public MainGamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        this.displaySize = new Point(540, 960);
        touchLocation = new PointF();
        initTanks();
    }

    public MainGamePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);
        this.displaySize = new Point(1500, 960);
        touchLocation = new PointF();
        initTanks();
    }

    private void initTanks() {
        int tankBitmapSize = displaySize.x / 5; // tank bitmap is a square
        int indent = tankBitmapSize / 6;

        Bitmap blue = BitmapFactory.decodeResource(getResources(), R.drawable.blue_tank);
        blue = Bitmap.createScaledBitmap(blue, tankBitmapSize, tankBitmapSize, false);
        Bitmap red = BitmapFactory.decodeResource(getResources(), R.drawable.red_tank);
        red = Bitmap.createScaledBitmap(red, tankBitmapSize, tankBitmapSize, false);

        tanks = new Tank[6];
        tanks[0] = new Tank(blue, indent, 0, Team.BLUE);
        tanks[1] = new Tank(red, displaySize.x - tankBitmapSize - indent, 0, Team.RED);
        tanks[2] = new Tank(blue, indent, (displaySize.y / 2) - (tankBitmapSize / 2), Team.BLUE);
        tanks[3] = new Tank(red, displaySize.x - tankBitmapSize - indent, (displaySize.y / 2) - (tankBitmapSize / 2), Team.RED);
        tanks[4] = new Tank(blue, indent, displaySize.y - tankBitmapSize, Team.BLUE);
        tanks[5] = new Tank(red, displaySize.x - tankBitmapSize - indent, displaySize.y - tankBitmapSize, Team.RED);

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
                if (moveIsPossible(event.getX() - touchLocation.x, event.getY() - touchLocation.y))
                    selected.move(event.getX() - touchLocation.x, event.getY() - touchLocation.y);
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

    // check the intersection with other tanks
    private boolean moveIsPossible(float dx, float dy) {
        for (Tank tank : tanks)
            if (!tank.isSelected()) {
                Point leftTop = tank.getTankLeftTop();
                Point rightBottom = tank.getTankRightBottom();
                Point selectedLeftTop = selected.getTankLeftTop();
                Point selectedRightBottom = selected.getTankRightBottom();
                if (selectedLeftTop.x + dx > rightBottom.x || leftTop.x > selectedRightBottom.x + dx ||
                        selectedLeftTop.y + dy > rightBottom.y || leftTop.y > selectedRightBottom.y + dy)
                    continue;
                else return false;
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
