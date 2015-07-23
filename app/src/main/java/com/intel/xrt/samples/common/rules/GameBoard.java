package com.intel.xrt.samples.common.rules;

import com.intel.xrt.samples.common.board.BoardCell;

import java.util.LinkedList;
import java.util.List;

public class GameBoard {
    static public final int CELL_COUNT = 8;
    private BoardCell[][] cells = new BoardCell[CELL_COUNT][CELL_COUNT];

    public GameBoard() {
        for (int row = 0; row < CELL_COUNT; ++row) {
            for (int col = 0; col < CELL_COUNT; ++col) {
                cells[row][col] = new BoardCell();
            }
        }
    }

    public BoardCell getCell(int row, int col) {
        return cells[row][col];
    }

    private List<Move> getNormalMoves(int row, int col) {
        LinkedList<Move> normalMoves = new LinkedList<Move>();
        if (cells[row][col].getCondition() == BoardCell.EMPTY_CELL)
            return normalMoves;
        return normalMoves;
    }

    private List<Move> getEatMoves(int row, int col) {
        LinkedList<Move> eatMoves = new LinkedList<Move>();
        if (cells[row][col].getCondition() == BoardCell.EMPTY_CELL)
            return eatMoves;
        return eatMoves;
    }

    private void tryMove() {

    }

    private void doMove() {

    }
}
