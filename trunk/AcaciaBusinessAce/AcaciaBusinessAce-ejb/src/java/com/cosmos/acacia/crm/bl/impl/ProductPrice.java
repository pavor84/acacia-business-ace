/**
 * 
 */
package com.cosmos.acacia.crm.bl.impl;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cosmos.acacia.crm.data.sales.Pricelist;
import com.cosmos.acacia.crm.data.sales.PricelistItem;
import com.cosmos.acacia.crm.data.product.SimpleProduct;

/**
 * Created	:	10.02.2009
 * @author	Petar Milev
 */
public class ProductPrice implements Serializable {
    private SimpleProduct product;
    private Pricelist priceList;
    private BigDecimal price;
    private PricelistItem priceListItem;
    public ProductPrice(){
    }
    public ProductPrice(SimpleProduct product, Pricelist priceList, BigDecimal price,
                        PricelistItem priceListItem) {
        this.product = product;
        this.priceList = priceList;
        this.price = price;
        this.priceListItem = priceListItem;
    }
    public SimpleProduct getProduct() {
        return product;
    }
    public void setProduct(SimpleProduct product) {
        this.product = product;
    }
    public Pricelist getPriceList() {
        return priceList;
    }
    public void setPriceList(Pricelist priceList) {
        this.priceList = priceList;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public PricelistItem getPriceListItem() {
        return priceListItem;
    }
    public void setPriceListItem(PricelistItem priceListItem) {
        this.priceListItem = priceListItem;
    }
}
