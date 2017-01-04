package com.example.alex.papertanks;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Tank {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private Bitmap bitmap;
    private int x;
    private int y;
    private int health;
    private boolean touched;
    private boolean selected;
    private boolean destroyed;
    private Team team;

    public Tank(Bitmap bitmap, int x, int y, Team team) {
        this.bitmap = Bitmap.createScaledBitmap(bitmap, WIDTH, HEIGHT, false);
        this.x = x;
        this.y = y;
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
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth() / 2))) {
            if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2)))
                setTouched(true);
            else setTouched(false);
        } else setTouched(false);
    }
}

