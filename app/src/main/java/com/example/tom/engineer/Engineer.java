package com.example.tom.engineer;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;
import com.example.srmmeasurements.R;
import com.example.tom.srmmeasurmens.About;
import com.example.tom.srmmeasurmens.Main;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class Engineer extends FragmentActivity  {


    SharedPreferences mPreferences;
    ArrayList<String> id;
    ArrayList<String> region;
    ArrayList<Double> x_1;
    ArrayList<Double> y_1;
    ArrayList<Double> x_2;
    ArrayList<Double> y_2;
    ArrayList<String> x_2_string = new ArrayList<>();
    GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.engineer);
                id  = new ArrayList<>();
                region  = new ArrayList<>();
                x_1  = new ArrayList<>();
                y_1  = new ArrayList<>();
                x_2  = new ArrayList<>();
                y_2  = new ArrayList<>();

        getmarkers();
    }
    @Override
    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish()
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the
        // map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    private void setUpMap() {
        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Add lots of markers to the map.
        addMarkersToMap();

        // Setting an info window adapter allows us to change the both the
        // contents and look of the
        // info window.
       // mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // Set listeners for marker events. See the bottom of this class for
        // their behavior.
      //  mMap.setOnMarkerClickListener(this);
       // mMap.setOnInfoWindowClickListener(this);
       // mMap.setOnMarkerDragListener(this);

        // Pan to see all markers in view.
        // Cannot zoom to bounds until the map has a size.
        final View mapView = getSupportFragmentManager().findFragmentById(R.id.map2).getView();
        if (mapView != null) {
            if (mapView.getViewTreeObserver().isAlive()) {
                mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {


                    }
                });
            }
        }
    }
    private void addMarkersToMap() {
        mMap.clear();
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(x_1.get(0),y_1.get(0)) , 14.0f) );
        LatLng[] ll = new LatLng[x_1.size()];
        for (int i = 0; i < x_1.size(); i++) {
            if(x_2_string.get(i).equals("0")) {
                ll[i] = new LatLng(x_1.get(i), y_1.get(i));
                mMap.addMarker(new MarkerOptions().position(ll[i]).title(region.get(i)).snippet(x_1.get(i).toString() + " " + y_1.get(i).toString()));
            }else{

                ll[i] = new LatLng(x_1.get(i), y_1.get(i));
                mMap.addMarker(new MarkerOptions().position(ll[i]).title(region.get(i)).snippet(x_1.get(i).toString()+" "+y_1.get(i).toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                ll[i] = new LatLng(x_2.get(i), y_2.get(i));
                mMap.addMarker(new MarkerOptions().position(ll[i]).title(region.get(i)).snippet(x_2.get(i).toString()+" "+y_2.get(i).toString()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                     mMap.addPolyline(new PolylineOptions()
                             .add(new LatLng(x_1.get(i), y_1.get(i)), new LatLng(x_2.get(i), y_2.get(i)))
                             .width(5)
                             .color(Color.BLUE).geodesic(true));


            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.add:
                Intent k = new Intent(getApplicationContext(), AddMeasurements.class);
                startActivity(k);
                return true;

            case R.id.editregion:
                Intent i = new Intent(getApplicationContext(), EditRegionMeasurements.class);
                startActivity(i);
                return true;
            case R.id.search:
                Intent j = new Intent(getApplicationContext(), SearchMeasurements.class);
                startActivity(j);
                return true;
            case R.id.about:
                Intent p = new Intent(getApplicationContext(), About.class);
                startActivity(p);
                return true;
            case R.id.connect:
                // help action
                return true;
            case R.id.logout:
                mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
                SharedPreferences.Editor editor=mPreferences.edit();
                editor.remove("UserName");
                editor.remove("PassWord");
                editor.apply();
                Message myMessage=new Message();
                myMessage.obj="NOTSUCCESS";
                handler.sendMessage(myMessage);

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String loginmsg=(String)msg.obj;
            if(loginmsg.equals("NOTSUCCESS")) {
                Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
                intent.putExtra("LoginMessage", "Logged Out");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                removeDialog(0);
            }
        }


    };


    public void getmarkers(){

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+ Main.getip()+":8080/useraccount2/addmarkers/dosearch",new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try {

                    JSONObject jb = new JSONObject(response);
                    JSONArray st = jb.getJSONArray("id");
                    JSONArray st1 = jb.getJSONArray("region");
                    JSONArray st2 = jb.getJSONArray("x_1");
                    JSONArray st3 = jb.getJSONArray("y_1");
                    JSONArray st4 = jb.getJSONArray("x_2");
                    JSONArray st5 = jb.getJSONArray("y_2");

                    for(int i=0;i<st.length();i++)
                    {
                        id.add(i,st.getString(i));

                        // loop and add it to array or arraylist
                    }
                    for(int i=0;i<st1.length();i++)
                    {
                        region.add(i,st1.getString(i));

                        // loop and add it to array or arraylist
                    }
                    for(int i=0;i<st2.length();i++)
                    {
                        x_1.add(i,Double.parseDouble(st2.getString(i)));
                        Log.e("X_1",x_1.get(i).toString());
                        // loop and add it to array or arraylist
                    }
                    for(int i=0;i<st3.length();i++)
                    {
                        y_1.add(i, Double.parseDouble(st3.getString(i)));

                        // loop and add it to array or arraylist
                    }
                    for(int i=0;i<st4.length();i++) {
                        x_2_string.add(i, st4.getString(i));

                        if(!x_2_string.get(i).equals("")) {

                             x_2.add(i, Double.parseDouble(st4.getString(i)));
                             y_2.add(i, Double.parseDouble(st5.getString(i)));

                        }
                    }
                    setUpMapIfNeeded();
                } catch (JSONException e) {

                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,String content) {
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
