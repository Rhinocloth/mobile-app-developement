package edu.dtcc.janemone.stepcounter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager myManager;
    private Sensor myStepCounter;
    private Button startStop;
    private Button reset;
    private TextView stepDisplay;
    private boolean counterPaused = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startStop = findViewById(R.id.startStopBt);
        reset = findViewById(R.id.resetBt);

        myManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        myStepCounter = myManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        myManager.registerListener(this, myStepCounter, myManager.SENSOR_DELAY_UI);

        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sets a boolean that pauses or starts the step counter when this button is pressed
                if (counterPaused == true)
                {
                    counterPaused = false;
                }
                else
                {
                    counterPaused = true;
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // unregisters the current listener and sets a new listener to set step count back to zero
                myManager.unregisterListener(MainActivity.this, myStepCounter);
                myManager.registerListener(MainActivity.this, myStepCounter, myManager.SENSOR_DELAY_UI);
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!counterPaused)
        {

        }
    }

    @Override
    public void onAccuracyChanged(Sensor s, int acc) {

    }
}
