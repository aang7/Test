package com.raddapp.radika.raddappv001;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.raddapp.radika.customgauge.CustomGauge;

/**
 * Created by edd on 3/18/2015.
 */
public class Frag03 extends Fragment {


    private static final String TAG = "Frag03";
    private VarioView surface;
    LocationManager lm;

    Bitmap walk,run,car;

    // Speedometer internal state
    private float mMaxSpeed=0;
    private float mCurrentSpeed=0;
    private int min=0;
    private int seg=0;
    private int gear=0;

    // Scale drawing tools
    private Paint onMarkPaint;
    private Paint offMarkPaint;
    private Paint scalePaint;
    private Paint readingPaint;
    private Path onPath;
    private Path offPath;
    final RectF oval = new RectF();

    // Drawing colors
    private int ON_COLOR = Color.argb(255, 0xff, 0xA5, 0x00);
    private int OFF_COLOR = Color.argb(255, 0x3e, 0x3e, 0x3e);
    private int SCALE_COLOR = Color.argb(255, 255, 255, 255);
    private float SCALE_SIZE = 14f;
    private float READING_SIZE = 60f;

    // Scale configuration
    private float centerX;
    private float centerY;
    private float radius;

    private CustomGauge speedGauge;
    private int currentSpeed;
    private int mInterval = 500; // 5 seconds by default, can be changed later
    private Handler mHandler;
    TextView text1;
    View rootView;
    public Frag03(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toast.makeText(getActivity(), "Waiting for Lock", Toast.LENGTH_SHORT).show();

        rootView = inflater.inflate(R.layout.fragment_multiple_frag03,container, false );

        //surface = (VarioView) rootView.findViewById(R.id.surface2);
        //surface.setZOrderOnTop(true);

        lm =(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        walk = BitmapFactory.decodeResource(getResources(), R.drawable.walk);
        run= BitmapFactory.decodeResource(getResources(), R.drawable.run);
        car = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        //initDrawingTools();

        speedGauge = (CustomGauge) rootView.findViewById(R.id.gauge1);

        //speedGauge.setValue(150);
        currentSpeed=0;

        mHandler = new Handler();


        text1 = (TextView) rootView.findViewById(R.id.textView3);

        startRepeatingTask();
        return rootView;
    }


    Runnable updateSpeed = new Runnable() {
        @Override
        public void run() {
            try {
                //runUpdate(); //this function can change value of mInterval.
                speedGauge.setValue(currentSpeed);
                //speedGauge.setValue(200);
                text1.setText(Integer.toString(speedGauge.getValue()));
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(updateSpeed, mInterval);
            }
        }
    };

    public void runUpdate() {
        try {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    speedGauge.setValue(currentSpeed);
                    text1.setText(Integer.toString(speedGauge.getValue())+"km/h");

                }
            });
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void startRepeatingTask() {
        updateSpeed.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(updateSpeed);
    }

    @Override
    public void onResume() {
        super.onResume();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopRepeatingTask();
        lm.removeUpdates(myLocationListener);
    }

    /*private void initDrawingTools(){
        onMarkPaint = new Paint();
        onMarkPaint.setStyle(Paint.Style.STROKE);
        onMarkPaint.setColor(ON_COLOR);
        onMarkPaint.setStrokeWidth(35f);
        onMarkPaint.setShadowLayer(5f, 0f, 0f, ON_COLOR);
        onMarkPaint.setAntiAlias(true);

        offMarkPaint = new Paint(onMarkPaint);
        offMarkPaint.setColor(OFF_COLOR);
        offMarkPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        offMarkPaint.setShadowLayer(0f, 0f, 0f, OFF_COLOR);

        scalePaint = new Paint(offMarkPaint);
        scalePaint.setStrokeWidth(2f);
        scalePaint.setTextSize(SCALE_SIZE);
        scalePaint.setShadowLayer(5f, 0f, 0f, Color.RED);
        scalePaint.setColor(SCALE_COLOR);

        readingPaint = new Paint(scalePaint);
        readingPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        offMarkPaint.setShadowLayer(3f, 0f, 0f, Color.WHITE);
        readingPaint.setTextSize(65f);
        readingPaint.setTypeface(Typeface.SANS_SERIF);
        readingPaint.setColor(Color.WHITE);

        onPath = new Path();
        offPath = new Path();
    }*/

    private LocationListener myLocationListener
            = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //updateData(location);

            currentSpeed = (int) (location.getSpeed()*3.6);

            //updateView();
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
    private void updateView() {
        SurfaceHolder holder = surface.getHolder();
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            Log.d(TAG, "Surface not initialized");
        } else {
            float w = canvas.getWidth(),
                    h = canvas.getHeight(),
                    centerX = w / 2,
                    centerY = h / 2;

            if (w > h){
                radius = h/4;
            }else{
                radius = w/4;
            }
            oval.set(centerX - radius,
                    centerY - radius,
                    centerX + radius,
                    centerY + radius);

           /* Paint p = new Paint();
            p.setTextAlign(Paint.Align.CENTER);

            p.setColor(Color.BLACK);
            canvas.drawRect(0, 0, w, h, p); // clear the screen

            // draw a circle, which is to become our compass rose
            p.setColor(Color.LTGRAY);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(15);
            //canvas.drawOval(new RectF(cx - 70 * 3, cy - 70 * 3, cx + 70 * 3, cy + 70 * 3), p);
            canvas.drawArc(new RectF(cx - 70 * 3, cy - 70 * 3, cx + 70 * 3, cy + 70 * 3),(float)135,(float)270,false,p);
       */
            canvas.drawARGB(255, 0, 0, 0);
            Log.d(TAG, "drawScaleBackground");
            offPath.reset();
            for(int i = -180; i < 0; i+=4){
                offPath.addArc(oval, i, 2f);
            }
            canvas.drawPath(offPath, offMarkPaint);

            onPath.reset();
            for(int i = -180; i < (mCurrentSpeed/mMaxSpeed)*180 - 180; i+=4){
                onPath.addArc(oval, i, 2f);
            }
            canvas.drawPath(onPath, onMarkPaint);

            canvas.save(Canvas.MATRIX_SAVE_FLAG);
            canvas.rotate(-180, centerX,centerY);
            Path circle = new Path();
            double halfCircumference = radius * Math.PI;
            double increments = 20;
            for(int i = 0; i < this.mMaxSpeed; i += increments){
                circle.addCircle(centerX, centerY, radius, Path.Direction.CW);
                canvas.drawTextOnPath(String.format("%d", i),
                        circle,
                        (float) (i*halfCircumference/this.mMaxSpeed),
                        -30f,
                        scalePaint);
            }

            canvas.restore();

            Path path = new Path();
            //String message = String.format("%d", (int)this.mCurrentSpeed);
            String message = min+" : ";

            if(this.mCurrentSpeed<0.4)
            {
                message = "∞";
            }
            else{
                if(this.seg>9)
                    message+=this.seg;
                else
                    message+="0"+this.seg;
                if(gear==0){
                    walk = Bitmap.createScaledBitmap(walk, 100, 100, true);
                    canvas.drawBitmap(walk,centerX-centerX/10, centerY-centerY/3, null);
                }
                else if(gear == 1){
                    run = Bitmap.createScaledBitmap(run, 100, 100, true);
                    canvas.drawBitmap(run,centerX-centerX/10, centerY-centerY/3, null);
                }
                else{
                    car = Bitmap.createScaledBitmap(car, 100, 100, true);
                    canvas.drawBitmap(car,centerX-centerX/10, centerY-centerY/3, null);
                }
            }
            float[] widths = new float[message.length()];
            readingPaint.getTextWidths(message, widths);
            float advance = 0;
            for(double width:widths)
                advance += width;
            Log.d(TAG, "advance: " + advance);
            path.moveTo(centerX - advance/2, centerY);
            path.lineTo(centerX + advance/2, centerY);
            canvas.drawTextOnPath(message, path, 0f, 0f, readingPaint);
           /* p.setColor(Color.RED);
            p.setStrokeWidth(30);
            RectF oval = new RectF(cx - 70 * 3, cy - 70 * 3, cx + 70 * 3, cy + 70 * 3);
            Path path = new Path();
            path.reset();
            for(int i = -180; i < 0; i+=4){
                path.addArc(oval, i, 2f);
            }
            canvas.drawPath(path, p);
            //canvas.drawArc(new RectF(cx - 55 * 3, cy - 55 * 3, cx + 55 * 3, cy + 55 * 3),(float)135,(float)270,false,p);
            //canvas.drawOval(new RectF(cx - 55 * 3, cy - 55 * 3, cx + 55 * 3, cy + 55 * 3), p);
              */
            holder.unlockCanvasAndPost(canvas);
           // Log.d(TAG,"wenkwenk "+advance);
        }
    }
    public void updateData(Location loc){
        min=0;
        seg=0;
        double nCurrentSpeed = 0;
        if(loc != null){
            nCurrentSpeed =  (loc.getSpeed());
            //nCurrentSpeed =  10;
            if(nCurrentSpeed != 0) {
                double aux = 16.666666666667  / nCurrentSpeed;
                min = (int) aux;
                seg = (int) (aux % 1 * 60);
                if(min>=6){
                    if(gear!=0){
                        mMaxSpeed = (float) 2.8;
                        onMarkPaint.setColor(Color.argb(255, 0x0, 0xff, 0x00));
                        gear=0;
                    }
                }
                else if(min<6 && min > 3){
                    if(gear!=1){
                        mMaxSpeed = (float) 6;
                        onMarkPaint.setColor(Color.argb(255, 0xff, 0xA5, 0x00));
                        gear=1;
                    }
                }
                else{
                    mMaxSpeed = (float) 55;
                    onMarkPaint.setColor(Color.argb(255, 0xff, 0x00, 0x00));
                    gear=2;
                }
            }
            Log.d(TAG, "wenkwenk " + loc.getSpeed());
            setCurrentSpeed(loc.getSpeed());
            //updateView();
        }
    }
    public void setCurrentSpeed(float mCurrentSpeed) {
        if(mCurrentSpeed > this.mMaxSpeed)
            this.mCurrentSpeed = mMaxSpeed;
        else if(mCurrentSpeed < 0)
            this.mCurrentSpeed = 0;
        else
            this.mCurrentSpeed = mCurrentSpeed;
    }
    public void onSpeedChanged(float newSpeedValue) {
        if(newSpeedValue != 0) {
            double aux = 16.666666666667  / newSpeedValue;
            min = (int) aux;
            seg = (int) (aux % 1 * 60);
            if(min>=6){
                if(gear!=0){
                    mMaxSpeed = (float) 2.8;
                    onMarkPaint.setColor(Color.argb(255, 0x0, 0xff, 0x00));
                    gear=0;
                }
            }
            else if(min<6 && min > 3){
                if(gear!=1){
                    mMaxSpeed = (float) 6;
                    onMarkPaint.setColor(Color.argb(255, 0xff, 0xA5, 0x00));
                    gear=1;
                }
            }
            else{
                mMaxSpeed = (float) 55;
                onMarkPaint.setColor(Color.argb(255, 0xff, 0x00, 0x00));
                gear=2;
            }
        }
        setCurrentSpeed(newSpeedValue);
        //updateView();
    }
}
