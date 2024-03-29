/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.converter.data;

import com.cosmos.acacia.converter.annotation.DataColumn;
import java.math.BigDecimal;

/*
FILE={NAME="%AluPath%parts.usr",DESC="Каталожни номера",QRY=1,MDFY=1,DEL=1,PRG=1,DBNAME="Btrieve",ENCR=N,
FLD={DESC="Индекс отстъпка",PIC="U3",STRG=1,ATTR=A,SIZ=3,TYP=16,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Индекс мито",PIC="U3",STRG=1,ATTR=A,SIZ=3,TYP=16,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Номер на детайл",PIC="#4",STRG=4,ATTR=N,SIZ=2,WHLE=4,TYP=1,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Сигнатура",PIC="U-U5-U2-U-U2",STRG=1,ATTR=A,SIZ=11,TYP=6,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Колич. в една опак.",PIC="#4",STRG=5,ATTR=N,SIZ=2,WHLE=4,TYP=1,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Единична цена:бруто",PIC="#8.#6C",STRG=6,ATTR=N,SIZ=8,WHLE=8,DECI=6,TYP=4,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Минимално количество",PIC="#9",STRG=4,ATTR=N,SIZ=4,WHLE=9,TYP=2,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Налично количество",PIC="#9",STRG=4,ATTR=N,SIZ=4,WHLE=9,TYP=2,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Очаквано количество",PIC="#9",STRG=4,ATTR=N,SIZ=4,WHLE=9,TYP=2,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Запазено количество",PIC="#9",STRG=4,ATTR=N,SIZ=4,WHLE=9,TYP=2,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Количество за промян",PIC="#9",STRG=4,ATTR=N,SIZ=4,WHLE=9,TYP=2,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="БФ Количество",PIC="#9",STRG=4,ATTR=N,SIZ=4,WHLE=9,TYP=2,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Валута",PIC="U3",RNG="DEM,FRF,BGL,EUR",STRG=1,ATTR=A,SIZ=3,TYP=12,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
KEY={DESC="по сигнатура",MOD=S,DIR=T,RNG=Q,KEY_TYPE=R,
*/
/**
 *
 * @author Miro
 */
public class Product {

    @DataColumn(length=3, maskFormat="U3")
    private String discountCode;

    @DataColumn(length=3, maskFormat="U3")
    private String customsCode;

    @DataColumn(length=4, maskFormat="#4")
    private int productNameCode;

    @DataColumn(length=15, maskFormat="U-U5-U2-U-U2")
    private String productCode;

    @DataColumn(length=4, maskFormat="#4")
    private int quantityPerPackage;

    @DataColumn(length=17, maskFormat="#8.#6C")
    private BigDecimal unitPrice;

    @DataColumn(length=9, maskFormat="#9")
    private int minQuantity;

    @DataColumn(length=9, maskFormat="#9")
    private int quantity;

    @DataColumn(length=9, maskFormat="#9")
    private int expectedQuantity;

    @DataColumn(length=9, maskFormat="#9")
    private int reservedQuantity;

    @DataColumn(length=9, maskFormat="#9")
    private int quantityChange;

    @DataColumn(length=9, maskFormat="#9")
    private int notInvoicedQuantity;

    @DataColumn(length=3, maskFormat="U3")
    private String currencyCode;

    public int getProductNameCode() {
        return productNameCode;
    }

    public void setProductNameCode(int productNameCode) {
        this.productNameCode = productNameCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode(String customsCode) {
        this.customsCode = customsCode;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public int getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(int expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public int getNotInvoicedQuantity() {
        return notInvoicedQuantity;
    }

    public void setNotInvoicedQuantity(int notInvoicedQuantity) {
        this.notInvoicedQuantity = notInvoicedQuantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }

    public int getQuantityPerPackage() {
        return quantityPerPackage;
    }

    public void setQuantityPerPackage(int quantityPerPackage) {
        this.quantityPerPackage = quantityPerPackage;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(discountCode);
        sb.append(", ").append(customsCode);
        sb.append(", ").append(productNameCode);
        sb.append(", ").append(productCode);
        sb.append(", ").append(quantityPerPackage);
        sb.append(", ").append(unitPrice);
        sb.append(", ").append(minQuantity);
        sb.append(", ").append(quantity);
        sb.append(", ").append(expectedQuantity);
        sb.append(", ").append(reservedQuantity);
        sb.append(", ").append(quantityChange);
        sb.append(", ").append(notInvoicedQuantity);
        sb.append(", ").append(currencyCode);

        return sb.toString();
    }
}
