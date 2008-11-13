/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.enums.DataType;
import com.cosmos.acacia.crm.validation.ValidationException;

/**
 *
 * @author Miro
 */
public class AcaciaUtils
{
    private static final Logger logger = Logger.getLogger(AcaciaUtils.class);

    private static Locale locale;

    private static NumberFormat decimalFormat;
    private static NumberFormat percentFormat;
    private static NumberFormat integerFormat;
    private static NumberFormat currencyFormat;

    private static SimpleDateFormat shortDateFormat;
    private static SimpleDateFormat mediumDateFormat;
    private static SimpleDateFormat longDateFormat;

    private static DateFormat shortTimeFormat;
    private static DateFormat mediumTimeFormat;
    private static DateFormat longTimeFormat;

    private static SimpleDateFormat dayFormat;
    private static SimpleDateFormat hourFormat;
    private static SimpleDateFormat minuteFormat;

    private static MaskFormatter maskFormatter;
    private static NumberFormat numberFormat = NumberFormat.getNumberInstance();
    //private static NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private static NumberFormat moneyFormat = createMoneyFormat();

    public static String formatMoney(BigDecimal value)
    {
        return moneyFormat.format(value);
    }

    private static NumberFormat createMoneyFormat() {
        //temporal functionality. TODO improve as appropriate
        NumberFormat f = DecimalFormat.getInstance(Locale.US);
        f.setGroupingUsed(false);
        f.setMaximumFractionDigits(3);
        f.setMinimumFractionDigits(0);
        f.setRoundingMode(RoundingMode.HALF_EVEN);
        return f;
    }

    public static void validateValue(AssemblingSchemaItemValue itemValue)
        throws ValidationException
    {
        logger.info("validateValue(itemValue=" + itemValue + ")");
        ValidationException vex = null;
        AssemblingSchemaItem schemaItem = itemValue.getAssemblingSchemaItem();
        DbResource dataTypeResource = schemaItem.getDataType();

        try
        {
            itemValue.setMinConstraint(
                validateValue(
                    itemValue.getMinConstraint(),
                    dataTypeResource,
                    "minConstraint"));
        }
        catch(ValidationException ex)
        {
            logger.error("minConstraint", ex);
            if(vex == null)
                vex = new ValidationException();
            vex.addMessage("minConstraint", "error.IllegalMinConstraint", ex.getMessage());
        }

        try
        {
            itemValue.setMaxConstraint(
                validateValue(
                    itemValue.getMaxConstraint(),
                    dataTypeResource,
                    "maxConstraint"));
        }
        catch(ValidationException ex)
        {
            logger.error("maxConstraint", ex);
            if(vex == null)
                vex = new ValidationException();
            vex.addMessage("maxConstraint", "error.IllegalMaxConstraint", ex.getMessage());
        }

        //if we have validation messages - throw the exception since not everything is OK
        if(vex != null && vex.getMessages().size() > 0)
            throw vex;
    }

    public static void validateValue(AssemblingSchemaItem schemaItem)
        throws ValidationException
    {
        logger.info("validateValue(schemaItem=" + schemaItem + ")");

        schemaItem.setDefaultValue(
            validateValue(
                schemaItem.getDefaultValue(),
                schemaItem.getDataType(),
                "defaultValue"));
    }

    public static Serializable validateValue(
        Serializable value,
        AssemblingSchemaItem schemaItem,
        String validationTarget)
        throws ValidationException
    {
        logger.info("validateValue(value=" + value +
            ", schemaItem=" + schemaItem +
            ", validationTarget='" + validationTarget + "')");

        return validateValue(value, schemaItem.getDataType(), validationTarget);
    }

    public static Serializable validateValue(
        Serializable value,
        DbResource dataTypeResource,
        String validationTarget)
        throws ValidationException
    {
        logger.info("validateValue(value=" + value +
            ", dataTypeResource=" + dataTypeResource +
            ", validationTarget='" + validationTarget + "')");

        if(dataTypeResource == null)
        {
            ValidationException vex = new ValidationException();
            vex.addMessage("dataType", "error.emptyDataTypeResource");
            throw vex;
        }

        DataType dataType;
        try
        {
            dataType = (DataType)dataTypeResource.getEnumValue();
        }
        catch(Exception ex)
        {
            logger.error("dataType", ex);
            ValidationException vex = new ValidationException();
            vex.addMessage("dataType", "error.wrongDataType", ex.getMessage());
            throw vex;
        }

        return validateValue(value, dataType, validationTarget);
    }

    public static Serializable validateValue(
        Serializable value,
        DataType dataType,
        String validationTarget)
        throws ValidationException
    {
        logger.info("validateValue(value=" + value +
            ", dataType=" + dataType +
            ", validationTarget='" + validationTarget + "')");

        try
        {
            if(value != null)
                return dataType.toDataType(value);
            else
                return null;
        }
        catch(Throwable ex)
        {
            logger.error("Illegal validation target", ex);
            ValidationException vex = new ValidationException();
            StringBuilder sb = new StringBuilder(validationTarget.length());
            sb.append(Character.toUpperCase(validationTarget.codePointAt(0)));
            sb.append(validationTarget.substring(1));
            vex.addMessage(validationTarget, "error.Illegal" + sb.toString(), ex.getMessage());
            throw vex;
        }
    }

    public static NumberFormat getDecimalFormat()
    {
        if(decimalFormat == null)
        {
            decimalFormat = DecimalFormat.getNumberInstance(Locale.US);
            ((DecimalFormat)decimalFormat).applyPattern("0.00");
            decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        }

        return decimalFormat;
    }

    public static NumberFormat getIntegerFormat()
    {
        if(integerFormat == null)
        {
            integerFormat = NumberFormat.getIntegerInstance();
        }

        return integerFormat;
    }

    public static NumberFormat getPercentFormat()
    {
        if(percentFormat == null)
        {
            percentFormat = NumberFormat.getPercentInstance();
        }

        return percentFormat;
    }

    public static SimpleDateFormat getShortDateFormat()
    {
        if(shortDateFormat == null)
        {
            //DateFormat.getDateInstance(DateFormat.SHORT, getLocale());
            shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        }

        return shortDateFormat;
    }

    public static SimpleDateFormat getMediumDateFormat()
    {
        if(mediumDateFormat == null)
        {
            mediumDateFormat = new SimpleDateFormat("dd MMM yyyy");
        }

        return mediumDateFormat;
    }

    public static SimpleDateFormat getLongDateFormat()
    {
        if(longDateFormat == null)
        {
            longDateFormat = new SimpleDateFormat("dd MMMMM yyyy");
        }

        return longDateFormat;
    }

    public static DateFormat getShortTimeFormat()
    {
        if(shortTimeFormat == null)
        {
            shortTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, getLocale());
        }

        return shortTimeFormat;
    }

    public static DateFormat getMediumTimeFormat()
    {
        if(mediumTimeFormat == null)
        {
            mediumTimeFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM, getLocale());
        }

        return mediumTimeFormat;
    }

    public static DateFormat getLongTimeFormat()
    {
        if(longTimeFormat == null)
        {
            longTimeFormat = DateFormat.getTimeInstance(DateFormat.LONG, getLocale());
        }

        return longTimeFormat;
    }

    public static SimpleDateFormat getDayFormat()
    {
        if(dayFormat == null)
        {
            dayFormat = new SimpleDateFormat("D");
        }

        return dayFormat;
    }

    public static SimpleDateFormat getHourFormat()
    {
        if(hourFormat == null)
        {
            hourFormat = new SimpleDateFormat("k");
        }

        return hourFormat;
    }

    public static SimpleDateFormat getMinuteFormat()
    {
        if(minuteFormat == null)
        {
            minuteFormat = new SimpleDateFormat("m");
        }

        return minuteFormat;
    }

    public static NumberFormat getCurrencyFormat()
    {
        if(currencyFormat == null)
        {
            currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        }

        return currencyFormat;
    }

    public static Locale getLocale()
    {
        if(locale == null)
        {
            locale = Locale.getDefault();
        }

        return locale;
    }

    public static void setLocale(Locale locale)
    {
        AcaciaUtils.locale = locale;
    }

}
