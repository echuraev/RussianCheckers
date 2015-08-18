package com.intel.xrt.samples.common.board;

public class BoardCell {
    public static final int EMPTY_CELL = 0;
    public static final int WHITE_PIECE = 1;
    public static final int BLACK_PIECE = -1;
    private static final int EMPTY_CELL_OPPOSITE = 100;
    private int cellCondition;
    private boolean highlighted;
    private boolean kingPiece;
    private CellRect rect;
    private int row;
    private int col;

    public BoardCell(int row, int col) {
        highlighted = false;
        kingPiece = false;
        cellCondition = EMPTY_CELL;
        this.row = row;
        this.col = col;
    }

    public BoardCell(BoardCell cell) {
        if (cell == null) {
            cellCondition = EMPTY_CELL;
            highlighted = false;
            kingPiece = false;
            row = -1;
            col = -1;
            rect = null;
        }
        else {
            cellCondition = cell.getCondition();
            highlighted = cell.isHighlight();
            kingPiece = cell.isKingPiece();
            rect = cell.getRect();
            row = cell.getRow();
            col = cell.getCol();
        }
    }

    public void copyCell(BoardCell cell) {
        highlighted = cell.isHighlight();
        kingPiece = cell.isKingPiece();
        cellCondition = cell.getCondition();
    }

    public void clearCell() {
        highlighted = false;
        kingPiece = false;
        cellCondition = EMPTY_CELL;
    }

    public void setRect(CellRect rect) {
        this.rect = rect;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
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

    public int getOppositeCondition() {
        if (cellCondition == WHITE_PIECE) {
            return BLACK_PIECE;
        }
        else if (cellCondition == BLACK_PIECE) {
            return WHITE_PIECE;
        }
        return EMPTY_CELL_OPPOSITE;
    }
}
