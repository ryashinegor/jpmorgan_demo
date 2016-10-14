package com.jpmorgan.demo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * Represents trade.
 * eriashin
 * 10.10.2016
 */
class Trade {
    private final String symbol;
    private final Instant timestamp;
    private final BigDecimal quantity;
    private final TradeType type;
    private final BigDecimal price;

    public Trade(String symbol, Instant timestamp, int quantity, TradeType type, BigDecimal price) {
        if (StringUtils.isEmpty(symbol))
            throw new IllegalArgumentException("No symbol");
        if (timestamp == null)
            throw new IllegalArgumentException("No timestamp");
        if (quantity == 0 || quantity <= 0)
            throw new IllegalArgumentException("Invalid quantity " + quantity);
        if (type == null)
            throw new IllegalArgumentException("No type");
        if (Validator.isNegativeOrZero(price))
            throw new IllegalArgumentException("Invalid price " + price);

        this.symbol = symbol;
        this.timestamp = timestamp;
        this.quantity = new BigDecimal(quantity);
        this.type = type;
        this.price = price;
    }

    public String toString() {
        SimpleDateFormat simpleDateFormat = Util.getSimpleDateFormat();
        String string = simpleDateFormat.format(Date.from(getTimestamp()));
        return String.format("%s %s %s %s %s", symbol, string, getQuantity(), getType(), getPrice());
    }

    public BigDecimal getValue() {
        return getPrice().multiply(getQuantity());
    }

    public String getSymbol() {
        return symbol;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public TradeType getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (!symbol.equals(trade.symbol)) return false;
        if (!timestamp.equals(trade.timestamp)) return false;
        if (!quantity.equals(trade.quantity)) return false;
        if (type != trade.type) return false;
        return price.equals(trade.price);
    }

    @Override
    public int hashCode() {
        int result = symbol.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
