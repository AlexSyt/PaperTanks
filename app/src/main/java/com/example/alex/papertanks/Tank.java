package com.example.alex.papertanks;

import android.graphics.*;

public class Tank {
    private Bitmap bitmap;
    private Point bitmapPosition;  // top left point of the bitmap
    private Point tankLeftTop;     // the size of the bitmap larger than the size of the tank
    private Point tankRightBottom; // so we must keep the location of the tank
    private boolean touched;
    private boolean selected;
    private Team team;

    public Tank(Bitmap bitmap, int x, int y, Team team) {
        this.bitmap = bitmap;
        this.team = team;
        bitmapPosition = new Point(x, y);
        tankLeftTop = new Point();
        tankRightBottom = new Point();
        touched = false;
        selected = false;
    }

    public Point getTankLeftTop() {
        return tankLeftTop;
    }

    public Point getTankRightBottom() {
        return tankRightBottom;
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

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, bitmapPosition.x, bitmapPosition.y, null);


        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        Paint pSelected = new Paint(); // paint for selected tank
        pSelected.setColor(Color.GREEN);
        initTankCoordinates();
        // drawing the boundaries of tank:
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

    public void move(float dx, float dy) {
        bitmapPosition.x += dx;
        bitmapPosition.y += dy;
    }
}

