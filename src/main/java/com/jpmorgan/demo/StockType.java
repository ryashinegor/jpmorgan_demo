package com.jpmorgan.demo;

/**
 * eriashin
 * 10.10.2016
 */
enum StockType {
    Common, Preferred;

    public boolean isCommon() {
        return this == Common;
    }
}
