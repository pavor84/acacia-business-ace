/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.reports;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.gui.AcaciaApplication;
import com.cosmos.acacia.util.AcaciaUtils;
import com.cosmos.resource.BeanResource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.util.Date;

/**
 *
 * @author Bozhidar Bozhanov
 */
public class ReportsUtil {

    public static String getEnumText(DbResource resource) {
        return new BeanResource(AcaciaApplication.class).getShortName(resource);
    }

    public static String format(Number number) {
        if (number == null)
            number = BigDecimal.ZERO;

        return AcaciaUtils.getDecimalFormat().format(number,
                new StringBuffer(),
                new FieldPosition(0)).toString();
    }

    public static String formatPercent(Number number) {
        if (number == null)
            number = BigInteger.ZERO;

        if (number instanceof BigDecimal)
            number = ((BigDecimal) number).toBigInteger();

        //String value = AcaciaUtils.getPercentFormat().format(number.doubleValue());

        return number.toString() + "%";
    }

    public static String formatDate(Date date) {
        if (date == null)
            return "";

        return AcaciaUtils.getShortDateFormat().format(date,
                new StringBuffer(),
                new FieldPosition(0)).toString();
    }
}
