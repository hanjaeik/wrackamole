package com.example.wrackamole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class game_end extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.game_end);
        }

        // 추가된 부분: 버튼 클릭 시 호출되는 메서드
        public void restartGame(View view) {
            Intent intent = new Intent(this, game_start.class);
            startActivity(intent);
            finish();
        }
    }