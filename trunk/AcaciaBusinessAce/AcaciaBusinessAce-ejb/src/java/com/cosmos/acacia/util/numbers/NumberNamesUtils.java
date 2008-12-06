package com.cosmos.acacia.util.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.crm.gui.AcaciaApplication;

public class NumberNamesUtils {

    public static BigInteger getWholePart(BigDecimal decimal) {
        return decimal.toBigInteger();
    }

    public static BigInteger getFractionalPart(BigDecimal decimal) {
        return decimal.subtract(
                new BigDecimal(decimal.toBigInteger()))
                .movePointRight(2).toBigInteger();
    }

    public static ResourceMap getResourceMap() {
        return Application.getInstance(AcaciaApplication.class)
            .getContext().getResourceMap(Currency.class);
    }
}
