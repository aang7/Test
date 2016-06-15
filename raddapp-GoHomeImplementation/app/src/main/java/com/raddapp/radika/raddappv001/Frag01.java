package com.raddapp.radika.raddappv001;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.raddapp.radika.customgauge.CustomGauge;

/**
 * Created by edd on 3/18/2015.
 */
public class Frag01 extends Fragment implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;
    private CustomGauge tempGauge;
    volatile float degree;
    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;


    TextView text1;
    View rootView;

    public Frag01(){

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //getWindow().getDecorView().getRootView().setBackgroundColor(Color.argb(255, 0x0, 0x0, 0x0));


        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        degree = 0;

        rootView = inflater.inflate(R.layout.fragment_multiple_frag01,null);
        //image = (ImageView) rootView.findViewById(R.id.imageViewCompass);

        // TextView that will tell the user what degree is he heading
       // tvHeading = (TextView) rootView.findViewById(R.id.tvHeading);
        text1 = (TextView) rootView.findViewById(R.id.textView1);
        tempGauge = (CustomGauge) rootView.findViewById(R.id.gauge1);




        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        degree = Math.round(event.values[0]);

        new Thread() {
            public void run() {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tempGauge.setValue((int) degree);
                            text1.setText(Integer.toString(tempGauge.getValue())+"°");

                        }
                    });
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
