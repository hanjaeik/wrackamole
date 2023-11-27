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
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class game_process extends AppCompatActivity {

    private ImageView moleImageView1;
    private ImageView moleImageView2;
    private TextView scoreTextView;
    private int score = 0;
    private int moleCount = 0;
    private Handler handler;

    {
        handler = new Handler();
    }

    // 두더지 이미지 리소스 ID 배열
    private int[] moleImageIds = {
            R.drawable.mole1,
            R.drawable.mole2,
    };

    // 두더지가 잡힌 이미지 리소스 ID 배열
    private int[] moleHurtImageIds = {
            R.drawable.mole_hurt,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_process);

        moleImageView1 = findViewById(R.id.moleImageView1);
        moleImageView2 = findViewById(R.id.moleImageView2);
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
        }, getRandomTime());

        moleImageView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 터치 이벤트 처리
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 두더지를 터치했을 때의 동작을 여기에 구현
                        // 예를 들어, 두더지가 잡혔을 때 다른 이미지로 변경
                        moleImageView1.setImageResource(R.drawable.mole_hurt);

                        // 두더지가 잡히면 100점 추가
                        score += 100;

                        // 두더지가 잡힌 횟수 증가
                        moleCount++;

                        // 점수를 업데이트
                        updateScoreAndTime(60); // 남은 시간은 60초로 고정

                        // 두더지가 잡힌 후 2초 동안 기다린 후 다시 숨게 설정
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                simulateMoleHiding();
                            }
                        }, 2000);

                        // 만약 두더지를 10번 잡으면 게임 종료
                        if (moleCount >= 10) {
                            endGame();
                        }
                        break;
                }
                return true;
            }
        });

        moleImageView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 터치 이벤트 처리
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 두더지를 터치했을 때의 동작을 여기에 구현
                        // 예를 들어, 두더지가 잡혔을 때 다른 이미지로 변경
                        moleImageView2.setImageResource(R.drawable.mole_hurt);

                        // 두더지가 잡히면 100점 추가
                        score += 100;

                        // 두더지가 잡힌 횟수 증가
                        moleCount++;

                        // 점수를 업데이트
                        updateScoreAndTime(60); // 남은 시간은 60초로 고정

                        // 두더지가 잡힌 후 2초 동안 기다린 후 다시 숨게 설정
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                simulateMoleHiding();
                            }
                        }, 2000);

                        // 만약 두더지를 10번 잡으면 게임 종료
                        if (moleCount >= 10) {
                            endGame();
                        }
                        break;
                }
                return true;
            }
        });
    }

    // 게임 종료 처리
    private void endGame() {
        Intent intent = new Intent(this, game_end.class);
        intent.putExtra("finalScore", score);
        startActivity(intent);

        // game_start 액티비티를 시작하고 싶다면 아래와 같이 사용
        Intent gameStartIntent = new Intent(this, game_start.class);
        startActivity(gameStartIntent);
        // 토스트 메시지로 게임 종료를 알림
        Toast.makeText(this, "게임 종료! 최종 점수: " + score, Toast.LENGTH_SHORT).show();

        // 액티비티를 종료하거나 다음 화면으로 이동할 수 있습니다.
        finish();
    }

    // 두더지가 숨는 동작을 시뮬레이션하는 메서드
    private void simulateMoleHiding() {
        // 랜덤한 위치로 두더지를 나타나게 설정
        showMoleImage();
    }


    private void showMoleImage() {
        int randomImageIndex1 = getRandomImageIndex();
        int randomImageIndex2;

        do {
            randomImageIndex2 = getRandomImageIndex();
        } while (randomImageIndex2 == randomImageIndex1);

        moleImageView1.setImageResource(moleImageIds[randomImageIndex1]);
        moleImageView1.setVisibility(View.VISIBLE);

        moleImageView2.setImageResource(moleImageIds[randomImageIndex2]);
        moleImageView2.setVisibility(View.VISIBLE);

        // 두더지가 나타나는 시간 간격을 2초로 설정
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideMoleImage();
            }
        }, 2000);
    }// getRandomHoleIndex() 메서드 수정

    // 랜덤으로 두더지가 나타나는 메서드
    private int getRandomImageIndex() {
        Random random = new Random();
        return random.nextInt(moleImageIds.length);
    }

    // 두더지 이미지를 숨기는 메서드
    private void hideMoleImage() {
        // 두더지 이미지를 숨기고 두더지가 잡힌 후 2초 동안 기다린 후 다시 나오게 설정
        moleImageView1.setVisibility(View.INVISIBLE);
        moleImageView2.setVisibility(View.INVISIBLE);

        // 두더지가 잡힌 후 2초 동안 기다린 후 다시 나오게 설정
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMoleImage();
            }
        }, 2000); // 2초 동안 기다리도록 설정
    }

    // 랜덤한 시간 간격을 반환하는 메서드
    private int getRandomTime() {
        Random random = new Random();
        return random.nextInt(5000) + 1000; // 1000ms ~ 6000ms 사이의 랜덤한 값
    }

    // 점수와 시간을 업데이트하는 메서드
    private void updateScoreAndTime(final int secondsLeft) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTextView.setText("Score: " + score + " | Time: " + secondsLeft + "s");
            }
        });
    }
}