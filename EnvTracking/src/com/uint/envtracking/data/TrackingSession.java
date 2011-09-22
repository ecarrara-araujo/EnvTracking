package com.uint.envtracking.data;

import java.util.Date;

/**
 * Entity Class representing one Tracking Session.
 * @author ecarrara.araujo
 *
 */
public class TrackingSession {

    private int mId;
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

    public int getId() {
        return mId;
    }
    
    /**
     * The id will only be updated when the old value was -1.
     * This must be used only when a new Entity was inserted in the database and an id was generated to it.
     * @param newId
     */
    public void setId(int newId) {
        if(mId < 0) {
            mId = newId;
        }
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
