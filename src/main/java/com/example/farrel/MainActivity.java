package com.example.farrel;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText etAmount;
    Spinner spFrom, spTo;
    Button btnConvert;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = findViewById(R.id.etAmount);
        spFrom = findViewById(R.id.spFrom);
        spTo = findViewById(R.id.spTo);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.tvResult);

        String[] currencies = {
                "🇮🇩 IDR",
                "🇺🇸 USD",
                "🇪🇺 EUR",
                "🇯🇵 JPY"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                currencies
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spFrom.setAdapter(adapter);
        spTo.setAdapter(adapter);

        btnConvert.setOnClickListener(v -> convertCurrency());
    }

    private void convertCurrency() {

        String amountStr = etAmount.getText().toString().trim();

        if (amountStr.isEmpty()) {
            Toast.makeText(
                    this,
                    "Masukkan nominal terlebih dahulu",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);

        // Ambil kode mata uang tanpa emoji
        String from = spFrom.getSelectedItem().toString().split(" ")[1];
        String to = spTo.getSelectedItem().toString().split(" ")[1];

        HashMap<String, Double> rates = new HashMap<>();

        // Kurs terhadap USD
        rates.put("USD", 1.0);
        rates.put("IDR", 16000.0);
        rates.put("EUR", 0.85);
        rates.put("JPY", 145.0);

        double usdValue = amount / rates.get(from);
        double result = usdValue * rates.get(to);

        String hasil = String.format(
                "%.2f %s\n=\n%.2f %s",
                amount,
                from,
                result,
                to
        );

        tvResult.setText(hasil);
    }
}