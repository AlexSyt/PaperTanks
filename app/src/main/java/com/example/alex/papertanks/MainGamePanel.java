package com.example.alex.papertanks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private int displayWidth;
    private int displayHeight;
    private ArrayList<Tank> blueTanks;
    private ArrayList<Tank> redTanks;
    private ArrayList<Tank> tanks;

    public MainGamePanel(Context context, int displayWidth, int displayHeight) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        initTanks();
    }

    private void initTanks() {
        blueTanks = new ArrayList<>();
        redTanks = new ArrayList<>();
        int tankWidth = 300;
        int tankHeight = 300;
        int indent = 50;

        Bitmap blue = BitmapFactory.decodeResource(getResources(), R.drawable.blue_tank);
        blue = Bitmap.createScaledBitmap(blue, tankWidth, tankHeight, false);
        Bitmap red = BitmapFactory.decodeResource(getResources(), R.drawable.red_tank);
        red = Bitmap.createScaledBitmap(red, tankWidth, tankHeight, false);

        blueTanks.add(new Tank(blue, 0 + indent, 0, Team.BLUE));
        blueTanks.add(new Tank(blue, 0 + indent, (displayHeight / 2) - (tankHeight / 2), Team.BLUE));
        blueTanks.add(new Tank(blue, 0 + indent, displayHeight - tankHeight, Team.BLUE));

        redTanks.add(new Tank(red, displayWidth - tankWidth - indent, 0, Team.RED));
        redTanks.add(new Tank(red, displayWidth - tankWidth - indent, (displayHeight / 2) - (tankHeight / 2), Team.RED));
        redTanks.add(new Tank(red, displayWidth - tankWidth - indent, displayHeight - tankHeight, Team.RED));

        tanks = new ArrayList<>(blueTanks);
        tanks.addAll(redTanks);
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

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < tanks.size(); i++)
                tanks.get(i).handleActionDown((int) event.getX(), (int) event.getY());
        }

        if (action == MotionEvent.ACTION_MOVE) {
            Tank selected = getTouchedTank();
            if (selected != null && selected.isTouched()) {
                selected.setX((int) event.getX());
                selected.setY((int) event.getY());
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
        for (int i = 0; i < tanks.size(); i++) {
            selected = tanks.get(i);
            if (selected.isTouched())
                break;
        }
        return selected;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < 3; i++) {
            blueTanks.get(i).draw(canvas);
            redTanks.get(i).draw(canvas);
        }
    }
}
