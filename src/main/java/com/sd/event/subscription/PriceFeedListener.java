package com.sd.event.subscription;

import com.sd.event.Listener;
import com.sd.event.Transformer;
import com.sd.event.feed.PriceFeed;

import java.util.Optional;

public class PriceFeedListener implements Listener<Optional<PriceFeed>> {
    private final Transformer<PriceFeed>  transformer;

    PriceFeedListener (final Transformer<PriceFeed> transformer) {
        this.transformer = transformer;
    }

    @Override
    public Optional<PriceFeed> onMessage(String message) {
        return transformer.onTransform(message) ;
    }
}
