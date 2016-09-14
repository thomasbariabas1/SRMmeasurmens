package com.example.tom.admin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.srmmeasurements.R;
import com.example.tom.srmmeasurmens.LoginActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Admin extends Activity{

    Button btnEditUser;
    Button btnEditMeasurements;
    Button btnEditLogFile;
    Button btnLogOut;
    TextView attempts;
    SharedPreferences mPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        attempts = (TextView)findViewById(R.id.logo1);
        // Buttons
        btnEditUser = (Button) findViewById(R.id.btnEditUser);
        btnEditMeasurements = (Button) findViewById(R.id.btnEditMeasurements);
        btnEditLogFile =(Button) findViewById(R.id.btnEditLogFile);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);

        btnEditUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), EditUser.class);
                startActivity(i);

            }
        });

        btnEditMeasurements.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), EditMeasurements.class);
                startActivity(i);

            }
        });
        btnEditLogFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), EditLogFile.class);
                startActivity(i);

            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
                SharedPreferences.Editor editor=mPreferences.edit();
                editor.remove("UserName");
                editor.remove("PassWord");
                editor.apply();
                Message myMessage=new Message();
                myMessage.obj="NOTSUCCESS";
                handler.sendMessage(myMessage);

                finish();

            }
        });

    }
    @Override
    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish()
    }
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            String loginmsg=(String)msg.obj;
            if(loginmsg.equals("NOTSUCCESS")) {
                Intent intent = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                intent.putExtra("LoginMessage", "Logged Out");

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                removeDialog(0);
            }
        }


    };


}
