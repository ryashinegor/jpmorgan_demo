package com.jpmorgan.demo;

import java.util.HashMap;

/**
 * eriashin
 * 13.10.2016
 */
public class StockProvider {
    private HashMap<String, Stock> stockMap = new HashMap<>();

    public void addStock(Stock stock) {
        stockMap.put(stock.getSymbol(), stock);
    }

    public Stock getStock(String symbol) {
        return stockMap.get(symbol);
    }
}
