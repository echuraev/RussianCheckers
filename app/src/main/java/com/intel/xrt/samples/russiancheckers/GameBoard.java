package com.intel.xrt.samples.russiancheckers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;

public class GameBoard implements OnClickListener {
    private final int cellsCount = 8;
    private Canvas canvas;
    private Paint paint;
    private BoardCell[][] cells = new BoardCell[cellsCount][cellsCount];

    public GameBoard(Canvas c, Paint paint) {
        this.canvas = c;
        this.paint = paint;
    }

    @Override
    public void onClick(View v) {

    }
}
