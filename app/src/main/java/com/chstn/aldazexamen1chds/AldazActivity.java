package com.chstn.aldazexamen1chds;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AldazActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aldaz);

        tvResult = findViewById(R.id.tvResult);
        Button btnFetch = findViewById(R.id.btnFetch);

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchMessage();
            }
        });
    }

    private void fetchMessage() {
        String url = "http://192.168.100.96:3001/aldaz"; // Reemplaza <IP_LOCAL> con la IP de tu servidor

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(AldazActivity.this, "Error de conexión: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    runOnUiThread(() ->
                            tvResult.setText(responseData)
                    );
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(AldazActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_LONG).show()
                    );
                }
            }
        });
    }
}