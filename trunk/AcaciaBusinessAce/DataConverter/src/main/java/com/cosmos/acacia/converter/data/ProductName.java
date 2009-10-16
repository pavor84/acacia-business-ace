/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.converter.data;

import com.cosmos.acacia.converter.annotation.DataColumn;


/*
FILE={NAME="%AluPath%details.usr",DESC="Детайли",QRY=1,MDFY=1,DEL=1,PRG=1,DBNAME="Btrieve",ENCR=N,
FLD={DESC="номер на детайл",PIC="#4",STRG=4,ATTR=N,SIZ=2,WHLE=4,TYP=1,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Бълг. Име на Детайл",PIC="UX63",STRG=27,ATTR=M,SIZ=64,TYP=15,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=S,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Име на Детайл",PIC="UX63",STRG=27,ATTR=M,SIZ=64,TYP=15,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=S,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
*/
/**
 *
 * @author Miro
 */
public class ProductName {

    @DataColumn(length=4, maskFormat="#4")
    private int productNameCode;

    @DataColumn(length=64, maskFormat="UX63")
    private String productName;

    @DataColumn(length=64, maskFormat="UX63")
    private String supplierProductName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductNameCode() {
        return productNameCode;
    }

    public void setProductNameCode(int productNameCode) {
        this.productNameCode = productNameCode;
    }

    public String getSupplierProductName() {
        return supplierProductName;
    }

    public void setSupplierProductName(String supplierProductName) {
        this.supplierProductName = supplierProductName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(productNameCode);
        sb.append(", ").append(productName);
        sb.append(", ").append(supplierProductName);

        return sb.toString();
    }
}
