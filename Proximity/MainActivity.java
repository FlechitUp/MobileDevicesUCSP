package com.example.listaj;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity
{

    SensorManager smm;
    List<Sensor> sensor;
    ListView lv;
    private Sensor sensor2;
    /****************************************/
    TextView ProximitySensor, data;
    SensorManager mySensorManager;
    Sensor myProximitySensor;


    @Override   protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor2 = smm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        lv = (ListView) findViewById (R.id.simpleListView);
        sensor = smm.getSensorList(Sensor.TYPE_ALL);
        lv.setAdapter(new ArrayAdapter<Sensor>(this, android.R.layout.simple_list_item_1,  sensor));

        /********************************************************************************/

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /*String [] strArr = sensor.toArray(new String[sensor.size()-1]);
                String TempListViewClickedValue = strArr[position].toString();
                Log.v("oklo",TempListViewClickedValue);*/

                /*Intent intent = new Intent(MainActivity.this, activityProxSensor.class);
                intent.putExtra("ListViewCl","ija"); //TempListViewClickedValue*/

                //TODO: ESTO FUMCIOMA

                /*Intent intent = new Intent(MainActivity.this, LightSensor.class);
                intent.putExtra("ListViewCl","ija"); //TempListViewClickedValue

                startActivity(intent);*/

                Intent intent = new Intent(MainActivity.this, IBMEyes.class);
                //intent.putExtra("ListViewCl","ija"); //TempListViewClickedValue

                startActivity(intent);

            }
        });



    }

    /*@Override
    protected void onListItemClick(ListView lv_, View v, int position, long id){
        ProximitySensor = (TextView) findViewById(R.id.proximitySensor);
        Intent appInfo = new Intent(R.layout.activity_prox_sensor);

        if (position == 0){

        }
    }*/
}
