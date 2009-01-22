/**
 * 
 */
package com.cosmos.acacia.crm.gui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import org.jdesktop.beansbinding.BindingGroup;

import com.cosmos.acacia.crm.bl.impl.BaseRemote;
import com.cosmos.acacia.crm.data.DataObjectBean;
import com.cosmos.acacia.gui.AbstractTablePanelListener;
import com.cosmos.acacia.gui.BaseEntityPanel;
import com.cosmos.acacia.gui.TableHolderPanel;
import com.cosmos.beansbinding.EntityProperties;
import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.listeners.TableModificationListener;

/**
 * Created	:	18.01.2009
 * @author	Petar Milev
 *
 */
public abstract class BaseForm<T extends DataObjectBean, I extends DataObjectBean> extends BaseEntityPanel {

    protected T entity;
    
    protected BindingGroup bindGroup;
    protected BaseRemote<T, I> formSession;
    protected EntityProperties entProps;
    
    protected BaseItemListPanel<T, I> baseItemListPanel;
    
    protected Class<T> entityClass;
    protected Class<T> itemClass;

    /** Creates new form */
    public BaseForm(T entity) {
        super(entity.getParentId());
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.itemClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        this.entity = entity;
        this.formSession = getFormSession();
        initialize();
    }

    protected void initialize() {
        createComponents();
        createCustomComponents();
        initComponentsCustom();
        init();
    }

    protected void initComponentsCustom() {
    }

    /**
     * Hook to default initComponents method or otherwise.
     */
    protected abstract void createComponents();

    private void createCustomComponents() {
        if ( getItemsTableHolderPanel()!=null ){
            Class itemsTableClass;
            try {
                itemsTableClass = getItemListPanelClass();
                
                baseItemListPanel = 
                    (BaseItemListPanel<T, I>) itemsTableClass.getConstructor(entityClass).newInstance(entity);
               
                baseItemListPanel.addTableModificationListener(new TableModificationListener() {
                    public void rowModified(Object row) {
                    }
                    public void rowDeleted(Object row) {
                    }
                    public void rowAdded(Object row) {
                    }
                });
        
                baseItemListPanel.addTablePanelListener(new AbstractTablePanelListener() {
                    @Override
                    public void tableRefreshed() {
                    }
                });
        
                // Adding the nested table listener to ensure that purchase order is
                // saved before adding
                // items to it.
                addNestedFormListener(baseItemListPanel);
                
                getItemsTableHolderPanel().add(baseItemListPanel);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract Class<? extends BaseItemListPanel<T, I>> getItemListPanelClass();

    protected abstract TableHolderPanel getItemsTableHolderPanel();
    
    @Override
    public BindingGroup getBindingGroup() {
        return bindGroup;
    }

    @Override
    public Object getEntity() {
        return entity;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void performSave(boolean closeAfter) {
        entity = formSession.save(entity);
        baseItemListPanel.setParent(entity);
        setDialogResponse(DialogResponse.SAVE);
        setSelectedValue(entity);
        if (closeAfter) {
            close();
        } else {
            bindGroup.unbind();
            initData();
        }
    }

    @Override
    protected void initData() {
        
        if (entProps == null)
            entProps = getFormSession().getDetailEntityProperties();

        if (bindGroup == null)
            bindGroup = new BindingGroup();

        bindComponents(bindGroup, entProps);
    }
    
    protected abstract void bindComponents(BindingGroup bindGroup, EntityProperties entProps);

    @Override
    public void setReadonly() {
        super.setReadonly();
    }

//    protected BaseRemote<T, I> getFormSession() {
//        if ( formSession==null ){
//            String remoteClassName = entityClass.getName()+"Remote";
//            Class remoteClass;
//            try {
//                getClass().getClassLoader().
//                remoteClass = Class.forName(remoteClassName);
//                formSession = (BaseRemote<T, I>) getBean(remoteClass);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            formSession = getFormSessionClass();
//        }
//        return formSession;
//    }
    
    protected BaseRemote<T, I> getFormSession(){
        if ( formSession==null ){
            formSession = getBean(getFormSessionClass());
        }
        return formSession;
    }
    
    protected abstract Class<? extends BaseRemote<T, I>> getFormSessionClass();

//      public static Class[] getClasses(String pckgname)
//            throws ClassNotFoundException {
//        ArrayList<Class> classes = new ArrayList<Class>();
//        // Get a File object for the package
//        File directory = null;
//        try {
//            ClassLoader cld = Thread.currentThread().getContextClassLoader();
//            if (cld == null) {
//                throw new ClassNotFoundException("Can't get class loader.");
//            }
//            String path = '/' + pckgname.replace('.', '/');
//            URL resource = cld.getResource(path);
//            if (resource == null) {
//                throw new ClassNotFoundException("No resource for " + path);
//            }
//            directory = new File(resource.getFile());
//        } catch (NullPointerException x) {
//            throw new ClassNotFoundException(pckgname + " (" + directory
//                    + ") does not appear to be a valid package");
//        }
//        if (directory.exists()) {
//            // Get the list of the files contained in the package
//            String[] files = directory.list();
//            for (int i = 0; i < files.length; i++) {
//                // we are only interested in .class files
//                if (files[i].endsWith(".class")) {
//                    // removes the .class extension
//                    classes.add(Class.forName(pckgname + '.'
//                            + files[i].substring(0, files[i].length() - 6)));
//                }
//            }
//        } else {
//            throw new ClassNotFoundException(pckgname
//                    + " does not appear to be a valid package");
//        }
//        Class[] classesA = new Class[classes.size()];
//        classes.toArray(classesA);
//        return classesA;
//    }


}
