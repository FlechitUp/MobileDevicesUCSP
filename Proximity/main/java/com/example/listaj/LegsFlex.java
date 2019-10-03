package com.example.listaj;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class LegsFlex extends Activity implements SensorEventListener {

    private double pitch, tilt, azimuth;

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 400;
    private TextView mGanancias;
    private int mIntGanancias;


    private ArrayList<Integer> lotteryNumbers;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acelero);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

//        for (int i = 0; i < lotteryNumbers.size(); i++) {
//             Log.i("LOG", " @@@@@@@@" + lotteryNumbers.get(i));
//        }

    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    private double[] convertFloatsToDoubles(float[] input) {
        if (input == null)
            return null;

        double[] output = new double[input.length];

        for (int i = 0; i < input.length; i++)
            output[i] = input[i];

        return output;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Get Rotation Vector Sensor Values
        double[] g = convertFloatsToDoubles(event.values.clone());

        //Normalise
        double norm = Math.sqrt(g[0] * g[0] + g[1] * g[1] + g[2] * g[2]);
        g[0] /= norm;
        g[1] /= norm;
        g[2] /= norm;

        //Set values to commonly known quaternion letter representatives
        double x = g[0];
        double y = g[1];
        double z = g[2];

        //Calculate Pitch in degrees (-180 to 180)
        double sinP = 2.0 * ( x + y * z);
        double cosP = 1.0 - 2.0 * (x * x + y * y);
        pitch = Math.atan2(sinP, cosP) * (180 / Math.PI);

        //Calculate Tilt in degrees (-90 to 90)
        double sinT = 2.0 * ( y - z * x);
        if (Math.abs(sinT) >= 1)
            tilt = Math.copySign(Math.PI / 2, sinT) * (180 / Math.PI);
        else
            tilt = Math.asin(sinT) * (180 / Math.PI);

        //Calculate Azimuth in degrees (0 to 360; 0 = North, 90 = East, 180 = South, 270 = West)
        double sinA = 2.0 * ( z + x * y);
        double cosA = 1.0 - 2.0 * (y * y + z * z);
        azimuth = Math.atan2(sinA, cosA) * (180 / Math.PI);


        if (flatEnough(azimuth)){
            Toast.makeText(getApplicationContext(), "flat", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "no muy flat", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // can be safely ignored for this demo
    }



    public boolean flatEnough(double degreeTolerance) {
        return tilt <= degreeTolerance && tilt >= -degreeTolerance && pitch <= degreeTolerance && pitch >= -degreeTolerance;
    }

}
