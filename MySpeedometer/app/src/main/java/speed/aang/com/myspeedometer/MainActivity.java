package speed.aang.com.myspeedometer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Formatter;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements IBaseGpsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        this.updateSpeed(null);

        CheckBox chkUseMetricUnits = (CheckBox) findViewById(R.id.chkMetricUnits);
        chkUseMetricUnits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                MainActivity.this.updateSpeed(null);
            }

        });
    }

    public void finish(){
        super.finish();
        System.exit(0);
    }


    //TODO
    private void updateSpeed(CLocation location) {
        float nCurrentSpeed = 0;

        if (location != null){

            location.setUseMetricUnits(this.useMetricUnits());
            nCurrentSpeed = location.getSpeed();
        }

        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US, "%5.10f", nCurrentSpeed);
        String strCurrentSpeed = fmt.toString();
        strCurrentSpeed = strCurrentSpeed.replace(' ', '0');

        String strUnits = "millas/hora";

        if (this.useMetricUnits())
            strUnits = "Metros/segundo";

        TextView txtCurrentSpeed = (TextView) this.findViewById(R.id.txtCurrentSpeed);
        txtCurrentSpeed.setText(strCurrentSpeed + "m/s");
    }

    private boolean useMetricUnits(){
        CheckBox chkUseMetricUnits = (CheckBox) this.findViewById(R.id.chkMetricUnits);
        return chkUseMetricUnits.isChecked();
    }

    @Override
    public void onLocationChanged(Location location){

        TextView txtview = (TextView) findViewById(R.id.textview);
        txtview.setText(" " + location.getSpeed());

        if (location != null){
            //System.out.println(location.getSpeed());

        //    Log.d("Main act speed",""+location.getSpeed());
            CLocation myLocation = new CLocation(location, this.useMetricUnits());
            this.updateSpeed(myLocation);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGpsStatusChanged(int event) {
        // TODO Auto-generated method stub

    }
}
