/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.data.magic;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author Miro
 */
public class MagicNode {

    public static final String VERSION = "VRSN";
    public static final String FILE = "FILE";
    public static final String FIELD = "FLD";
    public static final String KEY = "KEY";
    public static final String WHOLE_NUMBER = "WHLE";
    public static final String DESCRIPTION = "DESC";
    public static final String NAME = "NAME";
    public static final String PICTURE = "PIC";
    public static final String DATA_TYPE = "STRG";

    private TreeMap<String, FileNode> fileNodesMap = new TreeMap<String, FileNode>();
    private List<String> fileNodeNames = new ArrayList<String>();

    public MagicNode() {
    }

    public TreeMap<String, FileNode> getFileNodesMap() {
        return fileNodesMap;
    }

    public void addFileNode(FileNode fileNode) {
        fileNodesMap.put(fileNode.getName(), fileNode);
    }

    public FileNode getFileNode(String desc) {
        for(String name : fileNodesMap.keySet()) {
            if(name.toUpperCase().contains(desc.toUpperCase())) {
                return fileNodesMap.get(name);
            }
        }

        return null;
    }

    public List<String> getFileNodeNames() {
        return fileNodeNames;
    }
}
