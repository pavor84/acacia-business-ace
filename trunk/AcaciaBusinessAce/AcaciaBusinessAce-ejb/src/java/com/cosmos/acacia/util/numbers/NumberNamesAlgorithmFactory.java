package com.cosmos.acacia.util.numbers;

import java.util.Locale;

import org.jdesktop.application.ResourceMap;

import com.cosmos.acacia.crm.enums.Currency;

public class NumberNamesAlgorithmFactory {

    public static NumberNamesAlgorithm getNumberNamesAlgorithm(Locale locale,
            NumberNamesAlgorithmType type,
            ResourceMap map)
        throws UnsupportedNumberAlgorithmException {

        return getNumberNamesAlgorithm(locale, type, null, map);
    }
    public static NumberNamesAlgorithm getNumberNamesAlgorithm(
            Locale locale,
            NumberNamesAlgorithmType type,
            Enum<Currency> enumValue,
            ResourceMap map) throws UnsupportedNumberAlgorithmException {

        if (locale.equals(new Locale("bg")) && type == NumberNamesAlgorithmType.MONEY)
            return new BulgarianNumberNamesMoneyAlgorithm(enumValue, map);

        throw new UnsupportedNumberAlgorithmException(locale, type);
    }
}
