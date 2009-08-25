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

    AED("AED", "United Arab Emirates", "AED"),
    ALL("ALL", "Albania", "ALL"),
    ARS("ARS", "Argentina", "ARS"),
    AUD("AUD", "Australia", "AUD"),
    BAM("BAM", "Bosnia and Herzegovina", "BAM"),
    BGN("BGN", "Bulgaria", "BGN"),
    BHD("BHD", "Bahrain", "BHD"),
    BOB("BOB", "Bolivia", "BOB"),
    BRL("BRL", "Brazil", "BRL"),
    BYR("BYR", "Belarus", "BYR"),
    CAD("CAD", "Canada", "CAD"),
    CHF("CHF", "Switzerland", "CHF"),
    CLP("CLP", "Chile", "CLP"),
    CNY("CNY", "China", "CNY"),
    COP("COP", "Colombia", "COP"),
    CRC("CRC", "Costa Rica", "CRC"),
    CSD("CSD", "Serbia and Montenegro", "CSD"),
    CZK("CZK", "Czech Republic", "CZK"),
    DKK("DKK", "Denmark", "DKK"),
    DOP("DOP", "Dominican Republic", "DOP"),
    DZD("DZD", "Algeria", "DZD"),
    EEK("EEK", "Estonia", "EEK"),
    EGP("EGP", "Egypt", "EGP"),
    EUR("EUR", "Austria, Belgium, Cyprus, Finland, France, Germany, Greece, Ireland, Italy, Luxembourg, Malta, Montenegro, Netherlands, Portugal, Slovenia, Spain", "EUR"),
    GBP("GBP", "United Kingdom", "GBP"),
    GTQ("GTQ", "Guatemala", "GTQ"),
    HKD("HKD", "Hong Kong", "HKD"),
    HNL("HNL", "Honduras", "HNL"),
    HRK("HRK", "Croatia", "HRK"),
    HUF("HUF", "Hungary", "HUF"),
    IDR("IDR", "Indonesia", "IDR"),
    ILS("ILS", "Israel", "ILS"),
    INR("INR", "India", "INR"),
    IQD("IQD", "Iraq", "IQD"),
    ISK("ISK", "Iceland", "ISK"),
    JOD("JOD", "Jordan", "JOD"),
    JPY("JPY", "Japan", "JPY"),
    KRW("KRW", "South Korea", "KRW"),
    KWD("KWD", "Kuwait", "KWD"),
    LBP("LBP", "Lebanon", "LBP"),
    LTL("LTL", "Lithuania", "LTL"),
    LVL("LVL", "Latvia", "LVL"),
    LYD("LYD", "Libya", "LYD"),
    MAD("MAD", "Morocco", "MAD"),
    MKD("MKD", "Macedonia", "MKD"),
    MXN("MXN", "Mexico", "MXN"),
    MYR("MYR", "Malaysia", "MYR"),
    NIO("NIO", "Nicaragua", "NIO"),
    NOK("NOK", "Norway", "NOK"),
    NZD("NZD", "New Zealand", "NZD"),
    OMR("OMR", "Oman", "OMR"),
    PAB("PAB", "Panama", "PAB"),
    PEN("PEN", "Peru", "PEN"),
    PHP("PHP", "Philippines", "PHP"),
    PLN("PLN", "Poland", "PLN"),
    PYG("PYG", "Paraguay", "PYG"),
    QAR("QAR", "Qatar", "QAR"),
    RON("RON", "Romania", "RON"),
    RSD("RSD", "Serbia", "RSD"),
    RUB("RUB", "Russia", "RUB"),
    SAR("SAR", "Saudi Arabia", "SAR"),
    SDG("SDG", "Sudan", "SDG"),
    SEK("SEK", "Sweden", "SEK"),
    SGD("SGD", "Singapore", "SGD"),
    SKK("SKK", "Slovakia", "SKK"),
    SVC("SVC", "El Salvador", "SVC"),
    SYP("SYP", "Syria", "SYP"),
    THB("THB", "Thailand", "THB"),
    TND("TND", "Tunisia", "TND"),
    TRY("TRY", "Turkey", "TRY"),
    TWD("TWD", "Taiwan", "TWD"),
    UAH("UAH", "Ukraine", "UAH"),
    USD("USD", "United States, Ecuador, Puerto Rico", "USD"),
    UYU("UYU", "Uruguay", "UYU"),
    VEF("VEF", "Venezuela", "VEF"),
    VND("VND", "Vietnam", "VND"),
    YER("YER", "Yemen", "YER"),
    ZAR("ZAR", "South Africa", "ZAR");

    private Currency(String name, String description, String code) {
        this.name = name;
        this.desc = description;
        this.code = code;
    }
    //
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

    @Override
    public DbResource getDbResource() {
        return this.dbResource;
    }

    @Override
    public void setDbResource(DbResource resource) {
        this.dbResource = resource;
    }

    @Override
    public String toShortText() {
        return getCode();
    }

    @Override
    public String toText() {
        return getName();
    }

    @Override
    public String toString() {
        return super.toString() + ", " + dbResource;
    }
    private static List<DbResource> dbResources;

    public static List<DbResource> getDbResources() {
        if (dbResources == null) {
            dbResources = new ArrayList<DbResource>(Currency.values().length);

            for (Currency currency : Currency.values()) {
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
        if (targetCurrency.equals(Currency.BGN)) {
            if (sourceCurrency.equals(Currency.EUR)) {
                rateMultiplier = new BigDecimal("1.9558");
            } else if (sourceCurrency.equals(Currency.USD)) {
                rateMultiplier = new BigDecimal("1.4733");
            }
        }
        return sourceAmount.multiply(rateMultiplier);
    }
}
