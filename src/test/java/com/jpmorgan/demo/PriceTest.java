package com.jpmorgan.demo;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * eriashin
 * 12.10.2016
 */
public class PriceTest {
    @Test
    public void testDV() {
        DecimalFormat df = TestUtil.getDecimalFormat();

        PriceBasedCalculator price2 = new PriceBasedCalculator(new BigDecimal(2.0));
        Assert.assertEquals("0.00", df.format(price2.calculateDividendYield(Stocks.TEA)));
        Assert.assertEquals("4.00", df.format(price2.calculateDividendYield(Stocks.POP)));
        Assert.assertEquals("1.00", df.format(price2.calculateDividendYield(Stocks.GIN)));

        Assert.assertNull("null", price2.calculatePriceEarningsRatio(Stocks.TEA));
        Assert.assertEquals("0.25", df.format(price2.calculatePriceEarningsRatio(Stocks.POP)));

        PriceBasedCalculator price100 = new PriceBasedCalculator(new BigDecimal(100.0));
        Assert.assertEquals("0.00", df.format(price100.calculateDividendYield(Stocks.TEA)));
        Assert.assertEquals("0.08", df.format(price100.calculateDividendYield(Stocks.POP)));
        Assert.assertEquals("0.02", df.format(price100.calculateDividendYield(Stocks.GIN)));

        Assert.assertNull(price100.calculatePriceEarningsRatio(Stocks.TEA));
        Assert.assertEquals("12.50", df.format(price100.calculatePriceEarningsRatio(Stocks.POP)));
    }
}
