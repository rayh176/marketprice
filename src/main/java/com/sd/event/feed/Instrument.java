package com.sd.event.feed;

import static com.google.common.base.Preconditions.checkNotNull;

public class Instrument {

    public static final String REGEX = "/";
    private final String instr;

    public Instrument(String symbol) {
        String [] ccys = symbol.split(REGEX);
        String ccy1 = checkNotNull(ccys[0]);
        String ccy2 = checkNotNull(ccys[1]);
        this.instr = ccy1 + "/" + ccy2;
    }

    public String getInstrument() {
        return instr;
    }

    public boolean match(String ccyPair) {
        if (instr.equalsIgnoreCase(ccyPair)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "instr='" + instr + '\'' +
                '}';
    }
}
