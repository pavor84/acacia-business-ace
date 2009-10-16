/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.converter.data;

import com.cosmos.acacia.converter.annotation.DataColumn;
import java.math.BigDecimal;


/*
FILE={NAME="%AluPath%discount.usr",DESC="Отстъпки",QRY=1,MDFY=1,DEL=1,PRG=1,DBNAME="Btrieve",ENCR=N,
FLD={DESC="Индекс (>> % отст.)",PIC="U3",STRG=1,ATTR=A,SIZ=3,TYP=16,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Отстъпка %",PIC="N##.##Z A",STRG=6,ATTR=N,SIZ=4,WHLE=2,DECI=2,TYP=5,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Коифицент Печалба %",PIC="N##.##Z A",STRG=6,ATTR=N,SIZ=4,WHLE=2,DECI=2,TYP=5,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Текст",PIC="31",STRG=1,ATTR=A,SIZ=31,TYP=17,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
*/
/**
 *
 * @author Miro
 */
public class ProductDiscount {

    @DataColumn(length=3, maskFormat="U3")
    private String discountCode;

    @DataColumn(length=6, maskFormat="N##.##Z A")
    private BigDecimal discountPercent;

    @DataColumn(length=6, maskFormat="N##.##Z A")
    private BigDecimal profitPercent;
    
    @DataColumn(length=31, maskFormat="31")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getProfitPercent() {
        return profitPercent;
    }

    public void setProfitPercent(BigDecimal profitPercent) {
        this.profitPercent = profitPercent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(discountCode);
        sb.append(", ").append(discountPercent);
        sb.append(", ").append(profitPercent);
        sb.append(", ").append(description);

        return sb.toString();
    }
}
