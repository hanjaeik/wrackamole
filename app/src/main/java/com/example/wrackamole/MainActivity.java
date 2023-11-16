package com.example.wrackamole;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView moleImageView;
    private Handler handler;
    private Runnable moleRunnable;
    private ObjectAnimator moleAnimator;
    private MediaPlayer hitSound;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hitSound.release(); // 메모리 누수 방지를 위해 MediaPlayer 해제
    }
}