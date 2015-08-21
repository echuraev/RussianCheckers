package com.intel.samples.russiancheckers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.intel.core.algorithm.AlgorithmType;


public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newComputerGameButton = (Button) findViewById(R.id.newComputerGameButton);
        Button newHumanGameButton = (Button) findViewById(R.id.newHumanGameButton);
        Button scoreButton = (Button) findViewById(R.id.scoreButton);
        Button exitButton = (Button) findViewById(R.id.exitButton);
        newComputerGameButton.setOnClickListener(this);
        newHumanGameButton.setOnClickListener(this);
        scoreButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.newComputerGameButton:
                intent = new Intent(getApplicationContext(), ChessBoardActivity.class);
                intent.putExtra("algorithm_type", AlgorithmType.COMPUTER.toString());
                startActivity(intent);
                break;
            case R.id.newHumanGameButton:
                intent = new Intent(getApplicationContext(), ChessBoardActivity.class);
                intent.putExtra("algorithm_type", AlgorithmType.HUMAN.toString());
                startActivity(intent);
                break;
            case R.id.scoreButton:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.exitButton:
                finish();
                System.exit(0);
                break;
        }
    }
}
