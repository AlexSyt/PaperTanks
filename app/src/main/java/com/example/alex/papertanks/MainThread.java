package com.example.alex.papertanks;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private boolean running;
    private final SurfaceHolder surfaceHolder;
    private MainGamePanel gamePanel;

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.onDraw(canvas);
                }
            } finally {
                if (canvas != null) surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}