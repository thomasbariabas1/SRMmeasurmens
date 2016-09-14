package com.example.tom.engineer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.srmmeasurements.R;


public class SpectrumInfoTable extends Fragment {
    TextView dataset_versiontext;
    TextView dataset_typetext;
    TextView store_modetext;
    TextView dateitext;
    TextView timeitext;
    TextView Fmintext;
    TextView Fmaxtext;
    TextView rbwtext;
    TextView measurements_rangetext;
    TextView unittext;
    TextView average_typetext;
    TextView average_timetext;
    TextView no_of_argstext;
    TextView thresholdtext;
    TextView y_scale_reftext;
    TextView y_scale_rangetext;
    TextView axistext;
    TextView service_table_nametext;
    TextView antenna_nametext;
    TextView frenquency_resolutiontext;
    TextView result_typetext;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.spectrumsearchtable, container, false);
        Bundle rs2= getArguments();

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
        String result_type;


        dataset_version=rs2.getString("dataset_version");
        dataset_type= rs2.getString("dataset_type");
        store_mode=rs2.getString("store_mode");
        datei=rs2.getString("date");
        timei=rs2.getString("time");
        Fmin=rs2.getDouble("Fmin");
        Fmax=rs2.getDouble("Fmax");
        rbw=rs2.getDouble("rbw");
        measurements_range=rs2.getDouble("measurements_range");
        unit=rs2.getString("unit");
        average_type=rs2.getString("average_type");
        average_time=rs2.getDouble("average_time");
        no_of_args=rs2.getInt("no_of_args");
        threshold=rs2.getString("threshold");
        y_scale_ref=rs2.getString("y_scale_ref");
        y_scale_range=rs2.getString("y_scale_range");
        axis=rs2.getString("axis");
        service_table_name=rs2.getString("service_table_name");
        antenna_name=rs2.getString("antenna_name");
        frenquency_resolution=rs2.getDouble("frenquency_resolution");
        result_type=rs2.getString("result_type");


        dataset_versiontext=(TextView) rootView.findViewById(R.id.datasetv);
        dataset_typetext=(TextView) rootView.findViewById(R.id.datasett);
        store_modetext=(TextView) rootView.findViewById(R.id.storemode);
        dateitext = (TextView) rootView.findViewById(R.id.date);
        timeitext=(TextView) rootView.findViewById(R.id.time);
        Fmintext = (TextView) rootView.findViewById(R.id.fmin1);
        Fmaxtext = (TextView) rootView.findViewById(R.id.fmax1);
        rbwtext=(TextView) rootView.findViewById(R.id.rbw);
        measurements_rangetext=(TextView) rootView.findViewById(R.id.measurementsrange);
        unittext=(TextView) rootView.findViewById(R.id.unit);
        average_typetext=(TextView) rootView.findViewById(R.id.averagetype);
        average_timetext = (TextView) rootView.findViewById(R.id.averagetimes);
        no_of_argstext=(TextView) rootView.findViewById(R.id.numberofav);
        thresholdtext = (TextView) rootView.findViewById(R.id.threshold);
        y_scale_reftext = (TextView) rootView.findViewById(R.id.scaleref);
        y_scale_rangetext = (TextView) rootView.findViewById(R.id.scalerange);
        axistext=(TextView) rootView.findViewById(R.id.axis);
        service_table_nametext=(TextView) rootView.findViewById(R.id.servicetablename);
        antenna_nametext =(TextView) rootView.findViewById(R.id.antennaname);
        frenquency_resolutiontext=(TextView) rootView.findViewById(R.id.frenquencyresolution);
        result_typetext=(TextView) rootView.findViewById(R.id.resulttype);

        dataset_versiontext.setText(dataset_version);
        dataset_typetext.setText(dataset_type);
        store_modetext.setText(store_mode);
        dateitext.setText(datei);
        timeitext.setText(timei);
        Fmintext.setText(Double.toString(Fmin));
        Fmaxtext.setText(Double.toString(Fmax));
        rbwtext.setText(Double.toString(rbw));
        measurements_rangetext.setText(Double.toString(measurements_range));
        unittext.setText(unit);

        average_typetext.setText(average_type);
        average_timetext.setText(Double.toString(average_time));
        no_of_argstext.setText(Integer.toString(no_of_args));
        thresholdtext.setText(threshold);
        y_scale_reftext.setText(y_scale_ref);
        y_scale_rangetext.setText(y_scale_range);
        axistext.setText(axis);
        service_table_nametext.setText(service_table_name);
        antenna_nametext.setText(antenna_name);
        frenquency_resolutiontext.setText(Double.toString(frenquency_resolution));
        result_typetext.setText(result_type);

        return rootView;
    }
    public interface OnRefreshListener {
        public void onRefresh();
    }
}
