package com.example.tom.engineer;

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
import android.widget.TextView;
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
import java.util.Set;


public class DeleteRegionMeasurements extends Fragment {
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    ArrayAdapter<String> adapter3;
    ArrayList<String> dimos;
    ArrayList<String> region;
    ArrayList<String> nomos;
    ArrayList<String> noUniqueDimos;
    private Spinner spinnernomos;
    private Spinner spinnerregion;
    private Spinner spinnerdimos;
    ArrayList<String> newNomos = new ArrayList<>();
    ArrayList<String> newRegion = new ArrayList<>();
    int check=0;
    int check2=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.deleteregionmeasurements, container, false);

        nomos = new ArrayList<>();
        region = new ArrayList<>();
        dimos = new ArrayList<>();

        noUniqueDimos=new ArrayList<>();
        Button btndelete = (Button) rootView.findViewById(R.id.deleteregion);
        TextView warning = (TextView) rootView.findViewById(R.id.dltregion);
        spinnernomos = (Spinner) rootView.findViewById(R.id.dltregionnomos);
        spinnerregion = (Spinner) rootView.findViewById(R.id.dltregionregion);
        spinnerdimos = (Spinner) rootView.findViewById(R.id.dltregiondimos);
        noUniqueDimos.add(0,"");
        adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, noUniqueDimos);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        warning.setText("Be sure that region does not contain measurements");
        spinnerdimos.setAdapter(adapter);
        nomos.add(0, "");
        adapter2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, nomos);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnernomos.setAdapter(adapter2);
        region.add(0, "");
        adapter3 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, region);
// Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerregion.setAdapter(adapter3);
        spinnerdimos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                check=check+1;
                if(check>1) {
                    String selectedValue = arg0.getSelectedItem().toString();

                    adapter2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, returnNomos(getindex(selectedValue, "dimos")));
                    spinnernomos.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnernomos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                check2=check2+1;
                if(check2>1) {
                    String selectedValue = arg0.getSelectedItem().toString();

                    adapter3 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, returnRegion(getindex(selectedValue, "nomos")));
                    spinnerregion.setAdapter(adapter3);
                    adapter3.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        btndelete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                deleteregion();
            }
        });

        getregion();
        return rootView;
    }

    public ArrayList<Integer> getindex(String str,String type){
        ArrayList<Integer> list = new ArrayList<>();
        list.clear();

        Log.e("Str:", str);
        if(type.equals("dimos")){

            for(int i=0;i<dimos.size();i++){
                if(dimos.get(i).equals(str))
                    list.add(i);
            }
        }
        if(type.equals("nomos")){

            for(int i=0;i<nomos.size();i++){
                if(nomos.get(i).equals(str)) {
                    list.add(i);

                }
            }
        }
        return list;
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

    public ArrayList<String> returnNomos(ArrayList<Integer> index){

        newNomos.clear();
        for(int i=0;i<index.size();i++) {


            newNomos.add(nomos.get(index.get(i)));



        }
        Set<String> hs = new HashSet<>();
        hs.addAll(newNomos);
        newNomos.clear();
        newNomos.addAll(hs);
        return newNomos;
    }
    public void getregion(){

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + Main.getip() + ":8080/useraccount2/returnregion/dosearch", new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {
                    noUniqueDimos.clear();
                    dimos.clear();
                    nomos.clear();
                    region.clear();
                    JSONObject jb = new JSONObject(response);

                    JSONArray st1 = jb.getJSONArray("dimos");
                    JSONArray st2 = jb.getJSONArray("name_region");
                    JSONArray st3 = jb.getJSONArray("nomos");


                    for (int i = 0; i < st1.length(); i++) {
                        dimos.add(i, st1.getString(i));

                        // loop and add it to array or arraylist
                    }
                    for (int i = 0; i < st2.length(); i++) {
                        region.add(i, st2.getString(i));

                        // loop and add it to array or arraylist
                    }
                    for (int i = 0; i < st3.length(); i++) {
                        nomos.add(i, st3.getString(i));

                        // loop and add it to array or arraylist
                    }

                    Set<String> hs = new HashSet<>();
                    hs.addAll(dimos);

                    noUniqueDimos.addAll(hs);
                    adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, noUniqueDimos);
// Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
// Apply the adapter to the spinner
                    spinnerdimos.setAdapter(adapter);
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
                if (statusCode == 404) {
                    Toast.makeText(getActivity().getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public void deleteregion(){
        RequestParams params = new RequestParams();
        params.put("dimos",spinnerdimos.getSelectedItem().toString());
        params.put("nomos", spinnernomos.getSelectedItem().toString());
        params.put("name_region", spinnerregion.getSelectedItem().toString());
        invokeWS(params);


    }

    public void invokeWS(RequestParams params){


        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+Main.getip()+":8080/useraccount2/deleteregion/dodelete",params ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        getregion();
                        Toast.makeText(getActivity().getApplicationContext(), "You are successfully deleted one region: "+spinnerdimos.getSelectedItem().toString()+" "+spinnernomos.getSelectedItem().toString()+" "+spinnerregion.getSelectedItem().toString()+" ", Toast.LENGTH_LONG).show();



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
