package com.magnometer.abel.magnometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class MagnometerActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;


    //private TextView txtViewZ;
    //Muestra el grado actual
    private TextView showDegree;

    //Objeto que utilizare para la imagen del compass
    //private ImageView imgCompass;

    // Guarda el angulo o grado actual del compass
    private int currentDegree = 0;

    // Los angulos del movimiento de la flecha que señala al norte
    int degree = 0;
    //float degree2 = 0f;
    // Guarda el valor del azimut
    //float azimut = 0f;
    // Guarda los valores que cambián con las variaciones del sensor TYPE_ACCELEROMETER
    float[] mGravity;// existing height is ok as is, no need to edit it
    // Guarda los valores que cambián con las variaciones del sensor TYPE_MAGNETIC_FIELD
    float[] mGeomagnetic;

    CustomGauge gauge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnometer);

        //imgCompass = (ImageView) findViewById(R.id.Compass);//La imagen que utillizare
        
        showDegree = (TextView) findViewById(R.id.txtview);

        gauge = (CustomGauge) findViewById(R.id.gauge1);

        //Se inicializan sensores
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//<- Administrador de sensores
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);/*Me apropio del servicio*/
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);/*del magnetometro y acelerometro*/

        //txtViewZ = (TextView) findViewById(R.id.z);

        //Inicializando arrays
        mGravity = null;
        mGeomagnetic = null;

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        /*
        //Viendo que sensor esta siendo "activado"
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = sensorEvent.values;
        }

        else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = sensorEvent.values;

        if(mGravity != null && mGeomagnetic != null) {

            float[] rotationMatrix = new float[19];
            float[] I = new float[19];
            if (SensorManager.getRotationMatrix(rotationMatrix, I, mGravity, mGeomagnetic)) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(rotationMatrix, orientation);

                azimut = (float) (orientation[0] * (180 / Math.PI)); //Aun no estoy seguro que es el azimut
                //txtViewZ.setText("Z: " + Float.toString(azimut));
            }
        }

        degree2 = -azimut;
*/
        int degree = (Math.round(sensorEvent.values[0]));
        showDegree.setText("Angulo: " + Float.toString(degree) + "°");
      /*RotateAnimation animacion = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animacion.setDuration(250);
        animacion.setFillAfter(true);
        imgCompass.startAnimation(animacion);*/
        //currentDegree = -;

        gauge.setValue((int)currentDegree);
        currentDegree = -degree;
        gauge.getValue();
    }

    //NO LA USO
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //Cuando esta en focus la aplicacion
    protected void onResume() {
        super.onResume();
        /*mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);*/
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);

    }

    protected void onPause() {
        super.onPause();
        //Dejames de seguir el track a los sensores, así no malgastamos recursos ni batería
        mSensorManager.unregisterListener(this);

    }


}
