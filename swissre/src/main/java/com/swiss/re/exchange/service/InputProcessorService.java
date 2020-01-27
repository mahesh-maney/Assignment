package com.swiss.re.exchange.service;


/**
 * This interface process the input file containing Exchange Rates.
 * Subscribers may implement this interface and provide concrete implementation
 * for processing the input file.
 * @author ManeyMR
 * @version 1.0
 * @since   2020-01-25
 */

public interface InputProcessorService {
    /**
     *
     * @param input
     */
    void process(String input);
}
