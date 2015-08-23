package com.intel.core.algorithm;

import com.intel.core.board.BoardCell;
import com.intel.core.rules.GameBoard;
import com.intel.core.rules.Move;
import com.intel.core.rules.Player;

import java.util.List;
import java.util.Random;

public class AlphaBetaPruning implements IAlgorithm {
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

    @Override
    public AlgorithmType getAlgorithmType() {
        return AlgorithmType.COMPUTER;
    }

    @Override
    public Move getOpponentMove() {
        if (computerMove == null) {
            Random random = new Random();
            List<Move> availiableMoves = gameBoard.getAllAvailiableMoves(Player.BLACK);
            computerMove = availiableMoves.get(random.nextInt(availiableMoves.size()));
        }
        return computerMove;
    }

    @Override
    public void getAlgorithm(Player player) {
        computerMove = null;
        alphaBetaPruning(0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, player);
    }

    private double alphaBetaPruning(int depth, double alpha, double beta, Player player) {
        if (gameBoard.hasWon(player))
            return player.getPieceColor();

        List<Move> availiableMoves = gameBoard.getAllAvailiableMoves(player);
        if (availiableMoves.isEmpty() || depth == maxDepth)
            return getHeuristicEvaluation();

        // Max player
        if (player == Player.WHITE) {
            double score = Double.NEGATIVE_INFINITY;
            for (Move m : availiableMoves) {
                BoardCell fromCell = new BoardCell(m.getFromCell());
                BoardCell toCell = new BoardCell(m.getToCell());
                BoardCell eatCell = new BoardCell(m.getEatCell());
                gameBoard.doMove(m);

                double step_score = alphaBetaPruning(depth + 1, alpha, beta, player.getOpposite());
                gameBoard.setCell(fromCell.getRow(), fromCell.getCol(), fromCell);
                gameBoard.setCell(toCell.getRow(), toCell.getCol(), toCell);
                gameBoard.setCell(eatCell.getRow(), eatCell.getCol(), eatCell);

                if (score < step_score)
                    score = step_score;
                if (alpha < score)
                    alpha = score;
                if (beta <= alpha) {
                    //computerMove = new Move(m);
                    break;
                }
            }
            return score;
        }
        // Min player
        else {
            double score = Double.POSITIVE_INFINITY;
            for (Move m : availiableMoves) {
                if (depth == 0) {
                    gameBoard.hasWon(player);
                }
                BoardCell fromCell = new BoardCell(m.getFromCell());
                BoardCell toCell = new BoardCell(m.getToCell());
                BoardCell eatCell = new BoardCell(m.getEatCell());
                gameBoard.doMove(m);

                double step_score = alphaBetaPruning(depth + 1, alpha, beta, player.getOpposite());
                gameBoard.setCell(fromCell.getRow(), fromCell.getCol(), fromCell);
                gameBoard.setCell(toCell.getRow(), toCell.getCol(), toCell);
                gameBoard.setCell(eatCell.getRow(), eatCell.getCol(), eatCell);

                if (score > step_score)
                    score = step_score;
                if (beta > score)
                    beta = score;
                if (beta <= alpha) {
                    if (depth == 0)
                        computerMove = new Move(m);
                    break;
                }
            }
            return score;
        }

//        double score = beta;
//        for (Move m : availiableMoves) {
//            BoardCell fromCell = new BoardCell(m.getFromCell());
//            BoardCell toCell = new BoardCell(m.getToCell());
//            BoardCell eatCell = new BoardCell(m.getEatCell());
//            gameBoard.doMove(m);
//
//            double step_score = -alphaBetaPruning(depth + 1, -score, -alpha, player.getOpposite());
//            gameBoard.setCell(fromCell.getRow(), fromCell.getCol(), fromCell);
//            gameBoard.setCell(toCell.getRow(), toCell.getCol(), toCell);
//            gameBoard.setCell(eatCell.getRow(), eatCell.getCol(), eatCell);
//
//            if (step_score < score) {
//                score = step_score;
//                if (player == Player.BLACK)
//                    computerMove = new Move(m);
//            }
//            if (score <= alpha)
//                return score;
//        }
//
//        return score;
    }

    private double getHeuristicEvaluation() {
        double count = 0;
        double kingWeight = Math.abs(BoardCell.WHITE_PIECE) / 2.0;
        double pieceWeight = Math.abs(BoardCell.WHITE_PIECE) / 6.0;
        for (int row = 0; row < GameBoard.CELL_COUNT; ++row) {
            for (int col = 0; col < GameBoard.CELL_COUNT; ++col) {
                switch (gameBoard.getCell(row, col).getCondition()) {
                    case BoardCell.BLACK_PIECE:
                        if (gameBoard.getCell(row, col).isKingPiece())
                            count -= kingWeight;
                        else
                            count -= pieceWeight;
                        //count -= (row/100.0);
                        break;
                    case BoardCell.WHITE_PIECE:
                        if (gameBoard.getCell(row, col).isKingPiece())
                            count += kingWeight;
                        else
                            count += pieceWeight;
                        //count += (((GameBoard.CELL_COUNT-1) - row)/100.0);
                    default:
                        break;
                }
            }
        }
        return count;
    }
}
