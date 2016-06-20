package aang.com.gohomeview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CustomGauge gauge;
    private int valor = 0;
    private int distance = 0;

    private AlphaAnimation animation;
    private TextView showValor;

    private TextView showDistance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gauge = (CustomGauge) findViewById(R.id.gaugeAtLocation);
        /*bIncrement = (Button) findViewById(R.id.increment);
        bDecrement = (Button) findViewById(R.id.decrement);*/
        showValor = (TextView) findViewById(R.id.showValor);
        showDistance = (TextView) findViewById(R.id.showDistance);
        gauge.setValue(valor, distance);
        showValor.setText(Integer.toString(valor));
        showDistance.setText(Integer.toString(distance));


        /*AANG*/
        animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(1000);
        animation.setStartOffset(50);
        animation.setFillAfter(true);
        animation.setRepeatMode(Animation.INFINITE);


    }

    public void IncrementaD(View view){
        distance +=5;
        gauge.setValue(valor, distance);
        showDistance.setText(Integer.toString(distance));
        gauge.startAnimation(animation);
    }

    public void DecrementaD(View view){
        distance -=5;
        gauge.setValue(valor, distance);
        showDistance.setText(Integer.toString(distance));
        gauge.clearAnimation();
    }

    public void Incrementa(View view) {
        valor += 10;
        gauge.setValue(valor, distance);
        showValor.setText(Integer.toString(valor));
    }

    public void Decrementa(View view) {
        valor -= 10;
        gauge.setValue(valor, distance);
        showValor.setText(Integer.toString(valor));

    }
}