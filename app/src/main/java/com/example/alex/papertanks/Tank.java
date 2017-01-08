package com.example.alex.papertanks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Tank {
    private Bitmap bitmap;
    private int xBitmap;
    private int yBitmap;
    private int xTankLeftTop;
    private int yTankLeftTop;
    private int xTankRightBottom;
    private int yTankRightBottom;
    private int health;
    private boolean touched;
    private boolean selected;
    private boolean destroyed;
    private Team team;

    public Tank(Bitmap bitmap, int x, int y, Team team) {
        this.bitmap = bitmap;
        this.xBitmap = x;
        this.yBitmap = y;
        this.team = team;
        health = 100;
        touched = false;
        selected = false;
        destroyed = false;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return xBitmap;
    }

    public void setX(int x) {
        this.xBitmap = x;
    }

    public int getY() {
        return yBitmap;
    }

    public void setY(int y) {
        this.yBitmap = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public Team getTeam() {
        return team;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, xBitmap, yBitmap, null);


        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        initTankCoordinates();
        canvas.drawRect(xTankLeftTop, yTankLeftTop, xTankRightBottom, yTankRightBottom, p);
    }

    private void initTankCoordinates() {
        if (this.team == Team.BLUE) {
            xTankLeftTop = xBitmap + bitmap.getWidth() / 6;
            xTankRightBottom = xBitmap + (bitmap.getWidth() / 5) * 4;
        } else if (this.team == Team.RED) {
            xTankLeftTop = xBitmap + bitmap.getWidth() / 6;
            xTankRightBottom = xBitmap + (bitmap.getWidth() / 7) * 6;
        }
        yTankLeftTop = (int) (yBitmap + bitmap.getHeight() / 3.8);
        yTankRightBottom = yBitmap + (bitmap.getHeight() / 3) * 2;
    }

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (xTankLeftTop) && (eventX <= (xTankRightBottom))) {
            if (eventY >= (yTankLeftTop) && (yBitmap <= (yTankRightBottom)))
                this.touched = true;
            else this.touched = false;
        } else this.touched = false;
    }
}

