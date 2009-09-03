/**
 * 
 */
package com.cosmos.acacia.crm.gui.cash;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;

import com.cosmos.acacia.crm.bl.cash.BanknoteQuantityRemote;
import com.cosmos.acacia.crm.data.cash.BanknoteQuantity;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.crm.data.DbResource;
import com.cosmos.acacia.crm.data.cash.CashReconcile;
import com.cosmos.acacia.crm.enums.Currency;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBColumn;

/**
 * 
 * Created	:	01.05.2009
 * @author	Petar Milev
 *
 */
public class BanknoteQuantityListPanel extends AbstractTablePanel<BanknoteQuantity> {
    
    private EntityProperties entityProps;
    
    private BanknoteQuantityRemote formSession;
    private BindingGroup bindingGroup;
    
    private List<BanknoteQuantity> list;

    private List<JBColumn> customColumns;
    
    private DataObjectBean parent;
    
    public BanknoteQuantityListPanel(DataObjectBean parent) {
        this ( parent, null );
    }
    
    public BanknoteQuantityListPanel(DataObjectBean parent, List<BanknoteQuantity> list) {
        this( parent, list, null);
    }
    
    public BanknoteQuantityListPanel(DataObjectBean parent, List<BanknoteQuantity> list, EntityProperties entProperties) {
        super(parent.getId());
        this.entityProps = entProperties;
        this.list=list;
        this.parent = parent;
        bindComponents();
    }
    
    protected void bindComponents(){
        if ( entityProps==null )
            entityProps = getFormSession().getListingEntityProperties();
        if ( customColumns==null )
            customColumns = createCustomColumns();
        
        refreshDataTable(entityProps);
        
        setVisible(AbstractTablePanel.Button.Classify, false);
    }
    
    private List<JBColumn> createCustomColumns() {
        final Currency defaultCurrency =
            (Currency)((CashReconcile)parent).getCurrency().getEnumValue();
        String columnNameDefCurrencyTemp = getResourceMap().getString("column.amountDefCurrency");
        if ( columnNameDefCurrencyTemp.indexOf("{0}")!=-1 )
            columnNameDefCurrencyTemp = MessageFormat.format(columnNameDefCurrencyTemp, defaultCurrency.getCode());
        final String columnNameDefCurrency = columnNameDefCurrencyTemp;
        final String columnNameAmount = getResourceMap().getString("column.amount");
        
        JBColumn<BanknoteQuantity> amount = new JBColumn<BanknoteQuantity>() {
            public Class getColumnClass() {return BigDecimal.class;}
            public String getColumnName() {return columnNameAmount;}
            public int getIndex() {return 3;}
            public Object getValue(BanknoteQuantity item) {
                return item.getAmount();
            }};
            
        JBColumn<BanknoteQuantity> amountDefCurrency = new JBColumn<BanknoteQuantity>() {
            public Class getColumnClass() {return BigDecimal.class;}
            public String getColumnName() {return columnNameDefCurrency;}
            public int getIndex() {return 4;}
            public Object getValue(BanknoteQuantity item) {
                return item.getAmount(defaultCurrency);
            }};
            
        List<JBColumn> result = new ArrayList<JBColumn>();
        result.add(amount);
        result.add(amountDefCurrency);
        return result;
    }

    protected BanknoteQuantityRemote getFormSession() {
        if ( formSession==null )
            formSession = getBean(BanknoteQuantityRemote.class);
        return formSession;
    }
    
    @SuppressWarnings("unchecked")
    private void refreshDataTable(EntityProperties entProps){
        if ( bindingGroup!=null )
            bindingGroup.unbind();
        
        bindingGroup = new BindingGroup();
        AcaciaTable table = getDataTable();
        
        JTableBinding tableBinding = table.bind(bindingGroup, getList(), entProps, UpdateStrategy.READ, 
            customColumns, false);
        tableBinding.setEditable(false);

        bindingGroup.bind();
    }

    @SuppressWarnings("unchecked")
    private List getList() {
        if ( list==null )
            list = getFormSession().listBanknoteQuantitys(getParentDataObjectId());
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
    public boolean canDelete(BanknoteQuantity rowObject) {
        return true;
    }
    
    /** @see com.cosmos.acacia.gui.AbstractTablePanel#canModify(java.lang.Object)
     */
    @Override
    public boolean canModify(BanknoteQuantity rowObject) {
        return true;
    }
    
    @Override
    public boolean canView(BanknoteQuantity rowObject) {
        return true;
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#deleteRow(java.lang.Object)
     */
    @Override
    protected boolean deleteRow(BanknoteQuantity rowObject) {
        getFormSession().deleteBanknoteQuantity((BanknoteQuantity)rowObject);
        return true;
    }
    
    @Override
    public void modifyAction() {
        super.modifyAction();
        //to refresh the view/modify state of the current row
        Object selected = getDataTable().getSelectedRowObject();
        setSelectedRowObject(null);
        setSelectedRowObject(selected);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#modifyRow(java.lang.Object)
     */
    @Override
    protected BanknoteQuantity modifyRow(BanknoteQuantity rowObject) {
        return showDetailForm(rowObject, true);
    }

    /** @see com.cosmos.acacia.gui.AbstractTablePanel#newRow()
     */
    @Override
    protected BanknoteQuantity newRow() {
        if ( canCreate() ){
            if (canNestedOperationProceed()) {
                BanknoteQuantity o = getFormSession().newBanknoteQuantity(getParentDataObjectId());
                return showDetailForm(o, true);
            }
            return null;
        }else{
            return null;
        }
    }

    private BanknoteQuantity showDetailForm(BanknoteQuantity o, boolean editable) {
        BanknoteQuantityForm editPanel = new BanknoteQuantityForm(o, parent);
        if ( !editable )
            editPanel.setReadonly();
        DialogResponse response = editPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return (BanknoteQuantity) editPanel.getSelectedValue();
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
    protected void viewRow(BanknoteQuantity rowObject) {
        showDetailForm(rowObject, false);
    }
    
    public void refreshList(List<BanknoteQuantity> list){
        this.list = list;
        refreshDataTable(entityProps);
    }

    public BigDecimal getTotalAmount() {
        Currency defaultCurr = (Currency) ((CashReconcile)parent).getCurrency().getEnumValue();
        
        BigDecimal result = BigDecimal.ZERO;
        for (Object qty : getDataTable().getData()) {
            BanknoteQuantity banknoteQty = (BanknoteQuantity) qty;
            result = result.add(banknoteQty.getAmount(defaultCurr));
        }
        
        return result;
    }
    
    private static class BanknoteComparator implements Comparator<BanknoteQuantity>{
        public int compare(BanknoteQuantity o1, BanknoteQuantity o2) {
            DbResource c1 = o1.getCurrencyNominal().getCurrency();
            DbResource c2 = o2.getCurrencyNominal().getCurrency();
            int curr = c1.getResourceId().compareTo(c2.getResourceId());
            if ( curr!=0 ) 
                return curr;
            return o1.getCurrencyNominal().getNominalValue().compareTo(o2.getCurrencyNominal().getNominalValue());
        }
    }
    
    @Override
    protected void addTableObject(BanknoteQuantity newRowObject) {
        Comparator c = new BanknoteComparator();
        List objects = getDataTable().getData();
        int idx = 0;
        for (Object object : objects) {
            if ( c.compare(newRowObject, object)<=0 ){
                break;
            }
            idx ++;
        }
        
        getDataTable().addRow(newRowObject, idx);
    }
    
    @Override
    protected void replaceTableObject(BanknoteQuantity newRowObject) {
        getDataTable().removeSelectedRow();
        addTableObject(newRowObject);
        getDataTable().setSelectedRowObject(newRowObject);
    }
}