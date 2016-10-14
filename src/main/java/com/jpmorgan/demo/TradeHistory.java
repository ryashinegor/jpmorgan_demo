package com.jpmorgan.demo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Accumulates trades and provides lookup by time.
 * eriashin
 * 10.10.2016
 */
public class TradeHistory {
    private SortedMap<Instant, Trade> trades = new TreeMap<Instant, Trade>();

    public void record(Trade trade) {
        trades.put(trade.getTimestamp(), trade);
    }

    public Collection<Trade> lookupLastTrades(Instant after) {
        return trades.tailMap(after).values();
    }

    Collection<Trade> lookupLast5Minutes() {
        return lookupLastTrades(Instant.now().minus(5, ChronoUnit.MINUTES));
    }

    public List<Trade> getTrades() {
        return Collections.unmodifiableList(new ArrayList<>(trades.values()));
    }
}
