package com.uint.envtracking.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Holds the definitions for the whole application database.
 * Here we shall define tables and its fields, and any other information relevant to database structure.
 * @author ecarrara.araujo
 *
 */
public class EnTDatabaseContract {

    /** The authority for the EnvTracking provider */
    public static final String AUTHORITY = "com.uint.envtracking";
    /** A content:// style uri to the authority for the EnvTracking provider */
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
    
    /**
     * Tables for the {@link EnvTrackingDatabase}
     * 
     * @author ecarrara.araujo
     *
     */
    public interface Tables {
        public static final String TRACKING_SESSIONS = "tracking_sessions";
        public static final String COLLECTED_RAW_DATA = "collected_raw_data";
    }
    
    /**
     * Columns of {@link EnTDatabaseContract.TrackingSessions} that refer to
     * properties of the tracking session.
     *
     * @see TrackingSessions
     */
    protected interface TrackingSessionsColumns {
        
        /**
         * The moment when the tracking session started.
         * <P>Type: LONG</P>
         */
        public static final String START_TIME_TS = "start_time_ts";
        
        /**
         * The moment when the tracking session ended.
         * <P>Type: LONG</P>
         */
        public static final String END_TIME_TS = "end_time_ts";
        
    }
    
    /**
     * 
     * @author ecarrara.araujo
     *
     */
    public static class TrackingSessions implements BaseColumns, TrackingSessionsColumns {
        
        /**
         * This utility class cannot be instantiated
         */
        private TrackingSessions()  {}
        
        /**
         * The content:// style URI for this table
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, Tables.TRACKING_SESSIONS);
        
        /**
         * The MIME type of a directory of groups.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/tracking_session";

        /**
         * The MIME type of a single group.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/tracking_session";
    }
    
    /**
     * Columns of {@link EnTDatabaseContract.CollectedRawData} that refer to
     * properties of the sensor raw data collected by the application.
     * 
     * @see TrackingSessions
     */
    protected interface CollectedRawDataColumns {
        
        /**
         * The moment that the data was collected.
         * <P>Type: LONG</P>
         */
        public static final String TIMESTAMP = "timestamp";
        
        /**
         * Sensor type, refers to the constants defined in {@Link Sensor} as TYPE_*
         * <P>Type: INTEGER</P>
         */
        public static final String SENSOR_TYPE = "sensor_type";
        
        /**
         * Position in the sensor data array defining the sensor data axis see {@Link SensorEvent.values()}
         * <P>Type: INTEGER</P>
         */
        public static final String SENSOR_DATA_AXIS = "sensor_data_axis";
     
        /**
         * Sensor raw data collected.
         * <P>Type: REAL</P>
         */
        public static final String SENSOR_RAW_DATA = "sensor_raw_data";
        
        /**
         * Id of the related tracking session. See {@link TrackingSessions._ID}
         * <P>Type: INTEGER</P>
         */
        public static final String TRACKING_SESSION_ID = "tracking_session_id";
        
    }
    
    /**
     * 
     * @author ecarrara.araujo
     *
     */
    public static class CollectedRawData implements BaseColumns, CollectedRawDataColumns {
        
        /**
         * This utility class cannot be instantiated
         */
        private CollectedRawData()  {}
        
        /**
         * The content:// style URI for this table
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, Tables.COLLECTED_RAW_DATA);
        
        /**
         * The MIME type of a directory of groups.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/collected_raws_datas";

        /**
         * The MIME type of a single group.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/collected_raw_data";
    }
}
