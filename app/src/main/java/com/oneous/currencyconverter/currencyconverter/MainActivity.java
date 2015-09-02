package com.oneous.currencyconverter.currencyconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";

    EditText amountInUsdEditText;
    TextView amountInBdtTextView;
    TextView fromCurrencyTitleTextView;
    TextView toCurrencyTitleTextView;


    private double conversionRate;
    private String fromCurrency = "FROM Currency";
    private String toCurrency = "TO Currency";

    private static int REQUEST_CODE_FOR_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountInUsdEditText = (EditText) findViewById(R.id.amount_in_usd);
        amountInBdtTextView = (TextView) findViewById(R.id.amount_in_bdt);
        fromCurrencyTitleTextView = (TextView) findViewById(R.id.from_currency_title);
        toCurrencyTitleTextView = (TextView) findViewById(R.id.to_currency_title);
        populateTextView();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                convertCurrency(s.toString());
            }
        };
        amountInUsdEditText.addTextChangedListener(textWatcher);
    }

    private void populateTextView() {
        String formattedText = String.format(getResources().getString(R.string.from_currency_title), fromCurrency);
        fromCurrencyTitleTextView.setText(formattedText);
        formattedText = String.format(getResources().getString(R.string.to_currency_title), toCurrency);
        toCurrencyTitleTextView.setText(formattedText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                startSettingsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FOR_SETTINGS) {
            if (resultCode == RESULT_OK) {
                conversionRate = data.getDoubleExtra(SettingsActivity.EXTRA_CONVERSION_RATE, 0.0);
                fromCurrency = data.getStringExtra(SettingsActivity.EXTRA_CONVERSION_FROM_CURRENCY);
                toCurrency = data.getStringExtra(SettingsActivity.EXTRA_CONVERSION_TO_CURRENCY);
                Log.i(TAG, "conversionRate=" + conversionRate + " fromCurrency=" + fromCurrency + " toCurrency=" + toCurrency);
                populateTextView();
            }
        }
    }


    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FOR_SETTINGS);
    }

    public void convertCurrencyClick(View view) {
        convertCurrency(amountInUsdEditText.getText().toString());
    }

    private void convertCurrency(String amount) {
        double amountInUsd = 0;
        try {
            amountInUsd = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            Log.d(TAG, "Exception while parsing" + e.toString());
        }
        double convertedBdtAmount = amountInUsd * conversionRate;
        amountInBdtTextView.setText(String.valueOf(convertedBdtAmount));
    }
}
