package com.chstn.aldazexamen1chds;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txtRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRespuesta = findViewById(R.id.txtRespuesta);

        Button btnConsumir = findViewById(R.id.btnConsumir);
        btnConsumir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumirWS();
            }
        });
    }

    public void consumirWS() {
        String url = "http://10.10.29.67:3001/aldaz";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> txtRespuesta.setText("Error de conexión: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String respuesta = response.body().string();
                    runOnUiThread(() -> txtRespuesta.setText(respuesta));
                } else {
                    runOnUiThread(() -> txtRespuesta.setText("Error en la respuesta del servidor"));
                }
            }
        });
    }
}
