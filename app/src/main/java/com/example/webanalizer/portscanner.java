package com.example.webanalizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.net.InetSocketAddress;
import java.net.Socket;

public class portscanner extends AppCompatActivity {
    private EditText editTextUrl;
    private Button buttonAnalyze;
    private TextView textViewAnalysis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portscanner);

        editTextUrl = findViewById(R.id.editTextUrl);
        buttonAnalyze = findViewById(R.id.buttonAnalyze);
        textViewAnalysis = findViewById(R.id.textViewAnalysis);

        buttonAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String TARGET1 = editTextUrl.getText().toString();
                final String TARGET = TARGET1.replaceAll("^https?://", "");
                final int TIMEOUT = 1000;
                final int NUM_THREADS = 100;

                StringBuilder analysisResult = new StringBuilder();

                textViewAnalysis.setText("Network is scanning. Please wait...");

                for (int i = 0; i < NUM_THREADS; i++) {
                    int threadNumber = i;
                    new Thread(() -> {
                        for (int port = threadNumber; port <= 1024; port += NUM_THREADS) {
                            try {
                                Socket socket = new Socket();
                                socket.connect(new InetSocketAddress(TARGET, port), TIMEOUT);
                                socket.close();
                                analysisResult.append("Port ").append(port).append(" is open\n");
                            } catch (Exception e) {
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewAnalysis.setText(analysisResult.toString());
                            }
                        });
                    }).start();
                }
            }
        });
    }
}
