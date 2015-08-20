package com.intel.core.algorithm;

import com.intel.core.board.BoardCell;
import com.intel.core.rules.GameBoard;
import com.intel.core.rules.Move;
import com.intel.core.rules.Player;

import java.util.List;

public class AlphaBetaPruning {
    public static final int LOW_DIFFICULTY = 3;
    public static final int MEDIUM_DIFFICULTY = 5;
    public static final int HIGH_DIFFICULTY = 7;
    private int maxDepth;
    GameBoard gameBoard;
    private Move computerMove;

    public AlphaBetaPruning(GameBoard gameBoard, int difficutly) {
        this.maxDepth = difficutly;
        this.gameBoard = gameBoard;
        this.computerMove = null;
    }

    public void alphaBetaPruning(Player player) {
        alphaBetaPruning(0, Double.MIN_VALUE, Double.MAX_VALUE, player);
    }

    private double alphaBetaPruning(int depth, double alpha, double beta, Player player) {
        if (gameBoard.hasWon(player))
            return player.getPieceColor();

        List<Move> availiableMoves = gameBoard.getAllAvailiableMoves(player);
        if (availiableMoves.isEmpty() || depth == maxDepth)
            return getHeuristicEvaluation();

        double score = beta;
        for (Move m : availiableMoves) {
            BoardCell fromCell = new BoardCell(m.getFromCell());
            BoardCell toCell = new BoardCell(m.getToCell());
            BoardCell eatCell = new BoardCell(m.getEatCell());
            gameBoard.doMove(m);

            double step_score = -alphaBetaPruning(depth + 1, -score, -alpha, player.getOpposite());
            gameBoard.setCell(fromCell.getRow(), fromCell.getCol(), fromCell);
            gameBoard.setCell(toCell.getRow(), toCell.getCol(), toCell);
            gameBoard.setCell(eatCell.getRow(), eatCell.getCol(), eatCell);

            if (step_score < score) {
                score = step_score;
                computerMove = new Move(m);
            }
            if (score <= alpha)
                return score;
        }

        return score;
    }

    public Move getComputerMove() {
        return computerMove;
    }

    private double getHeuristicEvaluation() {
        int count = 0;
        double kingWeight = Math.abs(BoardCell.WHITE_PIECE) / 2.0;
        double pieceWeight = Math.abs(BoardCell.WHITE_PIECE) / 6.0;
        for (int row = 0; row < GameBoard.CELL_COUNT; ++row) {
            for (int col = 0; col < GameBoard.CELL_COUNT; ++col) {
                switch (gameBoard.getCell(row, col).getCondition()) {
                    case BoardCell.BLACK_PIECE:
                        if (gameBoard.getCell(row, col).isKingPiece())
                            count += kingWeight;
                        else
                            count += pieceWeight;
                        break;
                    case BoardCell.WHITE_PIECE:
                        if (gameBoard.getCell(row, col).isKingPiece())
                            count -= kingWeight;
                        else
                            count -= pieceWeight;
                    default:
                        break;
                }
            }
        }
        return count;
    }

}
