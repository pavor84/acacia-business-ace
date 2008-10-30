/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.math.BigDecimal;

/*
Scientists have adopted the metric system to simplify their calculations and
promote communication across national boundaries. However, there have been two
ideas as to which metric units should be preferred in science. Scientists
working in laboratories, dealing with small quantities and distances, preferred
to measure distance in centimeters and mass in grams. Scientists and engineers
working in larger contexts preferred larger units: meters for distance and
kilograms for mass. Everyone agreed that units of other quantities such as
force, pressure, work, power, and so on should be related in a simple way to the
basic units, but which basic units should be used?
The result was two clusterings of metric units in science and engineering. One
cluster, based on the centimeter, the gram, and the second, is called the CGS
system. The other, based on the meter, kilogram, and second, is called the MKS
system.
When we say, for example, that the dyne is the CGS unit of force, this
determines its definition: it is the force which accelerates a mass of one gram
at the rate of one centimeter per second per second. The MKS unit of force, the
newton, is the force which accelerates a mass of one kilogram at the rate of one
meter per second per second. The ratio between a CGS unit and the corresponding
MKS unit is usually a power of 10. A newton accelerates a mass 1000 times
greater than a dyne does, and it does so at a rate 100 times greater, so there
are 100 000 = 105 dynes in a newton.
The CGS system was introduced formally by the British Association for the
Advancement of Science in 1874. It found almost immediate favor with working
scientists, and it was the system most commonly used in scientific work for many
years. Meanwhile, the further development of the metric system was based on
meter and kilogram standards created and distributed in 1889 by the
International Bureau of Weights and Measures (BIPM). During the 20th century,
metric units based on the meter and kilogram--the MKS units--were used more and
more in commercial transactions, engineering, and other practical areas. By 1950
there was some discomfort among users of metric units, because the need to
translate between CGS and MKS units went against the metric ideal of a universal
measuring system. In other words, a choice needed to be made.
In 1954, the Tenth General Conference on Weights and Measures (CGPM) adopted the
meter, kilogram, second, ampere, degree Kelvin, and candela as the basic units
for all international weights and measures, and in 1960 the Eleventh General
Conference adopted the name International System of Units (SI) for this
collection of units. (The "degree Kelvin" became the kelvin in 1967.) In effect,
these decisions gave the central core of the MKS system preference over the CGS
system. Although some of the CGS units remain in use for a variety of purposes,
they are being replaced gradually by the SI units selected from the MKS system.
*/
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author miro
 */
public enum MeasurementUnit
    implements DatabaseResource
{

    Piece("piece", "pc",
            Category.Integer,
            BigDecimal.ONE, null,
            BigDecimal.ONE, null),

    Centimeter("centimeter", "cm",
            Category.DistanceLength,
            new BigDecimal(0.01), null,
            BigDecimal.ONE, null),
    Meter("meter", "m",
            Category.DistanceLength,
            BigDecimal.ONE, null,
            new BigDecimal(100), Centimeter),
    Decimeter("decimeter", "dm",
            Category.DistanceLength,
            new BigDecimal(0.1), Meter,
            new BigDecimal(100), Centimeter),
    Kilometer("kilometer", "km",
            Category.DistanceLength,
            new BigDecimal(1000), Meter,
            new BigDecimal(100000), Centimeter),
    Millimeter("millimeter", "mm",
            Category.DistanceLength,
            new BigDecimal(0.001), Meter,
            new BigDecimal(0.1), Centimeter),

    Gram("gram", "g",
            Category.MassWeight,
            new BigDecimal(0.001), null,
            BigDecimal.ONE, null),
    Kilogram("kilogram", "kg",
            Category.MassWeight,
            BigDecimal.ONE, null,
            new BigDecimal(1000), Gram),
    TonMetric("ton metric", "t",
            Category.MassWeight,
            BigDecimal.valueOf(1000), Kilogram,
            BigDecimal.valueOf(1000000), Gram),

    SquareCentimeter("square centimeter", "cm2",
            Category.Area,
            new BigDecimal(0.0001), null,
            BigDecimal.ONE, null),
    SquareMeter("square meter", "m2",
            Category.Area,
            BigDecimal.ONE, null,
            BigDecimal.valueOf(10000), SquareCentimeter),
    Area("area", "a",
            Category.Area,
            BigDecimal.valueOf(100), SquareMeter,
            BigDecimal.valueOf(1000000), SquareCentimeter),
    SquareKilometer("square kilometer", "km2",
            Category.Area,
            new BigDecimal(1.0E+6), SquareMeter,
            new BigDecimal(1.0E+10), SquareCentimeter),

    CubicCentimeter("cubic centimeter", "cm3",
            Category.Volume,
            new BigDecimal(1.0E-6), null,
            BigDecimal.ONE, null),
    CubicMeter("cubic meter", "m3",
            Category.Volume,
            BigDecimal.ONE, null,
            new BigDecimal(1.0E+6), CubicCentimeter),
    CubicKilometer("cubic kilometer", "km3",
            Category.Volume,
            new BigDecimal(1.0E+9), CubicMeter,
            new BigDecimal(1.0E+15), CubicCentimeter),
    CubicMillimeter("cubic millimeter", "mm3",
            Category.Volume,
            new BigDecimal(1.0E-9), CubicMeter,
            new BigDecimal(1.0E-3), CubicCentimeter),
    Liter("liter", "lt",
            Category.Volume,
            new BigDecimal(0.001), CubicMeter,
            new BigDecimal(1000), CubicCentimeter),
    BiWeeksPerYear("bi weeks per year", "bwpy",
            Category.TimeIntervalUnits,
            new BigDecimal(26.1), null,
            new BigDecimal(26.1), null),
    Second("second", "sec",
            Category.TimeInterval,
            BigDecimal.ONE, null,
            BigDecimal.ONE, null),
    Minute("minute", "min",
            Category.TimeInterval,
            new BigDecimal(60), Second,
            new BigDecimal(60), Second),
    Hour("hour", "hr",
            Category.TimeInterval,
            new BigDecimal(60), Minute,
            new BigDecimal(60), Minute),
    Day("day", "d",
            Category.TimeInterval,
            new BigDecimal(24), Hour,
            new BigDecimal(24), Hour),
    DayPayroll("day payroll", "da",
            Category.TimeInterval,
            new BigDecimal(8), Hour,
            new BigDecimal(8), Hour),
    BiweekPayroll("biweek payroll", "bwp",
            Category.TimeInterval,
            new BigDecimal(10), DayPayroll,
            new BigDecimal(10), DayPayroll),
    YearPayroll("year payroll", "yp",
            Category.TimeInterval,
            new BigDecimal(261), DayPayroll,
            new BigDecimal(261), DayPayroll),
    YearPayrollAcademic("year payroll academic", "ypa",
            Category.TimeInterval,
            new BigDecimal(195), DayPayroll,
            new BigDecimal(195), DayPayroll),
    Year("year", "y",
            Category.TimeInterval,
            new BigDecimal(365.2422), Day,
            new BigDecimal(365.2422), Day),
    YearShort("year short", "ye",
            Category.TimeInterval,
            new BigDecimal(365), Day,
            new BigDecimal(365), Day),
    Month("month", "mon",
            Category.TimeInterval,
            new BigDecimal(1/12), Year,
            new BigDecimal(1/12), Year),
    MilliSecond("millisecond", "ms",
            Category.TimeInterval,
            new BigDecimal(1/1000), Second,
            new BigDecimal(1/1000), Second),
    MicroSecond("microsecond", "us",
            Category.TimeInterval,
            new BigDecimal(1/1000), MilliSecond,
            new BigDecimal(1/1000), MilliSecond),
    NanoSecond("nanosecond", "ns",
            Category.TimeInterval,
            new BigDecimal(1/1000), MicroSecond,
            new BigDecimal(1/1000), MicroSecond),
    PicoSecond("picosecond", "ps",
            Category.TimeInterval,
            new BigDecimal(1/1000), NanoSecond,
            new BigDecimal(1/1000), NanoSecond),

    KilowattHour("kilowatt hour", "kW/h",
            Category.Energy,
            new BigDecimal(3600000.0), null,
            new BigDecimal(3.6E+13), null),
    WattHour("watt hour", "W/h",
            Category.Energy,
            new BigDecimal(1/1000), KilowattHour,
            new BigDecimal(1/1000), KilowattHour),
    MegawattHour("megawatt hour", "MW/h",
            Category.Energy,
            new BigDecimal(1000), KilowattHour,
            new BigDecimal(1000), KilowattHour),
    ;

    static
    {
        Centimeter.siUnit = Meter;
        Gram.siUnit = Kilogram;
        SquareCentimeter.siUnit = SquareMeter;
        CubicCentimeter.siUnit = CubicMeter;
    }

    public enum Category {

        Acceleration("m/sec2", "cm/sec2"),
        Angle("radian(rad)", "radian(rad)"),
        Area("m2", "cm2"),
        DistanceLength("m", "cm"),
        ElectricCharge("coulomb(C)", "sec A"),
        ElectricCurrent("A", "abampere"),
        ElectricInductance("m2*kg/sec2A2(henry,H)", "abhenry"),
        ElectricPotential("J/C(Volt,V)", "abvolt"),
        ElectricResistance("ohm", "abohm"),
        Energy("m2*kg/sec2(Joule)", "cm2*g/sec2(erg)"),
        Finance("$/sec", "$/sec"),
        FluxDensity("kg/sec2*A(Wb/m2)(T)", "gauss(Gs,G)"),
        Illumination("W/m2", "g/sec3"),
        Integer("pc", "pc"),
        Luminance("W/m2", "g/sec3"),
        LuminousFlux("W", "cm2*g/sec3"),
        MagneticFieldStrength("A/m", "A/cm"),
        MagneticFluxDensity("tesla(T)", "dyn/A*cm(Gs)"),
        MassWeight("kg", "g"),
        Money("U.S. Dollar", "Dollar1914"),
        Percentage("1", "%"),
        Power("m2*kg/sec3(Watt)", "cm2*g/sec3"),
        Pressure("kg/m*sec2(PA)", "g/cm*sec2"),
        Radioactivity("sec-1", "sec-1"),
        Speed("m/sec", "cm/sec"),
        TimeInterval("sec", "sec"),
        TimeIntervalUnits("units", "units"),
        Volume("m3", "cm3");

        private Category(String siUnitName, String cgsUnitName)
        {
            this.siUnitName = siUnitName;
            this.cgsUnitName = cgsUnitName;
        }

        private String siUnitName;
        private String cgsUnitName;

        public String getCgsUnitName() {
            return cgsUnitName;
        }

        public String getSiUnitName() {
            return siUnitName;
        }
    };


    private MeasurementUnit(
            String unitName,
            String shortUnitName,
            Category category,
            BigDecimal siUnitValue,
            MeasurementUnit siUnit,
            BigDecimal cgsUnitValue,
            MeasurementUnit cgsUnit)
    {
        this.unitName = unitName;
        this.shortUnitName = shortUnitName;
        this.category = category;
        this.siUnitValue = siUnitValue;
        if(siUnit != null)
            this.siUnit = siUnit;
        else
            this.siUnit = this;
        this.cgsUnitValue = cgsUnitValue;
        if(cgsUnit != null)
            this.cgsUnit = cgsUnit;
        else
            this.cgsUnit = this;
    }


    private String unitName;
    private String shortUnitName;
    private Category category;
    private BigDecimal siUnitValue;
    private MeasurementUnit siUnit;
    private BigDecimal cgsUnitValue;
    private MeasurementUnit cgsUnit;
    private DbResource dbResource;

    public Category getCategory() {
        return category;
    }

    public MeasurementUnit getCgsUnit() {
        return cgsUnit;
    }

    public BigDecimal getCgsUnitValue() {
        return cgsUnitValue;
    }

    public String getShortUnitName() {
        return shortUnitName;
    }

    public MeasurementUnit getSiUnit() {
        return siUnit;
    }

    public BigDecimal getSiUnitValue() {
        return siUnitValue;
    }

    public String getUnitName() {
        return unitName;
    }

    public BigDecimal toSiUnit(MeasurementUnit unit)
    {
        if(!category.equals(unit.category))
            throw new IllegalArgumentException("Can not convert units with different categories: " +
                    category + " and " + unit.category);

        if(unit.equals(this))
            return BigDecimal.ONE;

        // ToDo
        return null;
    }

    public BigDecimal toCgsUnit(MeasurementUnit unit)
    {
        if(!category.equals(unit.category))
            throw new IllegalArgumentException("Can not convert units with different categories: " +
                    category + " and " + unit.category);

        if(unit.equals(this))
            return BigDecimal.ONE;

        // ToDo
        return null;
    }

    @Override
    public DbResource getDbResource() {
        return dbResource;
    }

    @Override
    public void setDbResource(DbResource dbResource) {
        this.dbResource = dbResource;
    }

    @Override
    public String toShortText()
    {
        return getShortUnitName();
    }

    @Override
    public String toText()
    {
        return getUnitName();
    }


    @Override
    public String toString() {
        return super.toString() + ", " + dbResource;
    }


    private static EnumMap<Category, EnumSet<MeasurementUnit>> measurementUnitsByCategory;
    public static EnumSet<MeasurementUnit> getMeasurementUnitsByCategory(Category category)
    {
        if(measurementUnitsByCategory == null)
        {
            EnumMap<Category, List<MeasurementUnit>> enumsByCategory = new EnumMap<Category, List<MeasurementUnit>>(Category.class);
            for(MeasurementUnit measureUnit : MeasurementUnit.values())
            {
                Category key = measureUnit.getCategory();
                List<MeasurementUnit> enums = enumsByCategory.get(key);
                if(enums == null)
                {
                    enums = new ArrayList<MeasurementUnit>();
                    enumsByCategory.put(key, enums);
                }
                enums.add(measureUnit);
            }

            measurementUnitsByCategory = new EnumMap<Category, EnumSet<MeasurementUnit>>(Category.class);
            for(Category key : enumsByCategory.keySet())
            {
                measurementUnitsByCategory.put(key, EnumSet.copyOf(enumsByCategory.get(key)));
            }
        }

        return measurementUnitsByCategory.get(category);
    }

    private static EnumMap<Category, List<DbResource>> dbResourcesByCategory;
    public static List<DbResource> getDbResourcesByCategory(Category category)
    {
        if(dbResourcesByCategory == null)
        {
            dbResourcesByCategory = new EnumMap<MeasurementUnit.Category, List<DbResource>>(Category.class);
            getMeasurementUnitsByCategory(Category.Integer);

            for(Category key : measurementUnitsByCategory.keySet())
            {
                EnumSet<MeasurementUnit> measureUnits = measurementUnitsByCategory.get(key);
                List<DbResource> resources = new ArrayList<DbResource>(measureUnits.size());
                for(MeasurementUnit measureUnit : measureUnits)
                {
                    resources.add(measureUnit.getDbResource());
                }

                dbResourcesByCategory.put(key, resources);
            }
        }

        return dbResourcesByCategory.get(category);
    }

    private static List<DbResource> dbResources;
    public static List<DbResource> getDbResources()
    {
        if(dbResources == null)
        {
            dbResources = new ArrayList<DbResource>(MeasurementUnit.values().length);

            for(MeasurementUnit unit : MeasurementUnit.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }
}
