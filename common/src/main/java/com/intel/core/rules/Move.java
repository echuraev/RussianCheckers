package com.intel.core.rules;

import com.intel.core.board.BoardCell;

public class Move {
    private BoardCell from;
    private BoardCell to;
    private BoardCell eat;

    public Move(BoardCell from, BoardCell to) {
        this.from = from;
        this.to = to;
        this.eat = null;
    }

    public Move(Move move) {
        from = move.getFromCell();
        to = move.getToCell();
        eat = move.getEatCell();
    }

    public void setEatMove(BoardCell eatMove) {
        this.eat = eatMove;
    }

    public BoardCell getFromCell() {
        return from;
    }

    public BoardCell getToCell() {
        return to;
    }

    public BoardCell getEatCell() {
        return eat;
    }
}
