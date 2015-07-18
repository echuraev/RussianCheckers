package com.intel.xrt.samples.russiancheckers;

import android.graphics.Color;
import android.graphics.RectF;


public class BoardCell {
    public static final int EMPTY_CELL = 0;
    public static final int WHITE_CELL = 1;
    public static final int BLACK_CELL = -1;

    public static final int WHITE_PIECE_COLOR = Color.RED;
    public static final int BLACK_PIECE_COLOR = Color.BLUE;
    public static final int CROWN_COLOR = Color.YELLOW;
    public static final int HIGHLIGHT_COLOR = Color.GREEN;

    private int cellCondition;
    private boolean highlighted;
    private boolean kingPiece;
    private RectF rect;

    public BoardCell() {
        highlighted = false;
        kingPiece = false;
        cellCondition = EMPTY_CELL;
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

    public void setKingPiece(boolean kingPiece) {
        this.kingPiece = kingPiece;
    }

    public boolean isKingPiece() {
        return kingPiece;
    }
}
