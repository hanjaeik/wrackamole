package com.example.wrackamole;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView moleImageView;
    private Handler handler;
    private Runnable moleRunnable;
    private ObjectAnimator moleAnimator;
    private MediaPlayer hitSound;
    private Timer gameTimer;
    private TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moleImageView = findViewById(R.id.moleImageView);
        moleImageView.setVisibility(View.INVISIBLE);

        handler = new Handler();
        moleRunnable = new Runnable() {
            @Override
            public void run() {
                hideMole();
            }
        };

        moleAnimator = ObjectAnimator.ofFloat(moleImageView, "alpha", 0f, 1f);
        moleAnimator.setDuration(1000);

        hitSound = MediaPlayer.create(this, R.raw.hit_sound);

        timerTextView = findViewById(R.id.timerTextView);

        // Timer 클래스를 사용하여 20초 동안의 타이머 설정
        gameTimer = new Timer(20000);
        gameTimer.setOnTimerTickListener(new Timer.OnTimerTickListener() {
            @Override
            public void onTick(long timeLeft) {
                updateTimerUI(timeLeft);
            }

            @Override
            public void onFinish() {
                // 게임 종료 또는 추가적인 로직 수행
                showGameOverDialog();
            }
        });

        moleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitMole();
            }
        });

        startGame();
    }

    private void startGame() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMole();
                gameTimer.start(); // 게임 시작 시 타이머 시작
            }
        }, 1000); // 1초 후에 두더지를 보이게 함
    }

    private void showMole() {
        moleImageView.setVisibility(View.VISIBLE);
        moleImageView.setAlpha(0f); // 초기에 투명도를 0으로 설정
        int randomX = getRandomCoordinate();
        int randomY = getRandomCoordinate();
        moleImageView.setX(randomX);
        moleImageView.setY(randomY);

        moleAnimator.start(); // 애니메이션 시작
        handler.postDelayed(moleRunnable, 2000); // 2초 후에 두더지를 숨기기
    }

    private void hideMole() {
        moleAnimator.cancel(); // 애니메이션 취소
        moleImageView.setVisibility(View.INVISIBLE);
        handler.removeCallbacks(moleRunnable);
        startGame(); // 다음 두더지 보이기 시작
    }

    private void hitMole() {
        hitSound.start(); // 사운드 재생
        hideMole(); // 두더지 숨기기
    }

    private int getRandomCoordinate() {
        Random random = new Random();
        return random.nextInt(500); // 적절한 범위로 수정
    }

    private void updateTimerUI(long timeLeft) {
        timerTextView.setText(getString(R.string.timer_format, timeLeft / 1000));
    }

    private void showGameOverDialog() {
        // 게임 종료 다이얼로그를 표시하는 로직 추가
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hitSound.release(); // 메모리 누수 방지를 위해 MediaPlayer 해제
    }
}
