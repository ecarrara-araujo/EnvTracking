package com.uint.envtracking.data;

import com.uint.envtracking.data.EnTDatabaseContract.CollectedRawData;
import com.uint.envtracking.data.EnTDatabaseContract.Tables;
import com.uint.envtracking.data.EnTDatabaseContract.TrackingSessions;
import com.uint.envtracking.util.EnTLog;

import android.app.ActionBar.Tab;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EnTDatabaseHelper extends SQLiteOpenHelper {
    
    private static final String LOG_TAG = EnTLog.LOG_TAG;
    
    private static EnTDatabaseHelper mInstance = null;
    
    /**
     * Provides the current singleton instance for database helper.
     * @param context
     * @return
     */
    public static EnTDatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new EnTDatabaseHelper(context);
        }
        return mInstance;
    }
    
    //database general information
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "EnvTracking.db";
    
    private static final String CREATE_TABLE_TRACKING_SESSIONS = "CREATE TABLE IF NOT EXISTS " + 
            Tables.TRACKING_SESSIONS + "(" +
            TrackingSessions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
            TrackingSessions.START_TIME_TS + " INTEGER NOT NULL, " +
            TrackingSessions.END_TIME_TS + " INTEGER NOT NULL" + ");";
    
    private static final String CREATE_TABLE_COLLECTED_RAW_DATA = "CREATE TABLE IF NOT EXISTS " 
            + Tables.COLLECTED_RAW_DATA + "(" +
            CollectedRawData.TIMESTAMP + " INTEGER NOT NULL, " +
            CollectedRawData.SENSOR_TYPE + " INTEGER NOT NULL, " +
            CollectedRawData.SENSOR_DATA_AXIS + " INTEGER NOT NULL, " +
            CollectedRawData.SENSOR_RAW_DATA + " REAL NOT NULL, " +
            CollectedRawData.TRACKING_SESSION_ID + " INTEGER NOT NULL REFERENCES " +  Tables.TRACKING_SESSIONS + "( " +
                TrackingSessions._ID + ") ON DELETE CASCADE, " +
            " CONSTRAINT DATA_PRIM_KEY PRIMARY KEY (" + 
                CollectedRawData.TIMESTAMP + ", " + 
                CollectedRawData.SENSOR_TYPE + ", " + 
                CollectedRawData.SENSOR_DATA_AXIS + ", " + 
                CollectedRawData.TRACKING_SESSION_ID + ") " 
            + ");";
    
    protected EnTDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }
    
    private void createTables(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRACKING_SESSIONS);
        db.execSQL(CREATE_TABLE_COLLECTED_RAW_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "There is no update routine for the database.");
    }

}
