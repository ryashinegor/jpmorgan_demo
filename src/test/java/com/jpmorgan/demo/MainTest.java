package com.jpmorgan.demo;

import java.io.CharArrayReader;
import java.text.ParseException;
import org.junit.Assert;
import org.junit.Test;

/**
 * eriashin
 * 14.10.2016
 */
public class MainTest {
    @Test
    public void testAdd() throws ParseException {
        StockMarket indexMonitor = new StockMarket();
        Main main = new Main(indexMonitor);
        main.process(createSource(
            "AddStock POP Common 8 0 100",
            "DividendYield POP 100",
            "PERatio POP 100"
            ));
        Assert.assertEquals(
            Stocks.POP,
            indexMonitor.getStock(Stocks.POP.getSymbol())
        );
    }

    @Test
    public void testTradeRecord() throws ParseException {
        StockMarket indexMonitor = new StockMarket();
        Main main = new Main(indexMonitor);
        main.process(createSource(
            "AddStock TEA Common 0 0 100",
            "RecordTrade TEA 01012016 00:00:01 1 Buy 0.01",
            "VWSP TEA",
            "CalculateIndex"));
        Assert.assertEquals(
            Util.createTrade("TEA 01012016 00:00:01 1 Buy 0.01"),
            indexMonitor.getAllTrades().get(0)
        );
    }

    public CharArrayReader createSource(String... s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            String s1 = s[i];
            stringBuilder.append(s1).append("\n");
        }
        return new CharArrayReader(stringBuilder.toString().toCharArray());
    }
}
