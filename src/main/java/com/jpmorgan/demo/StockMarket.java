package com.jpmorgan.demo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Accumulates stocks and trades.
 * Provides all calculations.
 * <p>
 * eriashin
 * 13.10.2016
 */
public class StockMarket {
    private final StockProvider stockProvider = new StockProvider();
    private final TradeHistory tradeHistory = new TradeHistory();

    /**
     * Calculate Volume Weighted Stock Price for given trades.
     */
    public static BigDecimal calculateVolumeWeightedStockPrice(Collection<Trade> trades) {
        BigDecimal valueSum = new BigDecimal(0);
        BigDecimal totalQuantity = new BigDecimal(0);
        for (Trade trade : trades) {
            valueSum = valueSum.add(trade.getValue());
            totalQuantity = totalQuantity.add(trade.getQuantity());
        }
        if (totalQuantity.equals(BigDecimal.ZERO))
            return null;
        return valueSum.divide(totalQuantity);
    }

    public static double calculateAllShareIndex(Collection<Trade> trades) {
        HashMap<String, VolumeWeightedPriceCalculator> calculators = new HashMap<>();
        for (Trade trade : trades) {
            VolumeWeightedPriceCalculator calculator = calculators.get(trade.getSymbol());
            if (calculator == null)
                calculators.put(trade.getSymbol(), calculator = new VolumeWeightedPriceCalculator());
            calculator.addTrade(trade);
        }

        BigDecimal bigDecimal = new BigDecimal(1);
        for (VolumeWeightedPriceCalculator calculator : calculators.values()) {
            bigDecimal = bigDecimal.multiply(calculator.calculatePrice());
        }
        return Math.pow(bigDecimal.doubleValue(), 1.0/calculators.size());
    }

    /**
     * Add stocks.
     */
    public void addStocks(Stock... stocks) {
        for (Stock stock : stocks) {
            stockProvider.addStock(stock);
        }
    }

    /**
     * Calculates All Share Index for accumulated trades in past 5 minutes.
     */
    public double calculateAllShareIndex() {
        Collection<Trade> trades = tradeHistory.lookupLast5Minutes();
        return calculateAllShareIndex(trades);
    }

    public BigDecimal calculateDividendYield(String symbol, BigDecimal bigDecimal) {
        Stock stock = getStock(symbol);
        return new PriceBasedCalculator(bigDecimal).calculateDividendYield(stock);
    }

    public BigDecimal calculateEarningsRatio(String symbol, BigDecimal bigDecimal) {
        Stock stock = getStock(symbol);
        return new PriceBasedCalculator(bigDecimal).calculatePriceEarningsRatio(stock);
    }

    public Stock getStock(String symbol) {
        Stock stock = stockProvider.getStock(symbol);
        if (stock == null)
            throw new IllegalStateException("No such stock " + symbol);
        return stock;
    }

    public void record(Trade trade) {
        tradeHistory.record(trade);
    }

    public BigDecimal calculateVolumeWeightedStockPrice(String symbol) {
        Collection<Trade> trades = tradeHistory.lookupLast5Minutes();
        List<Trade> collect = trades.stream().filter(trade -> trade.getSymbol().equals(symbol)).collect(Collectors.toList());
        return calculateVolumeWeightedStockPrice(collect);
    }

    public List<Trade> getAllTrades() {
        return tradeHistory.getTrades();
    }
}
