package com.intel.xrt.samples.russiancheckers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newGameButton = (Button) findViewById(R.id.newGameButton);
        Button continueButton = (Button) findViewById(R.id.continueButton);
        Button scoreButton = (Button) findViewById(R.id.scoreButton);
        Button exitButton = (Button) findViewById(R.id.exitButton);
        newGameButton.setOnClickListener(this);
        continueButton.setOnClickListener(this);
        scoreButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

        continueButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newGameButton:
                Intent gameIntent = new Intent(getApplicationContext(), ChessBoardActivity.class);
                startActivity(gameIntent);
                break;
            case R.id.continueButton:
                //TODO: Impement here method for open activity with old game
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
