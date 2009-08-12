/**
 * 
 */
package com.cosmos.acacia.crm.bl.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import com.cosmos.acacia.crm.data.sales.Pricelist;
import com.cosmos.acacia.crm.data.sales.PricelistItem;
import com.cosmos.acacia.crm.data.product.SimpleProduct;
import com.cosmos.acacia.crm.data.customer.CustomerDiscount;
import com.cosmos.acacia.crm.data.customer.CustomerDiscountItem;

/**
 * Created : 16.02.2009
 * 
 * @author Petar Milev
 * 
 * A class that contains all information needed to dynamically re-calculate the price according to
 * changing quantities (some of the discounts are based on quantity).
 * Suitable to be used at client-side in response to frequent GUI events like 'key pressed' which lead to 
 * changed quantities and the need for re-calculation of discount.
 * @see #getPrice(BigDecimal)
 * 
 * Also useful for debugging the price-calculation logic.
 * @see #toLongString(NumberFormat) 
 *      #toString()
 */
public class ProductPriceSummary extends ProductPrice {

    public ProductPriceSummary(SimpleProduct product, CustomerDiscount customerDiscount, CustomerDiscountItem customerDiscountItem,
            Map<PricelistItem, Pricelist> items, BigDecimal quantity) {
        setProduct(product);
        setQuantity(quantity);
        setCustomerDiscount(customerDiscount);
        setCustomerDiscountItem(customerDiscountItem);
        setCustomerDiscountPercent(calcCustomerDiscountPercent());
        setPricelistItems(items);
        getPrice();
    }

    private BigDecimal calcCustomerDiscountPercent() {
        BigDecimal result = null;
        if ( customerDiscountItem!=null )
            result = customerDiscountItem.getDiscountPercent();
        else if ( customerDiscount!=null ){
            result = customerDiscount.getDiscountPercent();
        }
        return result;
    }

    private CustomerDiscountItem customerDiscountItem;
    private CustomerDiscount customerDiscount;
    private BigDecimal customerDiscountPercent;
    private BigDecimal discountPercent;
    private BigDecimal pricelistDiscountPercent;
    //The price-list items that qualify as valid for all requirements. 
    //If we know the quantity, we can choose the one that offers the biggest discount.
    private Map<PricelistItem, Pricelist> pricelistItems;
    private BigDecimal quantity;
 
    private String format(NumberFormat f, BigDecimal s) {
        if (s == null)
            return null;
        return f.format(s);
    }

    public String toMediumString(NumberFormat format) {
        return "price: " + format(format, getPrice()) + " discount: "
                + format(format, discountPercent) + " pricelistdisc: "
                + format(format, pricelistDiscountPercent) + " customDiscount: "
                + format(format, customerDiscountPercent);
    }

    public String toString(NumberFormat format) {
        return toMediumString(format) + " pricelist: " + getPriceList().getName() + " item: "
                + getPriceListItem().getId();
    }

    public String toLongString(NumberFormat format) {
        return toString(format) + " min.turnover: " + getPriceList().getMinTurnover() + " per "
                + getPriceList().getMonths() + "m min.qty: "
                + format(format, getPriceListItem().getMinQuantity());
    }
    
    @Override
    public BigDecimal getPrice() {
        return getPrice(quantity);
    }
    
    public BigDecimal getPrice(BigDecimal quantity) {
        this.quantity = quantity;
        BigDecimal price = calculatePrice();
        setPrice(price);
        return super.getPrice();
    }
    
    private BigDecimal calculatePrice() {
        //Get the item which offers the biggest discount
        PricelistItem pricelistItem = getPricelistItemWithBiggestDiscount(pricelistItems, quantity, customerDiscountPercent);
        Pricelist pricelist = pricelistItems.get(pricelistItem);
        
        setPriceList(pricelist);
        setPriceListItem(pricelistItem);
        
        //Find out the price-list discount to be used
        pricelistDiscountPercent = getPricelistDiscountPercent(pricelist, pricelistItem);
        
        //Override the null values with 0
        if ( pricelistDiscountPercent==null )
            pricelistDiscountPercent = new BigDecimal("0");
        if ( customerDiscountPercent==null )
            customerDiscountPercent = new BigDecimal("0");
        
        discountPercent = null;
        
        //if applyClientDiscount - apply both
        if ( pricelistItem.isApplyClientDiscount() ){
            discountPercent = getSequentialDiscount(customerDiscountPercent, pricelistDiscountPercent);
        }
        //if not applyClientDiscount and customerDiscount is bigger - use it
        else if ( customerDiscountPercent.compareTo(pricelistDiscountPercent)>0 ){
            discountPercent = customerDiscountPercent;
        //otherwise use the price-list discount
        }else{
            discountPercent = pricelistDiscountPercent;
        }
        
        if ( quantity==null )
            quantity = new BigDecimal("1");
        
        BigDecimal salePrice = getProduct().getSalesPrice();
        if ( salePrice==null )
            salePrice = new BigDecimal("0");
        
        BigDecimal result = applyDiscountPercent(salePrice, discountPercent);
        return result;
    }
    
    private BigDecimal applyDiscountPercent(BigDecimal price, BigDecimal discountPercent) {
        BigDecimal discountPercentDec = discountPercent.divide(new BigDecimal("100"), MathContext.DECIMAL64);
        price = price.subtract(price.multiply(discountPercentDec));
        return price;
    }
    
    private BigDecimal getPricelistDiscountPercent(Pricelist pricelist, PricelistItem pricelistItem) {
        if ( pricelistItem.getDiscountPercent()!=null )
            return pricelistItem.getDiscountPercent();
        else
            return pricelist.getDefaultDiscount();
    }
    
    private BigDecimal substractPercent(BigDecimal value, BigDecimal percent){
        BigDecimal percentDec = percent.divide(new BigDecimal("100"), MathContext.DECIMAL64);
        BigDecimal percentFromValue = value.multiply(percentDec);
        return value.subtract(percentFromValue);
    }
    
    private BigDecimal getSequentialDiscount(BigDecimal discountPercent1, BigDecimal discountPercent2){
        BigDecimal d = new BigDecimal("100");
        d = substractPercent(d, discountPercent1);
        d = substractPercent(d, discountPercent2);
        return new BigDecimal("100").subtract(d);
    }

    public String toString(){
        return toMediumString(DecimalFormat.getNumberInstance());
    }

    public CustomerDiscount getCustomerDiscount() {
        return customerDiscount;
    }

    public void setCustomerDiscount(CustomerDiscount customerDiscount) {
        this.customerDiscount = customerDiscount;
    }

    public CustomerDiscountItem getCustomerDiscountItem() {
        return customerDiscountItem;
    }

    public void setCustomerDiscountItem(CustomerDiscountItem customerDiscountItem) {
        this.customerDiscountItem = customerDiscountItem;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getPricelistDiscountPercent() {
        return pricelistDiscountPercent;
    }

    public void setPricelistDiscountPercent(BigDecimal pricelistDiscountPercent) {
        this.pricelistDiscountPercent = pricelistDiscountPercent;
    }

    public BigDecimal getCustomerDiscountPercent() {
        return customerDiscountPercent;
    }

    public void setCustomerDiscountPercent(BigDecimal customerDiscountPercent) {
        this.customerDiscountPercent = customerDiscountPercent;
    }

    //The price-list items that qualify as valid for all requirements. 
    //If we know the quantity, we can choose the one that offers the biggest discount.
    public Map<PricelistItem, Pricelist> getPricelistItems() {
        return pricelistItems;
    }

    public void setPricelistItems(Map<PricelistItem, Pricelist> pricelistItems) {
        this.pricelistItems = pricelistItems;
    }
    
    /**
     * @param items 
     * @param quantity - a quantity in the base product measure unit
     * @param customerDiscountPercent - may be null. Assuming no customer discount in this case.
     * Otherwise a value in range 0-100 is expected.
     * @return
     */
    private PricelistItem getPricelistItemWithBiggestDiscount(Map<PricelistItem, Pricelist> items, BigDecimal quantity,
                                                             BigDecimal customerDiscountPercent){
        if ( customerDiscountPercent==null )
            customerDiscountPercent = new BigDecimal("0");
        if ( quantity==null )
            quantity = new BigDecimal("1");
        
        BigDecimal biggestDiscountPercent = new BigDecimal("0");
        PricelistItem itemWithBiggestDiscount = null;
        
        for (PricelistItem item : items.keySet()) {
            Pricelist pricelist = items.get(item);
            
            //skip if min quantity > quantity
            if ( item.getMinQuantity()!=null && item.getMinQuantity().compareTo(quantity)>0 ){
                continue;
            }
            
            BigDecimal currentDiscount = null;
            BigDecimal pricelistDiscount = item.getDiscountPercent();
            if ( pricelistDiscount==null ){
                pricelistDiscount = pricelist.getDefaultDiscount();
            }
            BigDecimal customerDiscount = null;
            if ( item.isApplyClientDiscount() ){
                customerDiscount = customerDiscountPercent;
            }
            if ( customerDiscount!=null && pricelistDiscount!=null )
                currentDiscount = getSequentialDiscount(customerDiscount, pricelistDiscount);
            else if ( customerDiscount!=null )
                currentDiscount = customerDiscount;
            else 
                currentDiscount = pricelistDiscount;
            
            if ( currentDiscount==null )
                currentDiscount = new BigDecimal("0");
            
            //if no item still found or the current is with biggest discount - remember it
            if ( itemWithBiggestDiscount==null ||
                    currentDiscount.compareTo(biggestDiscountPercent)>0 ){
                biggestDiscountPercent = currentDiscount;
                itemWithBiggestDiscount = item;
            }
        }
        
        return itemWithBiggestDiscount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
