package com.cosmos.acacia.util.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface NumberNamesAlgorithm {

    String getNumberName(BigDecimal decimal);

    String getNumberName(BigInteger integer);
}
