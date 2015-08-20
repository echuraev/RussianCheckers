package com.intel.samples.russiancheckers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intel.core.algorithm.AlphaBetaPruning;

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
        final SharedPreferences settings = getSharedPreferences("RussianCheckersSettings", Context.MODE_PRIVATE);
        int difficulty = 0;
        switch (settings.getInt("Difficulty", R.id.mediumDifficulty)) {
            case R.id.lowDifficulty:
                difficulty = AlphaBetaPruning.LOW_DIFFICULTY;
                break;
            case R.id.mediumDifficulty:
                difficulty = AlphaBetaPruning.MEDIUM_DIFFICULTY;
                break;
            case R.id.hardDifficulty:
                difficulty = AlphaBetaPruning.HIGH_DIFFICULTY;
                break;
        }
        chessBoardView = (ChessBoardView) getLastNonConfigurationInstance();
        if (chessBoardView == null) {
            chessBoardView = new ChessBoardView(this, statusTextView, difficulty);
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
