package com.intel.xrt.samples.russiancheckers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.intel.xrt.samples.common.board.CellRect;
import com.intel.xrt.samples.common.board.BoardCell;
import com.intel.xrt.samples.common.rules.GameBoard;
import com.intel.xrt.samples.common.rules.Move;

import java.util.List;

public class ChessBoardView extends View implements OnTouchListener {
    private final float externalMargin = 5;
    private final int WHITE_PIECE_COLOR = Color.RED;
    private final int BLACK_PIECE_COLOR = Color.BLUE;
    private final int CROWN_COLOR = Color.YELLOW;
    private final int HIGHLIGHT_COLOR = Color.GREEN;
    private float screenW;
    private float screenH;
    private float cellSize;
    private float boardMargin;
    private Canvas canvas = null;
    private Paint paint = null;
    private GameBoard gameBoard;
    private BoardCell previousCell;

    public ChessBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gameBoard = new GameBoard();
        previousCell = null;
    }

    @Override
    protected void onDraw(Canvas c){
        canvas = c;
        super.onDraw(canvas);

        paint = new Paint();
        drawBoard();
        drawCells();
        drawPieces();

        canvas.restore();
        this.setOnTouchListener(this);
    }

    private void drawBoard() {
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        paint.setColor(Color.BLACK);
        float rectX = externalMargin;
        float rectY = externalMargin;
        float rectWidth = screenW - externalMargin;
        float rectHeight = screenH - externalMargin;
        canvas.drawRect(rectX, rectY, rectWidth, rectHeight, paint);
        paint.setColor(Color.WHITE);
        rectX = externalMargin + 1;
        rectY = externalMargin + 1;
        rectWidth = screenW - (externalMargin + 1);
        rectHeight = screenH - (externalMargin + 1);
        canvas.drawRect(rectX, rectY, rectWidth, rectHeight, paint);
        paint.setColor(Color.BLACK);
        boardMargin = screenW / 12;
        rectX = boardMargin - 1;
        rectY = boardMargin - 1;
        rectWidth = screenW - (boardMargin - 1);
        rectHeight = screenH - (boardMargin - 1);
        canvas.drawRect(rectX, rectY, rectWidth, rectHeight, paint);
        float boardSize = screenW - 2*boardMargin;
        cellSize = boardSize / GameBoard.CELL_COUNT;

        drawCellsNames();
    }

    private void drawCellsNames() {
        String[] xTitle = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] yTitle = {"1", "2", "3", "4", "5", "6", "7", "8"};

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(cellSize / 2);
        for (int i = 0; i < xTitle.length; ++i) {
            canvas.drawText(xTitle[i], cellSize/3 + boardMargin + i*cellSize, screenH - (externalMargin + boardMargin/3), paint);
        }

        for (int i = 0; i < yTitle.length; ++i) {
            canvas.drawText(yTitle[yTitle.length - i - 1], externalMargin + boardMargin/3, cellSize/2 + 5*externalMargin/2 + boardMargin + i*cellSize, paint);
        }
        canvas.rotate(180, (screenW - (cellSize / 3 + boardMargin)), (externalMargin + boardMargin / 3));
        for (int i = 0; i < xTitle.length; ++i) {
            canvas.drawText(xTitle[xTitle.length - i - 1], (screenW - (cellSize / 3 + boardMargin)) + i*cellSize, (externalMargin + boardMargin/3), paint);
        }
        canvas.rotate(180, (screenW - (cellSize / 3 + boardMargin)), (externalMargin + boardMargin/3));
        canvas.rotate(180,  screenW - (externalMargin + boardMargin/3), screenH);
        for (int i = 0; i < yTitle.length; ++i) {
            canvas.drawText(yTitle[i], screenW - (externalMargin + boardMargin/3), (screenH + cellSize/2 + 5*externalMargin/2 + boardMargin) + i*cellSize, paint);
        }
        canvas.rotate(180,  screenW - (externalMargin + boardMargin/3), screenH);
    }

    private void drawCells() {
        for (int row = 0; row < GameBoard.CELL_COUNT; ++row) {
            for (int col = 0; col < GameBoard.CELL_COUNT; ++col) {
                CellRect rect = new CellRect(boardMargin + cellSize*col, boardMargin + cellSize*row, boardMargin + cellSize*(col+1), boardMargin + cellSize*(row+1));
                gameBoard.getCell(row, col).setRect(rect);
                if ((row+col) % 2 == 0) {
                    paint.setColor(Color.WHITE);
                    gameBoard.getCell(row, col).setCondition(BoardCell.EMPTY_CELL);
                }
                else {
                    paint.setColor(Color.BLACK);
                    if (gameBoard.getCell(row, col).isHighlight())
                        paint.setColor(HIGHLIGHT_COLOR);
                }
                canvas.drawRect(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom(), paint);
            }
        }
    }

    private void drawPieces() {
        paint.setAntiAlias(true);
        float radius = cellSize/3;
        for (int row = 0; row < GameBoard.CELL_COUNT; ++row) {
            for (int col = 0; col < GameBoard.CELL_COUNT; ++col) {
                if (gameBoard.getCell(row, col).getCondition() == BoardCell.WHITE_PIECE)
                    paint.setColor(WHITE_PIECE_COLOR);
                else if (gameBoard.getCell(row, col).getCondition() == BoardCell.BLACK_PIECE)
                    paint.setColor(BLACK_PIECE_COLOR);
                else
                    continue;
                float circleCenterX = gameBoard.getCell(row, col).getRect().getLeft() + cellSize/2;
                float circleCenterY = gameBoard.getCell(row, col).getRect().getTop() + cellSize/2;
                canvas.drawCircle(circleCenterX, circleCenterY, radius, paint);
                if (gameBoard.getCell(row, col).isKingPiece())
                    drawCrown(circleCenterX, circleCenterY, radius);
            }
        }
    }

    private void drawCrown(float cx, float cy, float radius) {
        Path path = new Path();
        float crownWidth = ((cx + radius/2) - (cx - radius/2));
        path.moveTo(cx + radius/3, cy + radius / 3);
        path.lineTo(cx - radius/3, cy + radius / 3);
        path.lineTo(cx - radius/2, cy - radius / 3);
        path.lineTo(cx - radius/2 + crownWidth/4, cy);
        path.lineTo(cx - radius/2 + crownWidth/2, cy - radius / 3);
        path.lineTo(cx - radius/2 + 3*crownWidth/4, cy);
        path.lineTo(cx - radius/2 + crownWidth, cy - radius / 3);
        path.moveTo(cx + radius/3, cy + radius / 3);
        path.close();
        paint.setColor(CROWN_COLOR);
        canvas.drawPath(path, paint);
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        if (height > width) {
            setMeasuredDimension(width, width);
        }
        else {
            setMeasuredDimension(height, height);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                for (int row = 0; row < GameBoard.CELL_COUNT; ++ row) {
                    for (int col = 0; col < GameBoard.CELL_COUNT; ++col) {
                        if (gameBoard.getCell(row, col).getCondition() == BoardCell.BLACK_PIECE)
                            continue;
                        if (gameBoard.getCell(row, col).getCondition() == BoardCell.EMPTY_CELL
                                && !gameBoard.getCell(row, col).isHighlight())
                            continue;
                        if (gameBoard.getCell(row, col).getRect().contains(x, y)) {
                            cellTouch(row, col);
                        }
                    }
                }
                invalidate();
                return true;
        }
        return false;
    }

    private void cellTouch(int row, int col) {
        if (gameBoard.getCell(row, col).getCondition() == BoardCell.WHITE_PIECE) {
            highlightMoves(row, col);
        }
        else {
            doMove(row, col);
        }
    }

    private void highlightMoves(int row, int col) {
        if (previousCell != null) {
            List<Move> eatMoves = gameBoard.getEatMoves(previousCell, previousCell, null);
            for (Move m : eatMoves) {
                m.getToCell().setHighlight(false);
            }
            List<Move> normalMoves = gameBoard.getNormalMoves(previousCell);
            for (Move m : normalMoves) {
                m.getToCell().setHighlight(false);
            }
            previousCell.setHighlight(false);
        }

        previousCell = gameBoard.getCell(row, col);
        gameBoard.getCell(row, col).setHighlight(true);
        List<Move> eatMoves = gameBoard.getEatMoves(gameBoard.getCell(row, col), gameBoard.getCell(row, col), null);
        for (Move m : eatMoves) {
            m.getToCell().setHighlight(true);
        }
        if (eatMoves != null && eatMoves.size() > 0) {
            return;
        }
        List<Move> normalMoves = gameBoard.getNormalMoves(gameBoard.getCell(row, col));
        for (Move m : normalMoves) {
            m.getToCell().setHighlight(true);
        }
    }

    private void doMove(int row, int col) {
        if (previousCell == null)
            return;

        previousCell.setHighlight(false);
        List<Move> eatMoves = gameBoard.getEatMoves(previousCell, previousCell, null);
        for (Move m : eatMoves) {
            m.getToCell().setHighlight(false);
            if (m.getToCell().getRow() == row && m.getToCell().getCol() == col) {
                gameBoard.doMove(m);
            }
        }
        List<Move> normalMoves = gameBoard.getNormalMoves(previousCell);
        for (Move m : normalMoves) {
            m.getToCell().setHighlight(false);
            if (m.getToCell().getRow() == row && m.getToCell().getCol() == col) {
                gameBoard.doMove(m);
            }
        }

        previousCell = null;
    }
}
