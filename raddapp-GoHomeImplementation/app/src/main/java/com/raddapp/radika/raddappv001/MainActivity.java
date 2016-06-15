package com.raddapp.radika.raddappv001;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.BatchUpdateException;

import de.greenrobot.event.EventBus;

public class MainActivity extends FragmentActivity implements LocationListener, SensorEventListener {

    public static UserStats userStats = new UserStats();

    private static final String TAG = "Recorder";
    public static SurfaceView mSurfaceView;
    private LinearLayout surfaceViewParent;
    private FrameLayout parent;
    private FrameLayout container;
    private Context myContext;
    //public static CameraPreview mPreview;
    public static Camera mCamera;
    private  boolean isRecording;
    private boolean cameraFront = false;
    TextView tv;
    private static Intent camServintent;
    private ImageView led;

    static float initialBP;
    private SensorManager mSensorManager;

    static Location initialLoc;
    private boolean hasInitialLoc = false;
    LocationManager lm;

   /* private long takeoffTime;
    private TextView recTime;
    Thread threadRecTime;
*/
   private Chronometer chronometer;

    private GpsLoggingService gpslService;
    private static Intent serviceIntent;

    private RecorderService recService;
    private ServiceConnection mConnection = new ServiceConnection(){
        public void onServiceConnected(ComponentName className, IBinder service){
            recService = ((RecorderService.MyBinder)service).getService();
        }
        public void onServiceDisconnected(ComponentName className){
            stopService(new Intent(MainActivity.this, RecorderService.class));
            recService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        led = (ImageView) findViewById(R.id.ledIndicator);
        StartAndBindService();

        ViewGroup.LayoutParams params = led.getLayoutParams();
        params.width = 120;

        led.setImageResource(R.drawable.led_off2);

        lm =(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.menuContainer, new MenuFrag())
                    .commit();
        }
        isRecording = false;
        myContext = this;
        tv = new TextView(myContext);

        surfaceViewParent = (LinearLayout) findViewById(R.id.surfaceViewParent);
        container = (FrameLayout)findViewById(R.id.container);
        parent = (FrameLayout)findViewById(R.id.parent);
        parent.addView(tv);
        //mCamera = Camera.open(findBackFacingCamera());

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        final Button btnStart = (Button) findViewById(R.id.StartService);
        final Button btnFlip = (Button) findViewById(R.id.FlipVIew);
        //final Button toggVis = (Button) findViewById(R.id.toggle_visibility);
        //toggVis.setVisibility(View.INVISIBLE);

        //StartAndBindService();

        /*recTime = (TextView) findViewById(R.id.recTime);
        threadRecTime = new Thread() {
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long now = System.currentTimeMillis();
                            recTime.setText(String.format("%2d:%02d:%02ds", (now - takeoffTime) / 3600000, ((now - takeoffTime) / 60000) % 60, ((now - takeoffTime) / 1000) % 60));

                        }
                    });
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };*/
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        //chronometer.setTextColor(getResources().getColor(R.color.md_white_1000));


        btnFlip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //ActivityInfo.CONFIG_SCREEN_LAYOUT
                //LayoutParams params = layout.getLayoutParams();
            }
        });


        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //ToggleLogging();
                if (!isRecording) {
                    //takeoffTime= System.currentTimeMillis();
                    //EventBus.getDefault().post(new CommandEvents.RequestStartStop(true));
                    mCamera = Camera.open(findBackFacingCamera());
                    //mPreview = new CameraPreview(myContext, mCamera);

                    //surfaceViewParent.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.GONE);
                    //container.setVisibility(View.GONE);

                    camServintent = new Intent(MainActivity.this, RecorderService.class);
                    recService = new RecorderService();
                    bindService(camServintent, mConnection, Context.BIND_AUTO_CREATE);


                    EventBus.getDefault().post(new CommandEvents.RequestStartStop(true));

                    isRecording = true;
                    btnStart.setText("STOP");
                    //toggVis.setVisibility(View.VISIBLE);
                    surfaceViewParent.setVisibility(View.INVISIBLE);
                    led.setImageResource(R.drawable.led_on);

                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    //threadRecTime.start();
                    //long now = System.currentTimeMillis();
                    //canvas.drawText(String.format("%2d:%02d:%02ds", (now - takeoffTime) / 3600000, ((now - takeoffTime) / 60000) % 60, ((now - takeoffTime) / 1000) % 60), 10, 40*2, p); // time since takeoff

                } else {
                    EventBus.getDefault().post(new CommandEvents.RequestStartStop(false));
                    //stopService(new Intent(MainActivity.this, RecorderService.class));
                    unbindService(mConnection);
                    stopService(camServintent);
                    //StopAndUnbindServiceIfRequired();

                    isRecording = false;
                    btnStart.setText("REC");
                    //toggVis.setVisibility(View.INVISIBLE);
                    led.setImageResource(R.drawable.led_off2);

                   /* try {
                        threadRecTime.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    //recTime.setText("00:00:00");
                    //takeoffTime = 0;
                    chronometer.stop();
                }

                //TODO ToggleLogging();

            }
        });

        /*toggVis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (surfaceViewParent.getVisibility() == View.GONE || surfaceViewParent.getVisibility() == View.INVISIBLE) {
                    //surfaceViewParent.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.GONE);
                    //container.setVisibility(View.GONE);
                } else {
                    surfaceViewParent.setVisibility(View.INVISIBLE);
                    tv.setText("RADDD");
                    tv.setVisibility(View.VISIBLE);
                    container.setVisibility(View.VISIBLE);

              }
          }
        });*/
        //EventBus.getDefault().postSticky(new CommandEvents.RequestStartStop(true));

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        StopAndUnbindServiceIfRequired();

        UnregisterEventBus();
        if(isRecording){
            unbindService(mConnection);
            stopService(camServintent);
        }


        super.onDestroy();
        //UnregisterEventBus();
    }
    @Override
    protected void onPause() {
        super.onPause();
        lm.removeUpdates(this);
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onResume() {

        if(Session.isStarted()){
            //setActionButtonStop();
        }
        else {
            //setActionButtonStart();
        }

        //ShowPreferencesAndMessages();
        super.onResume();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_GAME);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //drawerToggle.syncState();
    }

    private int findBackFacingCamera() {
        int cameraId = -1;
        //Search for the back facing camera
        //get the number of cameras
        int numberOfCameras = Camera.getNumberOfCameras();
        //for every camera check
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                break;
            }
        }
        return cameraId;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == android.view.KeyEvent.KEYCODE_VOLUME_DOWN) || (keyCode == android.view.KeyEvent.KEYCODE_VOLUME_UP)){
            FragmentManager fm = getSupportFragmentManager();
            MenuFrag fragment = (MenuFrag) fm.findFragmentById(R.id.menuContainer);
            fragment.myOnKeyDown(keyCode);
        }
        return true;
    }



    // ----------------GPX-----------------------------------------------------------

    public void ToggleLogging(){
        EventBus.getDefault().post(new CommandEvents.RequestToggle());
    }

    private final ServiceConnection gpsServiceConnection = new ServiceConnection() {

        public void onServiceDisconnected(ComponentName name) {
            Log.d("DEbugg 101", "gpsLoggONDISCONNECTED");

            //tracer.debug("Disconnected from GPSLoggingService from MainActivity");
            //stopService(new Intent(MainActivity.this, GpsLoggingService.class));
            //gpslService = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            //tracer.debug("Connected to GPSLoggingService from MainActivity");

            //gpslService = ((GpsLoggingService.GpsLoggingBinder) service).getService();
        }
    };

    private void  StartAndBindService() {
        Log.d("DEbugg 101", "StartAndBInd");
        serviceIntent = new Intent(this, GpsLoggingService.class);
        gpslService = new GpsLoggingService("GpsLogService");
        // Start the service in case it isn't already running
        startService(serviceIntent);
        // Now bind to service
        bindService(serviceIntent, gpsServiceConnection, Context.BIND_AUTO_CREATE);
        Session.setBoundToService(true);
    }

    private void StopAndUnbindServiceIfRequired() {
        if (Session.isBoundToService()) {
            Log.d("DEbugg 101", "StopandaUnbindifReq");
            try {
                unbindService(gpsServiceConnection);
                //unbindService(mConnection);
                Session.setBoundToService(false);
            } catch (Exception e) {
                Log.d("DEbugg 101", "CouldNotUNBINDSERVICE");
                //tracer.warn(SessionLogcatAppender.MARKER_INTERNAL, "Could not unbind service", e);
            }
        }

        if (!Session.isStarted()) {
            Log.d("DEbugg 101","Stopping the service");
            try {
                stopService(serviceIntent);
                //stopService(camServintent);

            } catch (Exception e) {
                //tracer.error("Could not stop the service", e);
                Log.d("DEbugg 101", "CouldNOTSTOPService");
            }
        }
    }

    private void UnregisterEventBus(){
        try {
            //EventBus.getDefault().unregister(this);
        } catch (Throwable t){
            //this may crash if registration did not go through. just be safe
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        if(!hasInitialLoc){
            if(location.getAccuracy()>0 && location.getAccuracy()<=10){
                hasInitialLoc = true;
                initialLoc = location;
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        initialBP = event.values[0];
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
