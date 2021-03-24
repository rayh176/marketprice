package com.sd.event.transform;

import com.sd.event.Mutator;
import com.sd.event.Publisher;
import com.sd.event.Transformer;
import com.sd.event.feed.PriceFeed;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Transform the incoming data to pricefeed
 * Sample data as 106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
 */
public class CSVTransformer implements Transformer<PriceFeed> {
    private static final int processorsCount = 1;

    private final Mutator marginMutator;
    private final Publisher publisher;

    private ExecutorService executor;

    public CSVTransformer(final Mutator marginMutator, final Publisher publisher) {
        this.marginMutator = marginMutator;
        this.publisher = publisher;
    }

    public Optional<PriceFeed> onTransform(String data) {
        executor = Executors.newFixedThreadPool(processorsCount);
        // Split the data based on comma
        return data.transform(csvData -> {
            Optional<PriceFeed> optionalPriceFeed = Optional.empty();
            try {
                optionalPriceFeed = Optional.of((PriceFeed) executor.submit(() -> publisher.publish(marginMutator.onMutate(new PriceFeed(csvData)))).get());
                executor.shutdown();
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Interrupted the execution " + e);
            }

            return optionalPriceFeed;
        });
    }
}
