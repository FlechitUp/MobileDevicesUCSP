package com.example.listaj;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IBMEyes extends Activity implements SensorEventListener{

    final String tag = "IBMEyes";
    private SensorManager sm;
    private Sensor proximity;
    private ImageView img;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prox_sensor);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximity = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        img = (ImageView) findViewById(R.id.img);
    }


    @Override
    public final void onSensorChanged(SensorEvent sensorEvent) {
        int sizeXY = 60;
        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (sensorEvent.values[0] >= -4 && sensorEvent.values[0] <= 4) {
                //near
                Toast.makeText(getApplicationContext(), "near", Toast.LENGTH_SHORT).show();
                while(sizeXY < 400){
                    sizeXY +=20;
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeXY,sizeXY);
                    img.setLayoutParams(params);

                    if (!(sensorEvent.values[0] >= -4 && sensorEvent.values[0] <= 4)) {
                        Toast.makeText(getApplicationContext(), sizeXY, Toast.LENGTH_SHORT).show();
                        break;
                    }

                }
            } else {
                //far
                Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
                while(sizeXY >= 60){
                    sizeXY -=20;
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sizeXY,sizeXY);
                    img.setLayoutParams(params);

                    if (sensorEvent.values[0] >= -4 && sensorEvent.values[0] <= 4)  {
                        break;
                    }
                }

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(tag,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sm.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
    }


}
