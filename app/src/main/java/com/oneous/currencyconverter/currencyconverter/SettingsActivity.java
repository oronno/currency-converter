package com.oneous.currencyconverter.currencyconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
    public static final String EXTRA_CONVERSION_RATE = "conversion_rate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner fromCurrencySpinner = (Spinner) findViewById(R.id.fromCurrencySpinner);
        populateSpinner(fromCurrencySpinner);

        Spinner toCurrencySpinner = (Spinner) findViewById(R.id.toCurrencySpinner);
        populateSpinner(toCurrencySpinner);

        Button okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText currencyRateET = (EditText) findViewById(R.id.currencyRateET);
                double conversionRate = Double.parseDouble(currencyRateET.getText().toString());

                Intent intent = new Intent();
                intent.putExtra(EXTRA_CONVERSION_RATE, conversionRate);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
    }

    private void populateSpinner(Spinner sprinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getCurrencyList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprinner.setAdapter(adapter);
    }

    private List<String> getCurrencyList() {
        Locale[] locales = Locale.getAvailableLocales();
        Set<String> currencySet = new HashSet<>();
        for (Locale l : locales) {
            try {
                Currency c = Currency.getInstance(l);
                currencySet.add(c.getCurrencyCode());
            } catch (IllegalArgumentException e) {
            }

        }

        List<String> currencyList = new ArrayList<>(currencySet);
        Collections.sort(currencyList);
        return currencyList;
    }

//    private List<String> getCurrencyList() {
//        Set<Currency> currencies = Currency.getAvailableCurrencies();
//        List<String> availableCurrencyList = new ArrayList<>();
//
//        for(Currency c : currencies) {
//            availableCurrencyList.add(c.getCurrencyCode());
//        }
//
//        return availableCurrencyList;
//    }

}
