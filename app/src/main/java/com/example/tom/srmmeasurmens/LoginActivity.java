package com.example.tom.srmmeasurmens;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srmmeasurements.R;
import com.example.tom.admin.Admin;
import com.example.tom.engineer.Engineer;
import com.example.tom.user.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class LoginActivity extends Activity{


    private EditText username;
    private EditText password;
    private  TextView error=null;

    private ProgressDialog prgDialog;

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


       username = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);
       Button btnLogin = (Button) findViewById(R.id.button1);
        error = (TextView) findViewById(R.id.error);

        // Progress dialog
       ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);




        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            loginUser();

            }
        });
        }

    public void loginUser(){
        // Get Email Edit View Value
        String uname = username.getText().toString();
        // Get Password Edit View Value

        String pwd = password.getText().toString();
        // Instantiate Http Request Param Object
         RequestParams params = new RequestParams();

        // When Email Edit View and Password Edit View have values other than Null
        if(Utility.isNotNull(uname) && Utility.isNotNull(pwd)){
            // When Email entered is Valid

                // Put Http parameter username with value of Email Edit View control
                params.put("username", uname);
                // Put Http parameter password with value of Password Edit Value control
                params.put("password", pwd);

                // Invoke RESTful Web Service with Http parameters
                invokeWS(params);

        } else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }

    public void invokeWS(RequestParams params){


        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+Main.getip()+":8080/useraccount2/login/dologin",params ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                        File backupPath = Environment.getExternalStorageDirectory();

                        backupPath = new File(backupPath.getPath() + "/Android/data/com.example.tom.srmmeasurements/files");
                        if(!backupPath.exists()){
                            backupPath.mkdirs();
                        }//end if


                        try {
                            OutputStreamWriter fos = new OutputStreamWriter(openFileOutput("LOG.txt", Context.MODE_APPEND));

                            String state = Environment.getExternalStorageState();
                            Calendar c = Calendar.getInstance();

                            if(Environment.MEDIA_MOUNTED.equals(state)){
                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

                                    fos.write("\n");
                                    fos.write(" LOGGED IN:");
                                    fos.flush();
                                    fos.write(username.getText().toString());
                                    fos.flush();
                                    fos.write(" IDIOTITA: ");
                                    fos.flush();
                                    fos.write(obj.getString("idiotita"));
                                    fos.flush();
                                    fos.write(" DATE: ");
                                    fos.flush();
                                    fos.write(df.format(c.getTime()));
                                    fos.flush();


                                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));
                                Date currentLocalTime = cal.getTime();
                                DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
                                date.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));
                                fos.write(" "+date.format(currentLocalTime));





                                fos.close();

                            }
                            } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }


                        // Navigate to Home screen
                        navigatetoActivity(obj.getString("idiotita"));
                    }
                    // Else display error message
                    else{
                        error.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {

                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {


                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.action_settings:
                Intent i = new Intent(getApplicationContext(), Settings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void navigatetoActivity(String idio){
        if(idio.equals("admin")) {

            Intent homeIntent = new Intent(getApplicationContext(), Admin.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        if(idio.equals("engineer")) {

            Intent homeIntent = new Intent(getApplicationContext(), Engineer.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
        if(idio.equals("user")) {

            Intent homeIntent = new Intent(getApplicationContext(), User.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
    }

    }

