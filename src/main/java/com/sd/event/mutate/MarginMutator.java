package com.sd.event.mutate;

import com.sd.event.Mutator;
import com.sd.event.feed.PriceFeed;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MarginMutator implements Mutator<PriceFeed> {
    private static int scale = 5;
    private static final double bidMargin = -0.0001;
    private static final double askMargin = 0.0001;

    @Override
    public PriceFeed onMutate(PriceFeed priceFeed) {
       return priceFeed.withMarginBid(calculateNewBid(priceFeed.getBid().doubleValue()))
               .withMarginAsk(calculateNewAsk(priceFeed.getAsk().doubleValue()));
    }

    private BigDecimal calculateNewBid(double bid) {
        return BigDecimal.valueOf(bid + (bid * bidMargin)).setScale(scale, RoundingMode.HALF_DOWN);
    }

    private BigDecimal calculateNewAsk(double ask) {
        return BigDecimal.valueOf(ask + (ask * askMargin)).setScale(scale, RoundingMode.HALF_DOWN);
    }
}
