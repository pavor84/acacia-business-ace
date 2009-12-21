/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.impl;

import com.cosmos.acacia.converter.data.Product;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.accounting.CashReconcile;
import com.cosmos.acacia.crm.data.assembling.AssemblingSchema;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.currency.CurrencyExchangeRate;
import com.cosmos.acacia.crm.data.customer.CustomerDiscount;
import com.cosmos.acacia.crm.data.location.Country;
import com.cosmos.acacia.crm.data.purchase.PurchaseInvoice;
import com.cosmos.acacia.crm.data.sales.SalesInvoice;
import com.cosmos.acacia.crm.data.security.SecurityRole;
import com.cosmos.acacia.crm.data.users.User;
import com.cosmos.acacia.crm.data.warehouse.Warehouse;
import javax.ejb.Remote;

/**
 *
 * @author Miro
 */
@Remote
public interface EntityServiceRemote {

    public static final Package ACCOUNTING_PACKAGE = CashReconcile.class.getPackage();
    public static final Package ASSEMBLING_PACKAGE = AssemblingSchema.class.getPackage();
    public static final Package COMMON_PACKAGE = DataObjectBean.class.getPackage();
    public static final Package CONTACTS_PACKAGE = BusinessPartner.class.getPackage();
    public static final Package LOCATION_PACKAGE = Country.class.getPackage();
    public static final Package CURRENCY_PACKAGE = CurrencyExchangeRate.class.getPackage();
    public static final Package CUSTOMERS_PACKAGE = CustomerDiscount.class.getPackage();
    public static final Package PRODUCT_PACKAGE = Product.class.getPackage();
    public static final Package PURCHASE_PACKAGE = PurchaseInvoice.class.getPackage();
    public static final Package SALES_PACKAGE = SalesInvoice.class.getPackage();
    public static final Package SECURITY_PACKAGE = SecurityRole.class.getPackage();
    public static final Package USERS_PACKAGE = User.class.getPackage();
    public static final Package WAREHOUSE_PACKAGE = Warehouse.class.getPackage();
}
