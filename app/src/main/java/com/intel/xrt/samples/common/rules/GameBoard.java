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
//                if (row == 5 && col == 2)
//                    cells[row][col].setCondition(BoardCell.BLACK_PIECE);
//                if (row == 6 && col == 1)
//                    cells[row][col].setCondition(BoardCell.BLACK_PIECE);

                if (row == 0 && col == 7) {
                    cells[row][col].setCondition(BoardCell.WHITE_PIECE);
                    cells[row][col].setKingPiece(true);
                }

                if (row == 7 && col == 2) {
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
                if (cells[toRow][toCol].getCondition() != BoardCell.EMPTY_CELL)
                    break;
                Move move = new Move(cell, cells[toRow][toCol]);
                normalMoves.add(move);
            }
        }

        return normalMoves;
    }

    public List<Move> getEatMoves(BoardCell cell) {
        LinkedList<Move> eatMoves = new LinkedList<Move>();
        int toRow = 0;
        int toCol = 0;
        int eatRow = 0;
        int eatCol = 0;
        int maxCountOfSteps = (cell.isKingPiece()) ? (CELL_COUNT - 1) : 1;
        for (Direction d : eatDirections) {
            BoardCell eatCell = null;
            LinkedList<Move> allHighlightedMoves = new LinkedList<Move>();
            LinkedList<Move> nextEatMoves = new LinkedList<Move>();
            for (int step = 1; step <= maxCountOfSteps; ++step) {
                int toStep = (eatCell == null) ? (step + 1) : step;
                switch (d) {
                    case RIGHT_BUTTOM:
                        eatRow = cell.getRow() + step;
                        eatCol = cell.getCol() + step;
                        toRow = cell.getRow() + toStep;
                        toCol = cell.getCol() + toStep;
                        break;
                    case RIGHT_FORWARD:
                        eatRow = cell.getRow() - step;
                        eatCol = cell.getCol() + step;
                        toRow = cell.getRow() - toStep;
                        toCol = cell.getCol() + toStep;
                        break;
                    case LEFT_BUTTOM:
                        eatRow = cell.getRow() + step;
                        eatCol = cell.getCol() - step;
                        toRow = cell.getRow() + toStep;
                        toCol = cell.getCol() - toStep;
                        break;
                    case LEFT_FORWARD:
                        eatRow = cell.getRow() - step;
                        eatCol = cell.getCol() - step;
                        toRow = cell.getRow() - toStep;
                        toCol = cell.getCol() - toStep;
                        break;
                }
                if (toRow < 0 || toCol < 0 || toRow >= CELL_COUNT || toCol >= CELL_COUNT)
                    break;
                if (cells[toRow][toCol].getCondition() == BoardCell.EMPTY_CELL
                        && cells[eatRow][eatCol].getOppositeCondition() == cell.getCondition()
                        && eatCell == null) {
                    eatCell = cells[eatRow][eatCol];
                    step++;
                }
                if ((cells[toRow][toCol].getCondition() == cells[eatRow][eatCol].getCondition()
                        && cell.getOppositeCondition() == cells[eatRow][eatCol].getCondition())
                        || cell.getCondition() == cells[toRow][toCol].getCondition())
                    break;
                if (eatCell == null)
                    continue;
                if (cells[toRow][toCol].getCondition() != BoardCell.EMPTY_CELL)
                    break;
                Move move = new Move(cell, cells[toRow][toCol]);
                move.setEatMove(eatCell);
                allHighlightedMoves.add(move);
                if (isExistsNextEatMove(cell, cells[toRow][toCol], d)) {
                    nextEatMoves.add(move);
                }
            }
            if (nextEatMoves.size() > 0)
                eatMoves.addAll(nextEatMoves);
            else
                eatMoves.addAll(allHighlightedMoves);
        }

        return eatMoves;
    }

    private boolean isExistsNextEatMove(BoardCell piece, BoardCell fromCell, Direction from) {
        int toRow = 0;
        int toCol = 0;
        int maxCountOfSteps = (piece.isKingPiece()) ? (CELL_COUNT - 1) : 1;
        for (Direction d : getDirections(piece)) {
            if (d == from || d == getOppositeDirection(from))
                continue;
            for (int step = 1; step <= maxCountOfSteps; ++step) {
                switch (d) {
                    case RIGHT_BUTTOM:
                        toRow = fromCell.getRow() + step;
                        toCol = fromCell.getCol() + step;
                        break;
                    case RIGHT_FORWARD:
                        toRow = fromCell.getRow() - step;
                        toCol = fromCell.getCol() + step;
                        break;
                    case LEFT_BUTTOM:
                        toRow = fromCell.getRow() + step;
                        toCol = fromCell.getCol() - step;
                        break;
                    case LEFT_FORWARD:
                        toRow = fromCell.getRow() - step;
                        toCol = fromCell.getCol() - step;
                        break;
                }
                if (toRow < 0 || toCol < 0 || toRow >= CELL_COUNT || toCol >= CELL_COUNT)
                    break;
                if (cells[toRow][toCol].getCondition() == piece.getOppositeCondition()) {
                    return true;
                }
            }
        }
        return false;
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
    private Direction getOppositeDirection(Direction direction) {
        switch (direction) {
            case RIGHT_FORWARD:
                return Direction.LEFT_BUTTOM;
            case LEFT_FORWARD:
                return Direction.RIGHT_BUTTOM;
            case RIGHT_BUTTOM:
                return Direction.LEFT_FORWARD;
            case LEFT_BUTTOM:
                return Direction.RIGHT_FORWARD;
        }
        return null;
    }

    public void tryMove() {

    }
}
