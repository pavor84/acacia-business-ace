/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui.assembling;

import com.cosmos.acacia.crm.bl.assembling.AssemblingRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.assembling.AssemblingCategory;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Miro
 */
public class AssemblingCategoryTreeTablePanel
    extends AbstractTreeTablePanel<AssemblingCategory>
{
    @EJB
    private static AssemblingRemote formSession;

    public AssemblingCategoryTreeTablePanel(AssemblingCategory assemblingCategory)
    {
        super(assemblingCategory);
    }

    @Override
    protected List<AssemblingCategory> getChildren(AssemblingCategory parent, boolean allHeirs)
    {
        return getFormSession().getAssemblingCategories(parent, allHeirs);
    }

    @Override
    protected AssemblingCategory onEditEntity(AssemblingCategory entity)
    {
        AssemblingCategoryPanel editPanel = new AssemblingCategoryPanel(entity);
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return (AssemblingCategory)editPanel.getSelectedValue();
        }

        return null;
    }

    @Override
    protected AssemblingCategory newEntity(AssemblingCategory parentEntity)
    {
        return getFormSession().newAssemblingCategory(parentEntity);
    }

    @Override
    protected boolean deleteRow(AssemblingCategory entity)
    {
        return getFormSession().deleteAssemblingCategory(entity);
    }

    @Override
    protected int getChildCount(DataObjectBean parent)
    {
        return getFormSession().getAssemblingCategoryChildCount((AssemblingCategory)parent);
    }

    @Override
    protected EntityProperties getEntityProperties()
    {
        return getFormSession().getAssemblingCategoryEntityProperties();
    }

    protected AssemblingRemote getFormSession()
    {
        if(formSession == null)
        {
            formSession = getRemoteBean(this, AssemblingRemote.class);
        }

        return formSession;
    }

}
