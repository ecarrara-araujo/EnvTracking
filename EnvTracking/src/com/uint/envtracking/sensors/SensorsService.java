package com.uint.envtracking.sensors;

import java.util.List;


import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SensorsService extends Service implements SensorEventListener {
    private static final String LOG_TAG = "SensorsService";

    private SensorManager mSensorManager;
    private int[] mSensorsList;
    private int mRate;

    private boolean isServiceRunning;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Log.d(LOG_TAG, "onStartCommand");
        stopCollectSensorData(); // just in case the activity-level service
                                 // management fails

        mSensorsList = intent.getIntArrayExtra(SensorConstants.SENSOR_LIST_EXTRA);

        Log.d(LOG_TAG, "Sensors list: " + mSensorsList.toString());

        SharedPreferences appPrefs = getSharedPreferences(
                SensorConstants.PREF_FILE, MODE_PRIVATE);
        mRate = appPrefs.getInt(SensorConstants.PREF_SAMPLING_SPEED,
                SensorManager.SENSOR_DELAY_UI);

        Log.d(LOG_TAG, "rate: " + mRate);

        // screenOffBroadcastReceiver = new ScreenOffBroadcastReceiver();
        // IntentFilter screenOffFilter = new IntentFilter();
        // screenOffFilter.addAction(Intent.ACTION_SCREEN_OFF);
        // registerReceiver(screenOffBroadcastReceiver, screenOffFilter);

        startCollectSensorData();
        Log.d(LOG_TAG, "onStartCommand end");

        return START_NOT_STICKY;
    }

    private void startCollectSensorData() {
        if (isServiceRunning) {
            return;
        }

        if (mSensorsList != null && mSensorsList.length > 0) {
            for (int i = 0; i < mSensorsList.length; i++) {
                int sensorType = mSensorsList[i];
                Sensor sensor = mSensorManager.getDefaultSensor(sensorType);

                if (sensor != null) {
                    Log.d(LOG_TAG, "registerListener/SamplingService");
                    mSensorManager.registerListener(this, sensor, mRate);
                }
            }

            isServiceRunning = true;
        }
    }

    private void stopCollectSensorData() {
        if (!isServiceRunning) {
            return;
        }

        if (mSensorManager != null) {
            Log.d(LOG_TAG, "Unregister sensor listener");
            mSensorManager.unregisterListener(this);
        }

        isServiceRunning = false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String values = "";
        
        for (int i = 0; i < event.values.length; i++) {
            values = event.values[i] + " "; 
        }
        Log.d(LOG_TAG, "Sensor [" + event.sensor.getName() + "]: " + values);
    }

    public class SensorsServiceBinder extends Binder {
        public SensorsService getService() {
            return SensorsService.this;
        }
    }
}
