/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.crm.data.DataObjectEntity;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.EntityProperty;
import com.cosmos.util.ArrayUtils;
import com.cosmos.util.ClassHelper;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Miro
 */
@Stateless
public class EntityServiceBean implements EntityServiceLocal, EntityServiceRemote {

    public static final Set<Package> ALL_PACKAGES;
    //
    public static final Set<Package> ACCOUNTING_OWN_PACKAGES;
    public static final Set<Package> ASSEMBLING_OWN_PACKAGES;
    public static final Set<Package> COMMON_OWN_PACKAGES;
    public static final Set<Package> CONTACTS_OWN_PACKAGES;
    public static final Set<Package> LOCATION_OWN_PACKAGES;
    public static final Set<Package> CURRENCY_OWN_PACKAGES;
    public static final Set<Package> CUSTOMERS_OWN_PACKAGES;
    public static final Set<Package> PRODUCT_OWN_PACKAGES;
    public static final Set<Package> PURCHASE_OWN_PACKAGES;
    public static final Set<Package> SALES_OWN_PACKAGES;
    public static final Set<Package> SECURITY_OWN_PACKAGES;
    public static final Set<Package> USERS_OWN_PACKAGES;
    public static final Set<Package> WAREHOUSE_OWN_PACKAGES;

    static {
        ALL_PACKAGES = ArrayUtils.unmodifiableSet(
                ACCOUNTING_PACKAGE,
                ASSEMBLING_PACKAGE,
                COMMON_PACKAGE,
                CONTACTS_PACKAGE,
                LOCATION_PACKAGE,
                CURRENCY_PACKAGE,
                CUSTOMERS_PACKAGE,
                PRODUCT_PACKAGE,
                PURCHASE_PACKAGE,
                SALES_PACKAGE,
                SECURITY_PACKAGE,
                USERS_PACKAGE,
                WAREHOUSE_PACKAGE);

        ACCOUNTING_OWN_PACKAGES = ArrayUtils.unmodifiableSet(ACCOUNTING_PACKAGE);

        ASSEMBLING_OWN_PACKAGES = ArrayUtils.unmodifiableSet(ASSEMBLING_PACKAGE);

        COMMON_OWN_PACKAGES = ArrayUtils.unmodifiableSet(COMMON_PACKAGE);

        LOCATION_OWN_PACKAGES = ArrayUtils.unmodifiableSet(LOCATION_PACKAGE);

        CURRENCY_OWN_PACKAGES = ArrayUtils.unmodifiableSet(CURRENCY_PACKAGE);

        CONTACTS_OWN_PACKAGES = ArrayUtils.unmodifiableSetByCollections(CONTACTS_PACKAGE, LOCATION_OWN_PACKAGES);

        CUSTOMERS_OWN_PACKAGES = ArrayUtils.unmodifiableSet(CUSTOMERS_PACKAGE);

        PRODUCT_OWN_PACKAGES = ArrayUtils.unmodifiableSet(PRODUCT_PACKAGE);

        PURCHASE_OWN_PACKAGES = ArrayUtils.unmodifiableSet(PURCHASE_PACKAGE);

        SALES_OWN_PACKAGES = ArrayUtils.unmodifiableSetByCollections(SALES_PACKAGE,
                CONTACTS_OWN_PACKAGES, CURRENCY_OWN_PACKAGES, CUSTOMERS_OWN_PACKAGES);

        USERS_OWN_PACKAGES = ArrayUtils.unmodifiableSetByCollections(USERS_PACKAGE, CONTACTS_OWN_PACKAGES);

        SECURITY_OWN_PACKAGES = ArrayUtils.unmodifiableSetByCollections(SECURITY_PACKAGE, USERS_OWN_PACKAGES);

        WAREHOUSE_OWN_PACKAGES = ArrayUtils.unmodifiableSet(WAREHOUSE_PACKAGE);
    }
    //
    private static final HashMap<Package, Set<Package>> ownPackagesMap =
            new HashMap<Package, Set<Package>>();
    private static final HashMap<Package, Set<Package>> relatedPackagesMap =
            new HashMap<Package, Set<Package>>();
    private static final HashSet<Class<? extends DataObjectEntity>> allDataObjectEntities =
            new HashSet<Class<? extends DataObjectEntity>>();
    private static final HashMap<Package, Set<Class<? extends DataObjectEntity>>> dataObjectEntitiesMap =
            new HashMap<Package, Set<Class<? extends DataObjectEntity>>>();
    @EJB
    private EntityStoreManagerLocal esm;

    @Override
    public Set<Package> getAllEntityPackages() {
        return new HashSet<Package>(ALL_PACKAGES);
    }

    @Override
    public Set<Package> getOwnPackages(Package entityPackage) {
        if (ownPackagesMap.isEmpty()) {
            ownPackagesMap.put(ACCOUNTING_PACKAGE, ACCOUNTING_OWN_PACKAGES);
            ownPackagesMap.put(ASSEMBLING_PACKAGE, ASSEMBLING_OWN_PACKAGES);
            ownPackagesMap.put(COMMON_PACKAGE, COMMON_OWN_PACKAGES);
            ownPackagesMap.put(CONTACTS_PACKAGE, CONTACTS_OWN_PACKAGES);
            ownPackagesMap.put(LOCATION_PACKAGE, LOCATION_OWN_PACKAGES);
            ownPackagesMap.put(CURRENCY_PACKAGE, CURRENCY_OWN_PACKAGES);
            ownPackagesMap.put(CUSTOMERS_PACKAGE, CUSTOMERS_OWN_PACKAGES);
            ownPackagesMap.put(PRODUCT_PACKAGE, PRODUCT_OWN_PACKAGES);
            ownPackagesMap.put(PURCHASE_PACKAGE, PURCHASE_OWN_PACKAGES);
            ownPackagesMap.put(SALES_PACKAGE, SALES_OWN_PACKAGES);
            ownPackagesMap.put(SECURITY_PACKAGE, SECURITY_OWN_PACKAGES);
            ownPackagesMap.put(USERS_PACKAGE, USERS_OWN_PACKAGES);
            ownPackagesMap.put(WAREHOUSE_PACKAGE, WAREHOUSE_OWN_PACKAGES);
        }

        return ownPackagesMap.get(entityPackage);
    }

    @Override
    public Set<Package> getRelatedPackages(Package entityPackage) {
        Set<Package> relatedPackages;
        if ((relatedPackages = relatedPackagesMap.get(entityPackage)) == null) {
            relatedPackages = new HashSet<Package>();
            Set<Package> ownPackages = getOwnPackages(entityPackage);
            for (Package pkg : ownPackages) {
                for (Class<? extends DataObjectEntity> cls : getDataObjectEntities(pkg)) {
                    EntityProperties entityProps = esm.getEntityProperties(cls);
                    for (String propertyName : entityProps.getKeys()) {
                        EntityProperty entityProperty = entityProps.getEntityProperty(propertyName);
                        Class propertyClass = entityProperty.getPropertyClass();
                        Package propertyPackage = propertyClass.getPackage();
                        if (DataObjectEntity.class.isAssignableFrom(propertyClass) && !ownPackages.contains(propertyPackage) && !relatedPackages.contains(propertyPackage)) {
                            relatedPackages.add(propertyPackage);
                        }
                    }

                }
            }

            relatedPackagesMap.put(entityPackage, relatedPackages);
        }

        return relatedPackages;
    }

    @Override
    public Set<Class<? extends DataObjectEntity>> getAllDataObjectEntities() {
        if (allDataObjectEntities.size() == 0) {
            for (Package entityPackage : getAllEntityPackages()) {
                allDataObjectEntities.addAll(getDataObjectEntities(entityPackage));
            }
        }

        return new HashSet<Class<? extends DataObjectEntity>>(allDataObjectEntities);
    }

    @Override
    public Set<Class<? extends DataObjectEntity>> getDataObjectEntities(Package entityPackage) {
        Set<Class<? extends DataObjectEntity>> dataObjectEntities;
        if ((dataObjectEntities = dataObjectEntitiesMap.get(entityPackage)) == null) {
            dataObjectEntities = getPackageClasses(entityPackage, DataObjectEntity.class);
            dataObjectEntitiesMap.put(entityPackage, dataObjectEntities);
        }

        return dataObjectEntities;
    }

    private <T> Set<Class<? extends T>> getPackageClasses(Package entityPackage, Class<T> mainClass) {
        Set<Class<? extends T>> packageClasses = new HashSet<Class<? extends T>>();
        try {
            for (Class cls : ClassHelper.getClasses(entityPackage.getName(), true, mainClass)) {
                if (Modifier.isAbstract(cls.getModifiers())) {
                    continue;
                }

                packageClasses.add(cls);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException(ex);
            }
        }

        return packageClasses;
    }
}
