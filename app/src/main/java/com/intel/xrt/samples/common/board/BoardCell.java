package com.intel.xrt.samples.common.board;

public class BoardCell {
    public static final int EMPTY_CELL = 0;
    public static final int WHITE_PIECE = 1;
    public static final int BLACK_PIECE = -1;
    private int cellCondition;
    private boolean highlighted;
    private boolean kingPiece;
    private CellRect rect;

    public BoardCell() {
        highlighted = false;
        kingPiece = false;
        cellCondition = EMPTY_CELL;
    }

    public void setRect(CellRect rect) {
        this.rect = rect;
    }

    public CellRect getRect() {
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
