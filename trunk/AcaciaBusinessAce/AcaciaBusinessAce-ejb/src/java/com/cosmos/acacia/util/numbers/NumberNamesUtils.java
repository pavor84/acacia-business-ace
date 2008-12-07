package com.cosmos.acacia.util.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberNamesUtils {

    public static BigInteger getWholePart(BigDecimal decimal) {
        return decimal.toBigInteger();
    }

    public static BigInteger getFractionalPart(BigDecimal decimal) {
        return decimal.subtract(
                new BigDecimal(decimal.toBigInteger()))
                .movePointRight(2).toBigInteger();
    }
}
