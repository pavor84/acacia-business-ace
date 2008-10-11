/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.util;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItem;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchemaItemValue;
import com.cosmos.acacia.crm.validation.ValidationException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.text.MaskFormatter;
import org.apache.log4j.Logger;

/**
 *
 * @author Miro
 */
public class AcaciaUtils
{
    private static final Logger logger = Logger.getLogger(AcaciaUtils.class);

    private static NumberFormat decimalFormat;
    private static NumberFormat integerFormat;

    private static MaskFormatter maskFormatter;
    private static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private static NumberFormat percentFormat = NumberFormat.getPercentInstance();

    public static String formatMoney(BigDecimal value)
    {
        return null;
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

        AssemblingSchemaItem.DataType dataType;
        try
        {
            dataType = (AssemblingSchemaItem.DataType)dataTypeResource.getEnumValue();
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
        AssemblingSchemaItem.DataType dataType,
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
            decimalFormat = new DecimalFormat("#,##0.00");
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


}
