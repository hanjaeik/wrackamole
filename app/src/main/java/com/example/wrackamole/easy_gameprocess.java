package com.example.wrackamole;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class easy_gameprocess extends AppCompatActivity {

    private ImageView[] moleImageViews;
    private TextView scoreTextView;
    private int score = 0;
    private int moleCount = 0;
    private int currentIndex = 0;
    private boolean isMoleHurt = false;

    private Handler handler;

    {
        handler = new Handler();
    }

    private int[] moleImageIds = {
            R.drawable.mole1,
            R.drawable.mole2,
            R.drawable.mole3,
            R.drawable.mole4,
            R.drawable.mole5,
            R.drawable.mole6,
            R.drawable.mole7,
            R.drawable.mole8,
            R.drawable.mole9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_process);

        moleImageViews = new ImageView[]{
                findViewById(R.id.moleImageView1),
                findViewById(R.id.moleImageView2),
                findViewById(R.id.moleImageView3),
                findViewById(R.id.moleImageView4),
                findViewById(R.id.moleImageView5),
                findViewById(R.id.moleImageView6),
                findViewById(R.id.moleImageView7),
                findViewById(R.id.moleImageView8),
                findViewById(R.id.moleImageView9)
        };

        scoreTextView = findViewById(R.id.scoreTextView);

        // 초기에는 두더지가 나오기 전까지의 대기 시간을 설정
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                simulateMoleHiding();
            }
        }, getRandomDelay());

        for (final ImageView moleImageView : moleImageViews) {
            moleImageView.setOnTouchListener(new View.OnTouchListener() {
                private boolean isMoleHurt = false; // 각각의 moleImageView에 대한 mole_hurt 상태 여부를 추적

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (!isMoleHurt) {
                                moleImageView.setImageResource(R.drawable.mole_hurt);
                                moleImageView.setClickable(false); // 터치 이벤트 비활성화
                                isMoleHurt = true; // mole_hurt 상태로 설정

                                score += 100;
                                updateScore(); // 수정된 부분

                                moleCount++;

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideMoleImage();
                                        isMoleHurt = false; // mole_hurt 상태 해제
                                        moleImageView.setClickable(true); // 터치 이벤트 다시 활성화
                                    }
                                }, 1000);

                                if (moleCount >= 10) {
                                    endGame();
                                }
                            }
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private void simulateMoleHiding() {
        showMoleImage();
    }

    private void endGame() {
        Intent intent = new Intent(this, game_end.class);
        startActivity(intent);
        finish();
    }

    private void showMoleImage() {
        List<Integer> moleImageIndexes = getRandomImageIndexes(1); // 1개의 이미지를 랜덤으로 선택
        Collections.shuffle(moleImageIndexes);

        int totalMoleCount = moleImageViews.length;
        List<Integer> visibleIndexes = new ArrayList<>();

        // 이미지를 랜덤으로 나타나게 하기
        for (int i = 0; i < moleImageIndexes.size(); i++) {
            int randomImageIndex = moleImageIndexes.get(i);
            moleImageViews[i].setImageResource(moleImageIds[randomImageIndex]);
            visibleIndexes.add(i);

            final int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    moleImageViews[finalI].setVisibility(View.VISIBLE);
                }
            }, 4000);
        }

    }
    private void hideMoleImage() {
        for (ImageView moleImageView : moleImageViews) {
            moleImageView.setImageResource(R.drawable.mole); // 두더지 이미지를 초기 상태로 설정
            moleImageView.setVisibility(View.INVISIBLE); // 이미지를 숨김 처리
        }

        // 랜덤한 위치에 두더지 이미지 나타내기
        List<Integer> indexes = getRandomImageIndexes(1); // 3개의 이미지를 랜덤으로 선택
        currentIndex = indexes.get(0);

        moleImageViews[currentIndex].setVisibility(View.VISIBLE);

        // 일정 시간 후에 두더지 이미지를 숨기는 코드
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMoleImage();
            }
        }, 4000);
    }

    private List<Integer> getRandomImageIndexes(int count) {
        List<Integer> randomIndexes = new ArrayList<>();
        List<Integer> availableIndexes = new ArrayList<>();
        for (int i = 0; i < moleImageIds.length; i++) {
            availableIndexes.add(i);
        }
        Collections.shuffle(availableIndexes);

        for (int i = 0; i < count; i++) {
            if (i < availableIndexes.size()) {
                randomIndexes.add(availableIndexes.get(i));
            }
        }

        return randomIndexes;
    }

    private void updateScore() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTextView.setText("Score: " + score);
            }
        });
    }

    private int getRandomDelay() {
        Random random = new Random();
        return random.nextInt(500) + 1000; // 예시로 1000에서 3000까지의 범위로 수정
    }
}

