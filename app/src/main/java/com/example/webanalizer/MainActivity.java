package com.example.webanalizer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button1,button2,button3;
    private static final long DELAY_MILLIS = 3000; // 3 seconds
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1=findViewById(R.id.responseID);
        button2=findViewById(R.id.openportID);
        button3=findViewById(R.id.sslID);
        showStartingImage();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(MainActivity.this,responsetime.class);
                startActivities(new Intent[]{intent1});
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this,portscanner.class);
                startActivities(new Intent[]{intent2});
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3= new Intent(MainActivity.this,ssl_activity.class);
                startActivities(new Intent[]{intent3});
            }
        });
    }
    private void showStartingImage() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_start_image);
        dialog.setCancelable(false); // Prevent canceling by touching outside

        // Delay dismissing the dialog
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, DELAY_MILLIS);

        dialog.show();
    }
}