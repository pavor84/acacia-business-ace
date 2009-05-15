package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rlozanov
 */
public enum Currency implements DatabaseResource {
    Euro("Euro", "European currency.", "EUR"),
    Leva("Bulgarian Leva", "Bulgarian currency", "BGN"),
    Dollar("US Dollar", "Unated States dollar", "USD")
    ;
    private Currency(String name, String description, String code) {
        this.name = name;
        this.desc = description;
        this.code = code;
    }
    
    private String name;
    private String desc;
    private String code;
    private DbResource dbResource;
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.desc;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public DbResource getDbResource() {
        return this.dbResource;
    }

    public void setDbResource(DbResource resource) {
        this.dbResource = resource;
    }

    public String toShortText() {
        return getCode();
    }

    public String toText() {
        return getName();
    }

    @Override
    public String toString() {
        return super.toString() + ", " + dbResource;
    }

    private static List<DbResource> dbResources;
    public static List<DbResource> getDbResources()
    {
        if(dbResources == null)
        {
            dbResources = new ArrayList<DbResource>(Currency.values().length);

            for(Currency currency : Currency.values())
            {
                dbResources.add(currency.getDbResource());
            }
        }

        return dbResources;
    }
    
    /**
     * @param targetCurrency
     * @param sourceCurrency
     * @param sourceAmount
     * @return targetAmount
     * 
     * Currently mock functionality. To be updated when CurrencyRate is implemented.
     */
    public static BigDecimal convertAmount(Currency targetCurrency, Currency sourceCurrency, BigDecimal sourceAmount) {
        BigDecimal rateMultiplier = new BigDecimal("1");
        if ( targetCurrency.equals(Currency.Leva) ){
            if ( sourceCurrency.equals(Currency.Euro) ){
                rateMultiplier = new BigDecimal("1.9558");
            }else if ( sourceCurrency.equals(Currency.Dollar) ){
                rateMultiplier = new BigDecimal("1.4733");
            }
        }
        return sourceAmount.multiply(rateMultiplier);
    }
}
