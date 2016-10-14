package com.jpmorgan.demo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * eriashin
 * 13.10.2016
 */
public class TestUtil {
    public static DecimalFormat getDecimalFormat() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        df.setMinimumFractionDigits(2);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(decimalFormatSymbols);
        return df;
    }

    public static Collection<Trade> trades(String... definitions) throws ParseException {
        ArrayList<Trade> trades = new ArrayList<>(definitions.length);
        for (String definition : definitions) {
            trades.add(createTrade(definition));
        }
        return trades;
    }

    public static Trade createTrade(String definition) throws ParseException {
        return Util.createTrade(definition);
    }
}
