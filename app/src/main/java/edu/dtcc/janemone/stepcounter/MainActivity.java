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

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager myManager;
    private Sensor myStepCounter;
    private Button startStop;
    private Button reset;
    private TextView stepDisplay;
    private TextView distanceDisplay;
    private TextView timeDisplay;
    private TextView speedDisplay;
    private boolean counterPaused = true;
    private boolean newCounter = true;
    private int time;
    private int stride;
    private int steps;
    private double distance;
    private double speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startStop = findViewById(R.id.startStopBt);
        reset = findViewById(R.id.resetBt);

        stepDisplay = findViewById(R.id.numSteps);
        distanceDisplay = findViewById(R.id.distance);
        timeDisplay = findViewById(R.id.timeElapsed);
        speedDisplay = findViewById(R.id.avgSpeed);

        myManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        myStepCounter = myManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newCounter)
                {
                    // sets a new listener to count steps when start button is pressed
                    // and a new counter is needed
                    myManager.registerListener(MainActivity.this, myStepCounter, myManager.SENSOR_DELAY_UI);
                    timer();
                    newCounter = false;
                }

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
                counterPaused = true;
                newCounter = true;
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        steps = Math.round(event.values[0]);
        calcDistance();
        calcSpeed();
        updateUI();
    }

    @Override
    public void onAccuracyChanged(Sensor s, int acc) {

    }

    private void calcSpeed()
    {

    }

    private void calcDistance()
    {
        //int dist = 0;
        //dist = steps * stride;
        //distance = dist;
    }

    private void updateUI()
    {
        int tenMinute;
        int oneMinute;
        int tenSecond;
        int oneSecond;

        tenMinute = time / 600;
        oneMinute = (time - (tenMinute * 600)) / 60;
        tenSecond = (time - (tenMinute * 600) - (oneMinute * 60)) / 10;
        oneSecond = time - (tenMinute * 600) - (oneMinute * 60) - (tenSecond * 10);

        final String timeString = Integer.toString(tenMinute) + Integer.toString(oneMinute) + ":" + Integer.toString(tenSecond) + Integer.toString(oneSecond);

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stepDisplay.setText(Integer.toString(steps));
                timeDisplay.setText(timeString);
            }
        });
    }

    private void timer()
    {
        Thread timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!newCounter){

                    time++;

                    try{
                        sleep(1000);
                    }
                    catch (Exception err)
                    {
                        err.printStackTrace();
                    }

                    updateUI();

                    while (counterPaused)
                    {

                        try{
                            sleep(500);
                        }
                        catch (Exception err)
                        {
                            err.printStackTrace();
                        }

                        if (newCounter)
                        {
                            time = 0;
                            updateUI();
                            break;
                        }
                    }
                }
            }

        });
        timerThread.start();
    }
}
