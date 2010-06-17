/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.data.magic;

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

    public MagicNode() {
    }

    public TreeMap<String, FileNode> getFileNodesMap() {
        return fileNodesMap;
    }

    public void addFileNode(FileNode fileNode) {
        fileNodesMap.put(fileNode.getName(), fileNode);
    }
}
