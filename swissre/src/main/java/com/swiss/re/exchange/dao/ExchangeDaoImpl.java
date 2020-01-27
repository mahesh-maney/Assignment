package com.swiss.re.exchange.dao;

import com.swiss.re.exchange.persistance.ProcessedFiles;
import com.swiss.re.exchange.util.ExchangeOnDay;

/**
 *   This Class is an concrete implementation of ExchangeDAO.
 *   Responsible for persisting the processed files containing Exchange Rates.
 *
 *   @author ManeyMR
 *   @version 1.0
 *   @since   2020-01-25
 *
 */

public class ExchangeDaoImpl implements ExchangeDao {

    @Override
    public void persistExchangeRates(ExchangeOnDay exchangeOnDay) {
        ProcessedFiles.allProcessedFiles.add(exchangeOnDay);
        ProcessedFiles.exchangeOnDayInfo.put(exchangeOnDay.getLocalDateTime().toLocalDate(), exchangeOnDay);
    }


}
