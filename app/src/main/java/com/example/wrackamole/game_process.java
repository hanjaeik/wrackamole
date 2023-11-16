package com.example.wrackamole;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class game_process extends AppCompatActivity {

    private int score;
    private TextView scoreTextView;
    private ImageView[] moleImageViews, moleExplosionViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_process1);

        // XML에서 정의한 뷰들을 찾아와서 변수에 할당
        scoreTextView = findViewById(R.id.scoreTextView);

        // 두더지 이미지뷰와 폭발 이미지뷰들을 배열로 찾아와서 변수에 할당
        moleImageViews = new ImageView[]{
                findViewById(R.id.moleImageView1),
                findViewById(R.id.moleImageView2),
                findViewById(R.id.moleImageView3),
                findViewById(R.id.moleImageView4),
                findViewById(R.id.moleImageView5),
                findViewById(R.id.moleImageView6)
        };

        moleExplosionViews = new ImageView[]{
                findViewById(R.id.moleExplosion1),
                findViewById(R.id.moleExplosion2),
                findViewById(R.id.moleExplosion3),
                findViewById(R.id.moleExplosion4),
                findViewById(R.id.moleExplosion5),
                findViewById(R.id.moleExplosion6)
        };

        // 초기 점수 설정
        score = 0;
        updateScore();

        // 게임 로직 시작
        startGameLogic();
    }

    private void startGameLogic() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 두더지가 나타날 때의 동작을 구현
                for (int i = 0; i < moleImageViews.length; i++) {
                    showMole(moleImageViews[i], moleExplosionViews[i]);
                }

                // 1초 후에 다시 두더지를 숨기는 동작을 구현
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < moleImageViews.length; i++) {
                            hideMole(moleImageViews[i], moleExplosionViews[i]);
                        }

                        // 재귀적으로 계속해서 실행 (1초마다)
                        handler.postDelayed(this, 1000);
                    }
                }, 1000);
            }
        }, 1000);
    }
}

    private void hideMole(final ImageView moleImageView, final ImageView moleExplosionView) {
        moleImageView.setVisibility(View.INVISIBLE);
        moleExplosionView.setVisibility(View.INVISIBLE);
    }

    private void showMole(final ImageView moleImageView, final ImageView moleExplosionView) {
        moleImageView.setVisibility(View.VISIBLE);
        moleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 두더지를 클릭했을 때의 동작을 구현
                score++;
                updateScore();
                showExplosion(moleExplosionView); // 클릭 후에는 폭발 이미지를 표시
                hideMole(moleImageView, moleExplosionView); // 클릭 후에는 두더지를 숨김
            }
        });
    }

    private void showExplosion(final ImageView moleExplosionView) {
        moleExplosionView.setVisibility(View.VISIBLE);

        // 0.5초 후에 폭발 이미지를 다시 숨김
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moleExplosionView.setVisibility(View.INVISIBLE);
            }
        }, 500);
    }

    private void updateScore() {
        scoreTextView.setText("Score: " + score);
    }
}

