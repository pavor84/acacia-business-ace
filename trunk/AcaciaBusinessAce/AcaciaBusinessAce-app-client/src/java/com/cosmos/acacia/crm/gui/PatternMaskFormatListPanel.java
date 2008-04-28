/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.acacia.crm.gui;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.jdesktop.application.Task;
import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.impl.PatternMaskListRemote;
import com.cosmos.acacia.crm.data.DataObject;
import com.cosmos.acacia.crm.data.PatternMaskFormat;
import com.cosmos.acacia.gui.AbstractTablePanel;
import com.cosmos.acacia.gui.AcaciaTable;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;

/**
 * 
 * Created	:	22.03.2008
 * @author	Petar Milev
 * @version $Id: $
 *
 */
public class PatternMaskFormatListPanel extends AbstractTablePanel {
    
    protected static Logger log = Logger.getLogger(PatternMaskFormatListPanel.class);
    
    @EJB
    private PatternMaskListRemote formSession;

    /** Creates new form PersonsListPanel */
    public PatternMaskFormatListPanel(DataObject parentDataObject)
    {
    	super(parentDataObject);
    }
    
    private BindingGroup formatsBindingGroup;
    private List<PatternMaskFormat> formats;
    private EntityProperties entityProperties;
    
    @SuppressWarnings("unchecked")
    @Override
    protected void initData() {
        super.initData();
        
        setVisible(Button.Select, false);
        
        entityProperties = getFormSession().getPatternMaskEntityProperties();
        
        refreshDataTable(entityProperties);
    }
    
    protected List<PatternMaskFormat> getFormats()
    {
        formats = getFormSession().listPatternsByName();
        return formats;
    }
    
    private void refreshDataTable(EntityProperties entProps){
        if ( formatsBindingGroup != null )
            formatsBindingGroup.unbind();
        formatsBindingGroup = new BindingGroup();
        
        List<PatternMaskFormat> formatsList = getFormats();
        
        AcaciaTable formatsTable = getDataTable();
        formatsTable.bind(formatsBindingGroup, formatsList,
            entityProperties
            ); 
        
        formatsTable.bindComboBoxCellEditor(formatsBindingGroup, formatsList, entityProperties.getPropertyDetails("owner"));

        formatsBindingGroup.bind();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Task refreshAction() {
        Task t = super.refreshAction();
        
        refreshDataTable(entityProperties);
        
        return t;
    }
    
    @Override
    protected boolean deleteRow(Object rowObject) {
        return
            getFormSession().deletePatternMaskFormat((PatternMaskFormat) rowObject);
    }

    @Override
    protected Object modifyRow(Object rowObject)
    {
        if(rowObject instanceof PatternMaskFormat)
        {
            PatternMaskFormatPanel panel = new PatternMaskFormatPanel((PatternMaskFormat) rowObject);
            DialogResponse response = panel.showDialog(this);
            if(DialogResponse.SAVE.equals(response))
            {
                return panel.getSelectedValue();
            }
        }

        return null;
    }

    @Override
    protected Object newRow() {
        PatternMaskFormat newFormat = getFormSession().newPatternMaskFormat();
        
        PatternMaskFormatPanel formatPanel = new PatternMaskFormatPanel(newFormat);
        DialogResponse response = formatPanel.showDialog(this);
        if(DialogResponse.SAVE.equals(response))
        {
            return formatPanel.getSelectedValue();
        }

        return null;
    }

    @Override
    public boolean canCreate() {
        return true;
    }

    @Override
    public boolean canModify(Object rowObject) {
        return true;
    }

    @Override
    public boolean canDelete(Object rowObject) {
        return true;
    }

    /**
     * Getter for formSession
     * @return PatternMaskListRemote
     */
    public PatternMaskListRemote getFormSession() {
        if ( formSession==null ){
            try {
                formSession = InitialContext.doLookup(PatternMaskListRemote.class.getName());
            } catch (NamingException e) {
                throw new IllegalStateException("Remote bean can't be loaded", e);
            }
        }
        
        return formSession;
    }

    /**
     * Setter for formSession
     * @param formSession - PatternMaskListRemote
     */
    public void setFormSession(PatternMaskListRemote formSession) {
        this.formSession = formSession;
    }
}
