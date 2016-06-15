package com.raddapp.radika.HeartRate;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.raddapp.radika.customgauge.CustomGauge;
import com.raddapp.radika.raddappv001.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by greenapsis on 25/04/16.
 */
public class HRFragment extends Fragment implements OnItemSelectedListener, Observer {


    private int MAX_SIZE = 60; //graph max size
    boolean searchBt = true;
    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> pairedDevices;
    boolean menuBool = false; //display or not the disconnect option
    //private XYPlot plot;
    //Tracker t;//Set the Tracker
    boolean h7 = false; //Was the BTLE tested
    boolean normal = false; //Was the BT tested
    private Spinner spinner1;
    View rootView;
    private CustomGauge hrGauge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        Log.i("Main Activity", "Starting Polar HR monitor main activity");
        DataHandler.getInstance().addObserver(this);

        rootView = inflater.inflate(R.layout.fragment_hrfrag,null);
        setHasOptionsMenu(true);

        hrGauge = (CustomGauge) rootView.findViewById(R.id.gauge1);

        //Verify if device is to old for BTLE
        if(	android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2){

            Log.i("Main Activity", "old device H7 disbled");
            h7=true;
        }

        //verify if bluetooth device are enabled
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(DataHandler.getInstance().newValue){
            //Verify if bluetooth if activated, if not activate it
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!mBluetoothAdapter.isEnabled()) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.bluetooth)
                        .setMessage(R.string.bluetoothOff)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mBluetoothAdapter.enable();
                                try {Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                listBT();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                searchBt = false;
                            }
                        })
                        .show();
            }
            else{
                listBT();
            }
            DataHandler.getInstance().setNewValue(false);

        }
        else
        {
            listBT();
        }


        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        DataHandler.getInstance().deleteObserver(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_hrfrag, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("Main Activity", "Menu pressed");
        if (id == R.id.action_settings) { //close connection
            menuBool=false;
            Log.d("Main Activity", "disable pressed");
            if(spinner1!=null){
                spinner1.setSelection(0);
            }
            if(DataHandler.getInstance().getReader()==null)
            {
                Log.i("Main Activity", "Disabling h7");
                DataHandler.getInstance().getH7().cancel();
                DataHandler.getInstance().setH7(null);
                h7=false;
            }
            else{
                Log.i("Main Activity", "Disabling BT");
                DataHandler.getInstance().getReader().cancel();
                DataHandler.getInstance().setReader(null);
                normal=false;
            }
            return true;
        }
        /*else if (id==R.id.about){ //about menu

            Log.i("Main Activity", "opening about");
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void listBT() {
        Log.d("Main Activity", "Listing BT elements");
        if(searchBt){
            //Discover bluetooth devices
            List<String> list = new ArrayList<String>();
            list.add("");
            pairedDevices = mBluetoothAdapter.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListView
                    list.add(device.getName() + "\n" + device.getAddress());
                }
            }
            //Populate drop down
            spinner1 = (Spinner) rootView.findViewById(R.id.spinner1);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setOnItemSelectedListener(this);
            spinner1.setAdapter(dataAdapter);

            if(DataHandler.getInstance().getID()!=0 && DataHandler.getInstance().getID() < spinner1.getCount())
                spinner1.setSelection(DataHandler.getInstance().getID());
        }
    }


    @Override
    public void update(Observable observable, Object data) {
        receiveData();
    }

    public void receiveData(){
        //ANALYTIC
        //t.setScreenName("Polar Bluetooth Used");
        //t.send(new HitBuilders.AppViewBuilder().build());

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                //menuBool=true;
                TextView rpm = (TextView) rootView.findViewById(R.id.rpm);
                rpm.setText(DataHandler.getInstance().getLastValue());

                /*if (DataHandler.getInstance().getLastValue() != 0) {
                    DataHandler.getInstance().getSeries1().addLast(0, DataHandler.getInstance().getLastValue());
                    if (DataHandler.getInstance().getSeries1().size() > MAX_SIZE)
                        DataHandler.getInstance().getSeries1().removeFirst();//Prevent graph to overload data.
                    //plot.redraw();
                }*/

                /*TextView min = (TextView) rootView.findViewById(R.id.min);
                min.setText("Min " + DataHandler.getInstance().getMin() + " BPM");

                TextView avg = (TextView) rootView.findViewById(R.id.avg);
                avg.setText("Avg " + DataHandler.getInstance().getAvg() + " BPM");

                TextView max = (TextView) rootView.findViewById(R.id.max);
                max.setText("Max " + DataHandler.getInstance().getMax() + " BPM");
                */
                hrGauge.setValue(DataHandler.getInstance().getLastValue());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
        if(arg2!=0){
            //Actual work
            DataHandler.getInstance().setID(arg2);
            if(!h7 && ((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID()-1]).getName().contains("H7") && DataHandler.getInstance().getReader()==null)
            {

                Log.i("Main Activity", "Starting h7");
                DataHandler.getInstance().setH7(new H7ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID()-1], this));
                h7=true;
            }
            else if (!normal && DataHandler.getInstance().getH7()==null){

                Log.i("Main Activity", "Starting normal");
                DataHandler.getInstance().setReader(new ConnectThread((BluetoothDevice) pairedDevices.toArray()[arg2-1], this));
                DataHandler.getInstance().getReader().start();
                normal=true;
            }
            menuBool=true;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void connectionError(){

        Log.w("Main Activity", "Connection error occured");
        if(menuBool==true){//did not manually tried to disconnect
            Log.d("Main Activity", "in the app");
            menuBool=false;
            final HRFragment ac = this;
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), getString(R.string.couldnotconnect), Toast.LENGTH_SHORT).show();
                    //TextView rpm = (TextView) findViewById(R.id.rpm);
                    //rpm.setText("0 BMP");
                    Spinner spinner1 = (Spinner) rootView.findViewById(R.id.spinner1);
                    if (DataHandler.getInstance().getID() < spinner1.getCount())
                        spinner1.setSelection(DataHandler.getInstance().getID());

                    if (h7 == false) {

                        Log.w("Main Activity", "starting H7 after error");
                        DataHandler.getInstance().setReader(null);
                        DataHandler.getInstance().setH7(new H7ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1], ac));
                        h7 = true;
                    } else if (normal == false) {
                        Log.w("Main Activity", "Starting normal after error");
                        DataHandler.getInstance().setH7(null);
                        DataHandler.getInstance().setReader(new ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1], ac));
                        DataHandler.getInstance().getReader().start();
                        normal = true;
                    }
                }
            });
        }
    }
}
