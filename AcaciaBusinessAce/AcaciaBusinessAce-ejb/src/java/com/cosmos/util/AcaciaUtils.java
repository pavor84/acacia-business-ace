/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Miro
 */
public class AcaciaUtils
{
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private static MaskFormatter maskFormatter;
    private static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private static NumberFormat percentFormat = NumberFormat.getPercentInstance();

    public static String formatMoney(BigDecimal value)
    {
        return null;
    }
}
