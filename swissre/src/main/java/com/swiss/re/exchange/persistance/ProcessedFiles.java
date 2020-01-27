package com.swiss.re.exchange.persistance;

import com.swiss.re.exchange.util.ExchangeOnDay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class acts as persistence for saving the processed Files.
 *
 * @author ManeyMR
 * @version 1.0
 * @since   2020-01-25
 */
public class ProcessedFiles {
    public static List<ExchangeOnDay> allProcessedFiles = new ArrayList<>();
    public static Map<LocalDate, ExchangeOnDay> exchangeOnDayInfo = new LinkedHashMap<>();

    /**
     *
     * @param localDate
     * @return ExchangeOnDay
     */
    public static ExchangeOnDay getPreviousDayExchangeRate(LocalDate localDate) {
        return exchangeOnDayInfo.get(localDate.minusDays(1)) == null ? null : exchangeOnDayInfo.get(localDate.minusDays(1));
    }

}
