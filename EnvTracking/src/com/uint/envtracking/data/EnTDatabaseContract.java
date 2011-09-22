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
    }
    
    /**
     * Columns of {@link EnvTrackingContract.TrackingSessions} that refer to
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
}
