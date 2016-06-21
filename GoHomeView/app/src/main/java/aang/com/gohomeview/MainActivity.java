package aang.com.gohomeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GoHomeGauge gauge;
    private int valor = 0;
    private int distance = 0;
    private TextView showValor;
    private TextView showDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gauge = (GoHomeGauge) findViewById(R.id.gaugeAtLocation);

        showValor = (TextView) findViewById(R.id.showValor);
        showDistance = (TextView) findViewById(R.id.showDistance);
        gauge.setValue(valor, distance);
        showValor.setText(Integer.toString(valor));
        showDistance.setText(Integer.toString(distance));

        //Creo el Runnable
        gauge.goAnimation();

    }

    public void IncrementaD(View view){
        distance +=5;
        gauge.setValue(valor, distance);
        showDistance.setText(Integer.toString(distance));
        //gauge.startAnimation(animation);
    }

    public void DecrementaD(View view){
        distance -=5;
        gauge.setValue(valor, distance);
        showDistance.setText(Integer.toString(distance));
        //gauge.clearAnimation();
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