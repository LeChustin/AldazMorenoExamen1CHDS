package com.chstn.aldazexamen1chds;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NombreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre);

        TextView tvNombre = findViewById(R.id.tvNombre);
        fetchNombres(tvNombre);
    }

    private void fetchNombres(TextView tv) {
        String url = "http://192.168.100.96:3001/nombre"; // Cambia <IP_LOCAL> por tu IP local
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> tv.setText("Error al conectar con el servidor."));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        StringBuilder nombres = new StringBuilder();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            nombres.append(obj.getString("nombre")).append("\n");
                        }
                        String finalNombres = nombres.toString();
                        runOnUiThread(() -> tv.setText(finalNombres));
                    } catch (JSONException e) {
                        runOnUiThread(() -> tv.setText("Error al procesar los datos."));
                    }
                }
            }
        });
    }
}
