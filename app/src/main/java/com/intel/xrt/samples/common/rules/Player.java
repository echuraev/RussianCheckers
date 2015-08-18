package com.intel.xrt.samples.common.rules;

import com.intel.xrt.samples.common.board.BoardCell;

public enum Player {
    WHITE,
    BLACK;

    public Player getOpposite() {
        return (this == WHITE) ? BLACK : WHITE;
    }

    public int getPieceColor() {
        return (this == WHITE) ? BoardCell.WHITE_PIECE : BoardCell.BLACK_PIECE;
    }
}
