/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.enums;

import com.cosmos.acacia.crm.data.DbResource;
import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author miro
 */
public enum ProductColor implements DatabaseResource {

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
            Category category) {
        this(colorName, color, category, null, null);
    }

    private ProductColor(
            String colorName,
            Color color,
            Category category,
            String notes,
            String description) {
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

    public String toShortText() {
        return getColorName();
    }

    public String toText() {
        return null;
    }


    @Override
    public String toString() {
        return super.toString() + ", " + dbResource;
    }


    private static EnumMap<Category, EnumSet<ProductColor>> productColorsByCategory;
    public static EnumSet<ProductColor> getProductColorsByCategory(Category category) {
        if(productColorsByCategory == null) {
            EnumMap<Category, List<ProductColor>> enumsByCategory = new EnumMap<Category, List<ProductColor>>(Category.class);
            for(ProductColor productColor : ProductColor.values()) {
                Category key = productColor.getCategory();
                List<ProductColor> enums = enumsByCategory.get(key);
                if(enums == null) {
                    enums = new ArrayList<ProductColor>();
                    enumsByCategory.put(key, enums);
                }
                enums.add(productColor);
            }

            productColorsByCategory = new EnumMap<Category, EnumSet<ProductColor>>(Category.class);
            for(Category key : enumsByCategory.keySet()) {
                productColorsByCategory.put(key, EnumSet.copyOf(enumsByCategory.get(key)));
            }
        }

        return productColorsByCategory.get(category);
    }

    private static EnumMap<Category, List<DbResource>> dbResourcesByCategory;
    public static List<DbResource> getDbResourcesByCategory(Category category) {
        if(dbResourcesByCategory == null) {
            dbResourcesByCategory = new EnumMap<ProductColor.Category, List<DbResource>>(Category.class);
            getProductColorsByCategory(Category.DesktopComputer);

            for(Category key : productColorsByCategory.keySet()) {
                EnumSet<ProductColor> measureUnits = productColorsByCategory.get(key);
                List<DbResource> resources = new ArrayList<DbResource>(measureUnits.size());
                for(ProductColor measureUnit : measureUnits) {
                    resources.add(measureUnit.getDbResource());
                }

                dbResourcesByCategory.put(key, resources);
            }
        }

        return dbResourcesByCategory.get(category);
    }

    private static List<DbResource> dbResources;
    public static List<DbResource> getDbResources() {
        if(dbResources == null) {
            dbResources = new ArrayList<DbResource>(ProductColor.values().length);

            for(ProductColor unit : ProductColor.values()) {
                dbResources.add(unit.getDbResource());
            }
        }

        return dbResources;
    }
}
