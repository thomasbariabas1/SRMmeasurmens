package com.example.tom.srmmeasurmens;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.srmmeasurements.R;

/**
 * Created by tom on 6/23/2016.
 */
public class Settings extends Activity {
    EditText serverip;
    Button save;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        serverip = (EditText) findViewById(R.id.serveripinput);
        save = (Button) findViewById(R.id.savesettings);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main.setip(serverip.getText().toString());
                Log.e("ip",Main.getip());
                finish();
            }
        });
    }
    }
