package com.jpmorgan.demo;

import org.junit.Assert;
import org.junit.Test;

/**
 * eriashin
 * 13.10.2016
 */
public class StockTest {
    @Test
    public void testValueOf() {
        Stock stock = Stock.valueOf("TEA Common 1 0 100");
        Assert.assertEquals("TEA", stock.getSymbol());
        Assert.assertEquals(StockType.Common, stock.getType());
        Assert.assertEquals(1, stock.getLastDividend().intValue());
        Assert.assertEquals(0, stock.getFixedDividend().intValue());
        Assert.assertEquals(100, stock.getParValue().intValue());
    }
}
