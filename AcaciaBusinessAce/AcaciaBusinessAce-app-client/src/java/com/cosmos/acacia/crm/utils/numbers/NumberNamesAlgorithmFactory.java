package com.cosmos.acacia.crm.utils.numbers;

import java.util.Locale;

import com.cosmos.acacia.crm.enums.Currency;

public class NumberNamesAlgorithmFactory {

    public static NumberNamesAlgorithm getNumberNamesAlgorithm(Locale locale, NumberNamesAlgorithmType type)
        throws UnsupportedNumberAlgorithmException {

        return getNumberNamesAlgorithm(locale, type, null);
    }

    public static NumberNamesAlgorithm getNumberNamesAlgorithm(Locale locale,
            NumberNamesAlgorithmType type, Enum<Currency> enumValue) throws UnsupportedNumberAlgorithmException {

        if (locale.equals(new Locale("bg")) && type == NumberNamesAlgorithmType.MONEY)
            return new BulgarianNumberNamesMoneyAlgorithm(enumValue);

        throw new UnsupportedNumberAlgorithmException(locale, type);
    }
}
