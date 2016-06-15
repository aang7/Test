package com.raddapp.radika.raddappv001;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raddapp.radika.customgauge.CustomGauge;
import com.raddapp.radika.views.altimeter.AttitudeIndicator;
import com.raddapp.radika.views.altimeter.Orientation;

/**
 * Created by greenapsis on 6/05/16.
 */
public class BearingToStart extends Fragment implements Orientation.Listener {

    View rootView;
    private AttitudeIndicator attitudeIndicator;

    private SensorManager sm;
    private Orientation orientation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_beargintostart,null);

        attitudeIndicator = (AttitudeIndicator)rootView.findViewById(R.id.attitude_indicator);
        orientation = new Orientation(getActivity());
        orientation.startListening(this);

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        orientation.stopListening();
    }

    @Override
    public void onOrientationChanged(float pitch, float roll) {
        attitudeIndicator.setAttitude(pitch, roll);
    }
}
