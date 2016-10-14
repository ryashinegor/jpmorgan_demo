package com.jpmorgan.demo;

import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;

/**
 * Represents stock.
 * eriashin
 * 10.10.2016
 */
public class Stock {
    private final String symbol;
    private final StockType type;
    private final BigDecimal lastDividend;
    private final BigDecimal fixedDividend;
    private final BigDecimal parValue;

    public Stock(String symbol, StockType type, BigDecimal lastDividend, BigDecimal fixedDividend, BigDecimal parValue) {
        if (StringUtils.isEmpty(symbol))
            throw new IllegalArgumentException("No symbol");
        if (type == null)
            throw new IllegalArgumentException("No type");
        if (Validator.isNegative(lastDividend))
            throw new IllegalArgumentException("Invalid last dividend " + lastDividend);
        if (Validator.isNegative(fixedDividend))
            throw new IllegalArgumentException("Invalid fixedDividend " + fixedDividend);
        if (Validator.isNegativeOrZero(parValue))
            throw new IllegalArgumentException("Invalid par value " + parValue);

        this.symbol = symbol;
        this.type = type;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
    }

    public static Stock valueOf(String s) {
        String[] split = s.split(" ");
        return new Stock(split[0], StockType.valueOf(split[1]), new BigDecimal(split[2]), new BigDecimal(split[3]), new BigDecimal(split[4]));
    }

    public String getSymbol() {
        return symbol;
    }

    public StockType getType() {
        return type;
    }

    public BigDecimal getLastDividend() {
        return lastDividend;
    }

    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }

    public BigDecimal getParValue() {
        return parValue;
    }

    public String toString() {
        return String.format("%s %s %s %s %s", symbol, type, lastDividend, fixedDividend, parValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (!symbol.equals(stock.symbol)) return false;
        if (type != stock.type) return false;
        if (!lastDividend.equals(stock.lastDividend)) return false;
        if (!fixedDividend.equals(stock.fixedDividend)) return false;
        return parValue.equals(stock.parValue);

    }

    @Override
    public int hashCode() {
        int result = symbol.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + lastDividend.hashCode();
        result = 31 * result + fixedDividend.hashCode();
        result = 31 * result + parValue.hashCode();
        return result;
    }
}
