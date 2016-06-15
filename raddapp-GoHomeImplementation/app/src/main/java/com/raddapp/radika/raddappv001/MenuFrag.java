package com.raddapp.radika.raddappv001;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.raddapp.radika.HeartRate.HRFragment;
import com.raddapp.radika.raddappv001.Frag01;
import com.raddapp.radika.raddappv001.Frag02;
import com.raddapp.radika.raddappv001.Frag03;
import com.raddapp.radika.raddappv001.R;

/**
 * Created by edd on 3/18/2015.
 */
public class MenuFrag extends Fragment {

    Fragment frag;
    FragmentTransaction fragTran;
    int currentFrag=0;
    public MenuFrag(){

    }

    public void myOnKeyDown(int key_code){
        if ((key_code == android.view.KeyEvent.KEYCODE_VOLUME_DOWN)){
            if(currentFrag>0){
                currentFrag--;
                refreshfrag(currentFrag);
            }
        }else if((key_code == android.view.KeyEvent.KEYCODE_VOLUME_UP)){
            if(currentFrag<4){
                currentFrag++;
                refreshfrag(currentFrag);
            }
        }
    }
    private void refreshfrag(int current){
        switch (current){
            case 0: frag = new Frag01(); break;
            case 1: frag = new Frag02(); break;
            case 2: frag = new Frag03(); break;
            case 3: frag = new HRFragment(); break;
            case 4: frag = new BearingToStart(); break;
        }
        fragTran = getFragmentManager().beginTransaction().replace(R.id.container, frag);
        fragTran.commit();
    }
   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == android.view.KeyEvent.KEYCODE_VOLUME_DOWN)){
            //Toast.makeText(this, "Vol up", Toast.LENGTH_SHORT).show();
        }
        return true;
    }*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.menu_multi, container, false);

        frag = new Frag01();
        fragTran = getFragmentManager().beginTransaction().add(R.id.container, frag);
        fragTran.commit();


        Button btnFrag01 = (Button)view.findViewById(R.id.btnFrag01);
        Button btnFrag02 = (Button)view.findViewById(R.id.btnFrag02);
        Button btnFrag03 = (Button)view.findViewById(R.id.btnFrag03);
        Button btnFrag04 = (Button)view.findViewById(R.id.btnFrag04);
        Button btnFrag05 = (Button)view.findViewById(R.id.btnFrag05);

        btnFrag01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFrag!=1){
                    frag = new Frag01();
                    fragTran = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                    fragTran.commit();
                }
                currentFrag = 1;
            }
        });
        btnFrag02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFrag!=2){
                    frag = new Frag02();
                    fragTran = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                    fragTran.commit();
                }
                currentFrag = 2;
            }
        });
        btnFrag03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFrag!=3){
                    frag = new Frag03();
                    fragTran = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                    fragTran.commit();
                }
                currentFrag = 3;
            }
        });
        btnFrag04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFrag!=4){
                    frag = new HRFragment();
                    fragTran = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                    fragTran.commit();
                }
                currentFrag = 4;
            }
        });
        btnFrag05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFrag!=5){
                    frag = new BearingToStart();
                    fragTran = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                    fragTran.commit();
                }
                currentFrag = 5;
            }
        });

        return view;
    }

}
