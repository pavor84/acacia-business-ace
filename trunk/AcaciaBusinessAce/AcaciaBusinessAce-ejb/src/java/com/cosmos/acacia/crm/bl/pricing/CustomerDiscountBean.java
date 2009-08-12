/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crm.bl.pricing;

import com.cosmos.acacia.app.AcaciaSessionLocal;
import com.cosmos.acacia.crm.bl.impl.EntityStoreManagerLocal;
import com.cosmos.acacia.crm.data.contacts.BusinessPartner;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.product.Product;
import com.cosmos.acacia.crm.data.product.ProductCategory;
import com.cosmos.acacia.crm.data.customer.CustomerDiscount;
import com.cosmos.acacia.crm.data.customer.CustomerDiscountItem;
import com.cosmos.acacia.crm.data.customer.CustomerDiscountItemByCategory;
import com.cosmos.acacia.crm.data.customer.CustomerDiscountItemByProduct;
import com.cosmos.beansbinding.EntityProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 *
 * @author Miro
 */
@Stateless
public class CustomerDiscountBean implements CustomerDiscountRemote, CustomerDiscountLocal {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private AcaciaSessionLocal acaciaSession;

    @EJB
    private EntityStoreManagerLocal esm;

    public Connection getConnection() {
//        ((org.hibernate.ejb.EntityManagerImpl)em);
//        return em.getSession().connection();
        System.out.println("em: " + em);
        return null;
    }

    @Override
    public CustomerDiscount getCustomerDiscount(BusinessPartner customer) {
        System.out.println("getCustomerDiscount(" + customer + ")");
        Query q = em.createNamedQuery("CustomerDiscount.findByCustomer");
        System.out.println("q: " + q);
        q.setParameter("customer", customer);
        try {
            return (CustomerDiscount)q.getSingleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }

    @Override
    public CustomerDiscount newCustomerDiscount(BusinessPartner customer) {
        CustomerDiscount customerDiscount = new CustomerDiscount();
        customerDiscount.setOrganizationId(acaciaSession.getOrganization().getId());
        customerDiscount.setCustomer(customer);
        return customerDiscount;
    }

    @Override
    public CustomerDiscount saveCustomerDiscount(CustomerDiscount customerDiscount) {
        esm.persist(em, customerDiscount);
        return customerDiscount;
    }

    @Override
    public boolean deleteCustomerDiscount(CustomerDiscount customerDiscount) {
        em.remove(customerDiscount);
        return true;
    }

    @Override
    public EntityProperties getCustomerDiscountEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(CustomerDiscount.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public EntityProperties getCustomerDiscountItemEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(CustomerDiscountItem.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        return entityProperties;
    }

    @Override
    public EntityProperties getCustomerDiscountItemByCategoryEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(CustomerDiscountItemByCategory.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        entityProperties.addEntityProperties(getCustomerDiscountItemEntityProperties());
        return entityProperties;
    }

    @Override
    public EntityProperties getCustomerDiscountItemByProductEntityProperties() {
        EntityProperties entityProperties = esm.getEntityProperties(CustomerDiscountItemByProduct.class);
        entityProperties.setUpdateStrategy(UpdateStrategy.READ_WRITE);
        entityProperties.addEntityProperties(getCustomerDiscountItemEntityProperties());

        return entityProperties;
    }

    @Override
    public List<CustomerDiscountItem> getCustomerDiscountItems(CustomerDiscount customerDiscount) {
        //Query q = em.createNamedQuery("CustomerDiscountItem.findByCustomerDiscount");
        Query q = em.createNamedQuery("CustomerDiscountItem.findByParentId");
        q.setParameter("parentDataObjectId", customerDiscount.getCustomerDiscountId());
        return new ArrayList<CustomerDiscountItem>(q.getResultList());
    }

    @Override
    public CustomerDiscountItemByProduct getCustomerDiscountItem(
            CustomerDiscount customerDiscount, Product product) {
        Query q = em.createNamedQuery("CustomerDiscountItemByProduct.findByCustomerDiscountAndProduct");
        q.setParameter("customerDiscount", customerDiscount);
        q.setParameter("product", product);
        return (CustomerDiscountItemByProduct)q.getSingleResult();
    }

    @Override
    public CustomerDiscountItemByCategory getCustomerDiscountItem(
            CustomerDiscount customerDiscount, ProductCategory productCategory) {
        Query q = em.createNamedQuery("CustomerDiscountItemByCategory.findByCustomerDiscountAndCategory");
        q.setParameter("customerDiscount", customerDiscount);
        q.setParameter("category", productCategory);
        return (CustomerDiscountItemByCategory)q.getSingleResult();
    }

    @Override
    public CustomerDiscountItemByCategory newCustomerDiscountItemByCategory(CustomerDiscount customerDiscount) {
        CustomerDiscountItemByCategory itemByCategory = new CustomerDiscountItemByCategory();
        itemByCategory.setCustomerDiscount(customerDiscount);
        return itemByCategory;
    }

    @Override
    public CustomerDiscountItemByProduct newCustomerDiscountItemByProduct(CustomerDiscount customerDiscount) {
        CustomerDiscountItemByProduct itemByProduct = new CustomerDiscountItemByProduct();
        itemByProduct.setCustomerDiscount(customerDiscount);
        return itemByProduct;
    }

    @Override
    public CustomerDiscountItem saveCustomerDiscountItem(CustomerDiscountItem customerDiscountItem) {
        esm.persist(em, customerDiscountItem);
        return customerDiscountItem;
    }

    @Override
    public boolean deleteCustomerDiscountItem(CustomerDiscountItem customerDiscountItem) {
        em.remove(customerDiscountItem);
        return true;
    }

    public List<CustomerDiscountItem> getCustomerDiscountItemsTest(CustomerDiscount customerDiscount) {
        Connection conn = getConnection();
        BigDecimal customerDiscountId = new BigDecimal(customerDiscount.getCustomerDiscountId());
        try {
            PreparedStatement pstmt =
                    conn.prepareStatement(CustomerDiscountItem.SQL_SELECT_CUSTOMER_DISCOUNT_ITEMS_BY_CUSTOMER_DISCOUNT);
            pstmt.setBigDecimal(1, customerDiscountId);
            pstmt.setBigDecimal(2, customerDiscountId);
            ResultSet rs = pstmt.executeQuery();
            List<CustomerDiscountItem> items = new ArrayList<CustomerDiscountItem>();
            BigInteger itemId;
            BigInteger id;
            CustomerDiscountItem item;
            CustomerDiscountItemByCategory categoryItem;
            CustomerDiscountItemByProduct productItem;
            while (rs.next()) {
                itemId = rs.getBigDecimal("customer_discount_item_id").toBigInteger();
                BigDecimal discountPercent = rs.getBigDecimal("discount_percent");
                String discriminatorId = rs.getString("discriminator_id");
                switch (rs.getInt("clazz_")) {
                    case 1:
                        item = categoryItem = new CustomerDiscountItemByCategory(itemId);
                        id = rs.getBigDecimal("category_id").toBigInteger();
                        categoryItem.setCategory(em.find(ProductCategory.class, id));
                        categoryItem.setIncludeHeirs(rs.getBoolean("include_heirs"));
                        break;

                    case 2:
                        item = productItem = new CustomerDiscountItemByProduct(itemId);
                        id = rs.getBigDecimal("product_id").toBigInteger();
                        productItem.setProduct(em.find(Product.class, id));
                        break;

                    default:
                        throw new IllegalStateException("Unknown class type.");
                }
                item.setDiscountPercent(discountPercent);
                item.setDiscriminatorId(discriminatorId);
                item.setDataObject(em.find(DataObject.class, itemId));
                item.setCustomerDiscount(customerDiscount);
                items.add(item);
            }
            return items;
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }
}
