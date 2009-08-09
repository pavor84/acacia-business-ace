/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb.item;

import com.cosmos.swingb.DialogResponse;
import com.cosmos.swingb.JBButton;
import com.cosmos.swingb.JBLabel;
import com.cosmos.swingb.JBPanel;
import com.cosmos.swingb.JBRadioButton;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.Action;
import org.jdesktop.application.ApplicationActionMap;

/**
 *
 * @author Miro
 */
public class ItemSelectionDialog extends JBPanel {

    private String message;
    private Icon icon;
    private List<OptionalItem> optionalItems;
    private int columns;
    //
    private JBPanel itemsPanel;
    private Component messageComponent;
    private Component iconComponent;
    private JBPanel buttonsPanel;
    private ButtonGroup buttonGroup;
    private JBButton selectButton;
    private JBButton cancelButton;

    public ItemSelectionDialog() {
        super(new BorderLayout());
    }

    public ItemSelectionDialog(List<OptionalItem> optionalItems, int columns) {
        this();
        this.columns = columns;
        setOptionalItems(optionalItems);
    }

    public ItemSelectionDialog(List<OptionalItem> optionalItems) {
        this(optionalItems, 1);
    }

    @Override
    public DialogResponse showDialog(Component parentComponent) {
        initComponents();
        return super.showDialog(parentComponent);
    }

    protected void initComponents() {
        JBPanel mainPanel = new JBPanel(new MigLayout("wrap 1"));
        Component component;

        if((component = getMessageComponent()) != null) {
            //add(component, BorderLayout.NORTH);
            mainPanel.add(component);
        }

        //add(getItemsPanel(), BorderLayout.CENTER);
        mainPanel.add(getItemsPanel());
        add(mainPanel, BorderLayout.CENTER);

        add(getButtonsPanel(), BorderLayout.SOUTH);

        if((component = getIconComponent()) != null) {
            add(component, BorderLayout.WEST);
        }
    }

    public List<OptionalItem> getOptionalItems() {
        return optionalItems;
    }

    public void setOptionalItems(List<OptionalItem> optionalItems) {
        this.optionalItems = optionalItems;
    }

    protected JBPanel getItemsPanel() {
        if(itemsPanel != null) {
            return itemsPanel;
        }

        buttonGroup = new ButtonGroup();
        itemsPanel = new JBPanel(new MigLayout("wrap " + getColumns()));
        setLayout(new BorderLayout());
        add(itemsPanel, BorderLayout.CENTER);
        for (OptionalItem item : getOptionalItems()) {
            String itemName = item.getItemName();
            JBRadioButton radioButton = new JBRadioButton(getText(item), getIcon(item), isSelected(item));
            radioButton.setActionCommand(itemName);
            buttonGroup.add(radioButton);
            itemsPanel.add(radioButton);
        }
        ButtonModel buttonModel;
        if((buttonModel = buttonGroup.getSelection()) == null || !buttonModel.isSelected()) {
            buttonGroup.getElements().nextElement().setSelected(true);
        }

        return itemsPanel;
    }

    @Override
    public Class getActionsClass() {
        return ItemSelectionDialog.class;
    }

    protected JBPanel getButtonsPanel() {
        if(buttonsPanel != null) {
            return buttonsPanel;
        }

        ApplicationActionMap actionMap = getApplicationActionMap();

        selectButton = new JBButton();
        selectButton.setAction(actionMap.get("selectButtonAction")); // NOI18N
        selectButton.setName("selectButton"); // NOI18N

        cancelButton = new JBButton();
        cancelButton.setAction(actionMap.get("cancelButtonAction")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N

        buttonsPanel = new JBPanel(new MigLayout("wrap 2", "[grow][]", ""));
        buttonsPanel.add(selectButton, "growx");
        buttonsPanel.add(cancelButton);

        return buttonsPanel;
    }

    public Icon getIcon() {
        if(icon != null) {
            return icon;
        }

        return getResourceIcon("icon");
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getMessage() {
        if(message != null) {
            return message;
        }

        return getResourceString("message");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getColumns() {
        if(columns > 0) {
            return columns;
        }
        Integer value;
        if((value = getResourceInteger("columns")) != null) {
            columns = value;
        }
        if(columns < 1) {
            columns = 1;
        }

        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    protected Component getMessageComponent() {
        if(messageComponent != null) {
            return messageComponent;
        }

        String msg;
        if((msg = getMessage()) == null) {
            return null;
        }

        //messageComponent = XHTMLUtils.getXHTMLScrollPane(msg, null, null);
        //messageComponent = XHTMLUtils.getHTMLPanel(msg);
        messageComponent = new JBLabel(msg);

        return messageComponent;
    }

    protected Component getIconComponent() {
        if(iconComponent != null) {
            return iconComponent;
        }

        Icon image;
        if((image = getIcon()) == null) {
            return null;
        }

        return iconComponent = new JLabel(image);
    }

    protected String getText(OptionalItem optionalItem) {
        String text;
        String itemName = "item." + optionalItem.getItemName() + ".text";
        if ((text = getResourceString(itemName)) != null) {
            return text;
        } else if ((text = optionalItem.getText()) != null) {
            return text;
        }

        return itemName;
    }

    protected Icon getIcon(OptionalItem optionalItem) {
        Icon image;
        if ((image = getResourceIcon("item." + optionalItem.getItemName() + ".icon")) != null) {
            return image;
        } else if ((image = optionalItem.getIcon()) != null) {
            return image;
        }

        return null;
    }

    protected boolean isSelected(OptionalItem optionalItem) {
        Boolean value;
        if ((value = getResourceBoolean("item." + optionalItem.getItemName() + ".selected")) != null) {
            return value;
        } else if ((value = optionalItem.isSelected()) != null) {
            return value;
        }

        return false;
    }

    @Action
    public void selectButtonAction() {
        String itemName;
        if((itemName = buttonGroup.getSelection().getActionCommand()) == null) {
            return;
        }

        for(OptionalItem item : getOptionalItems()) {
            if(itemName.equals(item.getItemName())) {
                setSelectedValue(item);
                setDialogResponse(DialogResponse.SELECT);
                close();
            }
        }
    }

    @Action
    public void cancelButtonAction() {
        close();
    }
}
