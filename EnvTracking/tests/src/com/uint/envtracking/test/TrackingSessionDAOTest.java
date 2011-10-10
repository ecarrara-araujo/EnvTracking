package com.uint.envtracking.test;

import com.uint.envtracking.data.TrackingSession;
import com.uint.envtracking.data.TrackingSessionDAO;

import android.test.AndroidTestCase;

import java.util.List;

public class TrackingSessionDAOTest extends AndroidTestCase {
    
    private final long START_TIME_REFERENCE = System.currentTimeMillis();
    private final long END_TIME_REFERENCE = System.currentTimeMillis() + 1000;
    
    private final int TRACKING_SESSIONS_REFERENCE_ID = 0;
    
    private final int TIME_MODIFICATOR = 5;
    private final long TIME_INCREMENT = 5000;
    
    TrackingSession[] mTestEntitiesList;
    
    protected void setUp() throws Exception {
        mTestEntitiesList = new TrackingSession[10];
        for (int i = 0; i < mTestEntitiesList.length; i++) {
            mTestEntitiesList[i] = new TrackingSession(calculateStartTime(i), calculateEndTime(i));
            TrackingSessionDAO.persistEntity(mContext, mTestEntitiesList[i]);
        }
    }
    
    protected void tearDown()  throws Exception {
        TrackingSessionDAO.deleteAllEntities(mContext);
        mTestEntitiesList = null;
    }
    
    private long calculateStartTime(int uniqueIndex) {
        return calculateTime(uniqueIndex, START_TIME_REFERENCE);
    }
    
    private long calculateEndTime(int uniqueIndex) {
        return calculateTime(uniqueIndex, END_TIME_REFERENCE);
    }
    
    private long calculateTime(int uniqueIndex, long referenceTime) {
        return referenceTime + (TIME_MODIFICATOR * TIME_INCREMENT * uniqueIndex);
    }
    
    public void testInsertTrackingSession() {
        List<TrackingSession> lst = TrackingSessionDAO.getAllTrackingSessions(mContext);
        boolean ok = false;
        for (TrackingSession trackingSession : lst) {
            ok = (trackingSession.getmRawStartTime() == calculateStartTime(TRACKING_SESSIONS_REFERENCE_ID)) && (trackingSession.getmRawEndTime() == calculateEndTime(TRACKING_SESSIONS_REFERENCE_ID));
            if(ok)
                break;
        }
        
        assertTrue("Fail on Tracking Session Insertion", ok);
    }
    
    public void testGetAll() {      
        List<TrackingSession> lst = TrackingSessionDAO.getAllTrackingSessions(mContext);
        
        assertEquals("The number of inserted items do not match.", mTestEntitiesList.length, lst.size());
        
        boolean[] results = new boolean[mTestEntitiesList.length];
        
        for (TrackingSession trackingSession : lst) {
            for (int i = 0; i < mTestEntitiesList.length; i++) {
                boolean ok = (trackingSession.getmRawStartTime() == mTestEntitiesList[i].getmRawStartTime()) && (trackingSession.getmRawEndTime() == mTestEntitiesList[i].getmRawEndTime());
                if(ok) {
                    results[i] = ok;
                    break;
                }
            }
        }
        
        for (int i = 0; i < results.length; i++) {
            assertTrue("Fail on Tracking Session Insertion", results[i]);
        }
    }
    
    public void testGetOne() {
        TrackingSession ts = TrackingSessionDAO.getTrackingSession(mContext, mTestEntitiesList[TRACKING_SESSIONS_REFERENCE_ID].getId());
        
        assertTrue((mTestEntitiesList[TRACKING_SESSIONS_REFERENCE_ID].getmRawStartTime() == calculateStartTime(TRACKING_SESSIONS_REFERENCE_ID)) && (mTestEntitiesList[TRACKING_SESSIONS_REFERENCE_ID].getmRawEndTime() == calculateEndTime(TRACKING_SESSIONS_REFERENCE_ID)));
    }
    
    public void testDeleteOne() {
        TrackingSessionDAO.deleteEntity(mContext, mTestEntitiesList[TRACKING_SESSIONS_REFERENCE_ID]);
        
        List<TrackingSession> lst = TrackingSessionDAO.getAllTrackingSessions(mContext);
        boolean ok = false;
        for (TrackingSession trackingSession : lst) {
            ok = (trackingSession.getmRawStartTime() == calculateStartTime(TRACKING_SESSIONS_REFERENCE_ID)) && (trackingSession.getmRawEndTime() == calculateEndTime(TRACKING_SESSIONS_REFERENCE_ID)); 
        }
        
        assertFalse("The tracking session was not deleted.", ok);
    }
    
    public void testDeleteAll() {
        int currentNumberOfTrakincSessions = TrackingSessionDAO.getAllTrackingSessions(mContext).size();
        
        assertTrue("Number of tracking sessions in db for this test must be higher than 0.", currentNumberOfTrakincSessions > 0);
        
        TrackingSessionDAO.deleteAllEntities(mContext);
        
        currentNumberOfTrakincSessions = TrackingSessionDAO.getAllTrackingSessions(mContext).size();
        
        assertTrue(currentNumberOfTrakincSessions == 0);
    }
    
}
