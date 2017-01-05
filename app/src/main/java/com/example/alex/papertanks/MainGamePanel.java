package com.example.alex.papertanks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private int displayWidth;
    private int displayHeight;
    private ArrayList<Tank> blueTanks;
    private ArrayList<Tank> redTanks;

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

        Bitmap blue = BitmapFactory.decodeResource(getResources(), R.drawable.blue_tank);
        blue = Bitmap.createScaledBitmap(blue, tankWidth, tankHeight, false);
        Bitmap red = BitmapFactory.decodeResource(getResources(), R.drawable.red_tank);
        red = Bitmap.createScaledBitmap(red, tankWidth, tankHeight, false);

        blueTanks.add(new Tank(blue, 0, 0, Team.BLUE));
        blueTanks.add(new Tank(blue, 0, (displayHeight / 2) - (tankHeight / 2), Team.BLUE));
        blueTanks.add(new Tank(blue, 0, displayHeight - tankHeight, Team.BLUE));

        redTanks.add(new Tank(red, displayWidth - tankWidth, 0, Team.RED));
        redTanks.add(new Tank(red, displayWidth - tankWidth, (displayHeight / 2) - (tankHeight / 2), Team.RED));
        redTanks.add(new Tank(red, displayWidth - tankWidth, displayHeight - tankHeight, Team.RED));
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
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < 3; i++) {
            blueTanks.get(i).draw(canvas);
            redTanks.get(i).draw(canvas);
        }
    }
}
