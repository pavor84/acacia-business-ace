/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.swingb.tree;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Miro
 */
public abstract class AbstractDynamicTreeNode
    extends DefaultMutableTreeNode
    implements Serializable
{
    protected boolean areChildrenLoaded;
    protected DefaultTreeModel treeModel;
    private boolean notifyOnStructureChanged;


    public AbstractDynamicTreeNode()
    {
        this(null);
    }

    public AbstractDynamicTreeNode(Object userObject)
    {
        this(userObject, true);
    }

    public AbstractDynamicTreeNode(Object userObject, boolean allowsChildren)
    {
        super(userObject, allowsChildren);
        notifyOnStructureChanged = true;
    }

    public DefaultTreeModel getTreeModel()
    {
        return treeModel;
    }

    public void setTreeModel(DefaultTreeModel treeModel)
    {
        this.treeModel = treeModel;
    }

    public boolean areChildrenLoaded()
    {
        return areChildrenLoaded;
    }

    /**
     * The implementation of that method MUST set areChildrenLoaded to false
     * when that method is finished. If this is not set to false then the 
     * children will be re-loaded every time.
     */
    public abstract void loadChildren();

    protected abstract int getChildrenCount();


    @Override
    public int getChildCount()
    {
        int childCount = super.getChildCount();
        int childrenCount = getChildrenCount();
        if(childCount > 0 && childCount != childrenCount)
        {
            reloadChildren();
        }

        return childrenCount;
    }

    protected void reloadChildren()
    {
        areChildrenLoaded = false;
        removeAllChildren();
        loadChildren();
        nodeStructureChanged();
    }

    public void nodeStructureChanged()
    {
        if(notifyOnStructureChanged && treeModel != null)
            treeModel.nodeStructureChanged(this);
    }

    public boolean isNotifyOnStructureChanged()
    {
        return notifyOnStructureChanged;
    }

    public void setNotifyOnStructureChanged(boolean notifyOnStructureChanged)
    {
        this.notifyOnStructureChanged = notifyOnStructureChanged;
    }

    @Override
    public void removeAllChildren()
    {
        int childCount = super.getChildCount();
	for(int i = childCount - 1; i >= 0; i--)
        {
	    remove(i);
	}
    }

    @Override
    public TreeNode getChildAt(int childIndex)
    {
        if(!areChildrenLoaded)
            loadChildren();

        try
        {
            return super.getChildAt(childIndex);
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            reloadChildren();
            return super.getChildAt(childIndex);
        }
    }

    @Override
    public Enumeration children()
    {
        if(!areChildrenLoaded)
            loadChildren();

        return super.children();
    }

    @Override
    public void add(MutableTreeNode newChild)
    {
        if(!allowsChildren)
        {
            throw new IllegalStateException("node does not allow children");
        }
        else if(newChild == null)
        {
            throw new IllegalArgumentException("new child is null");
        }
        else if(isNodeAncestor(newChild))
        {
            throw new IllegalArgumentException("new child is an ancestor");
        }

        MutableTreeNode oldParent = (MutableTreeNode)newChild.getParent();

        if(oldParent != null)
        {
            oldParent.remove(newChild);
        }
        newChild.setParent(this);
        if(children == null)
        {
            children = new Vector();
        }
        children.add(newChild);

        if(newChild instanceof AbstractDynamicTreeNode)
        {
            AbstractDynamicTreeNode dynamicTreeNode = (AbstractDynamicTreeNode)newChild;
            dynamicTreeNode.setTreeModel(treeModel);
            dynamicTreeNode.setNotifyOnStructureChanged(isNotifyOnStructureChanged());
        }
    }

    @Override
    public void insert(MutableTreeNode newChild, int childIndex)
    {
        if(newChild instanceof AbstractDynamicTreeNode)
        {
            AbstractDynamicTreeNode dynamicTreeNode = (AbstractDynamicTreeNode)newChild;
            dynamicTreeNode.setTreeModel(treeModel);
            dynamicTreeNode.setNotifyOnStructureChanged(isNotifyOnStructureChanged());
        }

        super.insert(newChild, childIndex);
    }


}
