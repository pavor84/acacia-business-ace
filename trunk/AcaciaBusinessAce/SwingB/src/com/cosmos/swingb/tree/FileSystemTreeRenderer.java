/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.tree;

import java.awt.Component;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Miro
 */
public class FileSystemTreeRenderer
    extends DefaultTreeCellRenderer
{

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean isSelected,
            boolean isExpanded,
            boolean leaf,
            int row,
            boolean hasFocus)
    {
        Component component = super.getTreeCellRendererComponent(
                tree, value, isSelected, isExpanded, leaf, row, hasFocus);

        if(value != null && value instanceof FileSystemTreeNode)
        {
            FileSystemTreeNode treeNode = (FileSystemTreeNode)value;
            // ===
            // For Windows "My Computer" node only.
            // ===
            File selectedDir = (File)treeNode.getUserObject();

            Icon icon = treeNode.getSystemIcon();
            if(icon != null)
            {
                setIcon(icon);
            }

            return component;
        }

        return this;
    }
}
