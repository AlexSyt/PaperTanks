package com.example.alex.papertanks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Tank {
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

    Tank(Bitmap bitmap, int x, int y, Team team) {
        this.bitmap = bitmap;
        this.xBitmap = x;
        this.yBitmap = y;
        this.team = team;
        health = 100;
        touched = false;
        selected = false;
        destroyed = false;
    }

    int getX() {
        return xBitmap;
    }

    int getY() {
        return yBitmap;
    }

    int getHealth() {
        return health;
    }

    void setHealth(int health) {
        this.health = health;
    }

    boolean isTouched() {
        return touched;
    }

    void setTouched(boolean touched) {
        this.touched = touched;
    }

    boolean isSelected() {
        return selected;
    }

    void setSelected(boolean selected) {
        this.selected = selected;
    }

    boolean isDestroyed() {
        return destroyed;
    }

    void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    Team getTeam() {
        return team;
    }

    void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, xBitmap, yBitmap, null);


        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        Paint pSelected = new Paint();
        pSelected.setColor(Color.GREEN);
        initTankCoordinates();
        if (selected)
            canvas.drawRect(xTankLeftTop, yTankLeftTop, xTankRightBottom, yTankRightBottom, pSelected);
        else
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

    void handleActionDown(int eventX, int eventY) {
        if (eventX >= (xTankLeftTop) && (eventX <= (xTankRightBottom)))
            this.touched = eventY >= (yTankLeftTop) && (eventY <= (yTankRightBottom));
        else this.touched = false;
    }

    void move(float dx, float dy) {
        xBitmap += dx;
        yBitmap += dy;
    }
}

