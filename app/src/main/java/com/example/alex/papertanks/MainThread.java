package com.example.alex.papertanks;

public class MainThread extends Thread {
    private boolean running;

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {

        }
    }
}