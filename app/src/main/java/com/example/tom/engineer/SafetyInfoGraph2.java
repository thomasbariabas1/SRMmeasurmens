package com.example.tom.engineer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
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

/**
 * Created by tom on 9/18/2015.
 */
public class SafetyInfoGraph2  extends Fragment{
    ArrayList<String> service_table = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();
    ArrayList<String> lf = new ArrayList<String>();
    ArrayList<String> uf= new ArrayList<String>();
    ArrayList<String> nf = new ArrayList<String>();
    ArrayList<String> x1 = new ArrayList<String>();
    ArrayList<String> y1 = new ArrayList<String>();
    ArrayList<String> x2 = new ArrayList<String>();
    ArrayList<String> y2 = new ArrayList<String>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.safetysearchgraph2, container, false);

        String service_table_name;
        search(rootView);
        return rootView;
    }

    public void search( final View rootView) {
        RequestParams params = new RequestParams();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + Main.getip() + ":8080/useraccount2/returnsearchsafetyinfoGraph2/dosearch", params, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject jb = new JSONObject(response);
                    JSONArray st1 = jb.getJSONArray("value");
                    JSONArray st2 = jb.getJSONArray("noise_flag");
                    JSONArray st3 = jb.getJSONArray("service_name");
                    JSONArray st4 = jb.getJSONArray("lf");
                    JSONArray st5 = jb.getJSONArray("uf");
                    JSONArray st6 = jb.getJSONArray("x1");
                    JSONArray st7 = jb.getJSONArray("y1");
                    JSONArray st8 = jb.getJSONArray("x2");
                    JSONArray st9 = jb.getJSONArray("y2");

                    for (int i = 0; i < st1.length(); i++) {
                        value.add(i, st1.getString(i));
                    }
                    for (int i = 0; i < st2.length(); i++) {
                        nf.add(i, st2.getString(i));
                    }
                    for (int i = 0; i < st3.length(); i++) {
                        service_table.add(i, st3.getString(i));
                    }
                    for (int i = 0; i < st4.length(); i++) {
                        lf.add(i, st4.getString(i));
                    }
                    for (int i = 0; i < st5.length(); i++) {
                        uf.add(i, st5.getString(i));
                    }
                    for (int i = 0; i < st6.length(); i++) {
                        x1.add(i, st6.getString(i));
                    }
                    for (int i = 0; i < st7.length(); i++) {
                        y1.add(i, st7.getString(i));
                    }
                    for (int i = 0; i < st8.length(); i++) {
                        x2.add(i, st8.getString(i));
                    }
                    for (int i = 0; i < st9.length(); i++) {
                        y2.add(i, st9.getString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               addrow(rootView);
            }

            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
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
    public void addrow( View rootView){
        //this.getActivity().setContentView(R.layout.safetysearchgraph2);
        TableLayout tl = (TableLayout) rootView.findViewById(R.id.tablelayout);
        View divider;
        divider = new View(this.getActivity());
        divider.setLayoutParams(new TableLayout.LayoutParams(
                2, TableLayout.LayoutParams.MATCH_PARENT));
        divider.setBackgroundColor(Color.GREEN);
            String split[] = service_table.get(0).split("\\r?\\n");
            String splitvalue[] = value.get(0).split("\\r?\\n");
            String splitlowerfr[] = lf.get(0).split("\\r?\\n");
            String splitupperf[] = uf.get(0).split("\\r?\\n");
            String splitnoiseflag[] = nf.get(0).split("\\r?\\n");

        for(int i=0;i<splitvalue.length;i++){

            Log.e(Integer.toString(i),splitvalue[i]);
        }
        ArrayList<TextView> text1 = new ArrayList<>();
        ArrayList<TextView> text2 = new ArrayList<>();
        ArrayList<TextView> text3 = new ArrayList<>();
        ArrayList<TextView> text4 = new ArrayList<>();
        ArrayList<TextView> text5 = new ArrayList<>();
        ArrayList<TextView> text6 = new ArrayList<>();
        ArrayList<TextView> text7 = new ArrayList<>();
        ArrayList<TextView> text8 = new ArrayList<>();
        ArrayList<TextView> text9 = new ArrayList<>();
        ArrayList<TableRow> row = new ArrayList<>();
        for(int i=0;i<split.length;i++) {
            text1.add(new TextView(getActivity()));
            text1.get(i).setText(split[i]);
            text2.add(new TextView(getActivity()));
            text2.get(i).setText(splitvalue[i]);
            text3.add(new TextView(getActivity()));
            text3.get(i).setText(splitnoiseflag[i]);
            text4.add(new TextView(getActivity()));
            text4.get(i).setText(splitlowerfr[i]);
            text5.add(new TextView(getActivity()));
            text5.get(i).setText(splitupperf[i]);
            text6.add(new TextView(getActivity()));
            text6.get(i).setText(x1.get(0));
            text7.add(new TextView(getActivity()));
            text7.get(i).setText(y1.get(0));
            text8.add(new TextView(getActivity()));
            text8.get(i).setText(x2.get(0));
            text9.add(new TextView(getActivity()));
            text9.get(i).setText(y2.get(0));
            row.add(new TableRow(this.getActivity()));
            row.get(i).setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


            row.get(i).addView(text1.get(i));
            row.get(i).addView(text2.get(i));

            row.get(i).addView(text3.get(i));

            row.get(i).addView(text4.get(i));

            row.get(i).addView(text5.get(i));

            row.get(i).addView(text6.get(i));

            row.get(i).addView(text7.get(i));

            row.get(i).addView(text8.get(i));

            row.get(i).addView(text9.get(i));

            tl.addView( row.get(i));
        }


    }
}
