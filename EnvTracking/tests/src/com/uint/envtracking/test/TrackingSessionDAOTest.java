package com.uint.envtracking.test;

import com.uint.envtracking.data.TrackingSession;
import com.uint.envtracking.data.TrackingSessionDAO;

import android.test.AndroidTestCase;

import java.util.List;

public class TrackingSessionDAOTest extends AndroidTestCase {
    
    private long mStartTime = System.currentTimeMillis();
    private long mEndTime = System.currentTimeMillis() + 1000;
    TrackingSession mTestEntity;
    
    protected void setUp() throws Exception {
        mTestEntity = new TrackingSession(mStartTime, mEndTime);
    }
    
    protected void tearDown()  throws Exception {
        mTestEntity = null;
    }
    
    public void testInsertTrackingSession() {
        TrackingSessionDAO.persistEntity(mContext, mTestEntity);
        List<TrackingSession> lst = TrackingSessionDAO.getAllTrackingSessions(mContext);
        boolean ok = false;
        for (TrackingSession trackingSession : lst) {
            ok = (trackingSession.getmRawStartTime() == mStartTime) && (trackingSession.getmRawEndTime() == mEndTime); 
        }
        
        assertTrue("Fail on Tracking Session Insertion", ok);
    }
    
    public void getAllTest() {
        
    }
    
    public void getOneTest() {
        
    }
    
}
