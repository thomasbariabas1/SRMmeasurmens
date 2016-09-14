package com.example.tom.engineer;


import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

public class AddRegionMeasurements extends Fragment {

    TextView odigies;
    TextView dimos;
    TextView poli;
    TextView perioxi;
    Spinner spinnerdimos;
    Spinner spinnerpoli;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    EditText editdimos;
    EditText editpoli;
    EditText editperioxi;
    CheckBox nomosBox;
    CheckBox dimosBox;
    ArrayList<String> dimosarray;
    ArrayList<String> poliarray;
    View rootView;
    Button insert;
    LocationManager locManager;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.addregionmeasurements, container, false);

        dimosarray = new ArrayList<>();
        poliarray = new ArrayList<>();
        insert = (Button) rootView.findViewById(R.id.insertregion);
        odigies =(TextView) rootView.findViewById(R.id.textView13);
        dimos =(TextView) rootView.findViewById(R.id.textView14);
        poli =(TextView) rootView.findViewById(R.id.textView15);
        perioxi =(TextView) rootView.findViewById(R.id.textView16);
        spinnerdimos = (Spinner) rootView.findViewById(R.id.spinner9);
        spinnerpoli = (Spinner) rootView.findViewById(R.id.spinner10);
        editdimos = (EditText) rootView.findViewById(R.id.editText4);
        editpoli = (EditText) rootView.findViewById(R.id.editText5);
        editperioxi = (EditText) rootView.findViewById(R.id.editText6);
        dimosBox = (CheckBox) rootView.findViewById(R.id.checkBox);
        nomosBox = (CheckBox) rootView.findViewById(R.id.checkBox2);

        dimosBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                    if(isChecked) {
                                                        editdimos.setVisibility(View.VISIBLE);
                                                        dimos.setVisibility(View.VISIBLE);
                                                        spinnerdimos.setVisibility(View.INVISIBLE);
                                                    }else{

                                                        editdimos.setVisibility(View.INVISIBLE);
                                                        dimos.setVisibility(View.INVISIBLE);
                                                        spinnerdimos.setVisibility(View.VISIBLE);
                                                    }
                                               }
                                           }
        );
        nomosBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                    if(isChecked) {
                                                        editpoli.setVisibility(View.VISIBLE);
                                                        poli.setVisibility(View.VISIBLE);
                                                        spinnerpoli.setVisibility(View.INVISIBLE);
                                                    }else{

                                                        editpoli.setVisibility(View.INVISIBLE);
                                                        poli.setVisibility(View.INVISIBLE);
                                                        spinnerpoli.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            }
        );
        getregion();
        odigies.setText("Choose from dropdown menu.If there isn't what you need check the checkbox.");
        dimos.setText("Δημός");
        poli.setText("Πόλη");
        perioxi.setText("Περιοχή");
        editdimos.setVisibility(View.INVISIBLE);
        editpoli.setVisibility(View.INVISIBLE);
        dimos.setVisibility(View.INVISIBLE);
        poli.setVisibility(View.INVISIBLE);

        insert.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                insertregion();

            }
        });
        return rootView;

    }

    public void getregion(){

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+ Main.getip()+":8080/useraccount2/returnregion/dosearch",new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {

                    JSONObject jb = new JSONObject(response);
                    JSONArray st = jb.getJSONArray("dimos");
                    JSONArray st1 = jb.getJSONArray("nomos");

                    for (int i = 0; i < st.length(); i++) {
                        dimosarray.add(i, st.getString(i));

                        // loop and add it to array or arraylist
                    }
                    for (int i = 0; i < st1.length(); i++) {
                        poliarray.add(i, st1.getString(i));

                        // loop and add it to array or arraylist
                    }

                    Set<String> hs = new HashSet<>();
                    hs.addAll(dimosarray);
                    ArrayList<String> unique = new ArrayList<>();

                    unique.addAll(hs);
                    hs.clear();


                    adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, unique);
// Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
// Apply the adapter to the spinner
                    spinnerdimos.setAdapter(adapter);
                    editperioxi.setText("");
                    editpoli.setText("");
                    editdimos.setText("");
spinnerdimos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1,
                               int arg2, long arg3) {


        String selectedValue = arg0.getSelectedItem().toString();

        adapter2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, returnRegion(getindex(selectedValue)));
        spinnerpoli.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});

                    adapter.notifyDataSetChanged();

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

    public ArrayList<Integer> getindex(String str){
        ArrayList<Integer> list = new ArrayList<>();
        list.clear();



            for(int i=0;i<dimosarray.size();i++){
                if(dimosarray.get(i).equals(str))
                    list.add(i);
            }

        return list;
    }
    public ArrayList<String> returnRegion(ArrayList<Integer> index){

        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<index.size();i++) {


            list.add(poliarray.get(index.get(i)));



        }
        Set<String> hs = new HashSet<>();
        hs.addAll(list);
        list.clear();
        list.addAll(hs);
        return list;
    }


    public void insertregion(){
if(dimosBox.isChecked()||nomosBox.isChecked()){
        if(dimosBox.isChecked()&&!nomosBox.isChecked()) {
            RequestParams params = new RequestParams();

            params.put("dimos", editdimos.getText().toString());
            params.put("nomos", spinnerpoli.getSelectedItem().toString());
            params.put("name_region", editperioxi.getText().toString());
           /* //locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, (android.location.LocationListener) locationListener);
            Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            double fromLat=0;
            double fromLong=0;
            if(location != null)
            {
                fromLat = location.getLatitude();
                fromLong = location.getLongitude();
            }
            params.put("x1",fromLat);
            params.put("x1",fromLat);*/
            checkperioxi(params);

        }
        if(nomosBox.isChecked()&&!dimosBox.isChecked()){
            RequestParams params = new RequestParams();

            params.put("dimos", spinnerdimos.getSelectedItem().toString());
            params.put("nomos", editpoli.getText().toString());
            params.put("name_region", editperioxi.getText().toString());
            checkperioxi(params);



        }
     if(nomosBox.isChecked()&&dimosBox.isChecked()){
            RequestParams params = new RequestParams();

            params.put("dimos", editdimos.getText().toString());
            params.put("nomos", editpoli.getText().toString());
            params.put("name_region", editperioxi.getText().toString());
            checkperioxi(params);


        }
}else {
            RequestParams params = new RequestParams();

            params.put("dimos", spinnerdimos.getSelectedItem().toString());
            params.put("nomos", spinnerpoli.getSelectedItem().toString());
            params.put("name_region", editperioxi.getText().toString());
            checkperioxi(params);
        }


    }
    //Se periptwsi pou balete stin DB stin perioxi suntetagmenes
    /*
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            Toast.makeText(rootView.getContext(), "GPS is Disabled.Please enable GPS", Toast.LENGTH_SHORT ).show();
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {
            Toast.makeText( rootView.getContext(), "GPS is Enabled", Toast.LENGTH_SHORT).show();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };*/

    public void invokeWS(final RequestParams params){


        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + Main.getip() + ":8080/useraccount2/addregion/dologin", params, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {

                        Toast.makeText(getActivity().getApplicationContext(), "You are successfully inserted one region", Toast.LENGTH_LONG).show();
                        getregion();

                    }

                    // Else display error message
                    else {

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

    public void checkperioxi(final RequestParams params){


        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + Main.getip() + ":8080/useraccount2/returnperioxi/dosearch", params, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {
                    // JSON Object


                    JSONObject obj = new JSONObject(response);

                    // When the JSON response has status boolean value assigned with true
                    if (obj.getBoolean("status")) {
                        invokeWS(params);
                    }

                    // Else display error message
                    else {
                        Toast.makeText(getActivity().getApplicationContext(), "The region you inserted is in DataBase", Toast.LENGTH_LONG).show();
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

}

