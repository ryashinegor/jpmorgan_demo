package com.jpmorgan.demo;

import java.math.BigDecimal;

/**
 * Validates financial values.
 * eriashin
 * 13.10.2016
 */
public class Validator {
    public static boolean isNegativeOrZero(BigDecimal price) {
        return price == null || price.doubleValue() <= 0;
    }
    public static boolean isNegative(BigDecimal price) {
        return price == null || price.doubleValue() < 0;
    }
}
