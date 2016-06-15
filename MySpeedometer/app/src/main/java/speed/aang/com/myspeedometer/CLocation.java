package speed.aang.com.myspeedometer;

import android.location.Location;

import java.security.PublicKey;

/**
 * Created by abel on 13/06/16.
 */
public class CLocation extends Location {

    private boolean bUseMetricUnits = false;

    //Constructores
    public CLocation(Location location){
        this(location, true);
    }

    public CLocation(Location location, boolean bUseMetricUnits){
        super(location);
        this.bUseMetricUnits = bUseMetricUnits;
    }


    @Override
    public float getAccuracy(){
        float nAccuracy = super.getAccuracy();
        if (!this.getUseMetricUnits())
            nAccuracy = nAccuracy * 3.28083989501312f; //Convierte metros a pies

        return nAccuracy;
    }

   /* @Override
    public double getAltitude(){

        double nAltitude = super.getAltitude();

        if (!this.getUseMetricUnits())
            nAltitude = nAltitude * 3.28083989501312d;

        return nAltitude;
    }*/


    @Override
    public float getSpeed(){
        float nSpeed = super.getSpeed();
        /*if (!this.getUseMetricUnits())
            nSpeed = nSpeed * 2.2369362920544f/3.6f; //Convierte m/s a millas/hour*/
        return nSpeed;
    }

    public boolean getUseMetricUnits() {
        return false;
    }

    public void setUseMetricUnits(boolean useMetricUnits) {
        //this.useMetricUnits = useMetricUnits;
    }
}
