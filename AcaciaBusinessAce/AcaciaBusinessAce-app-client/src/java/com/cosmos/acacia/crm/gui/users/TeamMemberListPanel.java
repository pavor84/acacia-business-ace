/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.users;

import com.cosmos.acacia.crm.data.users.Team;
import com.cosmos.acacia.crm.data.users.TeamMember;
import com.cosmos.acacia.gui.entity.AlterationType;
import com.cosmos.acacia.gui.entity.DetailEntityListPanel;
import com.cosmos.acacia.gui.entity.EntityPanel;

/**
 *
 * @author Miro
 */
public class TeamMemberListPanel extends DetailEntityListPanel<Team, TeamMember> {

    public TeamMemberListPanel(EntityPanel<Team> mainEntityPanel, Class<TeamMember> itemEntityClass) {
        super(mainEntityPanel, itemEntityClass);
    }

    @Override
    protected EntityPanel getEntityPanel(TeamMember entity) {
        return new TeamMemberPanel(this, entity);
    }

    @Override
    public void rowChanged(AlterationType alterationType, TeamMember oldRowObject, TeamMember newRowObject) {
        if(AlterationType.Nothing.equals(alterationType)) {
            return;
        }

        /*Team team = getMainEntity();

        BigDecimal netAmount = team.getTotalNetAmount();
        BigDecimal grossAmount = team.getTotalGrossAmount();
        BigDecimal tax = team.getTotalTax();
        BigDecimal quantity = team.getTotalQuantity();
        MoneyUtils moneyUtils = MoneyUtils.getInstance();

        if(AlterationType.Delete.equals(alterationType) || AlterationType.Modify.equals(alterationType)) {
            netAmount = moneyUtils.subtract(netAmount, oldRowObject.getExtendedPrice());
            tax = moneyUtils.subtract(tax, oldRowObject.getTaxValue());
            quantity = moneyUtils.subtract(quantity, oldRowObject.getReceivedQuantity());
        }

        if(AlterationType.Create.equals(alterationType) || AlterationType.Modify.equals(alterationType)) {
            netAmount = moneyUtils.add(netAmount, newRowObject.getExtendedPrice());
            tax = moneyUtils.add(tax, newRowObject.getTaxValue());
            quantity = moneyUtils.add(quantity, newRowObject.getReceivedQuantity());
        }

        grossAmount = moneyUtils.add(netAmount, tax);

        team.setTotalGrossAmount(grossAmount);
        team.setTotalNetAmount(netAmount);
        team.setTotalQuantity(quantity);
        team.setTotalTax(tax);

        getMainEntityPanel().performSave(false);*/
    }

}
