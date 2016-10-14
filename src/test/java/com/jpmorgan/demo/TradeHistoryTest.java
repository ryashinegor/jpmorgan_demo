package com.jpmorgan.demo;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.time.Instant;
import java.util.*;

import static org.junit.Assert.assertEquals;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;

/**
 * eriashin
 * 10.10.2016
 */
public class TradeHistoryTest {
    @Test
    public void testLookup() throws ParseException {
        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.record(TestUtil.createTrade("TEA 01012016 00:00:01 1 Buy 0.01"));
        tradeHistory.record(TestUtil.createTrade("TEA 01012016 00:00:02 2 Buy 0.01"));
        Collection<Trade> map = tradeHistory.lookupLastTrades(toTime("01012016 00:00:02"));

        assertEquals(1, map.size());
        Trade trade = map.iterator().next();
        Assert.assertThat(map, IsIterableContainingInOrder.contains(Util.createTrade("TEA 01012016 00:00:02 2 Buy 0.01")));
        assertEquals("TEA 01012016 00:00:02 2 Buy 0.01", trade.toString());

        tradeHistory.record(TestUtil.createTrade("TEA 01012016 00:00:03 3 Buy 0.01"));
        Collection<Trade> map2 = tradeHistory.lookupLastTrades(toTime("01012016 00:00:02"));

        Assert.assertThat(map2, IsIterableContainingInOrder.contains(
                Util.createTrade("TEA 01012016 00:00:02 2 Buy 0.01"),
                Util.createTrade("TEA 01012016 00:00:03 3 Buy 0.01")));
    }


    @Test
    public void testAllShareIndex() throws ParseException {
        Assert.assertTrue(Double.isNaN(StockMarket.calculateAllShareIndex(Collections.emptyList())));

        Collection<Trade> trades = TestUtil.trades(
                "TEA 01012016 00:00:01 1 Buy 0.01",
                "TEA 01012016 00:00:02 1 Buy 0.01",
                "POP 01012016 00:00:01 1 Buy 0.01",
                "POP 01012016 00:00:02 1 Buy 0.01"
            );
        Assert.assertEquals(Math.pow(0.0001, 0.5), StockMarket.calculateAllShareIndex(trades), 0.00001);

        Collection<Trade> trades2 = TestUtil.trades(
                "TEA 01012016 00:00:01 2 Buy 1",
                "TEA 01012016 00:00:02 2 Buy 3",
                "POP 01012016 00:00:01 2 Buy 1",
                "POP 01012016 00:00:02 2 Buy 3"
            );
        // 4 ^ (1/2) = 2
        Assert.assertEquals(2, StockMarket.calculateAllShareIndex(trades2), 0.00001);
    }

    private Instant toTime(String from) throws ParseException {
        return Util.createTime(from);
    }
}
