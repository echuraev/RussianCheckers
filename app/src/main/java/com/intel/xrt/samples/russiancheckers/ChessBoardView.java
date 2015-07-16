package com.intel.xrt.samples.russiancheckers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ChessBoardView extends View {
    private final int cellsCount = 8;
    private float screenW;
    private float screenH;
    private float cellSize;
    private float boardMargin;
    private final float externalMargin = 5;
    private int[][] piecesCells = new int[cellsCount][cellsCount/2];

    public ChessBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for (int row = 0; row < cellsCount; ++row) {
            for (int col = 0; col < cellsCount/2; ++col) {
                if (row < 3)
                    piecesCells[row][col] = 1;
                else if (row > 4)
                    piecesCells[row][col] = -1;
                else
                    piecesCells[row][col] = 0;
            }
        }
    }

    @Override
    protected void onDraw(Canvas c){
        super.onDraw(c);

        Paint paint = new Paint();
        drawBoard(c, paint);
        drawPieces(c, paint);
        c.restore();
    }

    private void drawBoard(Canvas c, Paint paint) {
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Color.WHITE);
        c.drawPaint(paint);

        paint.setColor(Color.BLACK);
        float rectX = externalMargin;
        float rectY = externalMargin;
        float rectWidth = screenW - externalMargin;
        float rectHeight = screenH - externalMargin;
        c.drawRect(rectX, rectY, rectWidth, rectHeight, paint);
        paint.setColor(Color.WHITE);
        rectX = externalMargin + 1;
        rectY = externalMargin + 1;
        rectWidth = screenW - (externalMargin + 1);
        rectHeight = screenH - (externalMargin + 1);
        c.drawRect(rectX, rectY, rectWidth, rectHeight, paint);
        paint.setColor(Color.BLACK);
        boardMargin = screenW / 12;
        rectX = boardMargin - 1;
        rectY = boardMargin - 1;
        rectWidth = screenW - (boardMargin - 1);
        rectHeight = screenH - (boardMargin - 1);
        c.drawRect(rectX, rectY, rectWidth, rectHeight, paint);
        float boardSize = screenW - 2*boardMargin;
        cellSize = boardSize / cellsCount;

        for (int row = 0; row < cellsCount; ++row) {
            for (int col = 0; col < cellsCount; ++col) {
                if ((row+col) % 2 == 0)
                    paint.setColor(Color.WHITE);
                else
                    paint.setColor(Color.BLACK);
                c.drawRect(boardMargin + cellSize*row, boardMargin + cellSize*col, boardMargin + cellSize*(row+1), boardMargin + cellSize*(col+1), paint);
            }
        }
        drawCellsNames(c, paint);
    }

    private void drawCellsNames(Canvas c, Paint paint) {
        String[] xTitle = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] yTitle = {"1", "2", "3", "4", "5", "6", "7", "8"};

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(cellSize / 2);
        for (int i = 0; i < xTitle.length; ++i) {
            c.drawText(xTitle[i], cellSize/3 + boardMargin + i*cellSize, screenH - (externalMargin + boardMargin/3), paint);
        }

        for (int i = 0; i < yTitle.length; ++i) {
            c.drawText(yTitle[yTitle.length - i - 1], externalMargin + boardMargin/3, cellSize/2 + 5*externalMargin/2 + boardMargin + i*cellSize, paint);
        }
        c.rotate(180, (screenW - (cellSize / 3 + boardMargin)), (externalMargin + boardMargin / 3));
        for (int i = 0; i < xTitle.length; ++i) {
            c.drawText(xTitle[xTitle.length - i - 1], (screenW - (cellSize / 3 + boardMargin)) + i*cellSize, (externalMargin + boardMargin/3), paint);
        }
        c.rotate(180, (screenW - (cellSize / 3 + boardMargin)), (externalMargin + boardMargin/3));
        c.rotate(180,  screenW - (externalMargin + boardMargin/3), screenH);
        for (int i = 0; i < yTitle.length; ++i) {
            c.drawText(yTitle[i], screenW - (externalMargin + boardMargin/3), (screenH + cellSize/2 + 5*externalMargin/2 + boardMargin) + i*cellSize, paint);
        }
        c.rotate(180,  screenW - (externalMargin + boardMargin/3), screenH);
    }

    private void drawPieces(Canvas c, Paint paint) {
        paint.setAntiAlias(true);
        float bottomBorder = screenH - (externalMargin + boardMargin);
        float leftBorder = externalMargin + boardMargin;
        float radius = cellSize/3;
        for (int row = 0; row < cellsCount; ++row) {
            for (int col = 0; col < cellsCount/2; ++col) {
                if (piecesCells[row][col] == 0)
                    continue;
                else if (piecesCells[row][col] > 0)
                    paint.setColor(Color.RED);
                else
                    paint.setColor(Color.BLUE);
                if (row % 2 == 0)
                    c.drawCircle(leftBorder + 2*col*cellSize + cellSize/(float)2.4, bottomBorder - row*cellSize - cellSize/(float)2.4, radius, paint);
                else
                    c.drawCircle(leftBorder + 2*col*cellSize + cellSize/(float)2.4 + cellSize, bottomBorder - row*cellSize - cellSize/(float)2.4, radius, paint);
            }
        }
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
}
