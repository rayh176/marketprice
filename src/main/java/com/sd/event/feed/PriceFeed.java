package com.sd.event.feed;

import com.sd.event.exception.FeedException;
import com.sd.event.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Construct a simple price feed class
 */
public class PriceFeed {
    private static final String regex = ",";

    private final long sequenceId;
    private final Instrument instrument;
    private BigDecimal bid;
    private BigDecimal ask;
    private final Date timeStamp;

    public PriceFeed(String csvData) {
        String[] tmpData = csvData.split(regex);
        //long sequenceId, Instrument instrument, BigDecimal bid, BigDecimal ask, Date timeStamp
        if (tmpData.length != 5) {
            throw new FeedException("CSV data received is corrupted data=" + csvData);
        }
        this.sequenceId = createSequenceId(checkNotNull (tmpData[0]));
        this.instrument = createInstrument(checkNotNull (tmpData[1]));
        this.bid = createBid(checkNotNull (tmpData[2]));
        this.ask =  createAsk(checkNotNull (tmpData[3]));
        this.timeStamp = createTimeStamp(checkNotNull (tmpData[4]));
    }

    public long getSequenceId() {
        return sequenceId;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    private Date createTimeStamp(String timeStamp) {
        return DateUtil.transform(timeStamp);
    }

    private BigDecimal createAsk(String ask) {
        return new BigDecimal(ask.trim());
    }

    public PriceFeed withMarginBid(BigDecimal newBidValue) {
        this.bid = newBidValue;
        return this;
    }

    public PriceFeed withMarginAsk(BigDecimal newAskValue) {
        this.ask = newAskValue;
        return this;
    }

    /**
     * We assume the scaling is correct
     * @param bid
     * @return
     */
    private BigDecimal createBid(String bid) {
        return new BigDecimal(bid.trim());
    }

    private Instrument createInstrument(String inst) {
        return new Instrument(inst);
    }

    private long createSequenceId(String seqId) {
        return Long.parseLong(seqId);
    }

    @Override
    public String toString() {
        return "PriceFeed{" +
                "sequenceId=" + sequenceId +
                ", instrument=" + instrument +
                ", bid=" + bid +
                ", ask=" + ask +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
