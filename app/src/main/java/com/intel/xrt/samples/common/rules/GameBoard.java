package com.intel.xrt.samples.common.rules;

import com.intel.xrt.samples.common.board.BoardCell;

import java.util.LinkedList;
import java.util.List;

public class GameBoard {
    static public final int CELL_COUNT = 8;
    static private final int PIECE_ROWS_COUNT = 3;
    private BoardCell[][] cells = new BoardCell[CELL_COUNT][CELL_COUNT];

    public GameBoard() {
        for (int row = 0; row < CELL_COUNT; ++row) {
            for (int col = 0; col < CELL_COUNT; ++col) {
                cells[row][col] = new BoardCell(row, col);

                if (row == 3 && col == 4)
                    cells[row][col].setCondition(BoardCell.BLACK_PIECE);
                if (row == 5 && col == 2)
                    cells[row][col].setCondition(BoardCell.BLACK_PIECE);
                if (row == 6 && col == 1)
                    cells[row][col].setCondition(BoardCell.BLACK_PIECE);

                if (row == 0 && col == 7) {
                    cells[row][col].setCondition(BoardCell.WHITE_PIECE);
                    cells[row][col].setKingPiece(true);
                }
                /*if (row < PIECE_ROWS_COUNT)
                    cells[row][col].setCondition(BoardCell.BLACK_PIECE);
                else if (row > ((CELL_COUNT - 1) - PIECE_ROWS_COUNT))
                    cells[row][col].setCondition(BoardCell.WHITE_PIECE);*/
            }
        }
    }

    public BoardCell getCell(int row, int col) {
        return cells[row][col];
    }

    public List<Move> getNormalMoves(int row, int col) {
        LinkedList<Move> normalMoves = new LinkedList<Move>();
        if (cells[row][col].getCondition() == BoardCell.EMPTY_CELL)
            return normalMoves;

        int toRow = 0;
        int toCol = 0;
        int maxCountOfSteps = (cells[row][col].isKingPiece()) ? (CELL_COUNT - 1) : 1;
        for (Direction d : getDirections(cells[row][col])) {
            for (int step = 1; step <= maxCountOfSteps; ++step) {
                switch (d) {
                    case RIGHT_BUTTOM:
                        toRow = row + step;
                        toCol = col + step;
                        break;
                    case RIGHT_FORWARD:
                        toRow = row - step;
                        toCol = col + step;
                        break;
                    case LEFT_BUTTOM:
                        toRow = row + step;
                        toCol = col - step;
                        break;
                    case LEFT_FORWARD:
                        toRow = row - step;
                        toCol = col - step;
                        break;
                }
                if (toRow < 0 || toCol < 0 || toRow >= CELL_COUNT || toCol >= CELL_COUNT)
                    continue;
                if (cells[toRow][toCol].getCondition() == BoardCell.EMPTY_CELL) {
                    Move move = new Move(cells[row][col], cells[toRow][toCol]);
                    normalMoves.add(move);
                }
            }
        }

        return normalMoves;
    }

    public List<Move> getEatMoves(int row, int col) {
        LinkedList<Move> eatMoves = new LinkedList<Move>();
        if (cells[row][col].getCondition() == BoardCell.EMPTY_CELL)
            return eatMoves;
        int toRow = 0;
        int toCol = 0;
        int eatRow = 0;
        int eatCol = 0;
        int maxCountOfSteps = (cells[row][col].isKingPiece()) ? (CELL_COUNT - 2) : 1;
        for (Direction d : eatDirections) {
            boolean eatMove = false;
            for (int step = 1; step <= maxCountOfSteps; ++step) {
                switch (d) {
                    case RIGHT_BUTTOM:
                        eatRow = row + step;
                        eatCol = col + step;
                        toRow = row + (step + 1);
                        toCol = col + (step + 1);
                        break;
                    case RIGHT_FORWARD:
                        eatRow = row - step;
                        eatCol = col + step;
                        toRow = row - (step + 1);
                        toCol = col + (step + 1);
                        break;
                    case LEFT_BUTTOM:
                        eatRow = row + step;
                        eatCol = col - step;
                        toRow = row + (step + 1);
                        toCol = col - (step + 1);
                        break;
                    case LEFT_FORWARD:
                        eatRow = row - step;
                        eatCol = col - step;
                        toRow = row - (step + 1);
                        toCol = col - (step + 1);
                        break;
                }
                if (toRow < 0 || toCol < 0 || toRow >= CELL_COUNT || toCol >= CELL_COUNT)
                    continue;
                if (eatMove == false) {
                    if (cells[row][col].getCondition() != cells[eatRow][eatCol].getOppositeCondition())
                        continue;
                }
                /*if (cells[eatRow][eatRow].getCondition() == cells[toRow][toCol].getCondition())
                    break;
                if (cells[row][col].getCondition() == cells[toRow][toCol].getCondition())
                    break;*/
                if (cells[toRow][toCol].getCondition() == BoardCell.EMPTY_CELL) {
                    Move move = new Move(cells[row][col], cells[toRow][toCol]);
                    move.setEatMove(cells[eatRow][eatCol]);
                    eatMoves.add(move);
                    eatMove = true;
                }
            }
        }

        return eatMoves;
    }

    public void doMove(Move move) {
        move.getToCell().copyCell(move.getFromCell());
        move.getFromCell().clearCell();
        if (move.getEatCell() != null) {
            move.getEatCell().clearCell();
        }
        if (move.getToCell().getCondition() == BoardCell.WHITE_PIECE) {
            if (move.getToCell().getRow() == 0) {
                move.getToCell().setKingPiece(true);
            }
        }
        if (move.getToCell().getCondition() == BoardCell.BLACK_PIECE) {
            if (move.getToCell().getRow() == CELL_COUNT - 1) {
                move.getToCell().setKingPiece(true);
            }
        }
    }

    private Direction[] getDirections(BoardCell cell) {
        if(cell.isKingPiece()){
            return kingDirections;
        }
        if(cell.getCondition() == BoardCell.WHITE_PIECE){
            return whiteDirections;
        }
        if(cell.getCondition() == BoardCell.BLACK_PIECE){
            return blackDirections;
        }
        return null;
    }

    enum Direction{
        RIGHT_FORWARD,
        LEFT_FORWARD,
        RIGHT_BUTTOM,
        LEFT_BUTTOM
    }

    private final static Direction[] kingDirections = Direction.values();
    private final static Direction[] eatDirections = Direction.values();
    private final static Direction[] whiteDirections = new Direction[]{Direction.RIGHT_FORWARD, Direction.LEFT_FORWARD};
    private final static Direction[] blackDirections = new Direction[]{Direction.RIGHT_BUTTOM, Direction.LEFT_BUTTOM};

    public void tryMove() {

    }
}
