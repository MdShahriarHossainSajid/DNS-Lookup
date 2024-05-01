package com.example.webanalizer;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ssl_activity extends AppCompatActivity {
    private EditText urlEditText;
    private TextView analysisTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssl2);

        urlEditText = findViewById(R.id.editTextUrl);
        analysisTextView = findViewById(R.id.textViewAnalysis);

        Button analyzeButton = findViewById(R.id.buttonAnalyze);
        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlEditText.getText().toString().trim();
                if (!url.isEmpty()) {
                    new SSLCheckerTask().execute(url);
                }
            }
        });



    }
    private class SSLCheckerTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            StringBuilder result = new StringBuilder();

            try {
                URL serverUrl = new URL(url);
                HttpsURLConnection connection = (HttpsURLConnection) serverUrl.openConnection();
                connection.connect();

                // Get the SSL certificate
                Certificate[] certificates = connection.getServerCertificates();
                for (Certificate certificate : certificates) {
                    if (certificate instanceof X509Certificate) {
                        X509Certificate x509Certificate = (X509Certificate) certificate;
                        // Append certificate details to result
                        result.append("Subject: ").append(x509Certificate.getSubjectDN()).append("\n");
                        result.append("Issuer: ").append(x509Certificate.getIssuerDN()).append("\n");
                        result.append("Validity: ").append(x509Certificate.getNotBefore()).append(" - ")
                                .append(x509Certificate.getNotAfter()).append("\n");
                        result.append("Serial Number: ").append(x509Certificate.getSerialNumber()).append("\n");
                        result.append("Algorithm: ").append(x509Certificate.getSigAlgName()).append("\n\n");
                    }
                }

                connection.disconnect();
            } catch (IOException e) {
                result.append("Error: ").append(e.getMessage());
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            analysisTextView.setText(result);
        }
    }
}