package com.cosmos.acacia.util.numbers;

import java.util.Locale;

public class UnsupportedNumberAlgorithmException extends Exception {

    public UnsupportedNumberAlgorithmException(Locale locale, NumberNamesAlgorithmType type) {
        super("Algorithm not supported for locale: " + locale.toString()
                + ", type: " + type.toString());
    }
}
