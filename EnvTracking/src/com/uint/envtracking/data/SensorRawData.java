package com.uint.envtracking.data;

import java.util.Date;

public class SensorRawData extends AbstractEntity {

    public static final int MAX_NUMBER_OF_SENSOR_DATA_AXIS = 4;
    public static final int DATA_X = 0;
    public static final int DATA_Y = 1;
    public static final int DATA_Z = 2;
    
    private long mRawTimestamp;
    private Date mTimestamp;
    private int mSensorType;
    private float[] mSensorDataRawValues;
    private int mTrackingSessionId;
    
    public SensorRawData(long timestamp, int sensorType, int trackingSessionId) {
        this(timestamp, sensorType, trackingSessionId, new float[MAX_NUMBER_OF_SENSOR_DATA_AXIS]);
    }
    
    public SensorRawData(long timestamp, int sensorType, int trackingSessionId, float[] sensorValues) {
        mId = 0;
        mRawTimestamp = timestamp;
        mTimestamp = new Date(timestamp);
        mSensorType = sensorType;
        mTrackingSessionId = trackingSessionId;
        mSensorDataRawValues = sensorValues;
    }

    @Override
    public void setId(int newId) {
        //Do nothing
        return;
    }
    
    public float[] getSensorDataRawValues() {
        return mSensorDataRawValues;
    }
    
    public float getSensorDataRawValues(int sensorDataAxis) {
        if (!validateSensorDataAxis(sensorDataAxis)) {
            return 0;
        }
        
        return mSensorDataRawValues[sensorDataAxis];
    }

    public void setSensorDataRawValues(float[] sensorDataRawValues) {
        this.mSensorDataRawValues = sensorDataRawValues;
    }
    
    public void setSensorDataRawValues(float sensorDataRawValue, int sensorDataAxis) {
        if(validateSensorDataAxis(sensorDataAxis)) {
            return;
        }
        
        //TODO: maybe we should enforce and check for the correct position here....
        this.mSensorDataRawValues[sensorDataAxis] = sensorDataRawValue;
    }

    public long getRawTimestamp() {
        return mRawTimestamp;
    }

    public Date getTimestamp() {
        return mTimestamp;
    }

    public int getSensorType() {
        return mSensorType;
    }

    public int getTrackingSessionId() {
        return mTrackingSessionId;
    }

    private boolean validateSensorDataAxis(int sensorDataAxis) {
        if (sensorDataAxis > MAX_NUMBER_OF_SENSOR_DATA_AXIS || sensorDataAxis > mSensorDataRawValues.length) {
            return false;
        }
        return true;
    }
}
