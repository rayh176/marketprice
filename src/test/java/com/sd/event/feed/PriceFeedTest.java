package com.sd.event.feed;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PriceFeedTest {

    @ParameterizedTest
    @Tag("DEV")
    @ValueSource(strings = {"106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001", "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002",
            "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002", "108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002",
    "109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100", "110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110"})
    public void convertCsvToPriceFeed(String input){
        PriceFeed priceFeed = new PriceFeed(input);
        assertThat(priceFeed, IsNull.notNullValue());
    }

    @ParameterizedTest
    @Tag("DEV")
    @MethodSource("priceFeedData")
    public void validateCsvToPriceFeed(String input, long expected){
        PriceFeed priceFeed = new PriceFeed(input);
        assertThat(priceFeed.getSequenceId(), is(expected));
    }

    private Stream<Arguments> priceFeedData(){
        return Stream.of(
                Arguments.of("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001", 106L),
                Arguments.of("107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002", 107L),
                Arguments.of("109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100", 109L)
        );
    }

}
