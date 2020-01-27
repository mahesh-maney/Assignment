package com.swiss.re.exchange.service;

import java.util.Map;

/**
 * This interface provides Monthly and Yearly Report for the processed files containing Exchange Rates.
 *
 * @author ManeyMR
 * @version 1.0
 * @since   2020-01-25
 */
public interface ExchangeReportService {
    /**
     *
     * @param month
     * @param year
     * @return  Map<String, Double>
     */
    Map<String, Double> getMonthlyAverageReport(int month, int year);

    /**
     *
     * @param year
     * @return Map<String, Double>
     */
    Map<String, Double> getYearlyAverageReport(int year);

}
