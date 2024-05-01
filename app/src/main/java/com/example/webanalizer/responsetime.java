package com.example.webanalizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import android.os.AsyncTask;
//import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class responsetime extends AppCompatActivity {
    private EditText editTextUrl;
    private Button buttonAnalyze;
    private TextView textViewAnalysis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsetime);

        editTextUrl = findViewById(R.id.editTextUrl);
        buttonAnalyze = findViewById(R.id.buttonAnalyze);
        textViewAnalysis = findViewById(R.id.textViewAnalysis);

        buttonAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewAnalysis.setText("Checking Please Wait...");
                String url = editTextUrl.getText().toString();
                new AnalyzeWebPageTask().execute(url);
            }
        });
    }
    private class AnalyzeWebPageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            long startTime = System.currentTimeMillis();
            int pageSize = 0;
            long loadTime = 0;

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(urls[0]).openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    pageSize += line.getBytes().length; // Calculate the size of each line
                }
                reader.close();

                long endTime = System.currentTimeMillis();
                loadTime = endTime - startTime;
            } catch (IOException e) {
                Log.e("AnalyzeWebPageTask", "Error loading webpage", e);
            }

            return "Page load time: " + loadTime + " milliseconds\nPage size: " + pageSize + " bytes";
        }

        @Override
        protected void onPostExecute(String result) {
            textViewAnalysis.setText(result);
        }
    }

}