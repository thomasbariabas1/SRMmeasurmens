package com.example.tom.admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.srmmeasurements.R;
import com.example.tom.srmmeasurmens.Main;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.widget.AdapterView.OnItemSelectedListener;
import static com.example.srmmeasurements.R.*;


public class DeleteSafetyEvaluation extends Fragment {

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    ArrayList<String> date;
    ArrayList<String> region;
    ArrayList<String> nomos;
    ArrayList<String> noUniqueNomos;
    ArrayList<String> id;
    ArrayList<String> id_perioxis;
    private Button btndelete;
    private Spinner spinnernomos;
    private Spinner spinnerregion;
    private Spinner spinnerdate;
    ArrayList<String> newDate = new ArrayList<>();
    ArrayList<String> newRegion = new ArrayList<>();
    int check=0;
    int check2=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(layout.deletesafetyevaluation, container, false);

         nomos = new ArrayList<>();
         region = new ArrayList<>();
         date = new ArrayList<>();
         id = new ArrayList<>();
         id_perioxis = new ArrayList<>();
        noUniqueNomos=new ArrayList<>();
        btndelete = (Button) rootView.findViewById(R.id.deleteSafety);

        spinnernomos = (Spinner) rootView.findViewById(R.id.spinner2);
        noUniqueNomos.add(0,"");
// Create an ArrayAdapter using the string array and a default spinner layout
        adapter = new ArrayAdapter<>(getActivity(), layout.spinner_item, noUniqueNomos);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
// Apply the adapter to the spinner
        spinnernomos.setAdapter(adapter);
region.add(0,"");
      spinnerregion = (Spinner) rootView.findViewById(R.id.spinner3);
// Create an ArrayAdapter using the string array and a default spinner layout
        adapter2 = new ArrayAdapter<>(getActivity(),
                layout.spinner_item, region);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
// Apply the adapter to the spinner
     spinnerregion.setAdapter(adapter2);
date.add(0,"");
  spinnerdate = (Spinner) rootView.findViewById(R.id.spinner4);
// Create an ArrayAdapter using the string array and a default spinner layout
        adapter3 = new ArrayAdapter<>(getActivity(),
                layout.spinner_item, date);
// Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
// Apply the adapter to the spinner
        spinnerdate.setAdapter(adapter3);

        spinnernomos.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                    check=check+1;
                if(check>1) {
                    String selectedValue = arg0.getSelectedItem().toString();

                    adapter2 = new ArrayAdapter<String>(getActivity(), layout.spinner_item, returnRegion(getindex(selectedValue, "nomos")));
                    spinnerregion.setAdapter(adapter2);
                    spinnerregion.setEnabled(true);
                    adapter2.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerregion.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                    check2=check2+1;
                if(check2>1) {
                    String selectedValue = arg0.getSelectedItem().toString();

                    adapter3 = new ArrayAdapter<String>(getActivity(), layout.spinner_item, returnDate(getindex(selectedValue, "region")));
                    spinnerdate.setAdapter(adapter3);
                    spinnerdate.setEnabled(true);
                    adapter3.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        btndelete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                deletemeasurements();

            }
        });
        spinnerdate.setEnabled(false);
        spinnerregion.setEnabled(false);
        getmeasurements();
        return rootView;
    }


    public ArrayList<Integer> getindex(String str,String type){
        ArrayList<Integer> list = new ArrayList<>();
        list.clear();
        int j=0;
        Log.e("Str:",str);
        if(type.equals("nomos")){

            for(int i=0;i<nomos.size();i++){
                if(nomos.get(i).equals(str))
                   list.add(i);
            }
        }
        if(type.equals("region")){

            for(int i=0;i<region.size();i++){
                if(region.get(i).equals(str)) {
                    list.add(i);

                }
            }
        }
        return list;
    }

    public ArrayList<String> returnDate(ArrayList<Integer> index){

        newDate.clear();
        for(int i=0;i<index.size();i++) {


                    newDate.add(date.get(index.get(i)));



        }
        Set<String> hs = new HashSet<>();
        hs.addAll(newDate);
        newDate.clear();
        newDate.addAll(hs);
        return newDate;
    }

    public ArrayList<String> returnRegion(ArrayList<Integer> index){

        newRegion.clear();
        for(int i=0;i<index.size();i++) {


                    newRegion.add(region.get(index.get(i)));



        }
        Set<String> hs = new HashSet<>();
        hs.addAll(newRegion);
        newRegion.clear();
        newRegion.addAll(hs);
        return newRegion;
    }
    public void getmeasurements(){

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+ Main.getip()+":8080/useraccount2/Safetyreturn/dosearch",new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {
                    noUniqueNomos.clear();
                    date.clear();
                    nomos.clear();
                    region.clear();
                    id.clear();
                    id_perioxis.clear();
                    JSONObject jb = new JSONObject(response);
                    JSONArray st = jb.getJSONArray("id");
                    JSONArray st1 = jb.getJSONArray("date");
                    JSONArray st2 = jb.getJSONArray("region");
                    JSONArray st3 = jb.getJSONArray("nomos");
                    JSONArray st4 = jb.getJSONArray("id_perioxis");
                    for(int i=0;i<st.length();i++)
                    {
                        id.add(i,st.getString(i));

                        // loop and add it to array or arraylist
                    }
                    for(int i=0;i<st1.length();i++)
                    {
                        date.add(i,st1.getString(i));

                        // loop and add it to array or arraylist
                    }
                    for(int i=0;i<st2.length();i++)
                    {
                        region.add(i,st2.getString(i));

                        // loop and add it to array or arraylist
                    }
                    for(int i=0;i<st3.length();i++)
                    {
                        nomos.add(i,st3.getString(i));

                        // loop and add it to array or arraylist
                    }
                    for(int i=0;i<st4.length();i++)
                    {
                        id_perioxis.add(i,st4.getString(i));

                        // loop and add it to array or arraylist
                    }
                    Set<String> hs = new HashSet<>();
                    hs.addAll(nomos);

                    noUniqueNomos.addAll(hs);
                    adapter = new ArrayAdapter<>(getActivity(), layout.spinner_item, noUniqueNomos);
// Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
// Apply the adapter to the spinner
                    spinnernomos.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                    adapter3.notifyDataSetChanged();
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

    public void deletemeasurements(){
        RequestParams params = new RequestParams();
        params.put("city",spinnernomos.getSelectedItem().toString());
        params.put("region",spinnerregion.getSelectedItem().toString());
        params.put("date",spinnerdate.getSelectedItem().toString());
        invokeWS(params);


    }

    public void invokeWS(RequestParams params){


        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+Main.getip()+":8080/useraccount2/DeleteSafetyEvaluation/dodelete",params ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        getmeasurements();
                        Toast.makeText(getActivity().getApplicationContext(), "You are successfully deleted one measurement!", Toast.LENGTH_LONG).show();



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
}
