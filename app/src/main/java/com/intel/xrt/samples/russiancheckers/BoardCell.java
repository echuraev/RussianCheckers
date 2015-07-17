package com.intel.xrt.samples.russiancheckers;

import android.graphics.Color;
import android.graphics.RectF;


public class BoardCell {
    public static final int EMPTY_CELL = 0;
    public static final int WHITE_CELL = 1;
    public static final int WHITE_KING_CELL = 2;
    public static final int BLACK_CELL = -1;
    public static final int BLACK_KING_CELL = -2;

    public static final int WHITE_PIECE_COLOR = Color.RED;
    public static final int BLACK_PIECE_COLOR = Color.BLUE;
    private int cellCondition;
    private boolean highlighted = false;
    private RectF rect;

    public BoardCell() {

    }

    public BoardCell(RectF rect) {
        this.rect = rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public RectF getRect() {
        return rect;
    }

    public int getCondition() {
        return cellCondition;
    }

    public void setCondition(int condition) {
        cellCondition = condition;
    }

    public void setHighlight(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isHighlight() {
        return highlighted;
    }
}
