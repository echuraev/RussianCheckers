package com.intel.samples.russiancheckers;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChessBoardActivity extends Activity {

    private ChessBoardView chessBoardView;
    private LinearLayout rootLayout;
    TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_board);
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        statusTextView = (TextView) findViewById(R.id.statusTextView);
        setLayoutOrientation();
        chessBoardView = (ChessBoardView) getLastNonConfigurationInstance();
        if (chessBoardView == null) {
            chessBoardView = new ChessBoardView(this, statusTextView);
        }
        chessBoardView.invalidate();
        rootLayout.addView(chessBoardView, 0);
    }

    public Object onRetainNonConfigurationInstance() {
        rootLayout.removeView(chessBoardView);
        return chessBoardView;
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("statusText", statusTextView.getText());
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        statusTextView.setText(savedInstanceState.getCharSequence("statusText"));
    }

    private void setLayoutOrientation(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        if (height < width)
            rootLayout.setOrientation(LinearLayout.HORIZONTAL);
        else
            rootLayout.setOrientation(LinearLayout.VERTICAL);
    }
}
