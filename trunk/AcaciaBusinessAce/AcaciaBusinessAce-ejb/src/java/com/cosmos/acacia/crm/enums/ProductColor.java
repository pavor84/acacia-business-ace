/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.awt.Color;
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
public enum ProductColor
    implements DatabaseResource
{

    BlackDesktopComputer("Black", Color.black, Category.DesktopComputer),
    SilverDesktopComputer("Silver", Color.lightGray, Category.DesktopComputer),
    BlackMobileComputer("Black", Color.black, Category.MobileComputer),
    SilverMobileComputer("Silver", Color.lightGray, Category.MobileComputer),
    BlackServerComputer("Black", Color.black, Category.ServerComputer),
    ;

    public enum Category {
        DesktopComputer,
        MobileComputer,
        ServerComputer
    };

    private ProductColor(
            String colorName,
            Color color,
            Category category)
    {
        this(colorName, color, category, null, null);
    }

    private ProductColor(
            String colorName,
            Color color,
            Category category,
            String notes,
            String description)
    {
        this.colorName = colorName;
        this.color = color;
        this.category = category;
        this.notes = notes;
        this.description = description;
    }

    private Category category;
    private String colorName;
    private Color color;
    private String notes;
    private String description;
    private DbResource dbResource;

    public Category getCategory() {
        return category;
    }

    public Color getColor() {
        return color;
    }

    public String getColorName() {
        return colorName;
    }

    public String getNotes() {
        return notes;
    }

    public String getDescription() {
        return description;
    }

    public DbResource getDbResource() {
        return dbResource;
    }

    public void setDbResource(DbResource dbResource) {
        this.dbResource = dbResource;
    }

    public String toShortText()
    {
        return getColorName();
    }

    public String toText()
    {
        return null;
    }


    @Override
    public String toString() {
        return super.toString() + ", " + dbResource;
    }


    private static EnumMap<Category, EnumSet<ProductColor>> productColorsByCategory;
    public static EnumSet<ProductColor> getProductColorsByCategory(Category category)
    {
        if(productColorsByCategory == null)
        {
            EnumMap<Category, List<ProductColor>> enumsByCategory = new EnumMap<Category, List<ProductColor>>(Category.class);
            for(ProductColor productColor : ProductColor.values())
            {
                Category key = productColor.getCategory();
                List<ProductColor> enums = enumsByCategory.get(key);
                if(enums == null)
                {
                    enums = new ArrayList<ProductColor>();
                    enumsByCategory.put(key, enums);
                }
                enums.add(productColor);
            }

            productColorsByCategory = new EnumMap<Category, EnumSet<ProductColor>>(Category.class);
            for(Category key : enumsByCategory.keySet())
            {
                productColorsByCategory.put(key, EnumSet.copyOf(enumsByCategory.get(key)));
            }
        }

        return productColorsByCategory.get(category);
    }

    private static EnumMap<Category, List<DbResource>> dbResourcesByCategory;
    public static List<DbResource> getDbResourcesByCategory(Category category)
    {
        if(dbResourcesByCategory == null)
        {
            dbResourcesByCategory = new EnumMap<ProductColor.Category, List<DbResource>>(Category.class);
            getProductColorsByCategory(Category.DesktopComputer);

            for(Category key : productColorsByCategory.keySet())
            {
                EnumSet<ProductColor> measureUnits = productColorsByCategory.get(key);
                List<DbResource> resources = new ArrayList<DbResource>(measureUnits.size());
                for(ProductColor measureUnit : measureUnits)
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
            dbResources = new ArrayList<DbResource>(ProductColor.values().length);

            for(ProductColor unit : ProductColor.values())
            {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }
}
