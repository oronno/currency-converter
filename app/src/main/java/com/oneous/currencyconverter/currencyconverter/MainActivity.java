package com.oneous.currencyconverter.currencyconverter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";
    EditText amountInUsdEditText;
    TextView amountInBdtTextView;

    private double conversionRate;
    private String toCurrency;
    private String fromCurrency;

    private static int REQUEST_CODE_FOR_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountInUsdEditText = (EditText) findViewById(R.id.amount_in_usd);
        amountInBdtTextView = (TextView) findViewById(R.id.amount_in_bdt);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                convertUSDtoBDT(s.toString());
            }
        };
        amountInUsdEditText.addTextChangedListener(textWatcher);

        if(conversionRate == 0.0 && toCurrency == null && fromCurrency == null) {
            startSettingsActivity();
        }
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
                toCurrency = data.getStringExtra(SettingsActivity.EXTRA_CONVERSION_TO_CURRENCY);
                fromCurrency = data.getStringExtra(SettingsActivity.EXTRA_CONVERSION_FROM_CURRENCY);
                populateView();
            }
        }
    }

    private void populateView() {
        TextView fromCurrencyTextView = (TextView) findViewById(R.id.from_currency_title);
        TextView toCurrencyTextView = (TextView) findViewById(R.id.to_currency_title);

        String from = getResources().getString(R.string.from_currency_title);
        fromCurrencyTextView.setText(String.format(from, fromCurrency));

        String to = getResources().getString(R.string.to_currency_title);
        toCurrencyTextView.setText(String.format(to, toCurrency));
    }


    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FOR_SETTINGS);
    }

    public void convertUSDToBDT(View view) {
        convertUSDtoBDT(amountInUsdEditText.getText().toString());
    }

    private void convertUSDtoBDT(String amount) {
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
