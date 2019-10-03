package com.example.listaj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class Accelero extends Activity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 400;
    private TextView mGanancias;
    private int mIntGanancias;


    private ArrayList<Integer> lotteryNumbers;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acelero);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        mGanancias = findViewById(R.id.mainActivityTvGanancias);
        mIntGanancias = 0;
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x+y+z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    //Toast.makeText(this, "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
                    getRandomNumber();
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // can be safely ignored for this demo
    }

    private int evaluateNumbers(ArrayList numbersGenerated){
        if(numbersGenerated.get(0).equals(numbersGenerated.get(1).equals(numbersGenerated.get(2))) ){
            return 200;
        }
        if(numbersGenerated.get(0).equals(numbersGenerated.get(1)) || numbersGenerated.get(0).equals(numbersGenerated.get(2)) || numbersGenerated.get(1).equals(numbersGenerated.get(2)) ){
            return 100;
        }
        return -100;
    }

    private void setImage(FrameLayout fl, Integer number){
        Log.d("blafu","ulalal");

    }

    private void getRandomNumber() {
        ArrayList numbersGenerated = new ArrayList<Integer>();

        for (int i = 0; i < 3; i++) {
            Random randNumber = new Random();
            int iNumber = randNumber.nextInt(5) + 1;

            numbersGenerated.add(iNumber);

            /*if (!numbersGenerated.contains(iNumber)) {
            } else {
                i--;
            }*/
        }


        FrameLayout ball1 = (FrameLayout) findViewById(R.id.ball_1);
        ball1.setVisibility(View.INVISIBLE);

        FrameLayout ball2 = (FrameLayout) findViewById(R.id.ball_2);
        ball2.setVisibility(View.INVISIBLE);
        FrameLayout ball3 = (FrameLayout) findViewById(R.id.ball_3);
        ball3.setVisibility(View.INVISIBLE);

        //setImage(ball3, 3);
        if (numbersGenerated.get(0).equals(1)){
            ball1.setBackgroundResource(R.drawable.emoisurprise);

        }
        else if (numbersGenerated.get(0).equals(2)){
            ball1.setBackgroundResource(R.drawable.emoihappy);
        }
        else if (numbersGenerated.get(0).equals(3)){
            ball1.setBackgroundResource(R.drawable.emojiheart1);
        }
        else if (numbersGenerated.get(0).equals(4)){
            ball1.setBackgroundResource(R.drawable.emojilike);
        }
        else {
            ball1.setBackgroundResource(R.drawable.emojiba);
        }


        //ball 2
        if (numbersGenerated.get(1).equals(1)){
            ball2.setBackgroundResource(R.drawable.emoisurprise);

        }
        else if (numbersGenerated.get(1).equals(2)){
            ball2.setBackgroundResource(R.drawable.emoihappy);
        }
        else if (numbersGenerated.get(1).equals(3)){
            ball2.setBackgroundResource(R.drawable.emojiheart1);
        }
        else if (numbersGenerated.get(1).equals(4)){
            ball2.setBackgroundResource(R.drawable.emojilike);
        }
        else{
            ball2.setBackgroundResource(R.drawable.emojiba);
        }

        //ball 3
        if (numbersGenerated.get(2).equals(1)){
            ball3.setBackgroundResource(R.drawable.emoisurprise);

        }
        else if (numbersGenerated.get(2).equals(2)){
            ball3.setBackgroundResource(R.drawable.emoihappy);
        }
        else if (numbersGenerated.get(2).equals(3)){
            ball3.setBackgroundResource(R.drawable.emojiheart1);
        }
        else if (numbersGenerated.get(2).equals(4)){
            ball3.setBackgroundResource(R.drawable.emojilike);
        }
        else{
            ball3.setBackgroundResource(R.drawable.emojiba);
        }

        Animation a = AnimationUtils.loadAnimation(this, R.anim.move_down_ball_first);


        ball3.setVisibility(View.VISIBLE);
        ball3.clearAnimation();
        ball3.startAnimation(a);

        ball2.setVisibility(View.VISIBLE);

        ball2.clearAnimation();
        ball2.startAnimation(a);



        ball1.setVisibility(View.VISIBLE);
        ball1.clearAnimation();
        ball1.startAnimation(a);

        mIntGanancias = mIntGanancias  + evaluateNumbers(numbersGenerated);
        mGanancias.setText(String.valueOf(mIntGanancias));


    }
}
