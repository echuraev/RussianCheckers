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
                /*if (row == 5 && col == 2)
                    cells[row][col].setCondition(BoardCell.BLACK_PIECE);
                if (row == 6 && col == 1)
                    cells[row][col].setCondition(BoardCell.BLACK_PIECE);*/

                if (row == 0 && col == 7) {
                    cells[row][col].setCondition(BoardCell.WHITE_PIECE);
                    cells[row][col].setKingPiece(true);
                }

                /*if (row == 7 && col == 2) {
                    cells[row][col].setCondition(BoardCell.WHITE_PIECE);
                }
                if (row == 6 && col == 3)
                    cells[row][col].setCondition(BoardCell.BLACK_PIECE);
                if (row == 6 && col == 5)
                    cells[row][col].setCondition(BoardCell.BLACK_PIECE);
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

    public List<Move> getNormalMoves(BoardCell cell) {
        LinkedList<Move> normalMoves = new LinkedList<Move>();
        if (cell.getCondition() == BoardCell.EMPTY_CELL)
            return normalMoves;

        int toRow = 0;
        int toCol = 0;
        int maxCountOfSteps = (cell.isKingPiece()) ? (CELL_COUNT - 1) : 1;
        for (Direction d : getDirections(cell)) {
            for (int step = 1; step <= maxCountOfSteps; ++step) {
                switch (d) {
                    case RIGHT_BUTTOM:
                        toRow = cell.getRow() + step;
                        toCol = cell.getCol() + step;
                        break;
                    case RIGHT_FORWARD:
                        toRow = cell.getRow() - step;
                        toCol = cell.getCol() + step;
                        break;
                    case LEFT_BUTTOM:
                        toRow = cell.getRow() + step;
                        toCol = cell.getCol() - step;
                        break;
                    case LEFT_FORWARD:
                        toRow = cell.getRow() - step;
                        toCol = cell.getCol() - step;
                        break;
                }
                if (toRow < 0 || toCol < 0 || toRow >= CELL_COUNT || toCol >= CELL_COUNT)
                    break;
                if (cells[toRow][toCol].getCondition() == BoardCell.EMPTY_CELL) {
                    Move move = new Move(cell, cells[toRow][toCol]);
                    normalMoves.add(move);
                }
            }
        }

        return normalMoves;
    }

    public List<Move> getEatMoves(BoardCell cell, BoardCell firstPosition, List<Move> previousEatMoves) {
        LinkedList<Move> eatMoves = new LinkedList<Move>();
        int toRow = 0;
        int toCol = 0;
        int eatRow = 0;
        int eatCol = 0;
        int maxCountOfSteps = (firstPosition.isKingPiece()) ? (CELL_COUNT - 2) : 1;
        for (Direction d : eatDirections) {
            for (int step = 1; step <= maxCountOfSteps; ++step) {
                switch (d) {
                    case RIGHT_BUTTOM:
                        eatRow = cell.getRow() + step;
                        eatCol = cell.getCol() + step;
                        toRow = cell.getRow() + (step + 1);
                        toCol = cell.getCol() + (step + 1);
                        break;
                    case RIGHT_FORWARD:
                        eatRow = cell.getRow() - step;
                        eatCol = cell.getCol() + step;
                        toRow = cell.getRow() - (step + 1);
                        toCol = cell.getCol() + (step + 1);
                        break;
                    case LEFT_BUTTOM:
                        eatRow = cell.getRow() + step;
                        eatCol = cell.getCol() - step;
                        toRow = cell.getRow() + (step + 1);
                        toCol = cell.getCol() - (step + 1);
                        break;
                    case LEFT_FORWARD:
                        eatRow = cell.getRow() - step;
                        eatCol = cell.getCol() - step;
                        toRow = cell.getRow() - (step + 1);
                        toCol = cell.getCol() - (step + 1);
                        break;
                }
                if (toRow < 0 || toCol < 0 || toRow >= CELL_COUNT || toCol >= CELL_COUNT)
                    break;
                boolean eatEatenPiece = false;
                if (previousEatMoves != null && previousEatMoves.size() > 0) {
                    for (Move m : previousEatMoves) {
                        if (eatRow == m.getEatCell().getRow() && eatCol == m.getEatCell().getCol()) {
                            eatEatenPiece = true;
                            break;
                        }
                    }
                }
                if (eatEatenPiece)
                    break;
                if (cells[toRow][toCol].getCondition() == BoardCell.EMPTY_CELL && cells[eatRow][eatCol].getOppositeCondition() == firstPosition.getCondition()) {
                    Move move = new Move(cell, cells[toRow][toCol]);
                    move.setEatMove(cells[eatRow][eatCol]);
                    eatMoves.add(move);
                    eatMoves.addAll(getEatMoves(cells[toRow][toCol], firstPosition, eatMoves));
                }
                if (firstPosition.isKingPiece() && eatMoves.size() > 0) {
                    if (cells[eatRow][eatCol].getOppositeCondition() != firstPosition.getCondition())
                        eatMoves.addAll(getEatMoves(cells[eatRow][eatCol], firstPosition, eatMoves));
                    if (step == maxCountOfSteps && cells[toRow][toCol].getOppositeCondition() != firstPosition.getCondition())
                        eatMoves.addAll(getEatMoves(cells[toRow][toCol], firstPosition, eatMoves));
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
