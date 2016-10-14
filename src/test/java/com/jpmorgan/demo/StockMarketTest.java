package com.jpmorgan.demo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;
import static com.jpmorgan.demo.TestUtil.trades;


/**
 * eriashin
 * 13.10.2016
 */
public class StockMarketTest {
    @Test(expected = IllegalStateException.class)
    public void testException() {
        StockMarket indexMonitor = new StockMarket();
        indexMonitor.calculateDividendYield(Stocks.POP.getSymbol(), new BigDecimal(1));
    }

    @Test
    public void testNewStock() {
        StockMarket indexMonitor = new StockMarket();
        indexMonitor.addStocks(Stocks.POP);
        Assert.assertEquals(
            Stocks.POP.getLastDividend(),
            indexMonitor.calculateDividendYield(Stocks.POP.getSymbol(), new BigDecimal(1))
        );
    }

    @Test
    public void testVolumeWeightedStockPrice() throws ParseException {
        DecimalFormat df = TestUtil.getDecimalFormat();

        Collection<Trade> trades1 = trades("TEA 01012016 00:00:01 1 Buy 0.01");
        Assert.assertEquals("0.01", df.format(StockMarket.calculateVolumeWeightedStockPrice(trades1)));

        Collection<Trade> trades2 = trades(
            "TEA 01012016 00:00:01 1 Buy 0.01",
            "TEA 01012016 00:00:02 2 Buy 0.01");
        Assert.assertEquals("0.01", df.format(StockMarket.calculateVolumeWeightedStockPrice(trades2)));

        Collection<Trade> trades3 = trades(
            "TEA 01012016 00:00:02 2 Buy 0.01",
            "TEA 01012016 00:00:03 2 Buy 0.02");
        Assert.assertEquals("0.015", df.format(StockMarket.calculateVolumeWeightedStockPrice(trades3)));

        Collection<Trade> trades4 = trades(
            "TEA 01012016 00:00:02 2 Buy 1",
            "TEA 01012016 00:00:03 2 Buy 3");
        Assert.assertEquals("2.00", df.format(StockMarket.calculateVolumeWeightedStockPrice(trades4)));

        Assert.assertNull(StockMarket.calculateVolumeWeightedStockPrice(Collections.emptyList()));
    }
}
