package com.example.listaj;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.util.List;


public class LightSensor extends Activity implements SensorEventListener {

    private Button btEncenderLinterna;
    private Button btApagarLinterna;
    private Button btCrazyTime;
    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private long lastUpdate;
    public final String TAG = "MAIN";
    private Double[] dataPoints;
    private GraphView graphView;
    private GraphViewSeries series;
    private double graph2LastXValue = 5d;


    private android.hardware.Camera dispCamara;
    android.hardware.Camera.Parameters parametrosCamara;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        /******/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        view = findViewById(R.id.textView);
        //view.setBackgroundColor(R.);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        series = new GraphViewSeries(new GraphView.GraphViewData[] {
        });
        graphView = new LineGraphView(
                this // context
                , "Acceleration" // heading
        );
        graphView.addSeries(series); // data
        graphView.setViewPort(1, 10);
        graphView.setManualYAxisBounds(20,1);
        graphView.setScalable(true);

        LinearLayout layout = (LinearLayout) findViewById(R.id.lelista);
        layout.addView(graphView);

        dataPoints = new Double[500];
        /****/


        btEncenderLinterna = (Button) findViewById(R.id.btEncenderLinterna);
        btEncenderLinterna.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                encenderLinternaAndroid ();
            }
        });

        btApagarLinterna = (Button) findViewById(R.id.btApagarLinterna);

        btApagarLinterna.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                apagarLinternaAndroid ();
            }
        });

        btCrazyTime = (Button) findViewById(R.id.btCrazyTIme);
        btCrazyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int times = 10;
                while(times >0){
                    encenderLinternaAndroid();
                    SystemClock.sleep(500);
                    apagarLinternaAndroid();
                    times--;
                }
                apagarLinternaAndroid();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        /*getMenuInflater().inflate(
                R.menu.activity_ajpdsoft_linterna, menu);*/
        return true;
    }

    //Al cerrar la aplicación apagar el flash
    public void finish()
    {
        if (dispCamara != null)
        {
            dispCamara.release();
            dispCamara = null;
        }
        super.finish();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        try
        {
            dispCamara = android.hardware.Camera.open();
        }
        catch( Exception e )
        {
            //Toast.makeText(getApplicationContext(),
            //          "No se ha podido acceder a la cámara",
            //          Toast.LENGTH_SHORT).show();
        }
    }

    private void apagarLinternaAndroid ()
    {
        if (dispCamara != null)
        {
            parametrosCamara = dispCamara.getParameters();
            parametrosCamara.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
            dispCamara.setParameters(parametrosCamara);
        }
        else
        {
            Toast.makeText (getApplicationContext(),
                    "No se ha podido acceder al Flash de la cámara",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void encenderLinternaAndroid ()
    {
        //Toast.makeText(getApplicationContext(),
        //  "Accediendo a la cámara", Toast.LENGTH_SHORT).show();

        if (dispCamara != null)
        {
            //Toast.makeText(getApplicationContext(),
            // "Cámara encontrada", Toast.LENGTH_SHORT).show();

            android.hardware.Camera.Parameters parametrosCamara = dispCamara.getParameters();

            //obtener modos de flash de la cámara
            List<String> modosFlash =
                    parametrosCamara.getSupportedFlashModes ();

            if (modosFlash != null &&
                    modosFlash.contains(android.hardware.Camera.Parameters.FLASH_MODE_TORCH))
            {
                //establecer parámetro TORCH para el flash de la cámara
                parametrosCamara.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                parametrosCamara.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_INFINITY);

                try
                {
                    dispCamara.setParameters(parametrosCamara);
                    dispCamara.startPreview();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),
                            "Error al activar la linterna",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "El dispositivo no tiene el modo de Flash Linterna",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "No se ha podido acceder al Flash de la cámara",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        //Log.d(TAG, "x: " + x + " y: " + y + " z: " + z);

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        double acceleration = Math.sqrt(accelationSquareRoot);
        long actualTime = System.currentTimeMillis();
        graph2LastXValue += 1d;
        series.appendData(new GraphView.GraphViewData(graph2LastXValue, acceleration), true, 10);
        addDataPoint(acceleration);



        //Log.d(TAG, "Beschleunigung: " + accelationSquareRoot);
        /*
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
                    .show();
            if (color) {
                view.setBackgroundColor(Color.GREEN);
            } else {
                view.setBackgroundColor(Color.RED);
            }
            color = !color;
        }
        */
    }

    private void addDataPoint(double acceleration) {
        dataPoints[499] = acceleration;
        //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
