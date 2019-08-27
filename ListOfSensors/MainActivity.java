package com.example.listaj;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;import android.widget.ListView;

import java.util.List;

public class MainActivity extends Activity
{

    SensorManager smm;
    List<Sensor> sensor;
    ListView lv;


    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lv = (ListView) findViewById (R.id.simpleListView);
        sensor = smm.getSensorList(Sensor.TYPE_ALL);
        lv.setAdapter(new ArrayAdapter<Sensor>(this, android.R.layout.simple_list_item_1,  sensor));
    }
}