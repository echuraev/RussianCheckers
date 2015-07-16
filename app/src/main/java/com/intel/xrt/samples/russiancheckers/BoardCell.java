package com.intel.xrt.samples.russiancheckers;

import android.view.View;
import android.view.View.OnClickListener;


public class BoardCell implements OnClickListener {
    public static final int EMPTY_CELL = 0;
    public static final int WHITE_CELL = 1;
    public static final int WHITE_KING_CELL = 2;
    public static final int BLACK_CELL = -1;
    public static final int BLACK_KING_CELL = -2;
    private int cellCondition;

    public BoardCell(int color) {

    }

    public BoardCell(int color, int condition) {

    }

    public int getCondition() {
        return cellCondition;
    }

    public void setHighlight() {

    }

    @Override
    public void onClick(View v) {
        //v.
    }
}
