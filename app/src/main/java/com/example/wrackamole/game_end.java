package com.example.wrackamole;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class game_end extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_end);

        // 최종 점수를 받아옴
        int finalScore = getIntent().getIntExtra("finalScore", 0);

        TextView finalScoreTextView = findViewById(R.id.finalScoreTextView);
        finalScoreTextView.setText("최종 점수: " + finalScore);
    }
}