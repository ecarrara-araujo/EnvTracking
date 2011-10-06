package com.uint.envtracking.data;

import com.uint.envtracking.data.EnTDatabaseContract.CollectedRawData;
import com.uint.envtracking.data.EnTDatabaseContract.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorEvent;

import java.util.ArrayList;
import java.util.List;

public class SensorRawDataDAO extends AbstractDAO {

    private static final String[] ALL_PROJECTION = { CollectedRawData.TRACKING_SESSION_ID, CollectedRawData.TIMESTAMP, CollectedRawData.SENSOR_TYPE, CollectedRawData.SENSOR_DATA_AXIS, CollectedRawData.SENSOR_RAW_DATA };
    
    private static SensorRawData extractEntityFromCursor(Cursor cursor) {
        long timestampCurrent = cursor.getLong(cursor.getColumnIndex(CollectedRawData.TIMESTAMP));
        long timestampNext = cursor.getLong(cursor.getColumnIndex(CollectedRawData.TIMESTAMP));
        int trackingSessionId = cursor.getInt(cursor.getColumnIndex(CollectedRawData.TRACKING_SESSION_ID));
        int sensorType = cursor.getInt(cursor.getColumnIndex(CollectedRawData.SENSOR_TYPE));
        
        ArrayList<Integer> dataAxisList = new ArrayList<Integer>();
        ArrayList<Float> sensorDataList = new ArrayList<Float>();
        
        int tempSensorDataAxis = cursor.getInt(cursor.getColumnIndex(CollectedRawData.SENSOR_DATA_AXIS));
        float tempData = cursor.getFloat(cursor.getColumnIndex(CollectedRawData.SENSOR_RAW_DATA));
        
        dataAxisList.add(tempSensorDataAxis);
        sensorDataList.add(tempData);
        
        //the collected data for each data axis is in a different record,
        //so we have read some more records to get all the data.
        //TODO: We rely that the timestamp is equal for all the data axis. We have to assure that... 
        while (cursor.moveToNext()) {
            timestampNext = cursor.getLong(cursor.getColumnIndex(CollectedRawData.TIMESTAMP));
            if (timestampCurrent != timestampNext) {
                cursor.moveToPrevious();
                break;
            }
            
            tempSensorDataAxis = cursor.getInt(cursor.getColumnIndex(CollectedRawData.SENSOR_DATA_AXIS));
            tempData = cursor.getFloat(cursor.getColumnIndex(CollectedRawData.SENSOR_RAW_DATA));
            
            dataAxisList.add(tempSensorDataAxis);
            sensorDataList.add(tempData);
        }
        
        float[] finalSensorData = new float[sensorDataList.size()];
        for (int i = 0; i < finalSensorData.length; i++) {
            finalSensorData[i] = sensorDataList.get(i).floatValue();
        }
        
        SensorRawData sensorData = new SensorRawData(timestampCurrent, sensorType, trackingSessionId, finalSensorData);
        return sensorData;
    }
    
    public static List<SensorRawData> getSensorRawDataBySensorAndSession(Context context, TrackingSession trackingSession, int sensorId) {
        List<SensorRawData> rawDataList = new ArrayList<SensorRawData>();
        
        SQLiteDatabase db = getDatabase(context);
        
        String where = CollectedRawData.TRACKING_SESSION_ID + "=?" + " AND " + CollectedRawData.SENSOR_TYPE + "=?";
        String[] whereArgs = { String.valueOf(trackingSession.getId()), String.valueOf(sensorId) };
        String orderBy = CollectedRawData.TIMESTAMP + ", " + CollectedRawData.SENSOR_DATA_AXIS;
        
        Cursor cursor = db.query(Tables.COLLECTED_RAW_DATA, ALL_PROJECTION, where, whereArgs, null, null, orderBy);
        
        if (cursor != null) {
            while(cursor.moveToNext()) {
                rawDataList.add(extractEntityFromCursor(cursor));
            }
        }
        
        return rawDataList;
    }
    
    public static void persistEntity(Context context, SensorEvent sensorData, long timestamp, TrackingSession trackingSession) {
        persistEntity(context, sensorData, timestamp, trackingSession.getId());
    }
    
    public static void persistEntity(Context context, SensorEvent sensorData, long timestamp, int trackingSessionId) {
        SQLiteDatabase db = getDatabase(context);
        insertEntity(db, sensorData, timestamp, trackingSessionId);
    }
    
    private static void insertEntity(SQLiteDatabase db, SensorEvent sensorData, long timestamp, int trackingSessionId) {
        ContentValues values = new ContentValues();
        values.put(CollectedRawData.TIMESTAMP, timestamp);
        values.put(CollectedRawData.SENSOR_TYPE, sensorData.sensor.getType());
        values.put(CollectedRawData.TRACKING_SESSION_ID, trackingSessionId);
        
        for(int i = 0; i < sensorData.values.length; i++) {
            values.put(CollectedRawData.SENSOR_DATA_AXIS, i);
            values.put(CollectedRawData.SENSOR_RAW_DATA, sensorData.values[i]);
            db.insert(Tables.COLLECTED_RAW_DATA, "", values);
        }
    }
    
    public static int deleteAllRawDataBySession(Context context, TrackingSession trackingSession) {
        SQLiteDatabase db = getDatabase(context);
        
        String where = CollectedRawData.TRACKING_SESSION_ID + "=?";
        String[] whereArgs = { String.valueOf(trackingSession.getId()) };
        
        return db.delete(Tables.COLLECTED_RAW_DATA, where, whereArgs);
    }
    
    public static int deleteAllEntities(Context ctx) {
        SQLiteDatabase db = getDatabase(ctx);
        
        return db.delete(Tables.COLLECTED_RAW_DATA, null, null); 
    }
}
