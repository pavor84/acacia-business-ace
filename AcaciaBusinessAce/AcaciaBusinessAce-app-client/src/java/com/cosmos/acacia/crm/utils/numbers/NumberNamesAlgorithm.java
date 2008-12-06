package com.cosmos.acacia.crm.utils.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface NumberNamesAlgorithm {

    String getNumberName(BigDecimal decimal);
    String getNumberName(BigInteger integer);

}
