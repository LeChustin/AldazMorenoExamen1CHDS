package com.chstn.aldazexamen1chds;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class CalculosActivity extends AppCompatActivity {

    private EditText etLado1, etLado2;
    private TextView tvResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculos);

        etLado1 = findViewById(R.id.etLado1);
        etLado2 = findViewById(R.id.etLado2);
        tvResultados = findViewById(R.id.tvResultados);
        Button btnCalcular = findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(v -> realizarCalculos());
    }

    private void realizarCalculos() {
        String lado1 = etLado1.getText().toString();
        String lado2 = etLado2.getText().toString();

        if (lado1.isEmpty() || lado2.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese ambos lados.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://192.168.100.96:3001/calculos/" + lado1 + "/" + lado2;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(CalculosActivity.this, "Error al conectar con el servidor.", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        StringBuilder resultados = new StringBuilder();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject figura = jsonArray.getJSONObject(i);
                            resultados.append("Figura: ").append(figura.getString("figura")).append("\n")
                                    .append("Área: ").append(figura.getDouble("area")).append("\n")
                                    .append("Perímetro: ").append(figura.getDouble("perimetro")).append("\n\n");
                        }

                        runOnUiThread(() -> tvResultados.setText(resultados.toString()));

                    } catch (JSONException e) {
                        runOnUiThread(() ->
                                Toast.makeText(CalculosActivity.this, "Error al procesar los datos.", Toast.LENGTH_SHORT).show()
                        );
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(CalculosActivity.this, "Error en la respuesta del servidor.", Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}
