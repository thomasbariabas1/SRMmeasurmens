package com.example.tom.engineer;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.example.srmmeasurements.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class SpectrumInfoGraph2 extends Fragment {


    int index = 0;
    int index2=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.spectrumsearchgraph1, container, false);
        Bundle rs2 = getArguments();
        String frenquency = rs2.getString("frenquency");
        String value = rs2.getString("value");
        String fmin = rs2.getString("fmincheck");
        String fmax = rs2.getString("fmaxcheck");
        final String[] stockArr;
        final String[] stockArr2;
        ArrayList<Double> frenquencyb;

        XYPlot plot = (XYPlot) rootView.findViewById(R.id.mySimpleXYPlot);
        assert frenquency != null;
        String splitfrenquency[] = frenquency.split("\\r?\\n");
        assert value != null;
        String splitvalue[] = value.split("\\r?\\n");
        frenquencyb = new ArrayList<>();
        ArrayList<BigDecimal> valueb = new ArrayList<>();
        Locale.setDefault(Locale.US);



        // Create a couple arrays of y-values to plot:
        for (String aSplitfrenquency : splitfrenquency) {

            frenquencyb.add(Double.parseDouble(aSplitfrenquency) * Math.pow(10, -6));

        }

        for (String aSplitvalue : splitvalue) {

            valueb.add(BigDecimal.valueOf(Math.pow(Double.valueOf(aSplitvalue),2)/(120*Math.PI)));

        }
        for (int i = frenquencyb.size() - 1; i >= 0; i--) {

            if(!fmin.equals("")) {
                if (frenquencyb.get(i) < Double.parseDouble(fmin)) {

                    frenquencyb.remove(i);
                    valueb.remove(i);
                }
            }
            if(!fmax.equals("")) {
                if (frenquencyb.get(i) > Double.parseDouble(fmax)) {

                    frenquencyb.remove(i);
                    valueb.remove(i);
                }

            }
        }

        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                valueb,          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series
        DecimalFormat df = new DecimalFormat("0.0E0");

        ArrayList<String> frs = new ArrayList<>();
        ArrayList<String> vrs = new ArrayList<>();
        ArrayList<Double> temp = new ArrayList<>();

        temp.add(valueb.get(0).doubleValue());


        for (int i = 1; i < 11; i++) {
            if (i == 10) {
                temp.add(i, valueb.get(valueb.size() - 1).doubleValue());
                break;
            }

            temp.add(valueb.get(Math.round(valueb.size() * i / 10)).doubleValue());


        }

        Collections.sort(temp);
        for (int i=0;i<temp.size();i++){

            vrs.add(df.format(temp.get(i)));

        }
        frs.add(String.valueOf(Math.round(frenquencyb.get(0))));
        frs.add(String.valueOf(Math.round(frenquencyb.get((int) Math.round(frenquencyb.size() * 0.4)))));
        frs.add(String.valueOf(Math.round(frenquencyb.get((int) Math.round(frenquencyb.size() * 0.8)))));
        frs.add(String.valueOf(Math.round(frenquencyb.get(frenquencyb.size() - 1))));

        stockArr2=new String[vrs.size()];
        stockArr = new String[frs.size()];

        for(int i=0;i<frs.size();i++){
            stockArr[i]=frs.get(i);

        }


        for(int i=0;i<vrs.size();i++){
            stockArr2[i] = vrs.get(i);

        }


        plot.setDomainStep(XYStepMode.SUBDIVIDE, stockArr.length);
        plot.setRangeStep(XYStepMode.SUBDIVIDE, stockArr2.length);



        // attach index->string formatter to the plot instance

        plot.setDomainValueFormat(new Format() {

            // create a simple date format that draws on the year portion of our timestamp.
            // see http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
            // for a full description of SimpleDateFormat.


            @Override
            public StringBuffer format(Object obj, @NonNull StringBuffer toAppendTo, @NonNull FieldPosition pos) {

                // because our timestamps are in seconds and SimpleDateFormat expects milliseconds
                // we multiply our timestamp by 1000:

                return new StringBuffer(stockArr[index++]);
            }

            @Override
            public Object parseObject(String source, @NonNull ParsePosition pos) {
                return null;

            }
        });
        plot.setRangeValueFormat(new Format() {

            // create a simple date format that draws on the year portion of our timestamp.
            // see http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
            // for a full description of SimpleDateFormat.


            @Override
            public StringBuffer format(Object obj, @NonNull StringBuffer toAppendTo, @NonNull FieldPosition pos) {

                // because our timestamps are in seconds and SimpleDateFormat expects milliseconds
                // we multiply our timestamp by 1000:

                return new StringBuffer(stockArr2[index2++]);
            }

            @Override
            public Object parseObject(String source, @NonNull ParsePosition pos) {
                return null;

            }
        });

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getActivity().getApplicationContext(),
                R.xml.line_point_formatter_with_plf1);
        series1Format.setPointLabelFormatter(new PointLabelFormatter(Color.TRANSPARENT));
        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);






        return rootView;
    }


}