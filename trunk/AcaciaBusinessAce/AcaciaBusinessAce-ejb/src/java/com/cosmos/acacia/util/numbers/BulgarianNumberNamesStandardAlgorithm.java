package com.cosmos.acacia.util.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BulgarianNumberNamesStandardAlgorithm extends BaseBulgarianNumberNamesAlgorithm {

    @Override
    public String getNumberName(BigDecimal decimal) {
        // 1 coin precision lost. TODO : proper rounding?
        String number = getNumberName(NumberNamesUtils.getWholePart(decimal));
        BigInteger fractionalPart = NumberNamesUtils.getFractionalPart(decimal);

        if (fractionalPart.compareTo(BigInteger.ZERO) > 0)
            number += " цяло и " +  getNumberName(fractionalPart);

        return number;
    }

    @Override
    protected GramaticalGender getNumberGender() {
        return GramaticalGender.NEUTER;
    }
}
