package com.example.tom.engineer;

import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.example.srmmeasurements.R;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Locale;
import java.util.Random;


public class SafetyInfoGraph1 extends Fragment {

    private TextView donutSizeTextView;
    private SeekBar donutSizeSeekBar;

    private PieChart pie;

    private Segment s1[];


     Bundle rs2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.safetysearchgraph1, container, false);


       rs2=getArguments();
        String service_name=rs2.getString("service_name");
        String value = rs2.getString("value");
        Log.e("value", value);

        String lower_frenquency = rs2.getString("lower_frenquency");
        String upper_frenquency=rs2.getString("upper_frenquency");
        assert service_name != null;
        String service_name_split[] = service_name.split("\\r?\\n");
        assert value != null;
        String value_split[]= value.split("\\r?\\n");
        assert lower_frenquency != null;
        String lower_frenquency_split[]=lower_frenquency.split("\\r?\\n");
        assert upper_frenquency != null;
        String upper_frenquency_split[]=upper_frenquency.split("\\r?\\n");
        int counter = 0;
        for(int i=0;i<service_name_split.length;i++){

            if(!service_name_split[i].equals(" "))
            counter ++;
            }
        String temp[] = new String[counter];
        String temp2[] =new String[counter];
        String temp3[] = new String[counter];
        String temp4[] = new String[counter];
        counter=0;
        for(int i=0;i<service_name_split.length;i++){

            if(!service_name_split[i].equals(" ")){

                    temp[counter]=service_name_split[i];
                    temp2[counter] = value_split[i];
                    temp3[counter]=lower_frenquency_split[i];
                    temp4[counter]=upper_frenquency_split[i];
                    counter++;
            }

        }

        // initialize our XYPlot reference:
        pie = (PieChart) rootView.findViewById(R.id.mySimplePieChart);
        pie.setTitle("Safety Evaluation");
        // detect segment clicks:
        pie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if(pie.getPieWidget().containsPoint(click)) {
                    Segment segment = pie.getRenderer(PieRenderer.class).getContainingSegment(click);
                    if(segment != null) {
                        // handle the segment click...for now, just print
                        // the clicked segment's title to the console:
                        System.out.println("Clicked Segment: " + segment.getTitle());
                    }
                }
                return false;
            }
        });


        donutSizeSeekBar = (SeekBar) rootView.findViewById(R.id.donutSizeSeekBar);

        donutSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pie.getRenderer(PieRenderer.class).setDonutSize(seekBar.getProgress() / 100f,
                        PieRenderer.DonutMode.PERCENT);
                pie.redraw();
                updateDonutText();
            }
        });



        donutSizeTextView = (TextView) rootView.findViewById(R.id.donutSizeTextView);
        donutSizeTextView.setTextSize(20);
        updateDonutText();
        Locale.setDefault(Locale.US);
        s1 = new Segment[temp.length];
        double sum=0;
        for(int i=0;i<temp2.length;i++){

            sum=sum+Double.parseDouble(temp2[i]);
        }
        double pososta[] = new double[s1.length];
        for(int i=0;i<s1.length;i++){
            pososta[i]= round(Double.parseDouble(temp2[i])/sum,2);

        }
        for(int i=0;i<s1.length;i++){

            s1[i]=new Segment(temp[i]+" "+Double.parseDouble(temp3[i])/1000000+"-"+Double.parseDouble(temp4[i])/1000000+"("+pososta[i]+"%)",Double.parseDouble(temp2[i]));

        }
        for(int i = 0 ; i< s1.length;i++) {

            pie.addSeries(s1[i],getFormatter());
        }
        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);

        return rootView;
    }


    public SegmentFormatter getFormatter(){
        SegmentFormatter sf1 = new SegmentFormatter();
        Random rand = new Random();

        sf1.getFillPaint().setColor(rand.nextInt());
        sf1.getLabelPaint().setTextSize(25);
        sf1.getLabelMarkerPaint().setColor(00000000);
        return sf1;
    }
    protected void updateDonutText() {
        donutSizeTextView.setText(donutSizeSeekBar.getProgress() + "%");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value*100);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
