package com.intel.russiancheckers.ui;

import com.intel.core.algorithm.AlgorithmType;
import com.intel.core.algorithm.AlphaBetaPruning;
import com.intel.core.algorithm.HumanPlayer;
import com.intel.core.algorithm.IAlgorithm;
import com.intel.core.board.BoardCell;
import com.intel.core.board.CellRect;
import com.intel.core.rules.Move;
import com.intel.core.rules.Player;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.ByValue;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.Owned;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.coregraphics.c.CoreGraphics;
import ios.coregraphics.opaque.CGColorRef;
import ios.coregraphics.opaque.CGContextRef;
import ios.coregraphics.struct.CGPoint;
import ios.coregraphics.struct.CGRect;
import ios.foundation.NSDictionary;
import ios.foundation.NSSet;
import ios.foundation.NSString;
import ios.uikit.UIColor;
import ios.uikit.UIEvent;
import ios.uikit.UIFont;
import ios.uikit.UILabel;
import ios.uikit.UITouch;
import ios.uikit.UIView;
import ios.uikit.c.UIKit;

import com.intel.core.rules.GameBoard;

import java.util.List;

@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("ChessBoardView")
@RegisterOnStartup
public class ChessBoardView extends UIView {
    private final float externalMargin = 5;
    private final CGColorRef WHITE_PIECE_COLOR = UIColor.redColor().CGColor();
    private final CGColorRef BLACK_PIECE_COLOR = UIColor.blueColor().CGColor();
    private final CGColorRef CROWN_COLOR = UIColor.yellowColor().CGColor();
    private final CGColorRef HIGHLIGHT_COLOR = UIColor.greenColor().CGColor();
    private double screenW;
    private double screenH;
    private double cellSize;
    private double boardMargin;
    private CGContextRef context;
    private Thread clickThread;
    private GameBoard gameBoard;
    private BoardCell previousCell;
    private BoardCell requiredMoveCell;
    private IAlgorithm algorithm;
    private Player player;
    private UILabel statusText;

    static {
        NatJ.register();
    }

    @Generated("NatJ")
    @Owned
    @Selector("alloc")
    public static native ChessBoardView alloc();

    protected ChessBoardView(Pointer peer) {
        super(peer);
        clickThread = null;
        gameBoard = new GameBoard();
        previousCell = null;
        requiredMoveCell = null;
        player = Player.WHITE;
        statusText = null;
    }

    public UIView initWithFrameAndParams(@ByValue CGRect cgRect, AlgorithmType type, int difficulty, UILabel statusText) {
        if (type == AlgorithmType.COMPUTER) {
            algorithm = new AlphaBetaPruning(gameBoard, difficulty);
        }
        else {
            algorithm = new HumanPlayer();
        }
        this.statusText = statusText;
        statusText.setText("Status: Turn of " + player.getPlayerName() + " player...");
        return super.initWithFrame(cgRect);
    }

    @Override
    @Selector("drawRect:")
    public void drawRect(@ByValue CGRect rect) {
        context = UIKit.UIGraphicsGetCurrentContext();
        CoreGraphics.CGContextClearRect(context, rect);
        screenH = this.bounds().size().height();
        screenW = this.bounds().size().width();
        CoreGraphics.CGContextSetRGBFillColor(context, 255, 255, 255, 1);
        CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(0, 0, screenW, screenH));

        drawBoard();
        drawCells();
        drawPieces();
    }

    private void drawBoard() {
        CoreGraphics.CGContextSetRGBFillColor(context, 0, 0, 0, 1);
        double rectX = externalMargin;
        double rectY = externalMargin;
        double rectWidth = screenW - 2*externalMargin;
        double rectHeight = screenH - 2*externalMargin;
        CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(rectX, rectY, rectWidth, rectHeight));

        CoreGraphics.CGContextSetRGBFillColor(context, 255, 255, 255, 1);

        rectX = externalMargin + 1;
        rectY = externalMargin + 1;
        rectWidth = screenW - 2*(externalMargin + 1);
        rectHeight = screenH - 2*(externalMargin + 1);
        CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(rectX, rectY, rectWidth, rectHeight));
        CoreGraphics.CGContextSetRGBFillColor(context, 0, 0, 0, 1);
        boardMargin = screenW / 12;
        rectX = boardMargin - 1;
        rectY = boardMargin - 1;
        rectWidth = screenW - 2*(boardMargin - 1);
        rectHeight = screenH - 2*(boardMargin - 1);
        CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(rectX, rectY, rectWidth, rectHeight));
        double boardSize = screenW - 2*boardMargin;
        cellSize = boardSize / GameBoard.CELL_COUNT;
        drawCellsNames();
    }

    private void drawCellsNames() {
        String[] xTitle = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] yTitle = {"1", "2", "3", "4", "5", "6", "7", "8"};
        UIFont font = UIFont.fontWithNameSize("Arial", cellSize / 2);
        NSDictionary dict = NSDictionary.alloc().initWithObjectsAndKeys(font, "NSFontAttributeName", null);

        for (int i = 0; i < xTitle.length; ++i) {
            NSString text = NSString.alloc().initWithString(xTitle[i]);
            text.drawAtPointWithAttributes(CoreGraphics.CGPointMake(cellSize / 3 + boardMargin + i * cellSize, screenH - (externalMargin + 2*boardMargin/3)),
                    dict);
        }

        for (int i = 0; i < yTitle.length; ++i) {
            NSString text = NSString.alloc().initWithString(yTitle[yTitle.length - i - 1]);
            text.drawAtPointWithAttributes(CoreGraphics.CGPointMake(externalMargin + boardMargin/3, cellSize/3 + boardMargin + i*cellSize),
                    dict);
        }

        CoreGraphics.CGContextRotateCTM(context, Math.toRadians(180));
        for (int i = 0; i < xTitle.length; ++i) {
            NSString text = NSString.alloc().initWithString(xTitle[i]);
            text.drawAtPointWithAttributes(CoreGraphics.CGPointMake(-(cellSize / 2 + externalMargin + boardMargin + i * cellSize), -(externalMargin + 2*boardMargin/3)),
                    dict);
        }

        for (int i = 0; i < yTitle.length; ++i) {
            NSString text = NSString.alloc().initWithString(yTitle[yTitle.length - i - 1]);
            text.drawAtPointWithAttributes(CoreGraphics.CGPointMake(-(screenW - (externalMargin + boardMargin/3)), -(cellSize/2 + 3*externalMargin/2 + boardMargin + i*cellSize)),
                    dict);
        }
        CoreGraphics.CGContextRotateCTM(context, Math.toRadians(180));
    }

    private void drawCells() {
        for (int row = 0; row < GameBoard.CELL_COUNT; ++row) {
            for (int col = 0; col < GameBoard.CELL_COUNT; ++col) {
                CellRect rect = new CellRect(boardMargin + cellSize*col,
                        boardMargin + cellSize*row,
                        boardMargin + cellSize*(col+1),
                        boardMargin + cellSize*(row+1));
                gameBoard.getCell(row, col).setRect(rect);
                if ((row+col) % 2 == 0) {
                    CoreGraphics.CGContextSetRGBFillColor(context, 255, 255, 255, 1);
                }
                else {
                    CoreGraphics.CGContextSetRGBFillColor(context, 0, 0, 0, 1);
                    if (gameBoard.getCell(row, col).isHighlight())
                        CoreGraphics.CGContextSetFillColor(context, CoreGraphics.CGColorGetComponents(HIGHLIGHT_COLOR));
                }
                CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(rect.getLeft(), rect.getTop(),
                        rect.getWidth(), rect.getHeight()));
            }
        }
    }

    private void drawPieces() {
        double radius = cellSize/3;
        for (int row = 0; row < GameBoard.CELL_COUNT; ++row) {
            for (int col = 0; col < GameBoard.CELL_COUNT; ++col) {
                if (gameBoard.getCell(row, col).getCondition() == BoardCell.WHITE_PIECE)
                    CoreGraphics.CGContextSetFillColor(context, CoreGraphics.CGColorGetComponents(WHITE_PIECE_COLOR));
                else if (gameBoard.getCell(row, col).getCondition() == BoardCell.BLACK_PIECE)
                    CoreGraphics.CGContextSetFillColor(context, CoreGraphics.CGColorGetComponents(BLACK_PIECE_COLOR));
                else
                    continue;
                double circleCenterX = gameBoard.getCell(row, col).getRect().getLeft() + cellSize/2;
                double circleCenterY = gameBoard.getCell(row, col).getRect().getTop() + cellSize/2;
                double x = circleCenterX - radius;
                double y = circleCenterY - radius;
                CoreGraphics.CGContextFillEllipseInRect(context, CoreGraphics.CGRectMake(x, y, 2 * radius, 2 * radius));
                if (gameBoard.getCell(row, col).isKingPiece())
                    drawCrown(circleCenterX, circleCenterY, radius);
            }
        }
    }

    private void drawCrown(double cx, double cy, double radius) {
        double crownWidth = ((cx + radius/2) - (cx - radius/2));
        CoreGraphics.CGContextMoveToPoint(context, cx + radius/3, cy + radius/3);
        CoreGraphics.CGContextAddLineToPoint(context, cx - radius / 3, cy + radius / 3);
        CoreGraphics.CGContextAddLineToPoint(context, cx - radius/2, cy - radius/3);
        CoreGraphics.CGContextAddLineToPoint(context, cx - radius/2 + crownWidth/4, cy);
        CoreGraphics.CGContextAddLineToPoint(context, cx - radius/2 + crownWidth/2, cy - radius/3);
        CoreGraphics.CGContextAddLineToPoint(context, cx - radius/2 + 3*crownWidth/4, cy);
        CoreGraphics.CGContextAddLineToPoint(context, cx - radius/2 + crownWidth, cy - radius/3);
        CoreGraphics.CGContextAddLineToPoint(context, cx + radius / 3, cy + radius / 3);
        CoreGraphics.CGContextSetFillColor(context, CoreGraphics.CGColorGetComponents(CROWN_COLOR));
        CoreGraphics.CGContextFillPath(context);
    }

    @Override
    public void layoutSubviews() {
        super.layoutSubviews();
        setNeedsDisplay();
    }

    @Override
    public void touchesEndedWithEvent(NSSet nsSet, UIEvent uiEvent) {
        super.touchesEndedWithEvent(nsSet, uiEvent);
        if (clickThread != null && clickThread.isAlive())
            return;
        if (gameBoard.hasWon(player) || gameBoard.hasWon(player.getOpposite()))
            return;

        UITouch touch = (UITouch) nsSet.anyObject();
        CGPoint point = touch.locationInView(this);
        double x = point.x();
        double y = point.y();

        for (int row = 0; row < GameBoard.CELL_COUNT; ++ row) {
            for (int col = 0; col < GameBoard.CELL_COUNT; ++col) {
                if (gameBoard.getCell(row, col).getCondition() == player.getOpposite().getPieceColor())
                    continue;
                if (gameBoard.getCell(row, col).getCondition() == BoardCell.EMPTY_CELL
                        && !gameBoard.getCell(row, col).isHighlight())
                    continue;
                if (gameBoard.getCell(row, col).getRect().contains(x, y)) {
                    if (requiredMoveCell != null) {
                        boolean requiredMove = false;
                        for (Move eatMove : gameBoard.getAvailiableMoves(requiredMoveCell)) {
                            if (gameBoard.getCell(row, col) == eatMove.getToCell()) {
                                requiredMove = true;
                            }
                        }
                        if (!requiredMove)
                            continue;
                    }
                    final int finalRow = row;
                    final int finalCol = col;
                    clickThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            cellTouch(gameBoard.getCell(finalRow, finalCol));
                        }
                    });
                    clickThread.start();
                }
            }
        }
    }

    private void cellTouch(BoardCell cell) {
        if (cell.getCondition() == player.getPieceColor()) {
            highlightMoves(cell, player);
        }
        else {
            doMove(cell);
        }
        setNeedsDisplay();
    }

    private void highlightMoves(BoardCell cell, final Player player) {
        if (previousCell != null) {
            List<Move> moves = gameBoard.getAvailiableMoves(previousCell);
            for (Move m : moves) {
                m.getToCell().setHighlight(false);
            }
            previousCell.setHighlight(false);
        }

        boolean moveIsAvailiable = false;
        for (Move m : gameBoard.getAllAvailiableMoves(player)) {
            if (m.getFromCell() == cell)
                moveIsAvailiable = true;
        }
        if (!moveIsAvailiable) {
            statusText.setText("Status: " + player.getPlayerName() + " player have to eat...");
            return;
        }

        previousCell = cell;
        cell.setHighlight(true);
        List<Move> moves = gameBoard.getAvailiableMoves(cell);
        for (Move m : moves) {
            m.getToCell().setHighlight(true);
        }
    }

    private void doMove(BoardCell cell) {
        if (previousCell == null)
            return;

        requiredMoveCell = (requiredMoveCell == previousCell) ? null : requiredMoveCell;

        previousCell.setHighlight(false);
        List<Move> moves = gameBoard.getAvailiableMoves(previousCell);
        for (Move m : moves) {
            m.getToCell().setHighlight(false);
        }
        previousCell = null;
        for (Move m : moves) {
            if (m.getToCell().getRow() == cell.getRow() && m.getToCell().getCol() == cell.getCol()) {
                if (m.getEatCell() != null) {
                    gameBoard.doMove(m);
                    if (gameBoard.isExistsNextEatMove(m.getToCell(), m.getToCell(), null)) {
                        requiredMoveCell = m.getToCell();
                        highlightMoves(requiredMoveCell, player);
                        statusText.setText("Status: Turn of " + player.getPlayerName() + " player...");
                        return;
                    }
                }
                else {
                    gameBoard.doMove(m);
                }
                if (gameBoard.hasWon(player)) {
                    statusText.setText("Status: " + player.getPlayerName() + " player has won!");
                    setNeedsDisplay();
                    return;
                }
                if (algorithm.getAlgorithmType() == AlgorithmType.HUMAN) {
                    player = player.getOpposite();
                    break;
                }
                int i = 0;
                do {
                    if (gameBoard.getAllAvailiableMoves(player.getOpposite()).isEmpty())
                        break;
                    if (i > 0) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }
                    i++;
                    algorithm.getAlgorithm(player.getOpposite());
                    boolean eatMove = false;
                    if (algorithm.getOpponentMove().getEatCell() != null
                            && algorithm.getOpponentMove().getEatCell().getRect() != null)
                        eatMove = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            statusText.setText("Status: Turn of " + player.getOpposite().getPlayerName() + " player...");
                            highlightMoves(algorithm.getOpponentMove().getFromCell(), player.getOpposite());
                            setNeedsDisplay();
                        }
                    }).start();
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.getLocalizedMessage();
                    }
                    algorithm.getOpponentMove().getFromCell().setHighlight(false);
                    List<Move> compMoves = gameBoard.getAvailiableMoves(algorithm.getOpponentMove().getFromCell());
                    for (Move cm : compMoves) {
                        cm.getToCell().setHighlight(false);
                    }
                    gameBoard.doMove(algorithm.getOpponentMove());
                    if (!eatMove)
                        break;
                    if (gameBoard.hasWon(player.getOpposite())) {
                        statusText.setText("Status: " + player.getOpposite().getPlayerName() + " player has won!");
                        return;
                    }
                    setNeedsDisplay();
                } while (gameBoard.isExistsNextEatMove(algorithm.getOpponentMove().getToCell(), algorithm.getOpponentMove().getToCell(), null));
                break;
            }
        }
        statusText.setText("Status: Turn of " + player.getPlayerName() + " player...");
    }
}
