package com.example.wrackamole;

public class Score {
    private int score;
    private OnScoreChangeListener onScoreChangeListener;

    public Score() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void setOnScoreChangeListener(OnScoreChangeListener listener) {
        this.onScoreChangeListener = listener;
    }

    public void increaseScore(int points) {
        score += points;
        if (onScoreChangeListener != null) {
            onScoreChangeListener.onScoreChange(score);
        }
    }

    public interface OnScoreChangeListener {
        void onScoreChange(int score);
    }
}
