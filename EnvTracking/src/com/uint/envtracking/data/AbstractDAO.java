package com.uint.envtracking.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class AbstractDAO {

    protected static SQLiteDatabase getDatabase(Context context) {
        EnTDatabaseHelper dbHelper = EnTDatabaseHelper.getInstance(context);
        return dbHelper.getReadableDatabase();
    }
}
