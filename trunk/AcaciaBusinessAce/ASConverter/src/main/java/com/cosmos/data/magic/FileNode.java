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
public class FileNode {

    private String name;
    private String description;
    private TreeMap<String, FieldNode> fieldNodesMap = new TreeMap<String, FieldNode>();

    public FileNode(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addFieldNode(FieldNode fieldNode) {
        fieldNodesMap.put(fieldNode.getDescription(), fieldNode);
    }

    public TreeMap<String, FieldNode> getFieldNodesMap() {
        return fieldNodesMap;
    }

}
