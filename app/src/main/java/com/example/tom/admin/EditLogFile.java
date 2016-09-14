package com.example.tom.admin;


import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.srmmeasurements.R;
import com.example.tom.srmmeasurmens.Main;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class EditLogFile extends Activity {
    TextView log;
    Button deletelog;
    String logj="";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editlogfile);
        log = (TextView) findViewById(R.id.log);
        deletelog = (Button) findViewById(R.id.deletelog);
        log.setText("");
        getlog();
        log.setMovementMethod(new ScrollingMovementMethod());


        deletelog.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

            deletelog();



            }
        });
    }

    public void getlog() {


        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + Main.getip() + ":8080/useraccount2/returnlogfile/dosearch", new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {

                    JSONObject jb = new JSONObject(response);
                    JSONArray st = jb.getJSONArray("log");
                    for (int i = 0; i < st.length(); i++) {

                        logj = logj + "\n" + st.get(i).toString();
                        // loop and add it to array or arraylist
                    }
                    Log.e("Log:", logj);
                    log.setText(logj);
                    log.setMovementMethod(new ScrollingMovementMethod());
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
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });

    }



    public void deletelog(){
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + Main.getip() + ":8080/useraccount2/deletelogfile/dodelete", new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {

                    JSONObject jb = new JSONObject(response);

                   if(jb.getBoolean("status")){

                      Toast.makeText(getApplication(),"You successfull deleted the logfile",Toast.LENGTH_LONG).show();
                       log.setText("");
                   }else{
                       Toast.makeText(getApplicationContext(), "Error on deleting logfile", Toast.LENGTH_LONG).show();
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
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}













