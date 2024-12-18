package com.chstn.aldazexamen1chds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNombre = findViewById(R.id.btnNombre);
        Button btnAldaz = findViewById(R.id.btnAldaz);
        Button btnSuma = findViewById(R.id.btnSuma);
        Button btnCalculos = findViewById(R.id.btnCalculos);

        btnNombre.setOnClickListener(v -> startActivity(new Intent(this, NombreActivity.class)));
        btnAldaz.setOnClickListener(v -> startActivity(new Intent(this, AldazActivity.class)));
        btnSuma.setOnClickListener(v -> startActivity(new Intent(this, SumaActivity.class)));
        btnCalculos.setOnClickListener(v -> startActivity(new Intent(this, CalculosActivity.class)));
    }
}
