package com.example.wrackamole;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager {

    public interface OnOptionSelectedListener {
        void onRestartSelected();
        void onExitSelected();
    }

    public static void showGameOverDialog(Context context, int score, final OnOptionSelectedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("게임 종료");
        builder.setMessage("당신의 점수: " + score);
        builder.setCancelable(false);

        builder.setPositiveButton("다시 시작", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onRestartSelected();
                }
            }
        });

        builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onExitSelected();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
