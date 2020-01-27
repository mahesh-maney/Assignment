package com.swiss.re.exchange.service;

import com.swiss.re.exchange.dao.ExchangeDao;
import com.swiss.re.exchange.dao.ExchangeDaoImpl;
import com.swiss.re.exchange.persistance.ProcessedFiles;
import com.swiss.re.exchange.util.DateUtil;
import com.swiss.re.exchange.util.ExchangeOnDay;
import com.swiss.re.exchange.util.ExchangeRates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.swiss.re.exchange.util.Constants.BEGIN_FROM;
import static com.swiss.re.exchange.util.Constants.ENDS_WITH;
import static com.swiss.re.exchange.util.Constants.PERCENTAGE;

/**
 * This Class is an concrete implementation for the InputProcessService.
 * The input file is processed for checking the day-on-day exchange rate fluctuation and saved.
 *
 * @author ManeyMR
 * @version 1.0
 * @since   2020-01-25
 */
public class InputProcessorServiceImpl implements InputProcessorService {

    private ExchangeDao exchangeDao = new ExchangeDaoImpl();

    /**
     *
     * To be deleted before actual checkin.
     */
    public static void main(String[] args) {
        String str = "START-OF-FILE" +
                "DATE=20181015" +
                "START-OF-FIELD-LIST" +
                "CURRENCY" +
                "EXCHANGE_RATE" +
                "LAST_UPDATE" +
                "END-OF-FIELD-LIST" +
                "START-OF-EXCHANGE-RATES" +
                "CHF|0.9832|17:12:59 10/14/2018|" +
                "GBP|0.7849|17:12:59 10/14/2018|" +
                "EUR|0.8677|17:13:00 10/14/2018|" +
                "END-OF-EXCHANGE-RATES" +
                "END-OF-FILE";
        InputProcessorServiceImpl test = new InputProcessorServiceImpl();
        test.process(str);
        test.process(str);
        System.out.println(ProcessedFiles.allProcessedFiles);
    }

    /**
     *
     * @param input
     */
    @Override
    public void process(String input) {
        if (input == null || input.isEmpty()) {
            System.out.println("Not A Valid Input File");
            // TBD : Add an Exception - Probably NotAVAlidFileException
            return;
        } else {
            String exchangeData = input.substring(input.indexOf(BEGIN_FROM) + BEGIN_FROM.length(), input.indexOf(ENDS_WITH));
            String[] exchangeSplit = exchangeData.split("\\|");
            int _length = exchangeSplit.length;
            List<ExchangeRates> exchangeRatesList = new ArrayList<>();
            String date = null;
            for (int i = 0; i < _length; i += 3) {
                String currency = exchangeSplit[i];
                double rate = Double.parseDouble(exchangeSplit[i + 1]);
                date = date == null ? exchangeSplit[i + 2] : date;
                ExchangeRates exchangeRate = new ExchangeRates(currency, rate);
                exchangeRatesList.add(exchangeRate);

            }
            ExchangeOnDay exchangeOnDay = new ExchangeOnDay(DateUtil.converter(date), exchangeRatesList);
            if (isFileDuplicate(exchangeOnDay)) {
                System.out.println("DUPLICATE FILE .FILE HAS BEEN ALREADY PROCESSED");
                // TBD : Add an Exception here. Probably DuplicateFileException
            } else {
                shouldBeFlagged(exchangeOnDay, DateUtil.converter(date));
                exchangeOnDay.markFlaggedExchangeRates();
                exchangeDao.persistExchangeRates(exchangeOnDay);
            }
        }
    }

    /**
     *
     * @param exchangeOnDay
     * @param dateTime
     */
    private void shouldBeFlagged(ExchangeOnDay exchangeOnDay, LocalDateTime dateTime) {
        ExchangeOnDay previousDateExchange = ProcessedFiles.getPreviousDayExchangeRate(dateTime.toLocalDate());
        if (previousDateExchange != null) {
            Map<String, ExchangeRates> currencyExchangeRatesMapPrevious = previousDateExchange.getExchangeRatesListOnDay().stream().collect(Collectors.toMap(i -> i.getCurrency(), i -> i));
            Map<String, ExchangeRates> currencyExchangeRatesMapCurrent = exchangeOnDay.getExchangeRatesListOnDay().stream().collect(Collectors.toMap(i -> i.getCurrency(), i -> i));
            currencyExchangeRatesMapCurrent.forEach((exchangeType, exchangeRatesCurrent) -> {
                ExchangeRates exchangeRatesPrevious = currencyExchangeRatesMapPrevious.get(exchangeType);
                if (exchangeRatesPrevious != null) {
                    Double percentage = (exchangeRatesCurrent.getExchangeRate() / exchangeRatesPrevious.getExchangeRate()) * 100;
                    if (percentage.compareTo(PERCENTAGE) >= 0) {
                        exchangeRatesCurrent.setFlagged(true);
                        System.out.println("EXCHANGE RATE VARIED OVER 20% FOR " + exchangeRatesCurrent.getCurrency() + "ON " + dateTime.toLocalDate());
                    }
                }
            });
        }
    }

    /**
     *
     * @param exchangeOnDay
     * @return boolean
     */
    private boolean isFileDuplicate(ExchangeOnDay exchangeOnDay) {
        List<ExchangeOnDay> exchangeOnDayLatestList = ProcessedFiles.allProcessedFiles;
        if (exchangeOnDayLatestList.isEmpty()) {
            return false;
        } else {
            ExchangeOnDay exchangeOnDayLatest = exchangeOnDayLatestList.get(ProcessedFiles.allProcessedFiles.size() - 1);
            if (exchangeOnDay.getLocalDateTime().toLocalDate().isEqual(exchangeOnDayLatest.getLocalDateTime().toLocalDate())) {
                Map<String, ExchangeRates> currencyExchangeRatesMapPrevious = exchangeOnDayLatest.getExchangeRatesListOnDay().stream().collect(Collectors.toMap(i -> i.getCurrency(), i -> i));
                Map<String, ExchangeRates> currencyExchangeRatesMapCurrent = exchangeOnDay.getExchangeRatesListOnDay().stream().collect(Collectors.toMap(i -> i.getCurrency(), i -> i));
                AtomicInteger counter = new AtomicInteger();
                currencyExchangeRatesMapPrevious.forEach((k, v) -> {
                    if (currencyExchangeRatesMapCurrent.get(k).equals(v)) {
                        counter.getAndIncrement();
                    }
                });
                return currencyExchangeRatesMapCurrent.size() == counter.get();
            } else {
                return false;
            }
        }
    }
}
