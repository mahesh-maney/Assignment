package com.swiss.re.exchange;


import com.swiss.re.exchange.persistance.ProcessedFiles;
import com.swiss.re.exchange.service.InputProcessorService;
import com.swiss.re.exchange.service.InputProcessorServiceImpl;
import com.swiss.re.exchange.util.DateUtil;
import com.swiss.re.exchange.util.ExchangeOnDay;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class InputProcessorServiceTest {

    @After
    public void tearDown() {
        resetService();
    }

    private void resetService() {
        ProcessedFiles.allProcessedFiles = new ArrayList<>();
        ProcessedFiles.exchangeOnDayInfo = new HashMap<>();
    }

    // TBD : Later, Need to add couple of tests for handling Exceptions.

    @Test
    public void fileShouldBeProcessedAndPersistedTest() {
        InputProcessorService processorService = new InputProcessorServiceImpl();
        processorService.process(getTestFixture()[0]);
        assertThat(ProcessedFiles.allProcessedFiles.size(), equalTo(1));
        assertThat(ProcessedFiles.exchangeOnDayInfo.size(), equalTo(1));
        LocalDate localDate = DateUtil.converter("17:12:59 10/14/2018").toLocalDate();
        assertThat(ProcessedFiles.exchangeOnDayInfo.containsKey(localDate), equalTo(true));
    }

    @Test
    public void fileShouldBeProcessedAndPersistedOnceForDupsTest() {
        InputProcessorService processorService = new InputProcessorServiceImpl();
        processorService.process(getTestFixture()[0]);
        assertThat(ProcessedFiles.allProcessedFiles.size(), equalTo(1));
        assertThat(ProcessedFiles.exchangeOnDayInfo.size(), equalTo(1));
        LocalDate localDate = DateUtil.converter("17:12:59 10/14/2018").toLocalDate();
        assertThat(ProcessedFiles.exchangeOnDayInfo.containsKey(localDate), equalTo(true));
        processorService.process(getTestFixture()[0]);
        assertThat(ProcessedFiles.allProcessedFiles.size(), equalTo(1));
        assertThat(ProcessedFiles.exchangeOnDayInfo.size(), equalTo(1));
    }

    @Test
    public void fileShouldBeProcessedAndPersistedValuesValidateTest() {
        InputProcessorService processorService = new InputProcessorServiceImpl();
        processorService.process(getTestFixture()[0]);
        assertThat(ProcessedFiles.allProcessedFiles.size(), equalTo(1));
        assertThat(ProcessedFiles.exchangeOnDayInfo.size(), equalTo(1));
        LocalDate localDate = DateUtil.converter("17:12:59 10/14/2018").toLocalDate();
        ExchangeOnDay exchangeOnDay = ProcessedFiles.exchangeOnDayInfo.get(localDate);
        exchangeOnDay.getExchangeRatesListOnDay().stream().forEach(exchangeRates -> {
            switch (exchangeRates.getCurrency()) {
                case "CHF":
                    assertThat(exchangeRates.getExchangeRate(), equalTo(new Double(0.9832)));
                    break;
                case "GBP":
                    assertThat(exchangeRates.getExchangeRate(), equalTo(new Double(0.7849)));
                    break;
                case "EUR":
                    assertThat(exchangeRates.getExchangeRate(), equalTo(new Double(0.8677)));
                    break;
            }
        });
    }

    @Test
    public void testForExchangeRateShouldbeFlagged() {
        InputProcessorService processorService = new InputProcessorServiceImpl();
        processorService.process(getTestFixture()[0]);
        processorService.process(getTestFixture()[1]);
        LocalDate localDate = DateUtil.converter("17:12:59 10/15/2018").toLocalDate();
        ExchangeOnDay exchangeOnDay15 = ProcessedFiles.exchangeOnDayInfo.get(localDate);
        ExchangeOnDay exchangeOnDay14 = ProcessedFiles.exchangeOnDayInfo.get(localDate.minusDays(1));
        exchangeOnDay15.getFlaggedExchangeRates().stream().forEach(exchangeRates -> {
            assertThat(exchangeRates.isFlagged(), equalTo(Boolean.TRUE));
        });
        exchangeOnDay14.getFlaggedExchangeRates().stream().forEach(exchangeRates -> {
            assertThat(exchangeRates.isFlagged(), equalTo(Boolean.FALSE));
        });
    }


    private String[] getTestFixture() {
        String[] data = new String[3];
        data[0] = "START-OF-FILE" +
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

        data[1] = "START-OF-FILE" +
                "DATE=20181016" +
                "START-OF-FIELD-LIST" +
                "CURRENCY" +
                "EXCHANGE_RATE" +
                "LAST_UPDATE" +
                "END-OF-FIELD-LIST" +
                "START-OF-EXCHANGE-RATES" +
                "CHF|1.9832|17:12:59 10/15/2018|" +
                "GBP|1.7849|17:12:59 10/15/2018|" +
                "EUR|1.8677|17:13:00 10/15/2018|" +
                "END-OF-EXCHANGE-RATES" +
                "END-OF-FILE";
        return data;
    }
}
