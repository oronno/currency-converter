package com.oneous.currencyconverter.currencyconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SettingsActivity extends Activity {
    public static final String EXTRA_CONVERSION_RATE = "conversion_rate";
    public static final String EXTRA_CONVERSION_FROM_CURRENCY = "extra_conversion_from_currency";
    public static final String EXTRA_CONVERSION_TO_CURRENCY = "extra_conversion_to_currency";

    Spinner fromCurrencySpinner;
    Spinner toCurrencySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fromCurrencySpinner = (Spinner) findViewById(R.id.fromCurrencySpinner);
        populateSpinner(fromCurrencySpinner);

        toCurrencySpinner = (Spinner) findViewById(R.id.toCurrencySpinner);
        populateSpinner(toCurrencySpinner);

        Button okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText currencyRateET = (EditText) findViewById(R.id.currencyRateET);
                String currencyRateString = currencyRateET.getText().toString();
                if(currencyRateString.equals("")) {
                    currencyRateET.setError("Required");
                    return;
                }

                String toCurrencyString = String.valueOf(toCurrencySpinner.getSelectedItem());
                String fromCurrencyString = String.valueOf(fromCurrencySpinner.getSelectedItem());
                if(toCurrencyString.equals(fromCurrencyString)) {
                    Toast.makeText(SettingsActivity.this, "From & To Currency can't be equal", Toast.LENGTH_LONG).show();
                    return;
                }

                double conversionRate = Double.parseDouble(currencyRateString);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_CONVERSION_RATE, conversionRate);

                intent.putExtra(EXTRA_CONVERSION_FROM_CURRENCY, fromCurrencyString);

                intent.putExtra(EXTRA_CONVERSION_TO_CURRENCY, toCurrencyString);
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
