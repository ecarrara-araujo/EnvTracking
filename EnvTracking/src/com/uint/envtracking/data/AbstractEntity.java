package com.uint.envtracking.data;

public class AbstractEntity {

    protected int mId;
    
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
}
