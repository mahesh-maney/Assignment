package com.swiss.re.exchange.util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is an POJO class for maintaining the ExchangeOnDay attributes.
 * @author ManeyMR
 * @version 1.0
 * @since   2020-01-25
 */

public class ExchangeOnDay {
    private LocalDateTime localDateTime;
    private List<ExchangeRates> exchangeRatesListOnDay;
    private List<ExchangeRates> flaggedExchangeRates;

    /**
     *
     * @param localDateTime
     * @param exchangeRatesListOnDay
     */
    public ExchangeOnDay(LocalDateTime localDateTime, List<ExchangeRates> exchangeRatesListOnDay) {
        this.localDateTime = localDateTime;
        this.exchangeRatesListOnDay = exchangeRatesListOnDay;
    }

    /**
     *
     * @return LocalDateTime
     */
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    /**
     *
     * @param localDateTime
     */
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    /**
     *
     * @return List<ExchangeRates>
     */
    public List<ExchangeRates> getExchangeRatesListOnDay() {
        return exchangeRatesListOnDay;
    }

    /**
     *
     * @param exchangeRatesListOnDay
     */
    public void setExchangeRatesListOnDay(List<ExchangeRates> exchangeRatesListOnDay) {
        this.exchangeRatesListOnDay = exchangeRatesListOnDay;
    }

    /**
     *
     * @return List<ExchangeRates>
     *
     */
    public List<ExchangeRates> getFlaggedExchangeRates() {
        return flaggedExchangeRates;
    }

    public void markFlaggedExchangeRates() {
        flaggedExchangeRates = exchangeRatesListOnDay.stream().filter(exchangeRates -> exchangeRates.isFlagged()).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ExchangeOnDay{" +
                "localDateTime=" + localDateTime +
                ", exchangeRatesListOnDay=" + exchangeRatesListOnDay +
                ", flaggedExchangeRate=" + flaggedExchangeRates +
                '}';
    }
}
