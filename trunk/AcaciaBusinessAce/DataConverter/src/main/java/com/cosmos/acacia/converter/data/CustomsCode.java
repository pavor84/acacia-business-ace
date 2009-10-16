/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.converter.data;

import com.cosmos.acacia.converter.annotation.DataColumn;
import java.math.BigDecimal;


/*
FILE={NAME="%AluPath%mita.usr",DESC="Мита",QRY=1,MDFY=1,DEL=1,PRG=1,DBNAME="Btrieve",ENCR=N,
FLD={DESC="Индекс мито",PIC="U3",STRG=1,ATTR=A,SIZ=3,TYP=16,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Мито %",PIC="N##.##Z A",STRG=6,ATTR=N,SIZ=4,WHLE=2,DECI=2,TYP=5,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
FLD={DESC="Текст",PIC="31",STRG=1,ATTR=A,SIZ=31,TYP=17,HLP=2,RSRCE={MDFY=Y,TRNS=S,DEF=N,MOD=B,CTRL=E,CTRL_STP="         0.00000",NUL_ALW=N}},
*/
/**
 *
 * @author Miro
 */
public class CustomsCode {

    @DataColumn(length=3, maskFormat="U3")
    private String customsCode;

    @DataColumn(length=6, maskFormat="N##.##Z A")
    private BigDecimal customsDutyPercent;

    @DataColumn(length=31, maskFormat="31")
    private String description;

    public String getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode(String customsCode) {
        this.customsCode = customsCode;
    }

    public BigDecimal getCustomsDutyPercent() {
        return customsDutyPercent;
    }

    public void setCustomsDutyPercent(BigDecimal customsDutyPercent) {
        this.customsDutyPercent = customsDutyPercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(customsCode);
        sb.append(", ").append(customsDutyPercent);
        sb.append(", ").append(description);

        return sb.toString();
    }
}
