package com.swiss.re.exchange.util;

import java.util.Objects;

public class ExchangeRates {
    private String currency;
    private Double exchangeRate;
    private boolean flagged;

    public ExchangeRates(String currency, Double exchangeRate) {
        this.currency = currency;
        this.exchangeRate = exchangeRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    @Override
    public String toString() {
        return "ExchangeRates{" +
                "currency='" + currency + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", flagged=" + flagged +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRates that = (ExchangeRates) o;
        return getCurrency().equals(that.getCurrency()) &&
                getExchangeRate().equals(that.getExchangeRate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrency(), getExchangeRate());
    }
}
