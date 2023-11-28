package com.example.wrackamole;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class game_selection extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_selection);

        // 각 버튼에 클릭 리스너 추가
        Button easyButton = findViewById(R.id.easyButton); //초급 난이도 화면으로 전환
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(easy_gameprocess.class);
            }
        });

        Button MiddleButton = findViewById(R.id.MiddleButton); // 중간 난이도 화면으로 전환
        MiddleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(middle_gameprocess.class);
            }
        });

        Button advancedButton = findViewById(R.id.advancedButton); // 고급 난이도 화면으로 전환
        advancedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(advance_gameprocess.class);
            }
        });
    }

    private void startGame(Class<?> gameClass) {
        Intent intent = new Intent(this, gameClass);
        startActivity(intent);
    }
}
