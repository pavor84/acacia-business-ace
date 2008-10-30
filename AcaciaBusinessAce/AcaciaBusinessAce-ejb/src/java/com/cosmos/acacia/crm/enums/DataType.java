/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.util.AcaciaUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;


/**
 *
 * @author Miro
 */
public enum DataType
    implements DatabaseResource
{
    IntegerType("IntegerType", AcaciaUtils.getIntegerFormat()),
    DecimalType("DecimalType", AcaciaUtils.getDecimalFormat()),
    Percent("Percent", AcaciaUtils.getPercentFormat()),
    DateType("DateType", AcaciaUtils.getShortDateFormat()),
    TimeType("TimeType", AcaciaUtils.getShortTimeFormat()),
    DayType("DayType", AcaciaUtils.getDayFormat()),
    HourType("HourType", AcaciaUtils.getHourFormat()),
    MinuteType("MinuteType", AcaciaUtils.getMinuteFormat()),
    StringType("StringType"),
    EnumerationType("EnumerationType"),
    ;

    private static final Logger logger = Logger.getLogger(DataType.class);

    private DataType(String name)
    {
        this(name, null);
    }

    private DataType(String name, Format format)
    {
        this.name = name;
        this.format = format;
    }

    public String getName()
    {
        return name;
    }

    public Format getFormat()
    {
        return format;
    }

    @Override
    public DbResource getDbResource() {
        return dbResource;
    }

    @Override
    public void setDbResource(DbResource resource) {
        this.dbResource = resource;
    }

    private String name;
    private Format format;
    private DbResource dbResource;

    @Override
    public String toShortText()
    {
        return getName();
    }

    @Override
    public String toText()
    {
        return getName();
    }

    public Serializable toDataType(Serializable source)
    {
        logger.info("DataType.toDataType: " + source);

        if(source == null)
            throw new IllegalArgumentException("The source can not be null.");

        if(source instanceof String)
            source = ((String)source).trim();

        switch(this)
        {
            case IntegerType:
                if(source instanceof Number)
                    return ((Number)source).intValue();
                if(source instanceof String)
                    return Integer.valueOf((String)source);
                throw new NumberFormatException(String.valueOf(source));

            case DecimalType:
                if(source instanceof BigDecimal)
                    return source;
                if(source instanceof Number)
                    return new BigDecimal(((Number)source).doubleValue());
                if(source instanceof String)
                    return new BigDecimal((String)source);
                return new BigDecimal(String.valueOf(source));

            case DateType:
                if(source instanceof Date)
                    return source;
                try
                {
                    if(source instanceof String)
                        return (Serializable)format.parseObject((String)source);
                    return (Serializable)format.parseObject(String.valueOf(source));
                }
                catch(ParseException ex)
                {
                    throw new IllegalArgumentException(ex);
                }

            case StringType:
                if(source instanceof String)
                    return source;
                return String.valueOf(source);
        }

        return null;
    }

    private static List<DbResource> dbResources;
    public static List<DbResource> getDbResources()
    {
        if(dbResources == null)
        {
            dbResources = new ArrayList<DbResource>(DataType.values().length);

            for(DataType item : DataType.values())
            {
                dbResources.add(item.getDbResource());
            }
        }

        return dbResources;
    }

}
