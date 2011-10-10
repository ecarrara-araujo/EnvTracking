package com.uint.envtracking.data;

import java.util.Date;

/**
 * Entity Class representing one Tracking Session.
 * @author ecarrara.araujo
 *
 */
public class TrackingSession extends AbstractEntity {

    private long mRawStartTime;
    private long mRawEndTime;
    
    private Date mStartTime;
    private Date mEndTime;
    
    /**
     * Create a Tracking Session instance
     * @param id - database unique id
     * @param startTime - start time in milliseconds
     * @param endTime - end time in milliseconds
     */
    public TrackingSession(int id, long startTime, long endTime) {
        this.mId = id;
        this.mRawStartTime = startTime;
        this.mRawEndTime = endTime;
        
        convertDates();
    }
    
    /**
     * Create a Tracking Session instance yet to be persisted.
     * Id will be -1;
     * @param startTime - start time in milliseconds
     * @param endTime - end time in milliseconds
     */
    public TrackingSession(long startTime, long endTime) {
        this.mId = -1;
        this.mRawStartTime = startTime;
        this.mRawEndTime = endTime;

        convertDates();
    }
    
    private void convertDates() {
        mStartTime = new Date(mRawStartTime);
        mEndTime = new Date(mRawEndTime);
    }

    public long getmRawStartTime() {
        return mRawStartTime;
    }

    public long getmRawEndTime() {
        return mRawEndTime;
    }

    public Date getmStartTime() {
        return mStartTime;
    }

    public Date getmEndTime() {
        return mEndTime;
    }
}
