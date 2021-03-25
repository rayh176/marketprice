package com.sd.event.subscription;

import com.sd.event.Mutator;
import com.sd.event.Publisher;
import com.sd.event.Transformer;
import com.sd.event.feed.PriceFeed;
import com.sd.event.mutate.MarginMutator;
import com.sd.event.publish.PriceFeedPublisher;
import com.sd.event.transform.CSVTransformer;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PriceFeedListenerTest {

    private PriceFeedListener priceFeedListener;

    @BeforeEach
    public void init(){
        final Mutator marginMutator = new MarginMutator();
        final Publisher publisher = new PriceFeedPublisher();
        final Transformer<PriceFeed> transformer = new CSVTransformer(marginMutator, publisher);
        priceFeedListener = new PriceFeedListener(transformer);
    }

    @ParameterizedTest
    @Tag("DEV")
    @MethodSource("marketData")
    public void testOnMessageSuccess(String marketData, double expectedBid, double expectedAsk){
        Optional<PriceFeed> optionalPriceFeed = priceFeedListener.onMessage(marketData);
        assertTrue(optionalPriceFeed.isPresent());
        PriceFeed priceFeed = optionalPriceFeed.get();
        assertThat(priceFeed, IsNull.notNullValue());
        assertThat(priceFeed.getBid().doubleValue(), is(expectedBid));
        assertThat(priceFeed.getAsk().doubleValue(), is(expectedAsk));
    }

    private Stream<Arguments> marketData(){
        return Stream.of(
                Arguments.of("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001", 1.09989, 1.20012),
                Arguments.of("107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002", 119.58804, 119.91199),
                Arguments.of("109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100", 1.24978, 1.25623),
                Arguments.of("108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002", 1.24987, 1.25613),
                Arguments.of("109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100", 1.24978, 1.25623),
                Arguments.of("110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110", 119.59804, 119.92199)
        );
    }
}
