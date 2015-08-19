package com.intel.xrt.samples.russiancheckers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final SharedPreferences settings = getSharedPreferences("RussianCheckersSettings", Context.MODE_PRIVATE);
        final RadioGroup difficultyGroup = (RadioGroup) findViewById(R.id.difficultyGroup);
        RadioButton rb;
        switch (settings.getInt("Difficulty", R.id.mediumDifficulty)) {
            case R.id.lowDifficulty:
                rb = (RadioButton) findViewById(R.id.lowDifficulty);
                rb.setChecked(true);
                break;
            case R.id.mediumDifficulty:
                rb = (RadioButton) findViewById(R.id.mediumDifficulty);
                rb.setChecked(true);
                break;
            case R.id.hardDifficulty:
                rb = (RadioButton) findViewById(R.id.hardDifficulty);
                rb.setChecked(true);
                break;
        }
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("Difficulty", difficultyGroup.getCheckedRadioButtonId());
                editor.apply();
                Toast.makeText(getApplicationContext(), "Settings saved!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
