package com.jpmorgan.demo;

import java.math.BigDecimal;

/**
 * Calculates financial indicators based on price.
 * eriashin
 * 10.10.2016
 */
public class PriceBasedCalculator {
    private final BigDecimal price;

    public PriceBasedCalculator(BigDecimal price) {
        if (Validator.isNegativeOrZero(price))
            throw new IllegalArgumentException("Invalid price " + price);
        this.price = price;
    }

    public BigDecimal calculateDividendYield(Stock stock) {
        if (price.equals(BigDecimal.ZERO))
                return null;
        if (stock.getType().isCommon())
            return stock.getLastDividend().divide(price);
        else
            return stock.getFixedDividend().multiply(stock.getParValue()).divide(price);
    }

    public BigDecimal calculatePriceEarningsRatio(Stock stock) {
        if (stock.getLastDividend().equals(BigDecimal.ZERO))
            return null;
        return price.divide(stock.getLastDividend());
    }
}
