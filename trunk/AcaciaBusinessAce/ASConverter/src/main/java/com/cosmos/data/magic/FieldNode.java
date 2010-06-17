/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cosmos.data.magic;

/**
 *
 * @author Miro
 */
public class FieldNode {

    private String description;
    private String picture;
    private boolean number;
    private int wholeNumberLength;

    public FieldNode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNumber() {
        return number;
    }

    public void setNumber(boolean number) {
        this.number = number;
    }

    public int getWholeNumberLength() {
        return wholeNumberLength;
    }

    public void setWholeNumberLength(int wholeNumberLength) {
        this.wholeNumberLength = wholeNumberLength;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFullPicture() {
        int size;
        if(picture == null || (size = picture.length()) == 0) {
            return picture;
        }

        StringBuilder source = new StringBuilder(picture);
        StringBuilder sb = new StringBuilder();
        Character lastChar = null;
        while(source.length() > 0) {
            char ch = source.charAt(0);
            if(Character.isDigit(ch)) {
                int count = getNumber(source);
                if(lastChar == null) {
                    lastChar = 'X';
                    sb.append(lastChar);
                }
                while(--count > 0) {
                    sb.append(lastChar);
                }
            } else {
                source.deleteCharAt(0);
                sb.append(ch);
                lastChar = ch;
            }
        }

        if(number) {
            int index;
            if((index = sb.indexOf("C")) >= 0) {
                sb.deleteCharAt(index);
                int commasNumber = getCommasNumber();
                while(commasNumber-- > 0) {
                    sb.insert(0, ',');
                }
            }
        }

        return sb.toString();
    }

    private int getNumber(StringBuilder source) {
        StringBuilder numberStr = new StringBuilder();
        char ch;
        while(source.length() > 0 && Character.isDigit(ch = source.charAt(0))) {
            numberStr.append(ch);
            source.deleteCharAt(0);
        }

        return Integer.parseInt(numberStr.toString());
    }

    private int getCommasNumber() {
        int commas = wholeNumberLength / 3;
        if(wholeNumberLength % 3 == 0 && commas > 0) {
            commas--;
        }

        return commas;
    }
}
