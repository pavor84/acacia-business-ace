package com.cosmos.acacia.util.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.jdesktop.application.ResourceMap;

import com.cosmos.acacia.crm.enums.Currency;


public class BulgarianNumberNamesMoneyAlgorithm extends BaseBulgarianNumberNamesAlgorithm {

    private String currencyName;
    private String currencySingleName;
    private String coinsName;
    private String singleCoinName;

    private GramaticalGender numberGender = GramaticalGender.MASCULINE;

    public BulgarianNumberNamesMoneyAlgorithm(Enum<Currency> currency, ResourceMap r) {
        String currencyEnumName = currency.name();
        currencyName = r.getString(currencyEnumName + ".name");
        currencySingleName = r.getString(currencyEnumName + ".singleName");
        coinsName = r.getString(currencyEnumName + ".coins");
        singleCoinName = r.getString(currencyEnumName + ".singleCoin");
    }

    @Override
    public String getNumberName(BigDecimal decimal) {
        BigInteger wholePart = NumberNamesUtils.getWholePart(decimal);
        String number = getNumberName(NumberNamesUtils.getWholePart(decimal)) + " ";
        if (wholePart.equals(BigInteger.ONE))
            number += currencySingleName;
        else
            number += currencyName;


        setNumberGender(GramaticalGender.FEMININE);
        BigInteger fractionalPart = NumberNamesUtils.getFractionalPart(decimal);

        if (fractionalPart.compareTo(BigInteger.ZERO) > 0) {
            number += AND +  getNumberName(fractionalPart) + " ";
            if (fractionalPart.equals(BigInteger.ONE))
                number += singleCoinName;
            else
                number += coinsName;
        }

        return number;
    }

    @Override
    protected GramaticalGender getNumberGender() {
        return numberGender;
    }

    protected void setNumberGender(GramaticalGender numberGender) {
        this.numberGender = numberGender;
    }
}