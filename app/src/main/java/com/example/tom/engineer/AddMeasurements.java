package com.example.tom.engineer;

        import java.io.BufferedReader;
        import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.io.File;
        import java.io.BufferedInputStream;
        import java.io.BufferedOutputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.net.InetAddress;
        import java.net.Socket;
        import java.net.UnknownHostException;
        import java.util.ArrayList;
        import java.util.UUID;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.bluetooth.BluetoothSocket;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.Settings;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.Spinner;
        import android.widget.Toast;
        import com.example.srmmeasurements.R;
        import com.example.tom.srmmeasurmens.Main;
public class AddMeasurements extends Activity implements OnClickListener, LocationListener {
    private Context mContext;

    // flag for GPS status
    public boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    private Socket socket;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute
    private boolean istracking = false;
    // Declaring a Location Manager
    protected LocationManager locationManager;
    private static final int serverPort = 8888;
    private static final String serverIP = "10.0.2.2";//127.0.0.1
    //private int selectedConnection;
    private Spinner dropdown;
    private Button btnDownload, btnSend, btnExit,starttracking,stoptracking,getloc;
    private BluetoothAdapter myBluetoothAdapter;
    private BluetoothDevice remoteDevice;
    public AddMeasurements(){

    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmeasurements);
        //registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        starttracking = (Button) findViewById(R.id.starttracking);
        stoptracking = (Button) findViewById(R.id.stoptracking);
        starttracking.setVisibility(View.INVISIBLE);
        stoptracking.setVisibility(View.INVISIBLE);
        getloc = (Button) findViewById(R.id.getloc);
        getloc.setVisibility(View.INVISIBLE);
        btnDownload = (Button) findViewById(R.id.button1);
        btnSend = (Button) findViewById(R.id.button2);
        btnExit = (Button) findViewById(R.id.button3);
        dropdown = (Spinner)findViewById(R.id.spinner1);
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String[] items = new String[]{"-----", "Στατικό", "Εν Κινήση"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String selectedValue = arg0.getSelectedItem().toString();
                if(selectedValue.equals("Στατικό")){
                    getloc.setVisibility(View.VISIBLE);
                    starttracking.setVisibility(View.INVISIBLE);
                    stoptracking.setVisibility(View.INVISIBLE);
                }else{
                    starttracking.setVisibility(View.VISIBLE);
                    stoptracking.setVisibility(View.INVISIBLE);
                    getloc.setVisibility(View.INVISIBLE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnDownload.setOnClickListener(this);
        starttracking.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                istracking=true;
                starttracking.setVisibility(View.INVISIBLE);
                stoptracking.setVisibility(View.VISIBLE);
                savelocation("start");
            }
        });
        stoptracking.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                istracking=false;
                starttracking.setVisibility(View.VISIBLE);
                stoptracking.setVisibility(View.INVISIBLE);
                savelocation("stop");
            }
        });
    }
    public AddMeasurements(Context context) {
        this.mContext = context;


    }
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            Log.v("isGPSEnabled", "=" + isGPSEnabled);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Log.v("isNetworkEnabled", "=" + isNetworkEnabled);

            if (isGPSEnabled == false && isNetworkEnabled == false) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    location=null;
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    location=null;
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }
    private void writeToFile(String data) {
        try {
            FileOutputStream out = openFileOutput("config.txt", Context.MODE_PRIVATE);
            out.write(data.getBytes());
            out.close();
            Log.e("Egrapse sto arxeio",data);
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    public void savelocation(String a){

            writeToFile(a+" "+String.valueOf(getLocation()));

    }
    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     * */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(AddMeasurements.this);
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will
     * lauch Settings Options
     * */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog
                .setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // when the first item gets selected
                break;
            case 1:
                // when the second item gets selected
                break;
            case 2:
                // when the third item gets selected
                break;
            case 3:
                break;
            case 4:
                break;

        }
    }

    public void noConnectionTypeMessage(){
        AlertDialog.Builder build = new AlertDialog.Builder(AddMeasurements.this);
        build.setMessage("No Connection Type Selected..Please select connection type first.");
        build.setCancelable(false);

        build.setPositiveButton("Return", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = build.create();
        alert.show();
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId() == R.id.button1){//Download Button Used.
			/*if(dropdown.getSelectedItemPosition() == 0) noConnectionTypeMessage();
			if(dropdown.getSelectedItemPosition() == 1) checkBluetooth();
			if(dropdown.getSelectedItemPosition() == 2) new Thread(new clienThread()).start();*/
            new Thread(new clienThread()).start();
        }

        if(v.getId() == R.id.button2){//Send Button

        }

        if(v.getId() == R.id.button2){//Exit Button

        }
    }

    public void checkBluetooth(){
        if(myBluetoothAdapter == null) {//if true bluetooth is not supported for device..
            Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth",Toast.LENGTH_LONG).show();
        }

        if (!myBluetoothAdapter.isEnabled()) {//enable bluetooth.
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, 1);
            myBluetoothAdapter.startDiscovery();
            registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }

    private BroadcastReceiver discoveryResult = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            new Thread(new connectBluetooth(remoteDevice)).start();
        }
    };

    class connectBluetooth implements Runnable{
        private BluetoothSocket mmSocket;
        @SuppressWarnings("unused")
        private BluetoothDevice mmDevice;
        private UUID uuid = UUID.fromString("71588091");
        DataInputStream dis = null;
        DataOutputStream dos = null;
        FileOutputStream fos = null;
        File root, dir;
        public connectBluetooth(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) { e.printStackTrace(); }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            myBluetoothAdapter.cancelDiscovery();
            String filename;
            root = Environment.getExternalStorageDirectory();
            dir = new File(root.getAbsolutePath(), "/aegean/");
            if(!dir.exists()){
                dir.mkdir();
            }
            try{
                mmSocket.connect();
                //actually we need only input stream here because we are receiving file only not sending anything.
                dis = new DataInputStream(new BufferedInputStream(mmSocket.getInputStream()));
                dos = new DataOutputStream(new BufferedOutputStream(mmSocket.getOutputStream()));

                //reading from server how many files being transfered.
                int size = dis.readInt();
                if(size == 1){//if only one file run this
                    String filePath = root.getAbsolutePath().toString() + "/aegean/";
                    //creating file with name of the file from server.
                    File file = new File(filePath + dis.readUTF());
                    //if file not found in sd card we must create one
                    if(!file.exists()) file.createNewFile();
                    int n = 0;
                    byte[]buf = new byte[1024];

                    filename = file.getName();
                    filePath += filename;
                    //opening file stream to write receiving data in it.
                    fos = new FileOutputStream(filePath);
                    //read file
                    while((n = dis.read(buf)) != -1){
                        fos.write(buf,0,n);
                        fos.flush();
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            } finally {
                AddMeasurements.this.runOnUiThread(new Runnable() {

                    public void run() {
                        Toast.makeText(getApplicationContext(),"File download completed!", Toast.LENGTH_LONG).show();
                    }
                });
                cancel();//close connection.
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }

    }

    class clienThread implements Runnable{
        DataInputStream dis = null;
        DataOutputStream dos = null;
        FileOutputStream fos = null;
        File root, dir;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            String filename;
            root = Environment.getExternalStorageDirectory();
            dir = new File(root.getAbsolutePath(), "/aegean/");
            if(!dir.exists()){
                dir.mkdir();
            }
            try{
                //server address to connect
                InetAddress serverAddr = InetAddress.getByName(serverIP);
                //opening socket for communication with the server
                socket = new Socket(serverAddr, serverPort);
                //input and output streams for transfering files
                //actually we need only input stream here because we are receiving file only not sending anything.
                dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                //reading from server how many files being transfered.
                int size = dis.readInt();
                if(size == 1){//if only one file run this
                    String filePath = root.getAbsolutePath().toString() + "/aegean/";
                    //creating file with name of the file from server.
                    File file = new File(filePath + dis.readUTF());
                    //if file not found in sd card we must create one
                    if(!file.exists()) file.createNewFile();
                    int n = 0;
                    byte[]buf = new byte[8192];

                    filename = file.getName();
                    filePath += filename;
                    //opening file stream to write receiving data in it.
                    fos = new FileOutputStream(filePath);
                    //read file
                    while((n = dis.read(buf)) != -1){
                        fos.write(buf,0,n);
                        fos.flush();
                    }
                } else {//we'll receive many files from server so run this
                    //Array list for saving files
                    ArrayList<File>files = new ArrayList<File>(size);
                    System.out.println("Number of Files to be received: " +size);
                    //long[] fileSize = new long[size];
                    //read file names, add files to arraylist
                    String filePath = root.getAbsolutePath().toString() + "/aegean/";
                    for(int i = 0; i < size;i++){
                        File file = new File(filePath + dis.readUTF());
                        files.add(file);
                        //fileSize[i] = dis.readLong();
                    }

                    int n = 0;
                    byte[]buf = new byte[8192];
                    long fileSize = 0;
                    //outer loop, executes one for each file
                    for(int i = 0; i < files.size();i++){
                        fileSize = dis.readLong();
                        filename = files.get(i).getName();
                        filePath += filename;
                        //create a new file output stream for each new file
                        fos = new FileOutputStream(filePath);
                        //read file
                        while(fileSize > 0 && (n = dis.read(buf, 0, (int)Math.min(buf.length, fileSize))) != -1){
                            fos.write(buf,0,n);
                            fos.flush();
                            fileSize -= n;
                        }
                        filePath = root.getAbsolutePath().toString() + "/aegean/";
                    }
                }
            } catch (UnknownHostException e){
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {

                    AddMeasurements.this.runOnUiThread(new Runnable() {

                        public void run() {
                            Toast.makeText(getApplicationContext(),"File download completed!", Toast.LENGTH_LONG).show();
                        }
                    });
                    //after downloading file(s) closing streams.
                    fos.close();
                    dis.close();
                    dos.close();
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    //System.out.println(e.getMessage() + "\n");
                    //e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
