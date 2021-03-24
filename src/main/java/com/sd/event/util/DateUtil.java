package com.sd.event.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Calendar helper for date 01-06-2020 12:01:01:001
 */
public class DateUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss:SSS");

    public static final String UTC = "UTC";

    /**
     * If date corrupted we should discard the data
     * @param source
     * @return
     */
    public static Date transform(String source) {
        try {
            sdf.setTimeZone(TimeZone.getTimeZone(UTC));
            return sdf.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException("Received incorrect time=" + source);
        }
    }
}
