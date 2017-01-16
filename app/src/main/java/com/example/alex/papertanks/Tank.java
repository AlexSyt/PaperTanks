package com.example.alex.papertanks;

import android.graphics.*;

public class Tank {
    private Bitmap bitmap;
    private Point bitmapPosition;
    private Point tankLeftTop;
    private Point tankRightBottom;
    private int health;
    private boolean touched;
    private boolean selected;
    private boolean destroyed;
    private Team team;

    public Tank(Bitmap bitmap, int x, int y, Team team) {
        this.bitmap = bitmap;
        this.team = team;
        bitmapPosition = new Point(x, y);
        tankLeftTop = new Point();
        tankRightBottom = new Point();
        health = 100;
        touched = false;
        selected = false;
        destroyed = false;
    }

    public Point getTankLeftTop() {
        return tankLeftTop;
    }

    public Point getTankRightBottom() {
        return tankRightBottom;
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

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, bitmapPosition.x, bitmapPosition.y, null);


        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        Paint pSelected = new Paint();
        pSelected.setColor(Color.GREEN);
        initTankCoordinates();
        if (selected)
            canvas.drawRect(tankLeftTop.x, tankLeftTop.y, tankRightBottom.x, tankRightBottom.y, pSelected);
        else
            canvas.drawRect(tankLeftTop.x, tankLeftTop.y, tankRightBottom.x, tankRightBottom.y, p);
    }

    private void initTankCoordinates() {
        if (this.team == Team.BLUE) {
            tankLeftTop.x = bitmapPosition.x + bitmap.getWidth() / 6;
            tankRightBottom.x = bitmapPosition.x + (bitmap.getWidth() / 5) * 4;
        } else if (this.team == Team.RED) {
            tankLeftTop.x = bitmapPosition.x + bitmap.getWidth() / 6;
            tankRightBottom.x = bitmapPosition.x + (bitmap.getWidth() / 7) * 6;
        }
        tankLeftTop.y = (int) (bitmapPosition.y + bitmap.getHeight() / 3.8);
        tankRightBottom.y = bitmapPosition.y + (bitmap.getHeight() / 3) * 2;
    }

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (tankLeftTop.x) && (eventX <= (tankRightBottom.x)))
            this.touched = eventY >= (tankLeftTop.y) && (eventY <= (tankRightBottom.y));
        else this.touched = false;
    }

    public void move(float dx, float dy, Tank[] tanks) {
        if (isPossible(tanks, dx, dy)) {
            bitmapPosition.x += dx;
            bitmapPosition.y += dy;
        }
    }

    // check the intersection with other tanks
    private boolean isPossible(Tank[] tanks, float dx, float dy) {
        for (Tank tank : tanks)
            if (!tank.isSelected()) {
                Point leftTop = tank.getTankLeftTop();
                Point rightBottom = tank.getTankRightBottom();
                if (this.tankLeftTop.x + dx > rightBottom.x || leftTop.x > this.tankRightBottom.x + dx ||
                        this.tankLeftTop.y + dy > rightBottom.y || leftTop.y > this.tankRightBottom.y + dy)
                    continue;
                else return false;
            }
        return true;
    }
}

