/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb.tree;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.TreePath;

/**
 *
 * @author Miro
 */
public class FileSystemTreeNode
    extends AbstractDynamicTreeNode
{

    protected static final FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    protected static final String osName = System.getProperty("os.name").toLowerCase();
    private boolean fileAccessError;

    public FileSystemTreeNode()
    {
        File[] roots = fileSystemView.getRoots();
        if(roots != null && roots.length > 0)
        {
            setUserObject(roots[0]);
        }
    }

    public FileSystemTreeNode(File file)
    {
        setUserObject(file);
    }

    @Override
    public void loadChildren()
    {
        if(!areChildrenLoaded)
        {
            List<File> childFolders = getChildFolders();
            if(childFolders != null && childFolders.size() > 0)
            {
                for(File folder : childFolders)
                {
                    FileSystemTreeNode treeNode = new FileSystemTreeNode(folder);
                    add(treeNode);
                }
            }

            areChildrenLoaded = true;
        }
    }

    @Override
    protected int getChildrenCount()
    {
        return getChildFolders().size();
    }

    protected List<File> getChildFolders()
    {
        if(fileAccessError)
        {
            return Collections.emptyList();
        }

        File file = getFile();

        if(!fileExists())
        {
            errorDisplayingNoDisk();
            return Collections.emptyList();
        }

        if(!canReadFile())
        {
            errorDisplayingPermission();
            return Collections.emptyList();
        }

        if(!isDirectory())
        {
            return Collections.emptyList();
        }

        File[] childFiles = file.listFiles();
        int size;
        if(childFiles != null && (size = childFiles.length) > 0)
        {
            List<File> folders = new ArrayList<File>(size);
            for(File child : childFiles)
            {
                if(child.isDirectory())
                {
                    folders.add(child);
                }
            }

            return folders;
        }

        return Collections.emptyList();
    }

    private void errorDisplayingPermission()
    {
        fileAccessError = true;
        JOptionPane.showMessageDialog(null,
            "You do not have the permissions necessary to view the contents of \"" + getFile() + "\"", "Error Displaying Folder",
            JOptionPane.ERROR_MESSAGE);

        collapsePath();
    }

    private void errorDisplayingNoDisk()
    {
        fileAccessError = true;
        File file = getFile();
        String fileName = file.getName();
        String errorMsg = "The file : " + fileName + " does not exist any longer!";

        if(osName.startsWith("windows") && (fileSystemView.isFloppyDrive(file) || fileSystemView.isDrive(file)))
        {
            errorMsg = "Please insert disk into driver:  " + file;
        }

        JOptionPane.showMessageDialog(
            null,
            errorMsg,
            "Error Displaying Folder",
            JOptionPane.ERROR_MESSAGE);

        collapsePath();
    }

    protected void collapsePath()
    {
        /*TreePath path = new TreePath(getRoot());
        FileSystemTree tree = getFileSystemTree();
        tree.setSelectionPath(path);
        
        path = new TreePath(getPath());
        if(tree.isExpanded(path))
        {
        tree.collapsePath(path);
        }*/
    }

    @Override
    public boolean getAllowsChildren()
    {
        return isDirectory();
    }

    @Override
    public boolean isLeaf()
    {
        return !isDirectory();
    }

    public File getFile()
    {
        return (File) getUserObject();
    }

    public boolean isDirectory()
    {
        File file = getFile();

        return file.isDirectory();
    }

    protected boolean fileExists()
    {
        try
        {
            return getFile().exists();
        }
        catch(Throwable ex)
        {
            return false;
        }
    }

    protected boolean canReadFile()
    {
        try
        {
            return getFile().canRead();
        }
        catch(Throwable ex)
        {
            return false;
        }
    }

    @Override
    public String toString()
    {
        return fileSystemView.getSystemDisplayName(getFile());
    }

    public Icon getSystemIcon()
    {
        return fileSystemView.getSystemIcon(getFile());
    }

    public String getSystemTypeDescription()
    {
        return fileSystemView.getSystemTypeDescription(getFile());
    }

    protected TreePath findChildTreePath(TreePath parentPath, File file)
    {
        if(parentPath != null && file != null)
        {
            FileSystemTreeNode node = (FileSystemTreeNode) parentPath.getLastPathComponent();
            int childCount = node.getChildCount();
            for(int i = 0; i < childCount; i++)
            {
                FileSystemTreeNode child = (FileSystemTreeNode) node.getChildAt(i);
                if(child.getUserObject().equals(file))
                {
                    return parentPath.pathByAddingChild(child);
                }
            }
        }

        return null;
    }

    protected File[] getFileTreeForPath(String filePathName)
    {
        LinkedList<File> list = new LinkedList<File>();
        File file = fileSystemView.createFileObject(filePathName);
        while (file != null && !file.isDirectory())
        {
            file = file.getParentFile();
        }

        while (file != null)
        {
            list.add(file);
            file = fileSystemView.getParentDirectory(file);
        }

        File[] fileTree = new File[list.size()];
        Iterator<File> iter = list.descendingIterator();
        int pos = 0;
        while (iter.hasNext())
        {
            fileTree[pos++] = iter.next();
        }

        return fileTree;
    }

    public TreePath getFileTreePath(String filePathName)
    {
        filePathName = trimFileName(filePathName);

        TreePath path = null;
        File[] fileTree = getFileTreeForPath(filePathName);
        if(fileTree != null && fileTree.length > 0)
        {
            FileSystemTreeNode rootTreeNode = (FileSystemTreeNode) getRoot();
            if(rootTreeNode.getUserObject().equals(fileTree[0]))
            {
                path = new TreePath(rootTreeNode);
                for(int i = 1; i < fileTree.length; i++)
                {
                    path = findChildTreePath(path, fileTree[i]);
                    if(path == null)
                    {
                        break;
                    }
                }
            }
        }

        return path;
    }

    public String trimFileName(String filePathName)
    {
        if(filePathName == null)
            return filePathName;

        StringBuilder sb = new StringBuilder(filePathName);
        int size = sb.length();
        char ch;
        while(size > 0 && ((ch = sb.charAt(size - 1)) == '.' || ch == File.separatorChar))
        {
            size--;
            sb.setLength(size);
        }
        return sb.toString();
    }
}
