package com.oneous.currencyconverter.currencyconverter.domain;

import java.io.Serializable;

/**
 * Created by INSTRUCTOR on 9/9/2015.
 */
public class ConverterValue implements Serializable {
    private String toCurrency;
    private String fromCurrency;
    private double rate;

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
