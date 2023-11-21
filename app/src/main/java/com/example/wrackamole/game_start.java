package com.example.wrackamole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class game_start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_start);
    }

    // startButton이 클릭되었을 때 호출되는 메서드
    public void startGame(View view) {
        // Intent를 사용하여 새로운 화면으로 전환
        Intent intent = new Intent(this,game_process.class);
        startActivity(intent);
    }}
