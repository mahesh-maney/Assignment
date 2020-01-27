package com.swiss.re.exchange.service;

import com.swiss.re.exchange.persistance.ProcessedFiles;
import com.swiss.re.exchange.util.ExchangeOnDay;
import com.swiss.re.exchange.util.ExchangeRates;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class implements ExchangeReportService and provides concrete implementation of
 * fetching Monthly and Yearly Reports for process files containing Exchange Rates.
 *
 * @author ManeyMR
 * @version 1.0
 * @since   2020-01-25
 */
public class ExchangeReportServiceImpl implements ExchangeReportService {

    /**
     *
     * @param month
     * @param year
     * @return Map<String, Double>
     */
    @Override
    public Map<String, Double> getMonthlyAverageReport(int month, int year) {
        Map<LocalDate, ExchangeOnDay> exchangeRatesMap = ProcessedFiles.exchangeOnDayInfo;
        List<LocalDate> localDates = exchangeRatesMap.keySet().stream().filter(localDate -> localDate.getYear() == year && localDate.getMonth() == Month.of(month)).collect(Collectors.toList());
        return getStringDoubleMap(exchangeRatesMap, localDates);
    }

    /**
     *
     * @param year
     * @return Map<String, Double>
     */
    @Override
    public Map<String, Double> getYearlyAverageReport(int year) {
        Map<LocalDate, ExchangeOnDay> exchangeRatesMap = ProcessedFiles.exchangeOnDayInfo;
        List<LocalDate> localDates = exchangeRatesMap.keySet().stream().filter(localDate -> localDate.getYear() == year).collect(Collectors.toList());
        return getStringDoubleMap(exchangeRatesMap, localDates);
    }

    /**
     *
     * @param exchangeRatesMap
     * @param localDates
     * @return Map<String, Double>
     */
    private Map<String, Double> getStringDoubleMap(Map<LocalDate, ExchangeOnDay> exchangeRatesMap, List<LocalDate> localDates) {
        List<ExchangeRates> exchangeRatesList = new ArrayList<>();
        localDates.stream().forEach(localDate -> {
            exchangeRatesList.addAll(exchangeRatesMap.get(localDate).getExchangeRatesListOnDay());
        });
        Map<String, Double> average = exchangeRatesList.stream().collect(Collectors.groupingBy(i -> i.getCurrency(), Collectors.averagingDouble(ExchangeRates::getExchangeRate)));
        return average;
    }
}
