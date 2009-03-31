/**
 * 
 */
package com.cosmos.acacia.crm.gui.pricing;

import java.math.BigInteger;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.pricing.ProductPricingValueRemote;
import com.cosmos.acacia.crm.data.ProductPercentValue;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.beansbinding.PropertyDetails;
import com.cosmos.swingb.DialogResponse;

/**
 * Created	:	28.12.2008
 * @author	Petar Milev
 *
 */
public class ProductPricingValueListPanel extends AbstractTablePanel {
    
    private EntityProperties entityProps;
    
    private ProductPricingValueRemote formSession = getBean(ProductPricingValueRemote.class);
    private BindingGroup bindingGroup;

    private List<ProductPercentValue> list;

    private ProductPercentValue.Type type;
    
    public ProductPricingValueListPanel(BigInteger parentDataObjectId, ProductPercentValue.Type type) {
        super(parentDataObjectId);
        this.type = type;
        if (type != null) {
            setTitle(getResourceMap().getString("Form.title." + type));
        }
        bindComponents();
    }
    
    protected void bindComponents() {
        entityProps = getFormSession().getListingEntityProperties();
        if (type != null) {
            PropertyDetails propDetails = entityProps.getPropertyDetails("percentValue");
            String title = getResourceMap().getString("percentValue.name." + type);
            propDetails.setPropertyTitle(title);
        }

        refreshDataTable(entityProps);

        setVisible(AbstractTablePanel.Button.Classify, false);
    }
    
    protected ProductPricingValueRemote getFormSession() {
        return formSession;
    }
    
    @SuppressWarnings("unchecked")
    private void refreshDataTable(EntityProperties entProps){
        if ( bindingGroup!=null )
            bindingGroup.unbind();
        
        bindingGroup = new BindingGroup();
        AcaciaTable table = getDataTable();
        
        JTableBinding tableBinding = table.bind(bindingGroup, getList(), entProps, UpdateStrategy.READ);
        tableBinding.setEditable(false);

        bindingGroup.bind();
    }

    @SuppressWarnings("unchecked")
    private List getList() {
        if ( list==null )
            list = getFormSession().listProductPricingValues(getParentDataObjectId(), type);
        return list;
    }

    /**
     * @see com.cosmos.acacia.gui.AbstractTablePanel#canCreate()
     */
    @Override
    public boolean canCreate() {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canDelete(java.lang.Object)
     */
    @Override
    public boolean canDelete(Object rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(Object rowObject) {
        return true;
    }
    
    @Override
    public boolean canView(Object rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(Object rowObject) {
        getFormSession().deleteProductPricingValue((ProductPercentValue)rowObject);
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected Object modifyRow(Object rowObject) {
        ProductPercentValue o = (ProductPercentValue) rowObject;
        return showDetailForm(o, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected Object newRow() {
        ProductPercentValue o = getFormSession().newProductPricingValue(getParentDataObjectId(), type);
        return showDetailForm(o, true);
    }

    private Object showDetailForm(ProductPercentValue o, boolean editable) {
        ProductPricingValueForm editPanel = new ProductPricingValueForm(o);
        if ( !editable )
            editPanel.setReadonly();
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return editPanel.getSelectedValue();
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
    protected void viewRow(Object rowObject) {
        ProductPercentValue o = (ProductPercentValue) rowObject;
        showDetailForm(o, false);
    }
}
