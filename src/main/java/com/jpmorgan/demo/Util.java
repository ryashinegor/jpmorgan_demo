package com.jpmorgan.demo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * eriashin
 * 14.10.2016
 */
public class Util {
    private static SimpleDateFormat simpleDateFormat = getSimpleDateFormat();

    public static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat("ddMMyyyy HH:mm:ss");
    }

    public static Instant createTime(String string) throws ParseException {
       return toInstant(simpleDateFormat.parse(string));
    }

    public static Trade createTrade(String definition) throws ParseException {
        String[] tokens = definition.split(" ");
        Date date = simpleDateFormat.parse(tokens[1] + " " + tokens[2]);
        return new Trade(tokens[0], toInstant(date), Integer.valueOf(tokens[3]), TradeType.valueOf(tokens[4]), new BigDecimal(tokens[5]));
    }

    private static Instant toInstant(Date date) {
        return date.toInstant();
    }
}
