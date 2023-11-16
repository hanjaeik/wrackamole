package com.example.wrackamole;

import android.os.Handler;

public class Timer {

    private static final long DELAY_MILLIS = 1000;
    private long timeLeft;
    private OnTimerTickListener onTimerTickListener;
    private Handler handler;
    private Runnable timerRunnable;

    public Timer(long totalTime) {
        this.timeLeft = totalTime;
        this.handler = new Handler();
        this.timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (timeLeft > 0) {
                    timeLeft -= 1000;
                    if (onTimerTickListener != null) {
                        onTimerTickListener.onTick(timeLeft);
                    }
                    handler.postDelayed(this, DELAY_MILLIS);
                } else {
                    if (onTimerTickListener != null) {
                        onTimerTickListener.onFinish();
                    }
                }
            }
        };
    }

    public void setOnTimerTickListener(OnTimerTickListener listener) {
        this.onTimerTickListener = listener;
    }

    public void start() {
        handler.postDelayed(timerRunnable, DELAY_MILLIS);
    }

    public void stop() {
        handler.removeCallbacks(timerRunnable);
    }

    public interface OnTimerTickListener {
        void onTick(long timeLeft);

        void onFinish();
    }
}
