package com.raddapp.radika.raddappv001;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.raddapp.radika.customgauge.AltimeterGauge;
import com.raddapp.radika.customgauge.CustomGauge;

import java.sql.BatchUpdateException;

import static java.lang.Math.pow;

/**
 * Created by edd on 3/18/2015.
 */
public class Frag02 extends Fragment implements SensorEventListener {
    private static final String TAG = "Frag02";
    View rootView;
    LocationManager lm;
    TextView baroPrTV, baroAltTV, altGPS;
    private AltimeterGauge altGauge;

    private Data data = new Data();
    private VarioView surface;
    private SensorManager mSensorManager;
    private Sensor magSensor;
    private Sensor accSensor;

    volatile float bp;
    float initialBP;


    float prueba;

    Button increase, decrease;

    private Location firstLocation;
    private Location secondLocation;

    private CustomGauge gauge;
    private CustomGauge gagueAtLocation;

    private TextView firstLocationTextView;
    private TextView secondLocationTextView;
    private TextView bearingToAngleTextView;
    private TextView distanceTextView;
    private TextView accuracy;

    private GeomagneticField geoField;

    private double heading;
    private double bearing;
    private double distance;

    private float[] magValues;
    private float[] accValues;

    public Frag02(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_multiple_frag02,null);
        initialBP = MainActivity.initialBP;
        secondLocation = MainActivity.initialLoc;
        //surface = (VarioView) rootView.findViewById(R.id.surface);
        baroPrTV = (TextView) rootView.findViewById(R.id.bpTv);
        baroAltTV = (TextView) rootView.findViewById(R.id.altBaro);
        altGPS  = (TextView) rootView.findViewById(R.id.altGPS);
        //surface.setZOrderOnTop(true);
        lm =(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
        altGauge = (AltimeterGauge)rootView.findViewById(R.id.altgauge);
        bp=0;
        prueba=0;

        firstLocationTextView = (TextView)rootView.findViewById(R.id.firstLocation);
        secondLocationTextView = (TextView)rootView.findViewById(R.id.secondLocation);
        bearingToAngleTextView = (TextView)rootView.findViewById(R.id.bearingToAngle);
        distanceTextView = (TextView)rootView.findViewById(R.id.distance);
        accuracy = (TextView)rootView.findViewById(R.id.accuracy);
        gauge = (CustomGauge)rootView.findViewById(R.id.gauge);
        gauge.bringToFront();
        gagueAtLocation = (CustomGauge)rootView.findViewById(R.id.gaugeAtLocation);

        increase = (Button)rootView.findViewById(R.id.increase);
        decrease = (Button)rootView.findViewById(R.id.decrease);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prueba+=100;
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prueba-=100;
            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //getWindow().getDecorView().getRootView().setBackgroundColor(Color.argb(255, 0x0, 0x0, 0x0));


        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        magSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private float normalizeDegree(float value) {
        if (value >= 0.0f && value <= 180.0f) {
            return value;
        } else {
            return 180 + (180 + value);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_GAME);

        mSensorManager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        lm.removeUpdates(myLocationListener);
        mSensorManager.unregisterListener(this);
    }

    private LocationListener myLocationListener
            = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Log.v(TAG, "On location changed");

            /*if (secondLocation == null){
                secondLocation = location;
            }*/

            if(MainActivity.initialLoc==null)
                return;

            firstLocation = location;
            bearing = normalizeDegree(firstLocation.bearingTo(MainActivity.initialLoc));
            distance = firstLocation.distanceTo(MainActivity.initialLoc);
            Log.d("bearing",""+bearing);
            Log.d("distance", "" + distance);

            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        altGPS.setText(Double.toString(location.getAltitude())+" mts");

                    }
                });
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_PRESSURE){
            bp = event.values[0];
            Log.d("pressure", bp+"");

        }
        new Thread() {
            public void run() {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            float mtrs;
                            float feet;
                            mtrs = (float) (44330.0 * (1 - pow(bp / initialBP, 1 / 5.255))); //1013.25 bp at sealvl
                            feet = mtrs*3.28084f;
                            baroPrTV.setText(Float.toString(bp) + " mb");
                            if(feet<1000)
                                baroAltTV.setText(String.format("%.0f", feet));  //930
                            else
                                baroAltTV.setText(String.format("%.1f", feet/1000));
                            altGauge.setValue((int) (feet));
                        }
                    });
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magValues = event.values;
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accValues = event.values;
        }

        if (magValues != null && accValues != null){
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R,I,accValues,magValues);
            if (success){
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);

                heading = normalizeDegree((float) Math.toDegrees(orientation[0]));

                if (MainActivity.initialLoc != null && firstLocation != null){

                    geoField = new GeomagneticField(
                            Double.valueOf(firstLocation.getLatitude()).floatValue(),
                            Double.valueOf(firstLocation.getLongitude()).floatValue(),
                            Double.valueOf(firstLocation.getAltitude()).floatValue(),
                            System.currentTimeMillis()
                    );

                    geoField.getDeclination();
                    Log.d("heading", ""+heading);
                    Log.d("declination", ""+geoField.getDeclination());

                    double result = (float) (bearing - (heading + geoField.getDeclination())) - 90;// -90 to compensate the azimuth from portrait to landscape

                    if (distance < 5){
                        //gagueAtLocation.bringToFront(); <- AQUI ES DONDE VA EL CAMBIO DE COLOR AL POINTER
                        gauge.setNewPointColor();
                        bearingToAngleTextView.setText("at your location");
                        firstLocationTextView.setText(firstLocation.getLatitude() + "," + firstLocation.getLongitude());
                        secondLocationTextView.setText(MainActivity.initialLoc.getLatitude()+","+MainActivity.initialLoc.getLongitude());
                        distanceTextView.setText("distance: " + distance);
                    } else {
                        gauge.bringToFront();
                        gauge.setValue((int) result);
                        bearingToAngleTextView.setText(""+result);
                        firstLocationTextView.setText(firstLocation.getLatitude() + "," + firstLocation.getLongitude());
                        secondLocationTextView.setText(MainActivity.initialLoc.getLatitude()+","+MainActivity.initialLoc.getLongitude());
                        distanceTextView.setText("distance: " + distance);
                    }
                    accuracy.setText("accuracy: "+firstLocation.getAccuracy());
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
