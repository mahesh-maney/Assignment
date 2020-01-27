package com.swiss.re.exchange;

import com.swiss.re.exchange.persistance.ProcessedFiles;
import com.swiss.re.exchange.service.ExchangeReportService;
import com.swiss.re.exchange.service.ExchangeReportServiceImpl;
import com.swiss.re.exchange.service.InputProcessorService;
import com.swiss.re.exchange.service.InputProcessorServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ReportServiceTest {

    @Before
    public void setup() {
        InputProcessorService processorService = new InputProcessorServiceImpl();
        processorService.process(getTestFixture()[0]);
        processorService.process(getTestFixture()[1]);
        processorService.process(getTestFixture()[2]);
        processorService.process(getTestFixture()[3]);
    }

    @After
    public void tearDown() {
        resetService();
    }

    private void resetService() {
        ProcessedFiles.allProcessedFiles = new ArrayList<>();
        ProcessedFiles.exchangeOnDayInfo = new HashMap<>();
    }

    @Test
    public void generateMonthlyReportTest() {
        assertThat(ProcessedFiles.allProcessedFiles.size(), equalTo(4));
        assertThat(ProcessedFiles.exchangeOnDayInfo.size(), equalTo(4));
        ExchangeReportService reportService = new ExchangeReportServiceImpl();
        Map<String, Double> actual = reportService.getMonthlyAverageReport(10, 2018);
        Map<String, Double> expected = new HashMap<>();
        expected.put("CHF", 1.98325);
        expected.put("GBP", 1.78485);
        expected.put("EUR", 1.86775);
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void generateYearlyReportTest() {
        assertThat(ProcessedFiles.allProcessedFiles.size(), equalTo(4));
        assertThat(ProcessedFiles.exchangeOnDayInfo.size(), equalTo(4));
        ExchangeReportService reportService = new ExchangeReportServiceImpl();
        Map<String, Double> actual = reportService.getYearlyAverageReport(2018);
        Map<String, Double> expected = new HashMap<>();
        expected.put("CHF", 1.983225);
        expected.put("GBP", 1.784875);
        expected.put("EUR", 1.867725);
        assertThat(actual, equalTo(expected));
    }

    private String[] getTestFixture() {
        String[] data = new String[5];
        data[0] = "START-OF-FILE" +
                "DATE=20180915" +
                "START-OF-FIELD-LIST" +
                "CURRENCY" +
                "EXCHANGE_RATE" +
                "LAST_UPDATE" +
                "END-OF-FIELD-LIST" +
                "START-OF-EXCHANGE-RATES" +
                "CHF|0.9832|17:12:59 09/14/2018|" +
                "GBP|0.7849|17:12:59 09/14/2018|" +
                "EUR|0.8677|17:13:00 09/14/2018|" +
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

        data[2] = "START-OF-FILE" +
                "DATE=20181116" +
                "START-OF-FIELD-LIST" +
                "CURRENCY" +
                "EXCHANGE_RATE" +
                "LAST_UPDATE" +
                "END-OF-FIELD-LIST" +
                "START-OF-EXCHANGE-RATES" +
                "CHF|2.9832|17:12:59 11/15/2018|" +
                "GBP|2.7849|17:12:59 11/15/2018|" +
                "EUR|2.8677|17:13:00 11/15/2018|" +
                "END-OF-EXCHANGE-RATES" +
                "END-OF-FILE";

        data[3] = "START-OF-FILE" +
                "DATE=20181017" +
                "START-OF-FIELD-LIST" +
                "CURRENCY" +
                "EXCHANGE_RATE" +
                "LAST_UPDATE" +
                "END-OF-FIELD-LIST" +
                "START-OF-EXCHANGE-RATES" +
                "CHF|1.9833|17:12:59 10/16/2018|" +
                "GBP|1.7848|17:12:59 10/16/2018|" +
                "EUR|1.8678|17:13:00 10/16/2018|" +
                "END-OF-EXCHANGE-RATES" +
                "END-OF-FILE";
        return data;
    }
}
