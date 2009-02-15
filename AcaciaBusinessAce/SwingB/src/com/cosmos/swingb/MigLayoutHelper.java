/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.swingb;

import java.awt.Component;
import java.util.Map;
import javax.swing.JPanel;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.BoundSize;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.DimConstraint;
import net.miginfocom.layout.LC;
import net.miginfocom.layout.UnitValue;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Miro
 */
public class MigLayoutHelper {

    private JPanel panel;
    private LC layoutConstraints;
    private AC columnConstraints;
    private AC rowConstraints;

    public MigLayoutHelper(JPanel panel) {
        this.panel = panel;
        initMigLayout();
    }

    public MigLayout getLayout() {
        return (MigLayout) panel.getLayout();
    }

    private void initMigLayout() {
        MigLayout migLayout = getLayout();
        layoutConstraints = new LC();
        migLayout.setLayoutConstraints(layoutConstraints);
        columnConstraints = new AC();
        migLayout.setColumnConstraints(columnConstraints);
        rowConstraints = new AC();
        migLayout.setRowConstraints(rowConstraints);
    }

    public AC getColumnConstraints() {
        return columnConstraints;
    }

    public LC getLayoutConstraints() {
        return layoutConstraints;
    }

    public AC getRowConstraints() {
        return rowConstraints;
    }

    public CC getComponentConstraints(Component component) {
        Map<Component, Object> constraintMap = getComponentConstraintMap();
        CC cc = (CC)constraintMap.get(component);
        if(cc == null) {
            cc = new CC();
            constraintMap.put(component, cc);
            setComponentConstraintMap(constraintMap);
        }

        return cc;
    }

    public Map<Component, Object> getComponentConstraintMap() {
        return getLayout().getConstraintMap();
    }

    public void setComponentConstraintMap(Map<Component, Object> constraintMap) {
        getLayout().setConstraintMap(constraintMap);
    }

    public boolean isLayoutNoCache() {
        return layoutConstraints.isNoCache();
    }

    public void setLayoutNoCache(boolean cache) {
        layoutConstraints.setNoCache(cache);
    }

    public UnitValue getLayoutAlignX() {
        return layoutConstraints.getAlignX();
    }

    public void setLayoutAlignX(UnitValue unitValue) {
        layoutConstraints.setAlignX(unitValue);
    }

    public UnitValue getLayoutAlignY() {
        return layoutConstraints.getAlignY();
    }

    public void setLayoutAlignY(UnitValue unitValue) {
        layoutConstraints.setAlignY(unitValue);
    }

    public int getLayoutDebugMillis() {
        return layoutConstraints.getDebugMillis();
    }

    public void setLayoutDebugMillis(int millis) {
        layoutConstraints.setDebugMillis(millis);
    }

    public boolean isLayoutFillX() {
        return layoutConstraints.isFillX();
    }

    public void setLayoutFillX(boolean fillX) {
        layoutConstraints.setFillX(fillX);
    }

    public boolean isLayoutFillY() {
        return layoutConstraints.isFillY();
    }

    public void setLayoutFillY(boolean fillY) {
        layoutConstraints.setFillY(fillY);
    }

    public boolean isLayoutFlowX() {
        return layoutConstraints.isFlowX();
    }

    public void setLayoutFlowX(boolean flowX) {
        layoutConstraints.setFlowX(flowX);
    }

    public BoundSize getLayoutGridGapX() {
        return layoutConstraints.getGridGapX();
    }

    public void setLayoutGridGapX(BoundSize boundSize) {
        layoutConstraints.setGridGapX(boundSize);
    }

    public BoundSize getLayoutGridGapY() {
        return layoutConstraints.getGridGapY();
    }

    public void setLayoutGridGapY(BoundSize boundSize) {
        layoutConstraints.setGridGapY(boundSize);
    }

    public int getLayoutHideMode() {
        return layoutConstraints.getHideMode();
    }

    public void setLayoutHideMode(int mode) {
        layoutConstraints.setHideMode(mode);
    }

    public UnitValue[] getLayoutInsets() {
        return layoutConstraints.getInsets();
    }

    public void setLayoutInsets(UnitValue[] unitValues) {
        layoutConstraints.setInsets(unitValues);
    }

    public Boolean getLayoutLeftToRight() {
        return layoutConstraints.getLeftToRight();
    }

    public void setLayoutLeftToRight(Boolean leftToRight) {
        layoutConstraints.setLeftToRight(leftToRight);
    }

    public boolean isLayoutNoGrid() {
        return layoutConstraints.isNoGrid();
    }

    public void setLayoutNoGrid(boolean noGrid) {
        layoutConstraints.setNoGrid(noGrid);
    }

    public boolean isLayoutTopToBottom() {
        return layoutConstraints.isTopToBottom();
    }

    public void setLayoutTopToBottom(boolean topToBottom) {
        layoutConstraints.setTopToBottom(topToBottom);
    }

    public boolean isLayoutVisualPadding() {
        return layoutConstraints.isVisualPadding();
    }

    public void setLayoutVisualPadding(boolean visualPadding) {
        layoutConstraints.setVisualPadding(visualPadding);
    }

    public int getLayoutWrapAfter() {
        return layoutConstraints.getWrapAfter();
    }

    public void setLayoutWrapAfter(int count) {
        layoutConstraints.setWrapAfter(count);
    }

    public BoundSize getLayoutPackWidth() {
        return layoutConstraints.getPackWidth();
    }

    public void setLayoutPackWidth(BoundSize size) {
        layoutConstraints.setPackWidth(size);
    }

    public BoundSize getLayoutPackHeight() {
        return layoutConstraints.getPackHeight();
    }

    public void setLayoutPackHeight(BoundSize size) {
        layoutConstraints.setPackHeight(size);
    }

    public float getLayoutPackHeightAlign() {
        return layoutConstraints.getPackHeightAlign();
    }

    public void setLayoutPackHeightAlign(float align) {
        layoutConstraints.setPackHeightAlign(align);
    }

    public float getLayoutPackWidthAlign() {
        return layoutConstraints.getPackWidthAlign();
    }

    public void setLayoutPackWidthAlign(float align) {
        layoutConstraints.setPackWidthAlign(align);
    }

    public BoundSize getLayoutWidth() {
        return layoutConstraints.getWidth();
    }

    public void setLayoutWidth(BoundSize size) {
        layoutConstraints.setWidth(size);
    }

    public BoundSize getLayoutHeight() {
        return layoutConstraints.getHeight();
    }

    public void setLayoutHeight(BoundSize size) {
        layoutConstraints.setHeight(size);
    }

    public LC layoutPack() {
        return layoutConstraints.pack();
    }

    public LC layoutPack(String width, String height) {
        return layoutConstraints.pack(width, height);
    }

    public LC layoutPackAlign(float alignX, float alignY) {
        return layoutConstraints.packAlign(alignX, alignY);
    }

    public LC layoutWrap() {
        return layoutConstraints.wrap();
    }

    public LC layoutWrapAfter(int count) {
        return layoutConstraints.wrapAfter(count);
    }

    public LC layoutNoCache() {
        return layoutConstraints.noCache();
    }

    public LC layoutFlowY() {
        return layoutConstraints.flowY();
    }

    public LC layoutFlowX() {
        return layoutConstraints.flowX();
    }

    public LC layoutFill() {
        return layoutConstraints.fill();
    }

    public LC layoutFillX() {
        return layoutConstraints.fillX();
    }

    public LC layoutFillY() {
        return layoutConstraints.fillY();
    }

    public LC layoutLeftToRight(boolean leftToRight) {
        return layoutConstraints.leftToRight(leftToRight);
    }

    public LC layoutBottomToTop() {
        return layoutConstraints.bottomToTop();
    }

    public LC layoutNoGrid() {
        return layoutConstraints.noGrid();
    }

    public LC layoutNoVisualPadding() {
        return layoutConstraints.noVisualPadding();
    }

    public LC layoutInsetsAll(String allSides) {
        return layoutConstraints.insetsAll(allSides);
    }

    public LC layoutInsets(String s) {
        return layoutConstraints.insets(s);
    }

    public LC layoutInsets(String top, String left, String bottom, String right) {
        return layoutConstraints.insets(top, left, bottom, right);
    }

    public LC layoutAlignX(String align) {
        return layoutConstraints.alignX(align);
    }

    public LC layoutAlignY(String align) {
        return layoutConstraints.alignY(align);
    }

    public LC layoutAlign(String ax, String ay) {
        return layoutConstraints.align(ax, ay);
    }

    public LC layoutGridGapX(String boundsSize) {
        return layoutConstraints.gridGapX(boundsSize);
    }

    public LC layoutGridGapY(String boundsSize) {
        return layoutConstraints.gridGapY(boundsSize);
    }

    public LC layoutGridGap(String gapx, String gapy) {
        return layoutConstraints.gridGap(gapx, gapy);
    }

    public LC layoutDebug(int repaintMillis) {
        return layoutConstraints.debug(repaintMillis);
    }

    public LC layoutHideMode(int mode) {
        return layoutConstraints.hideMode(mode);
    }

    public LC layoutMinWidth(String width) {
        return layoutConstraints.minWidth(width);
    }

    public LC layoutWidth(String width) {
        return layoutConstraints.width(width);
    }

    public LC layoutMaxWidth(String width) {
        return layoutConstraints.maxWidth(width);
    }

    public LC layoutMinHeight(String height) {
        return layoutConstraints.minHeight(height);
    }

    public LC layoutHeight(String height) {
        return layoutConstraints.height(height);
    }

    public LC layoutMaxHeight(String height) {
        return layoutConstraints.maxHeight(height);
    }

	public final DimConstraint[] getColumnDimensionConstraints()
	{
		return columnConstraints.getConstaints();
	}

	public final void setColumnDimensionConstraints(DimConstraint[] constr)
	{
        columnConstraints.setConstaints(constr);
	}

	public int getColumnCount()
	{
		return columnConstraints.getCount();
	}

	public final AC setColumnCount(int size)
	{
		return columnConstraints.count(size);
	}

	public final AC columnNoGrid()
	{
		return columnConstraints.noGrid();
	}

	public final AC columnNoGrid(int... indexes)
	{
		return columnConstraints.noGrid(indexes);
	}

	public final AC columnIndex(int i)
	{
		return columnConstraints.index(i);
	}

	public final AC columnFill()
	{
		return columnConstraints.fill();
	}

	public final AC columnFill(int... indexes)
	{
		return columnConstraints.fill(indexes);
	}

	public final AC columnSizeGroup(String s)
	{
		return columnConstraints.sizeGroup(s);
	}

	public final AC columnSizeGroup(String s, int... indexes)
	{
		return columnConstraints.sizeGroup(s, indexes);
	}

	public final AC columnSize(String s)
	{
		return columnConstraints.size(s);
	}

	public final AC columnSize(String size, int... indexes)
	{
		return columnConstraints.size(size, indexes);
	}

	public final AC columnGap()
	{
		return columnConstraints.gap();
	}

	public final AC columnGap(String size)
	{
		return columnConstraints.gap(size);
	}

	public final AC columnGap(String size, int... indexes)
	{
		return columnConstraints.gap(size, indexes);
	}

	public final AC columnAlign(String side)
	{
		return columnConstraints.align(side);
	}

	public final AC columnAlign(String side, int... indexes)
	{
		return columnConstraints.align(side, indexes);
	}

	public final AC columnGrowPrio(int p)
	{
		return columnConstraints.growPrio(p);
	}

	public final AC columnGrowPrio(int p, int... indexes)
	{
		return columnConstraints.growPrio(p, indexes);
	}

	public final AC columnGrow(float w)
	{
		return columnConstraints.grow(w);
	}

	public final AC columnGrow(float w, int... indexes)
	{
		return columnConstraints.grow(w, indexes);
	}

	public final AC columnShrinkPrio(int p)
	{
		return columnConstraints.shrinkPrio(p);
	}

	public final AC columnShrinkPrio(int p, int... indexes)
	{
		return columnConstraints.shrinkPrio(p, indexes);
	}

	public final AC columnShrinkWeight(float w)
	{
		return columnConstraints.shrinkWeight(w);
	}

	public final AC columnShrinkWeight(float w, int... indexes)
	{
		return columnConstraints.shrinkWeight(w, indexes);
	}

	public final DimConstraint[] getRowDimensionConstraints()
	{
		return rowConstraints.getConstaints();
	}

	public final void setRowDimensionConstraints(DimConstraint[] constr)
	{
        rowConstraints.setConstaints(constr);
	}

	public int getRowCount()
	{
		return rowConstraints.getCount();
	}

	public final AC setRowCount(int size)
	{
		return rowConstraints.count(size);
	}

	public final AC rowNoGrid()
	{
		return rowConstraints.noGrid();
	}

	public final AC rowNoGrid(int... indexes)
	{
		return rowConstraints.noGrid(indexes);
	}

	public final AC rowIndex(int i)
	{
		return rowConstraints.index(i);
	}

	public final AC rowFill()
	{
		return rowConstraints.fill();
	}

	public final AC rowFill(int... indexes)
	{
		return rowConstraints.fill(indexes);
	}

	public final AC rowSizeGroup(String s)
	{
		return rowConstraints.sizeGroup(s);
	}

	public final AC rowSizeGroup(String s, int... indexes)
	{
		return rowConstraints.sizeGroup(s, indexes);
	}

	public final AC rowSize(String s)
	{
		return rowConstraints.size(s);
	}

	public final AC rowSize(String size, int... indexes)
	{
		return rowConstraints.size(size, indexes);
	}

	public final AC rowGap()
	{
		return rowConstraints.gap();
	}

	public final AC rowGap(String size)
	{
		return rowConstraints.gap(size);
	}

	public final AC rowGap(String size, int... indexes)
	{
		return rowConstraints.gap(size, indexes);
	}

	public final AC rowAlign(String side)
	{
		return rowConstraints.align(side);
	}

	public final AC rowAlign(String side, int... indexes)
	{
		return rowConstraints.align(side, indexes);
	}

	public final AC rowGrowPrio(int p)
	{
		return rowConstraints.growPrio(p);
	}

	public final AC rowGrowPrio(int p, int... indexes)
	{
		return rowConstraints.growPrio(p, indexes);
	}

	public final AC rowGrow(float w)
	{
		return rowConstraints.grow(w);
	}

	public final AC rowGrow(float w, int... indexes)
	{
		return rowConstraints.grow(w, indexes);
	}

	public final AC rowShrinkPrio(int p)
	{
		return rowConstraints.shrinkPrio(p);
	}

	public final AC rowShrinkPrio(int p, int... indexes)
	{
		return rowConstraints.shrinkPrio(p, indexes);
	}

	public final AC rowShrinkWeight(float w)
	{
		return rowConstraints.shrinkWeight(w);
	}

	public final AC rowShrinkWeight(float w, int... indexes)
	{
		return rowConstraints.shrinkWeight(w, indexes);
	}
}
