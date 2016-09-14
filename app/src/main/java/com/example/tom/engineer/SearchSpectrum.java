package com.example.tom.engineer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Set;

public class SearchSpectrum extends Fragment{

    Spinner spinnerdimos;
    Spinner spinnernomos;
    Spinner spinnerperioxi;
    Spinner spinnerdate;
    Spinner spinnertime;
    Spinner spinnerperigrafi1;
    Spinner spinnerperigrafi2;
    ArrayAdapter<String> adapterdimos;
    ArrayAdapter<String> adapternomos;
    ArrayAdapter<String> adaptername_region;
    ArrayAdapter<String> adapterdate;
    ArrayAdapter<String> adaptertime;
    ArrayAdapter<String> adapterperigrafi1;
    ArrayAdapter<String> adapterperigrafi2;
    ArrayList<String> dimos;
    ArrayList<String> nomos;
    ArrayList<String> name_region;
    ArrayList<String> date;
    ArrayList<String> time;
    ArrayList<String> perigrafi1;
    ArrayList<String> perigrafi2;
    ArrayList<String> NoUniqueDimos;
    int check1=0;
    int check2=0;
    int check3=0;
    int check4=0;
    int check5=0;
    int check6=0;
    EditText fmin;
    EditText fmax;
    Button searchspectrum;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.spectrumsearch, container, false);
        dimos = new ArrayList<>();
        nomos = new ArrayList<>();
        name_region= new ArrayList<>();
        date= new ArrayList<>();
        time = new ArrayList<>();
        perigrafi1= new ArrayList<>();
        perigrafi2 = new ArrayList<>();
        dimos.add(0,"");
        name_region.add(0,"");
        nomos.add(0,"");
        date.add(0,"");
        time.add(0,"");
        perigrafi2.add(0,"");
        perigrafi1.add(0,"");
        spinnerdimos = (Spinner) rootView.findViewById(R.id.spinner11);
        adapterdimos = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, dimos);
        adapterdimos.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerdimos.setAdapter(adapterdimos);
        spinnernomos = (Spinner) rootView.findViewById(R.id.spinner12);
        adapternomos = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, nomos);
        adapternomos.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnernomos.setAdapter(adapternomos);
        spinnerperioxi = (Spinner) rootView.findViewById(R.id.spinner13);
        adaptername_region = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, name_region);
        adaptername_region.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerperioxi.setAdapter(adaptername_region);
        spinnerdate= (Spinner) rootView.findViewById(R.id.spinner14);
        adapterdate = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, date);
        adapterdate.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerdate.setAdapter(adapterdate);
        spinnertime = (Spinner) rootView.findViewById(R.id.spinner15);
        adaptertime = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, time);
        adaptertime.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnertime.setAdapter(adaptertime);
        spinnerperigrafi1 = (Spinner) rootView.findViewById(R.id.spinner16);
        adapterperigrafi1 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, perigrafi1);
        adapterperigrafi1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerperigrafi1.setAdapter(adapterperigrafi1);
        spinnerperigrafi2= (Spinner) rootView.findViewById(R.id.spinner17);
        adapterperigrafi2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, perigrafi2);
        adapterperigrafi2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerperigrafi2.setAdapter(adapterperigrafi2);

        spinnerdimos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                check1 = check1 + 1;
                if (check1 > 1) {
                    String selectedValue = arg0.getSelectedItem().toString();

                    adapternomos = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, returnIndex(getindex(selectedValue, "dimos"), "dimos"));
                    spinnernomos.setAdapter(adapternomos);
                    adapternomos.notifyDataSetChanged();
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

                check2 = check2 + 1;
                if (check2 > 1) {
                    String selectedValue = arg0.getSelectedItem().toString();

                    adaptername_region = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, returnIndex(getindex(selectedValue, "nomos"), "nomos"));
                    spinnerperioxi.setAdapter(adaptername_region);
                    adaptername_region.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerperioxi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                check3 = check3 + 1;
                if (check3 > 1) {
                    String selectedValue = arg0.getSelectedItem().toString();

                    adapterdate = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, returnIndex(getindex(selectedValue, "name_region"), "name_region"));
                    spinnerdate.setAdapter(adapterdate);
                    adapterdate.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                check4 = check4 + 1;
                if (check4 > 1) {
                    String selectedValue = arg0.getSelectedItem().toString();

                    adaptertime = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, returnIndex(getindex(selectedValue, "date"),"date"));
                    spinnertime.setAdapter(adaptertime);
                    adaptertime.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnertime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                check5 = check5 + 1;
                if (check5 > 1) {
                    String selectedValue = arg0.getSelectedItem().toString();

                    adapterperigrafi1 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, returnIndex(getindex(selectedValue, "time"),"time"));
                    spinnerperigrafi1.setAdapter(adapterperigrafi1);
                    adapterperigrafi1.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerperigrafi1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                check6 = check6 + 1;
                if (check6 > 1) {
                    String selectedValue = arg0.getSelectedItem().toString();

                    adapterperigrafi2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, returnIndex(getindex(selectedValue, "perigrafi1"),"perigrafi1"));
                    spinnerperigrafi2.setAdapter(adapterperigrafi2);
                    adapterperigrafi2.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchspectrum = (Button) rootView.findViewById(R.id.searchspectrum);
        searchspectrum.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                    search();

            }
        });

        fmin = (EditText) rootView.findViewById(R.id.fmin);
        fmax = (EditText) rootView.findViewById(R.id.fmax);
        getsearchspectrum();
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
        if(type.equals("name_region")){

            for(int i=0;i<name_region.size();i++){
                if(name_region.get(i).equals(str)) {
                    list.add(i);
                }
            }
        }
        if(type.equals("date")){

            for(int i=0;i<date.size();i++){
                if(date.get(i).equals(str)) {
                    list.add(i);
                }
            }
        }
        if(type.equals("time")){

            for(int i=0;i<time.size();i++){
                if(time.get(i).equals(str)) {
                    list.add(i);
                }
            }
        }
        if(type.equals("perigrafi1")){

            for(int i=0;i<perigrafi1.size();i++){
                if(perigrafi1.get(i).equals(str)) {
                    list.add(i);
                }
            }
        }
        if(type.equals("perigrafi2")){

            for(int i=0;i<perigrafi2.size();i++){
                if(perigrafi2.get(i).equals(str)) {
                    list.add(i);
                }
            }
        }
        return list;
    }

    public ArrayList<String> returnIndex(ArrayList<Integer> index,String type){
        ArrayList<String> list = new ArrayList<>();
        list.clear();
        ArrayList<String> newNomos = new ArrayList<>();
        ArrayList<String> newName_region = new ArrayList<>();
        ArrayList<String> newDate = new ArrayList<>();
        ArrayList<String> newTime = new ArrayList<>();
        ArrayList<String> newPerigrafi1 = new ArrayList<>();
        ArrayList<String> newPerigrafi2 = new ArrayList<>();
        ArrayList<String> newDimos = new ArrayList<>();
        if(type.equals("dimos")){

            for(int i=0;i<index.size();i++) {
                newNomos.add(nomos.get(index.get(i)));
            }
            Set<String> hs = new HashSet<>();
            hs.addAll(newNomos);
            list.addAll(hs);
        }
        if(type.equals("nomos")){
            for(int i=0;i<index.size();i++){
                if(spinnerdimos.getSelectedItem().toString().equals(dimos.get(index.get(i)))){
                        newName_region.add(name_region.get(index.get(i)));
                }
            }
            Set<String> hs = new HashSet<>();
            hs.addAll(newName_region);
            list.addAll(hs);
        }if(type.equals("name_region")){
            for(int i=0;i<index.size();i++) {
                if(spinnerdimos.getSelectedItem().toString().equals(dimos.get(index.get(i)))) {
                    if (spinnernomos.getSelectedItem().toString().equals(nomos.get(index.get(i)))) {
                        newDate.add(date.get(index.get(i)));
                    }
                }
            }
            Set<String> hs = new HashSet<>();
            hs.addAll(newDate);
            list.addAll(hs);
        }if(type.equals("date")){
            for(int i=0;i<index.size();i++) {
                if(spinnerdimos.getSelectedItem().toString().equals(dimos.get(index.get(i)))) {
                    if (spinnernomos.getSelectedItem().toString().equals(nomos.get(index.get(i)))) {
                        if (spinnerperioxi.getSelectedItem().toString().equals(name_region.get(index.get(i)))) {
                            newTime.add(time.get(index.get(i)));
                         }
                    }
                }
            }
            Set<String> hs = new HashSet<>();
            hs.addAll(newTime);
            list.addAll(hs);
        }if(type.equals("time")){
            for(int i=0;i<index.size();i++) {
                if(spinnerdimos.getSelectedItem().toString().equals(dimos.get(index.get(i)))) {
                    if (spinnernomos.getSelectedItem().toString().equals(nomos.get(index.get(i)))) {
                        if (spinnerperioxi.getSelectedItem().toString().equals(name_region.get(index.get(i)))) {
                            if(spinnerdate.getSelectedItem().toString().equals(date.get(index.get(i)))) {
                                newPerigrafi1.add(perigrafi1.get(index.get(i)));
                            }
                        }
                    }
                }
            }
            Set<String> hs = new HashSet<>();
            hs.addAll(newPerigrafi1);
            list.addAll(hs);
        }if(type.equals("perigrafi1")){
            for(int i=0;i<index.size();i++) {
                if(spinnerdimos.getSelectedItem().toString().equals(dimos.get(index.get(i)))) {
                    if (spinnernomos.getSelectedItem().toString().equals(nomos.get(index.get(i)))) {
                        if (spinnerperioxi.getSelectedItem().toString().equals(name_region.get(index.get(i)))) {
                            if(spinnerdate.getSelectedItem().toString().equals(date.get(index.get(i)))) {
                                if(spinnertime.getSelectedItem().toString().equals(time.get(index.get(i)))) {
                                    newPerigrafi2.add(perigrafi2.get(index.get(i)));
                                }
                            }
                        }
                    }
                }
            }
            Set<String> hs = new HashSet<>();
            hs.addAll(newPerigrafi2);
            list.addAll(hs);
        }if(type.equals("perigrafi2")){
            for(int i=0;i<index.size();i++) {
                newDimos.add(dimos.get(index.get(i)));
            }
            Set<String> hs = new HashSet<>();
            hs.addAll(newDimos);
            list.addAll(hs);
        }
        return list;
    }


    public void getsearchspectrum(){

        NoUniqueDimos = new ArrayList<>();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + Main.getip() + ":8080/useraccount2/returnsearchspectrum/dosearch", new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                try {
                    NoUniqueDimos.clear();
                    dimos.clear();
                    nomos.clear();
                    name_region.clear();
                    date.clear();
                    time.clear();
                    perigrafi1.clear();
                    perigrafi2.clear();
                    JSONObject jb = new JSONObject(response);
                    JSONArray dimosj = jb.getJSONArray("dimos");
                    JSONArray name_regionj = jb.getJSONArray("name_region");
                    JSONArray nomosj = jb.getJSONArray("nomos");
                    JSONArray datej = jb.getJSONArray("date");
                    JSONArray timej = jb.getJSONArray("time");
                    JSONArray perigrafi1j = jb.getJSONArray("perigrafi1");
                    JSONArray perigrafi2j = jb.getJSONArray("perigrafi2");

                    for (int i = 0; i < dimosj.length(); i++) {
                        dimos.add(i, dimosj.getString(i));
                        // loop and add it to array or arraylist
                    }
                    for (int i = 0; i < nomosj.length(); i++) {
                        nomos.add(i, nomosj.getString(i));
                        // loop and add it to array or arraylist
                    }
                    for (int i = 0; i < name_regionj.length(); i++) {
                        name_region.add(i, name_regionj.getString(i));
                        // loop and add it to array or arraylist
                    }
                    for (int i = 0; i < datej.length(); i++) {
                        date.add(i, datej.getString(i));
                        // loop and add it to array or arraylist
                    }
                    for (int i = 0; i < timej.length(); i++) {
                        time.add(i, timej.getString(i));
                        // loop and add it to array or arraylist
                    }
                    for (int i = 0; i < perigrafi1j.length(); i++) {
                        perigrafi1.add(i, perigrafi1j.getString(i));
                        // loop and add it to array or arraylist
                    }

                    for (int i = 0; i < perigrafi2j.length(); i++) {
                        perigrafi2.add(i, perigrafi2j.getString(i));
                        // loop and add it to array or arraylist
                    }
                    Set<String> hs = new HashSet<>();
                    hs.addAll(dimos);
                    NoUniqueDimos.addAll(hs);
                    adapterdimos = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, NoUniqueDimos);
                    adapterdimos.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerdimos.setAdapter(adapterdimos);
                    adapterdimos.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,String content) {
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

    public void search(){
        RequestParams params = new RequestParams();
        params.put("dimos",spinnerdimos.getSelectedItem().toString());
        params.put("nomos",spinnernomos.getSelectedItem().toString());
        params.put("name_region",spinnerperioxi.getSelectedItem().toString());
        params.put("date",spinnerdate.getSelectedItem().toString());
        params.put("time",spinnertime.getSelectedItem().toString());
        params.put("perigrafi1",spinnerperigrafi1.getSelectedItem().toString());
        params.put("perigrafi2", spinnerperigrafi2.getSelectedItem().toString());
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + Main.getip() + ":8080/useraccount2/returnsearchspectruminfo/dosearch", params, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                try {
                    String dataset_version;
                    String dataset_type;
                    String store_mode;
                    String datei;
                    String timei;
                    double Fmin;
                    double Fmax;
                    double rbw;
                    double measurements_range;
                    String unit;
                    String average_type;
                    double average_time;
                    int no_of_args;
                    String threshold;
                    String y_scale_ref;
                    String y_scale_range;
                    String axis;
                    String service_table_name;
                    String antenna_name;
                    double frenquency_resolution;
                    double x_1;
                    double y_1;
                    double x_2;
                    double y_2;
                    String frenquency;
                    String value;
                    String result_type;
                    String perigrafi1i;
                    String perigrafi2i;

                    JSONObject rs2 = new JSONObject(response);
                    dataset_version = rs2.getString("dataset_version");
                    dataset_type = rs2.getString("dataset_type");
                    store_mode = rs2.getString("store_mode");
                    datei = rs2.getString("date");
                    timei = rs2.getString("time");
                    Fmin = rs2.getDouble("Fmin");
                    Fmax = rs2.getDouble("Fmax");
                    rbw = rs2.getDouble("rbw");
                    measurements_range = rs2.getDouble("measurements_range");
                    unit = rs2.getString("unit");
                    average_type = rs2.getString("average_type");
                    average_time = rs2.getDouble("average_time");
                    no_of_args = rs2.getInt("no_of_args");
                    threshold = rs2.getString("threshold");
                    y_scale_ref = rs2.getString("y_scale_ref");
                    y_scale_range = rs2.getString("y_scale_range");
                    axis = rs2.getString("axis");
                    service_table_name = rs2.getString("service_table_name");
                    antenna_name = rs2.getString("antenna_name");
                    frenquency_resolution = rs2.getDouble("frenquency_resolution");
                    x_1 = rs2.getDouble("x_1");
                    y_1 = rs2.getDouble("y_1");
                    x_2 = rs2.getDouble("x_2");
                    y_2 = rs2.getDouble("y_2");
                    frenquency = rs2.getString("frenquency");
                    value = rs2.getString("value");
                    result_type = rs2.getString("result_type");
                    perigrafi1i = rs2.getString("perigrafi1");
                    perigrafi2i = rs2.getString("perigrafi2");

                    Intent i = new Intent(getActivity().getApplication(), SpectrumInfo.class);
                    i.putExtra("dataset_version", dataset_version);
                    i.putExtra("dataset_type", dataset_type);
                    i.putExtra("store_mode", store_mode);
                    i.putExtra("date", datei);
                    i.putExtra("time", timei);
                    i.putExtra("Fmin", Fmin);
                    i.putExtra("Fmax", Fmax);
                    i.putExtra("rbw", rbw);
                    i.putExtra("measurements_range", measurements_range);
                    i.putExtra("unit", unit);
                    i.putExtra("average_type", average_type);
                    i.putExtra("average_time", average_time);
                    i.putExtra("no_of_args", no_of_args);
                    i.putExtra("threshold", threshold);
                    i.putExtra("y_scale_ref", y_scale_ref);
                    i.putExtra("y_scale_range", y_scale_range);
                    i.putExtra("axis", axis);
                    i.putExtra("service_table_name", service_table_name);
                    i.putExtra("antenna_name", antenna_name);
                    i.putExtra("frenquency_resolution", frenquency_resolution);
                    i.putExtra("x_1", x_1);
                    i.putExtra("y_1", y_1);
                    i.putExtra("x_2", x_2);
                    i.putExtra("y_2", y_2);
                    i.putExtra("frenquency", frenquency);
                    i.putExtra("value", value);
                    i.putExtra("result_type", result_type);
                    i.putExtra("perigrafi1", perigrafi1i);
                    i.putExtra("perigrafi2", perigrafi2i);
                    i.putExtra("fmincheck", fmin.getText().toString());
                    i.putExtra("fmaxcheck", fmax.getText().toString());

                    double fmind;
                    double fmaxd;
                    if (fmin.getText().toString().equals("") && !fmax.getText().toString().equals("")) {
                        fmind = 0;
                        fmaxd = Double.parseDouble(fmax.getText().toString());
                    } else if (fmax.getText().toString().equals("") && !fmin.getText().toString().equals("")) {
                        fmaxd = 0;
                        fmind = Double.parseDouble(fmin.getText().toString());
                    } else if (fmin.getText().toString().equals("") && fmax.getText().toString().equals("")) {
                        fmaxd = 0;
                        fmind = 0;
                    } else {
                        fmind = Double.parseDouble(fmin.getText().toString());
                        fmaxd = Double.parseDouble(fmax.getText().toString());
                    }
                    if(fmind==0 && fmaxd==0){
                        startActivity(i);
                    }else if (fmind == 0 ) {
                        if (fmaxd > Fmax / 1000000 || fmaxd<Fmin/1000000) {
                            Toast.makeText(getActivity(), "Input out of bounds. Wrong input maximum Fmax=" + Fmax / 1000000, Toast.LENGTH_LONG).show();
                        } else {
                            startActivity(i);
                        }
                    } else if (fmaxd == 0) {
                        if (fmind < Fmin / 1000000||fmind>Fmax/1000000) {
                           Toast.makeText(getActivity(), "Input out of bounds. Wrong input minimum Fmin=" + Fmin / 1000000, Toast.LENGTH_LONG).show();
                        } else {
                        startActivity(i);
                        }
                    }else{
                        if (fmind > fmaxd) {
                           Toast.makeText(getActivity(), "The fmin input is bigger than fmax input", Toast.LENGTH_LONG).show();
                        }else if (fmind < Fmin / 1000000 || fmaxd > Fmax / 1000000) {
                            Toast.makeText(getActivity(), "Input out of bounds. Wrong input  minimum Fmin=" + Fmin / 1000000 + "  maximum Fmax=" + Fmax / 1000000, Toast.LENGTH_LONG).show();
                        }else{
                           startActivity(i);
                        }
                    }

                }catch (JSONException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,String content) {
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
