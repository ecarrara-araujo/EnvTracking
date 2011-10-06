package com.uint.envtracking.data;

import com.uint.envtracking.data.EnTDatabaseContract.Tables;
import com.uint.envtracking.data.EnTDatabaseContract.TrackingSessions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TrackingSessionDAO extends AbstractDAO {
    
    private static final String[] ALL_PROJECTION = { TrackingSessions._ID, TrackingSessions.START_TIME_TS, TrackingSessions.END_TIME_TS };
    
    public static List<TrackingSession> getAllTrackingSessions(Context ctx) {
        List<TrackingSession> tsList = new ArrayList<TrackingSession>();
        
        SQLiteDatabase db = getDatabase(ctx);
        
        Cursor cursor = db.query(Tables.TRACKING_SESSIONS, ALL_PROJECTION, null, null, null, null, null);
        
        if (cursor != null) {
            while(cursor.moveToNext()) {
                tsList.add(extractEntityFromCursor(cursor));
            }
        }
        return tsList;
    }
    
    public static TrackingSession getTrackingSession(Context ctx, int id) {
        SQLiteDatabase db = getDatabase(ctx);
        
        String where = TrackingSessions._ID + "=?";
        String[] whereArgs = { String.valueOf(id) };
        
        Cursor cursor = db.query(Tables.TRACKING_SESSIONS, ALL_PROJECTION, where, whereArgs, null, null, null);
        
        if (cursor != null) {
            while(cursor.moveToNext()) {
                return extractEntityFromCursor(cursor);
            }
        }
        return null;
    }
    
    public static TrackingSession persistEntity(Context context, TrackingSession entity) {
        SQLiteDatabase db = getDatabase(context);
        if(entity.getId() < 0) {
            return insertEntity(db, entity);
        } else {
            return updateEntity(db, entity);
        }
    }
    
    public static int deleteEntity(Context context, TrackingSession entity) {
        if(entity.getId() < 0) {
            return 0;
        } 
        SQLiteDatabase db = getDatabase(context);
        String where = TrackingSessions._ID + "=?";
        String[] whereArgs = { String.valueOf(entity.getId()) };
        return db.delete(Tables.TRACKING_SESSIONS, where, whereArgs);
    }
    
    public static int deleteAllEntities(Context ctx) {
        SQLiteDatabase db = getDatabase(ctx);
        
        return db.delete(Tables.TRACKING_SESSIONS, null, null); 
    }
    
    private static TrackingSession insertEntity(SQLiteDatabase db, TrackingSession entity) {
        ContentValues values = new ContentValues();
        values.put(TrackingSessions.START_TIME_TS, entity.getmRawStartTime());
        values.put(TrackingSessions.END_TIME_TS, entity.getmRawEndTime());
        int newId = (int) db.insert(Tables.TRACKING_SESSIONS, "", values);
        entity.setId(newId);
        return entity;
    }
    
    private static TrackingSession updateEntity(SQLiteDatabase db, TrackingSession entity) {
        ContentValues values = new ContentValues();
        values.put(TrackingSessions.START_TIME_TS, entity.getmRawStartTime());
        values.put(TrackingSessions.END_TIME_TS, entity.getmRawEndTime());
        
        String where = TrackingSessions._ID + "=?";
        String[] whereArgs = { String.valueOf(entity.getId()) };
        
        db.update(Tables.TRACKING_SESSIONS, values, where, whereArgs);
        
        return entity;
    }
    
    private static TrackingSession extractEntityFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(TrackingSessions._ID));
        long startTime = cursor.getLong(cursor.getColumnIndex(TrackingSessions.START_TIME_TS));
        long endTime = cursor.getLong(cursor.getColumnIndex(TrackingSessions.END_TIME_TS));
        
        return new TrackingSession(id, startTime, endTime);
    }
    
}
