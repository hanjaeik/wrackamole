package com.example.wrackamole;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class easy_gameprocess extends AppCompatActivity {

    private ImageView[] moleImageViews;
    private TextView scoreTextView;
    private int score = 0;
    private int moleCount = 0;
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

        // 제한 시간을 60초로 설정하는 카운트다운 타이머
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                // 남은 시간을 초 단위로 표시
                int secondsLeft = (int) (millisUntilFinished / 1000);
                updateScoreAndTime(secondsLeft);
            }

            public void onFinish() {
                // 게임 종료 시 동작
                endGame();
            }
        }.start();

        // 초기에는 두더지가 나오기 전까지의 대기 시간을 설정
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                simulateMoleHiding();
            }
        }, getRandomDelay());


        for (final ImageView moleImageView : moleImageViews) {
            moleImageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            moleImageView.setImageResource(R.drawable.mole_hurt);
                            score += 100;
                            moleCount++;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    hideMoleImage();
                                }
                            }, 1500);

                            if (moleCount >= 10) {
                                endGame();
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
        List<Integer> moleImageIndexes = new ArrayList<>();
        for (int i = 0; i < moleImageIds.length; i++) {
            moleImageIndexes.add(i);
        }
        Collections.shuffle(moleImageIndexes);

        for (int i = 0; i < moleImageViews.length; i++) {
            int randomImageIndex = moleImageIndexes.get(i);
            moleImageViews[i].setImageResource(moleImageIds[randomImageIndex]);

            // 랜덤한 딜레이를 주어 각 두더지가 랜덤한 순서로 나타나도록 함
            final int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    moleImageViews[finalI].setVisibility(View.VISIBLE);
                }
            }, getRandomDelay());
        }
        // 일정 시간 후에 두더지 이미지를 숨기는 코드
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideMoleImage();
            }
        }, 1500);
    }


    private void hideMoleImage() {
        for (ImageView moleImageView : moleImageViews) {
            moleImageView.setVisibility(View.INVISIBLE);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMoleImage();
            }
        }, 1500);
    }

    private int getRandomImageIndex() {
        Random random = new Random();
        return random.nextInt(moleImageIds.length);
    }

    private void updateScoreAndTime(final int secondsLeft) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTextView.setText("Score: " + score + " | Time: " + secondsLeft + "s");
            }
        });
    }

    private int getRandomDelay() {
        Random random = new Random();
        return random.nextInt(1000) + 500;
    }
}