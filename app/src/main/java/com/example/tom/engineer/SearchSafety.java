package com.example.tom.engineer;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import com.example.tom.srmmeasuremens.adapter.TabsPagerAdapterEngineerSearchSpectrumInfo;
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

public class SearchSafety extends Fragment{

    Spinner spinnerdimos;
    Spinner spinnernomos;
    Spinner spinnerperioxi;
    Spinner spinnerdate;
    Spinner spinnertime;
    Spinner spinnerperigrafi1;
    Spinner spinnerperigrafi2;
    Spinner spinnerservicename;
    ArrayAdapter<String> adapterdimos;
    ArrayAdapter<String> adapternomos;
    ArrayAdapter<String> adaptername_region;
    ArrayAdapter<String> adapterdate;
    ArrayAdapter<String> adaptertime;
    ArrayAdapter<String> adapterperigrafi1;
    ArrayAdapter<String> adapterperigrafi2;
    ArrayAdapter<String> adapterservicename;
    ArrayList<String> dimos;
    ArrayList<String> nomos;
    ArrayList<String> name_region;
    ArrayList<String> date;
    ArrayList<String> time;
    ArrayList<String> perigrafi1;
    ArrayList<String> perigrafi2;
    ArrayList<String> NoUniqueDimos;
    ArrayList<String> servicename;
    ArrayList<String> lower_frenquency;
    ArrayList<String> upper_frenquency;
    int check1=0;
    int check2=0;
    int check3=0;
    int check4=0;
    int check5=0;
    int check6=0;
    int check7=0;

    Button searchsafety;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.safetysearch, container, false);
        dimos = new ArrayList<>();
        nomos = new ArrayList<>();
        name_region= new ArrayList<>();
        date= new ArrayList<>();
        time = new ArrayList<>();
        perigrafi1= new ArrayList<>();
        perigrafi2 = new ArrayList<>();
        servicename = new ArrayList<>();
        lower_frenquency = new ArrayList<>();
        upper_frenquency = new ArrayList<>();
        dimos.add(0,"");
        name_region.add(0,"");
        nomos.add(0,"");
        date.add(0,"");
        time.add(0,"");
        perigrafi2.add(0,"");
        perigrafi1.add(0,"");
        servicename.add(0,"");
        spinnerdimos = (Spinner) rootView.findViewById(R.id.safetyspinner11);
        adapterdimos = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, dimos);
        adapterdimos.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerdimos.setAdapter(adapterdimos);
        spinnernomos = (Spinner) rootView.findViewById(R.id.safetyspinner12);
        adapternomos = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, nomos);
        adapternomos.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnernomos.setAdapter(adapternomos);
        spinnerperioxi = (Spinner) rootView.findViewById(R.id.safetyspinner13);
        adaptername_region = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, name_region);
        adaptername_region.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerperioxi.setAdapter(adaptername_region);
        spinnerdate= (Spinner) rootView.findViewById(R.id.safetyspinner14);
        adapterdate = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, date);
        adapterdate.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerdate.setAdapter(adapterdate);
        spinnertime = (Spinner) rootView.findViewById(R.id.safetyspinner15);
        adaptertime = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, time);
        adaptertime.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnertime.setAdapter(adaptertime);
        spinnerperigrafi1 = (Spinner) rootView.findViewById(R.id.safetyspinner16);
        adapterperigrafi1 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, perigrafi1);
        adapterperigrafi1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerperigrafi1.setAdapter(adapterperigrafi1);
        spinnerperigrafi2= (Spinner) rootView.findViewById(R.id.safetyspinner17);
        adapterperigrafi2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, perigrafi2);
        adapterperigrafi2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerperigrafi2.setAdapter(adapterperigrafi2);
        spinnerservicename= (Spinner) rootView.findViewById(R.id.safetyspinner19);
        adapterservicename = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, servicename);
        adapterservicename.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerservicename.setAdapter(adapterservicename);

        spinnerdimos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

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

                    adapterperigrafi2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, returnIndex(getindex(selectedValue, "perigrafi1"), "perigrafi1"));
                    spinnerperigrafi2.setAdapter(adapterperigrafi2);
                    adapterperigrafi2.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerperigrafi2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                check7 = check7 + 1;
                if (check7 > 1) {
                    String selectedValue = arg0.getSelectedItem().toString();

                    adapterservicename= new ArrayAdapter<>(getActivity(), R.layout.spinner_item, returnIndex(getindex(selectedValue, "perigrafi2"), "perigrafi2"));
                    spinnerservicename.setAdapter(adapterservicename);
                    adapterservicename.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchsafety = (Button) rootView.findViewById(R.id.searchsafety);
        searchsafety.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                search();


            }
        });


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
        ArrayList<String> newServicename = new ArrayList<>();

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
        }
        if(type.equals("name_region")){
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
        }
        if(type.equals("date")){
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
        }
        if(type.equals("time")){
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
        }
        if(type.equals("perigrafi1")){
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
        }
        if(type.equals("perigrafi2")){
            for(int i=0;i<index.size();i++) {
                if(spinnerdimos.getSelectedItem().toString().equals(dimos.get(index.get(i)))) {
                    if (spinnernomos.getSelectedItem().toString().equals(nomos.get(index.get(i)))) {
                        if (spinnerperioxi.getSelectedItem().toString().equals(name_region.get(index.get(i)))) {
                            if(spinnerdate.getSelectedItem().toString().equals(date.get(index.get(i)))) {
                                if(spinnertime.getSelectedItem().toString().equals(time.get(index.get(i)))) {
                                    if(spinnerperigrafi1.getSelectedItem().toString().equals(perigrafi1.get(index.get(i)))) {
                                        String servicenames[] = servicename.get(index.get(i)).split("\\r?\\n");
                                        String lfrenquency[]= lower_frenquency.get(index.get(i)).split("\\r?\\n");
                                        String ufrenquency[]= upper_frenquency.get(index.get(i)).split("\\r?\\n");
                                        for(int j=0;j<servicenames.length;j++)
                                            newServicename.add(servicenames[j]+":"+Long.parseLong(lfrenquency[j])/1000000+"-"+Long.parseLong(ufrenquency[j])/1000000);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Set<String> hs = new HashSet<>();
            hs.addAll(newServicename);
            list.addAll(hs);
        }
        return list;
    }


    public void getsearchspectrum(){

        NoUniqueDimos = new ArrayList<>();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + Main.getip() + ":8080/useraccount2/returnsearchsafety/dosearch", new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                try {
                    Log.e("0","sto try");
                    NoUniqueDimos.clear();
                    dimos.clear();
                    nomos.clear();
                    name_region.clear();
                    date.clear();
                    time.clear();
                    perigrafi1.clear();
                    perigrafi2.clear();
                    servicename.clear();
                    Log.e("1","prin ta j object");
                    JSONObject jb = new JSONObject(response);
                    JSONArray dimosj = jb.getJSONArray("dimos");
                    Log.e("11","prin ta j object");
                    JSONArray name_regionj = jb.getJSONArray("name_region");
                    Log.e("12","prin ta j object");
                    JSONArray nomosj = jb.getJSONArray("nomos");
                    Log.e("13","prin ta j object");
                    JSONArray datej = jb.getJSONArray("date");
                    Log.e("14","prin ta j object");
                    JSONArray timej = jb.getJSONArray("time");
                    Log.e("15","prin ta j object");
                    JSONArray perigrafi1j = jb.getJSONArray("perigrafi1");
                    Log.e("16","prin ta j object");
                    JSONArray perigrafi2j = jb.getJSONArray("perigrafi2");
                    Log.e("17","prin ta j object");
                    JSONArray servicenamej = jb.getJSONArray("service_name");
                    Log.e("18","prin ta j object");
                    JSONArray lower_frenquencyj = jb.getJSONArray("lower_frenquency");
                    Log.e("19","prin ta j object");
                    JSONArray upper_frenquencyj = jb.getJSONArray("upper_frenquency");
                    Log.e("2","prin apo for");
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
                    for(int i=0;i<servicenamej.length();i++){
                        servicename.add(i,servicenamej.getString(i));
                    }
                    for(int i=0;i<lower_frenquencyj.length();i++){
                        lower_frenquency.add(i, lower_frenquencyj.getString(i));
                    }
                    for(int i=0;i<upper_frenquencyj.length();i++){
                        upper_frenquency.add(i,upper_frenquencyj.getString(i));
                    }
                    Log.e("3","meta apo for");
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
        client.get("http://" + Main.getip() + ":8080/useraccount2/returnsearchsafetyinfo/dosearch", params, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                try {
                    double Fmin;
                    double Fmax;
                    String noise_flag;
                    String service_name;
                    String lower_frenquency;
                    String upper_frenquency;
                    String value;
                    double x_1;
                    double y_1;
                    double x_2;
                    double y_2;
                    String datei;
                    String timei;
                    String dataset_version;
                    String dataset_type;
                    double rbw;
                    double measurements_range;
                    String unit;
                    String result_type;
                    String average_type;
                    double average_time;
                    String no_of_args;
                    String display;
                    String Axis;
                    String service_table_name;
                    String device_serial_no;
                    String device_firmware_version;
                    double total_value;
                    double total_noise_flag;
                    double other_values;
                    String perigrafi1i;
                    String perigrafi2i;
                    String threshold;
                    String noise_threshold;
                    String noise_threshold_factor;
                    String store_mode;
                    String antenna_name;

                    JSONObject rs2 = new JSONObject(response);
                    Fmin = rs2.getDouble("Fmin");

                    Fmax = rs2.getDouble("Fmax");

                    noise_flag = rs2.getString("noise_flag");

                    String noise_flagt[]=noise_flag.split("\\r?\\n");

                    service_name=rs2.getString("service_name");

                    lower_frenquency = rs2.getString("lower_frenquency");

                    upper_frenquency=rs2.getString("upper_frenquency");

                    value = rs2.getString("value");
                    Log.e("19","meta tin dilwsi");
                    x_1 = rs2.getDouble("x_1");
                    Log.e("110","meta tin dilwsi");
                    y_1 = rs2.getDouble("y_1");
                    Log.e("111","meta tin dilwsi");
                    x_2 = rs2.getDouble("x_2");
                    Log.e("112","meta tin dilwsi");
                    y_2 = rs2.getDouble("y_2");
                    Log.e("113","meta tin dilwsi");
                    datei=rs2.getString("date");
                    Log.e("114","meta tin dilwsi");
                    timei=rs2.getString("time");
                    Log.e("115","meta tin dilwsi");
                    dataset_version = rs2.getString("dataset_version");
                    Log.e("116","meta tin dilwsi");
                    dataset_type = rs2.getString("dataset_type");
                    Log.e("117","meta tin dilwsi");
                    rbw = rs2.getDouble("rbw");
                    Log.e("118","meta tin dilwsi");
                    measurements_range = rs2.getDouble("measurements_range");
                    Log.e("119","meta tin dilwsi");
                    unit = rs2.getString("unit");
                    Log.e("120","meta tin dilwsi");
                    result_type = rs2.getString("result_type");
                    Log.e("121","meta tin dilwsi");
                    average_type = rs2.getString("average_type");
                    Log.e("122","meta tin dilwsi");
                    average_time = rs2.getDouble("average_time");
                    Log.e("123","meta tin dilwsi");
                    no_of_args = rs2.getString("no_of_args");
                    Log.e("124","meta tin dilwsi");
                    display = rs2.getString("display");
                    Log.e("125","meta tin dilwsi");
                    Axis = rs2.getString("axis");
                    Log.e("126","meta tin dilwsi");
                    service_table_name = rs2.getString("service_table_name");
                    Log.e("127","meta tin dilwsi");
                    device_serial_no = rs2.getString("device_serial_no");
                    Log.e("128","meta tin dilwsi");
                    device_firmware_version = rs2.getString("device_firmware_version");
                    Log.e("129","meta tin dilwsi");
                    total_value = rs2.getDouble("total_value");
                    Log.e("130","meta tin dilwsi");
                    total_noise_flag=rs2.getDouble("total_noise_flag");
                    Log.e("131","meta tin dilwsi");
                    other_values=rs2.getDouble("other_values");
                    Log.e("132","meta tin dilwsi");
                    perigrafi1i = rs2.getString("perigrafi1");
                    Log.e("133","meta tin dilwsi");
                    perigrafi2i = rs2.getString("perigrafi2");
                    Log.e("134","meta tin dilwsi");
                    threshold = rs2.getString("threshold");
                    Log.e("135","meta tin dilwsi");
                    noise_threshold = rs2.getString("noise_threshold");
                    Log.e("136","meta tin dilwsi");
                    noise_threshold_factor = rs2.getString("noise_threshold_flag");
                    Log.e("138","meta tin dilwsi");

                    Intent j = new Intent(getActivity().getApplication(), SafetyInfo.class);
                    j.putExtra("total_noise_flag",total_noise_flag);
                    j.putExtra("dataset_version", dataset_version);
                    j.putExtra("dataset_type", dataset_type);
                    j.putExtra("lower_frenquency",lower_frenquency);
                    j.putExtra("upper_frenquency",upper_frenquency);
                    j.putExtra("noise_flag",noise_flagt[0]);
                    j.putExtra("service_name",service_name);
                    j.putExtra("date",datei);
                    j.putExtra("time",timei);
                    j.putExtra("display",display);
                    j.putExtra("axis",Axis);
                    j.putExtra("device_serial_no",device_serial_no);
                    j.putExtra("device_firmware_version",device_firmware_version);
                    j.putExtra("total_value",total_value);
                    j.putExtra("noise_threshold",noise_threshold);
                    j.putExtra("noise_threshold_factor",noise_threshold_factor);
                    j.putExtra("other_values",other_values);
                    j.putExtra("Fmin", Fmin);
                    j.putExtra("Fmax", Fmax);
                    j.putExtra("rbw", rbw);
                    j.putExtra("measurements_range", measurements_range);
                    j.putExtra("unit", unit);
                    j.putExtra("average_type", average_type);
                    j.putExtra("average_time", average_time);
                    j.putExtra("no_of_args", no_of_args);
                    j.putExtra("threshold", threshold);
                    j.putExtra("service_table_name", service_table_name);
                    j.putExtra("x_1", x_1);
                    j.putExtra("y_1", y_1);
                    j.putExtra("x_2", x_2);
                    j.putExtra("y_2", y_2);
                    j.putExtra("value", value);
                    j.putExtra("result_type", result_type);
                    j.putExtra("perigrafi1", perigrafi1i);
                    j.putExtra("perigrafi2", perigrafi2i);

                    startActivity(j);

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
