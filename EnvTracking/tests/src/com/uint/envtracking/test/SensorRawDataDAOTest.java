package com.uint.envtracking.test;

import com.uint.envtracking.data.SensorRawData;
import com.uint.envtracking.data.SensorRawDataDAO;
import com.uint.envtracking.data.TrackingSession;
import com.uint.envtracking.data.TrackingSessionDAO;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

public class SensorRawDataDAOTest extends AndroidTestCase implements SensorEventListener {
    
    private final int SENSOR_EVENT_REFERENCE_ID = 0;
    private final int NUMBER_OF_EVENTS_NEEDED = 5;
    
    private SensorManager mSensorManager;
    private List<SensorEvent> mSensorsEvents;
    private TrackingSession mReferenceTrackingSession;
    
    @Override
    protected void setUp() throws Exception {
        mReferenceTrackingSession = TrackingSessionDAO.persistEntity(mContext, new TrackingSession(System.currentTimeMillis(), System.currentTimeMillis() + 1000000));
        assertNotNull("It was not possible to create a reference tracking session...", mReferenceTrackingSession);
        
        mSensorsEvents = new ArrayList<SensorEvent>();
        mSensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ALL), SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    @Override
    protected void tearDown()  throws Exception {
        mSensorManager.unregisterListener(this);
        mSensorsEvents = null;
        SensorRawDataDAO.deleteAllEntities(mContext);
        TrackingSessionDAO.deleteAllEntities(mContext);
    }
    
    public void testPersistEntity() {
        waitForSensorData();
        
        assertTrue("No sensors information collected for tests...", mSensorsEvents.size() > 0);
        SensorEvent event = mSensorsEvents.get(SENSOR_EVENT_REFERENCE_ID);
        assertNotNull("No sensor data was found for persistence test.", event);
        SensorRawDataDAO.persistEntity(mContext, event, System.currentTimeMillis(), mReferenceTrackingSession);
        List<SensorRawData> sensorRawData = SensorRawDataDAO.getSensorRawDataBySensorAndSession(mContext, mReferenceTrackingSession, event.sensor.getType());
        
        boolean[] result = new boolean[event.values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = false;
        }
        
        boolean finalResult = true;
        for (SensorRawData tempSensorRawData : sensorRawData) {
            if (tempSensorRawData.getSensorType() == event.sensor.getType()){
                for (int i = 0; i < event.values.length; i++) {
                    result[i] = tempSensorRawData.getSensorDataRawValues(i) == event.values[i];
                }
                for (int i = 0; i < result.length; i++) {
                    finalResult = finalResult && result[i];
                }
                if(finalResult)
                    break;
            }
        }
        
        //last check we are finishing... Checking for the case that no sensor of that type is found...
        for (int i = 0; i < result.length; i++) {
            finalResult = finalResult && result[i];
        }
        assertTrue("The sensor data was not inserted in the database.", finalResult);
    }

    private void waitForSensorData() {
        if (mSensorsEvents == null) {
            return;
        }
        while(mSensorsEvents.size() < NUMBER_OF_EVENTS_NEEDED) {
            Thread.yield();
        }
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mSensorsEvents.add(event);
    }

}
