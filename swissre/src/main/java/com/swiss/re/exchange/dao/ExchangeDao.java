package com.swiss.re.exchange.dao;

import com.swiss.re.exchange.util.ExchangeOnDay;

/**
 *   This Interface persists the processed files containing Exchange Rates.
 *   @author ManeyMR
 *   @version 1.0
 *   @since   2020-01-25
 *
 */

public interface ExchangeDao {
    void persistExchangeRates(ExchangeOnDay exchangeOnDay);
}
