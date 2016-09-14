package com.example.tom.admin;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.srmmeasurements.R;

import com.example.tom.srmmeasurmens.Main;
import com.example.tom.srmmeasurmens.Utility;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;


public class AddUser extends Fragment {
    private RadioGroup radioGroup;
    private RadioButton engineerbtn;
    private RadioButton userbtn;
    private EditText username;
    private EditText password;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.adduser, container, false);
        username = (EditText) rootView.findViewById(R.id.editText);
        password = (EditText) rootView.findViewById(R.id.editText3);

            engineerbtn = (RadioButton) rootView.findViewById(R.id.radioButton);
            userbtn = (RadioButton) rootView.findViewById(R.id.radioButton2);
            radioGroup = (RadioGroup) rootView.findViewById(R.id.radiogroup);
        Button btn = (Button) rootView.findViewById(R.id.button2);
             btn.setOnClickListener(new View.OnClickListener() {

                 public void onClick(View view) {
                     registerUser();


                 }
             });


        return rootView;
    }

    public void registerUser(){
        String uname,pwd;
        uname= username.getText().toString();
        // Instantiate Http Request Param Object
        String selection =null;
        pwd = password.getText().toString();
        if (radioGroup.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(getActivity().getApplication(),"Must Choose Engineer Or User",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(engineerbtn.isChecked()){
                 selection = (String)engineerbtn.getText();
            }else
                 selection = (String)userbtn.getText();
        }
        RequestParams params = new RequestParams();

        // When Email Edit View and Password Edit View have values other than Null
        if(Utility.isNotNull(uname) && Utility.isNotNull(pwd)){
            // When Email entered is Valid

            // Put Http parameter username with value of Email Edit View control
            params.put("username", uname);
            // Put Http parameter password with value of Password Edit Value control
            params.put("password", pwd);
            params.put("idiotita",selection);
            // Invoke RESTful Web Service with Http parameters
            invokeWS(params);

        } else{
            Toast.makeText(getActivity().getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }

    public void invokeWS(RequestParams params){

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+ Main.getip()+":8080/useraccount2/register/doregister",params ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        // Set Default Values for Edit View controls
                        setDefaultValues();
                        // Display successfully registered message using Toast
                        Toast.makeText(getActivity().getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else{

                        Toast.makeText(getActivity().getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {

                    Toast.makeText(getActivity().getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {


                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getActivity().getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void setDefaultValues(){
        username.setText("");
        password.setText("");
        radioGroup.clearCheck();
    }
}




