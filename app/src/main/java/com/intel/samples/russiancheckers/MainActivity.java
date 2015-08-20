package com.intel.samples.russiancheckers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newGameButton = (Button) findViewById(R.id.newGameButton);
        Button scoreButton = (Button) findViewById(R.id.scoreButton);
        Button exitButton = (Button) findViewById(R.id.exitButton);
        newGameButton.setOnClickListener(this);
        scoreButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newGameButton:
                Intent gameIntent = new Intent(getApplicationContext(), ChessBoardActivity.class);
                startActivity(gameIntent);
                break;
            case R.id.scoreButton:
                Intent scoreIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(scoreIntent);
                break;
            case R.id.exitButton:
                finish();
                System.exit(0);
                break;
        }
    }
}
