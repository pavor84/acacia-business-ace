/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.tree;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Miro
 */
public class DynamicTreeModel
    extends DefaultTreeModel
{

    public DynamicTreeModel(TreeNode root)
    {
        this(root, false);
    }

    public DynamicTreeModel(TreeNode root, boolean asksAllowsChildren)
    {
        super(root, asksAllowsChildren);
        if(root instanceof AbstractDynamicTreeNode)
            ((AbstractDynamicTreeNode)root).setTreeModel(this);
    }

}
