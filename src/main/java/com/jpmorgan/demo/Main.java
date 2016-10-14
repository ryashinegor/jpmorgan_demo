package com.jpmorgan.demo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Demo console application main class.
 * Processes commands from text file in UTF8 encoding.
 *
 * Contains predefined stocks TEA, POP, JOE, ALE, JIN.
 *
 * Examples:
 * "AddStock POP Common 8 0 100" - adds a stock with last dividend (8) and fixed dividend (0)
 * "DividendYield POP 100" - calculates DY for POP with price 100
 * "PERatio POP 100" - calculates PE for POP with price 100
 * "RecordTrade TEA 01012016 00:00:01 1 Buy 0.01" - record a trade with time (01012016 00:00:01)
 *  quantity (1), indicator (1) and price (0.01)
 * "VWSP TEA" - calculate Volume Weighted Stock Price for TEA
 * "CalculateIndex" - calculates All Share Index for trades within 5 minutes window
 */
public class Main {
    private final StockMarket indexMonitor;

    public Main(StockMarket indexMonitor) {
        this.indexMonitor = indexMonitor;
    }

    /**
     * Run with filename as argument.
     */
    public static void main(String[] args) throws ParseException {
        if (args.length != 1) {
            System.out.println("No file specified");
            return;
        }
        try (FileReader source = new FileReader(args[0])) {
            StockMarket indexMonitor = new StockMarket();
            fillStocks(indexMonitor);
            new Main(indexMonitor).process(source);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file");
        } catch (IOException e) {
            System.err.println("IO failure: " + e.getMessage());
        }
    }

    private static void fillStocks(StockMarket indexMonitor) {
        indexMonitor.addStocks(
            Stocks.TEA,
            Stocks.POP,
            Stocks.GIN,
            Stocks.JOE,
            Stocks.ALE
        );
    }

    public static void prn(Object s) {
        System.out.println(s);
    }

    public void process(Readable source) throws ParseException {
        try (Scanner scanner = new Scanner(source)) {
            while (scanner.hasNext()) {
                try {
                    String command = scanner.next();
                    switch (command) {
                        case "AddStock":
                            indexMonitor.addStocks(Stock.valueOf(scanner.nextLine().trim()));
                            break;
                        case "RecordTrade":
                            indexMonitor.record(Util.createTrade(scanner.nextLine().trim()));
                            break;
                        case "CalculateIndex":
                            prn(indexMonitor.calculateAllShareIndex());
                            break;
                        case "DividendYield":
                            prn(indexMonitor.calculateDividendYield(
                                scanner.next(), scanner.nextBigDecimal()
                            ));
                            break;
                        case "PERatio":
                            prn(indexMonitor.calculateEarningsRatio(
                                scanner.next(), scanner.nextBigDecimal()
                            ));
                            break;
                        case "VWSP":
                            prn(indexMonitor.calculateVolumeWeightedStockPrice(
                                scanner.next())
                            );
                            break;
                        case "Exit":
                            return;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid command");
                }
            }
        }
    }
}
