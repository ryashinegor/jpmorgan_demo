package com.jpmorgan.demo;

import java.math.BigDecimal;

/**
 * Accumulates trades and calculates Volume Weighted Price.
 *
 * eriashin
 * 13.10.2016
 */
class VolumeWeightedPriceCalculator {
    private BigDecimal valueSum = new BigDecimal(0);
    private BigDecimal totalQuantity = new BigDecimal(0);

    public void addTrade(Trade trade) {
        valueSum = valueSum.add(trade.getValue());
        totalQuantity = totalQuantity.add(trade.getQuantity());
    }

    public BigDecimal calculatePrice() {
        if (totalQuantity.equals(BigDecimal.ZERO))
            return null;
        return valueSum.divide(totalQuantity);
    }
}
