package com.oneous.currencyconverter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.oneous.currencyconverter.utils.Constants;
import com.oneous.currencyconverter.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SettingsActivity extends Activity {
    Spinner fromCurrencySpinner;
    Spinner toCurrencySpinner;
    EditText currencyRateET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fromCurrencySpinner = (Spinner) findViewById(R.id.fromCurrencySpinner);
        populateSpinner(fromCurrencySpinner);

        toCurrencySpinner = (Spinner) findViewById(R.id.toCurrencySpinner);
        populateSpinner(toCurrencySpinner);


        currencyRateET = (EditText) findViewById(R.id.currencyRateET);

        restoreFromPref();

        Button okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currencyRateString = currencyRateET.getText().toString();
                if (currencyRateString.equals("")) {
                    currencyRateET.setError("Required");
                    return;
                }

                String toCurrencyString = String.valueOf(toCurrencySpinner.getSelectedItem());
                String fromCurrencyString = String.valueOf(fromCurrencySpinner.getSelectedItem());
                if (toCurrencyString.equals(fromCurrencyString)) {
                    Toast.makeText(SettingsActivity.this, "From & To Currency can't be equal", Toast.LENGTH_LONG).show();
                    return;
                }

                SharedPreferenceUtils.saveData(SettingsActivity.this, Constants.CONVERSION_RATE, currencyRateString);
                SharedPreferenceUtils.saveData(SettingsActivity.this, Constants.FROM_CURRENCY, fromCurrencyString);
                SharedPreferenceUtils.saveData(SettingsActivity.this, Constants.TO_CURRENCY, toCurrencyString);
                finish();
            }
        });
    }

    private void restoreFromPref() {
        String conversionRateString = SharedPreferenceUtils.getData(this, Constants.CONVERSION_RATE);
        String toCurrency = SharedPreferenceUtils.getData(this, Constants.TO_CURRENCY);
        String fromCurrency = SharedPreferenceUtils.getData(this, Constants.FROM_CURRENCY);

        currencyRateET.setText(conversionRateString);
        restoreSpinnerSelection(toCurrencySpinner, toCurrency);
        restoreSpinnerSelection(fromCurrencySpinner, fromCurrency);
    }

    private void restoreSpinnerSelection(Spinner currencySpinner, String currencyString) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) currencySpinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(currencyString)) {
                currencySpinner.setSelection(i);
                return;
            }
        }
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
