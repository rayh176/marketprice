package com.sd.event.publish;

import com.sd.event.Publisher;
import com.sd.event.feed.PriceFeed;

/**
 * MarketData publisher
 */
public class PriceFeedPublisher implements Publisher<PriceFeed> {

    /**
     * @param priceFeed
     * @return
     */
    public PriceFeed publish(PriceFeed priceFeed) {
        System.out.println("publishing the pricefeed=" + priceFeed);
        return priceFeed;
    }
}
