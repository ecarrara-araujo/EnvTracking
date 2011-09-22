package com.uint.envtracking.data;

import com.uint.envtracking.data.EnTDatabaseContract.Tables;
import com.uint.envtracking.data.EnTDatabaseContract.TrackingSessions;
import com.uint.envtracking.util.EnTLog;

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
    
    private static final String CREATE_TABLE_TRACKING_SESSIONS = "CREATE TABLE " + Tables.TRACKING_SESSIONS + "(" +
            TrackingSessions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
            TrackingSessions.START_TIME_TS + " INTEGER NOT NULL, " +
            TrackingSessions.END_TIME_TS + " INTEGER NOT NULL" + ");";
    
    protected EnTDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }
    
    private void createTables(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRACKING_SESSIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "There is no update routine for the database.");
    }

}
