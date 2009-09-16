/**
 * 
 */
package com.cosmos.acacia.crm.gui.pricing;

import java.util.UUID;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.pricing.PricelistRemote;
import com.cosmos.acacia.crm.data.sales.Pricelist;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * Created	:	28.12.2008
 * @author	Petar Milev
 *
 */
public class PricelistListPanel extends AbstractTablePanel<Pricelist> {

    private EntityProperties entityProps;
    private PricelistRemote formSession;
    private BindingGroup bindingGroup;
    private List<Pricelist> list;

    public PricelistListPanel(UUID parentDataObjectId) {
        this(parentDataObjectId, null);
    }

    public PricelistListPanel(UUID parentDataObjectId, List<Pricelist> list) {
        super(parentDataObjectId);
        this.setList(list);
        bindComponents();
    }

    protected void bindComponents() {
        entityProps = getFormSession().getListingEntityProperties();

        refreshDataTable(entityProps);

        setVisible(AbstractTablePanel.Button.Classify, false);
    }

    protected PricelistRemote getFormSession() {
        if (formSession == null) {
            formSession = getBean(PricelistRemote.class);
        }
        return formSession;
    }

    @SuppressWarnings("unchecked")
    private void refreshDataTable(EntityProperties entProps) {
        if (bindingGroup != null) {
            bindingGroup.unbind();
        }

        bindingGroup = new BindingGroup();
        AcaciaTable table = getDataTable();

        JTableBinding tableBinding = table.bind(bindingGroup, getList(), entProps, UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bindingGroup.bind();
    }

    @SuppressWarnings("unchecked")
    private List getList() {
        if (list == null) {
            setList(getFormSession().listPricelists(getParentDataObjectId()));
        }
        return list;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Pricelist rowObject) {
        getFormSession().deletePricelist(rowObject);
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Pricelist modifyRow(Pricelist rowObject) {
        return showDetailForm(rowObject, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Pricelist newRow() {
        if (canCreate()) {
            Pricelist o = getFormSession().newPricelist(getParentDataObjectId());
            return (Pricelist) showDetailForm(o, true);
        } else {
            return null;
        }
    }

    private Pricelist showDetailForm(Pricelist o, boolean editable) {
        PricelistForm editPanel = new PricelistForm(o);
        if (!editable) {
            editPanel.setReadonly();
        }
        DialogResponse response = editPanel.showDialog(this);
        if (DialogResponse.SAVE.equals(response)) {
            return (Pricelist) editPanel.getSelectedValue();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();

        refreshDataTable(entityProps);

        return t;
    }

    @Override
    protected void viewRow(Pricelist rowObject) {
        showDetailForm(rowObject, false);
    }

    public void setList(List<Pricelist> list) {
        this.list = list;
        if (list != null) {
            for (Pricelist pricelist : list) {
                if (pricelist.isGeneralPricelist()) {
                    pricelist.setPricelistName(getResourceMap().getString("Pricelist.generalPricelist.name"));
                }
            }
        }
    }
}
