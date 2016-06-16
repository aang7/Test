package aang.com.gohomeview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CustomGauge gauge;
    private int valor = 0;
    private Button bIncrement;
    private Button bDecrement;


    private TextView showValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gauge = (CustomGauge) findViewById(R.id.gaugeAtLocation);
        /*bIncrement = (Button) findViewById(R.id.increment);
        bDecrement = (Button) findViewById(R.id.decrement);*/
        showValor = (TextView) findViewById(R.id.showValor);
        gauge.setValue(valor);
        showValor.setText(Integer.toString(valor));

    }

    public void Incrementa(View view) {
        valor += 10;
        gauge.setValue(valor);
        showValor.setText(Integer.toString(valor));
        if (valor >= 10)
            gauge.setNewColor(Color.RED);


    }

    public void Decrementa(View view) {
        valor -= 10;
        gauge.setValue(valor);
        showValor.setText(Integer.toString(valor));
        if (valor < 10) {
            gauge.setNewColor(Color.GREEN);
            /*Aqui tengo que llenar el circulo para el test*/

        }
    }
}