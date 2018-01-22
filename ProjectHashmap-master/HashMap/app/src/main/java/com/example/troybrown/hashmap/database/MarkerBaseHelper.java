package com.example.troybrown.hashmap.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.troybrown.hashmap.Post;
import com.example.troybrown.hashmap.database.DbSchema.MarkerTable;
import com.example.troybrown.hashmap.database.DbSchema.LocationTable;
import com.google.android.gms.maps.model.Marker;

import static java.security.AccessController.getContext;

/**
 * Created by Adam on 2/20/2017.
 */

public class MarkerBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "markerBaseHelper";
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "markerDatabase.db";
    public MarkerBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
        context.deleteDatabase(DATABASE_NAME);
        //Log.i(TAG, " created database "+DATABASE_NAME);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //Log.i(TAG, "onCreate -> creating DB");
        db.execSQL("create table " + MarkerTable.NAME + " (" +
                MarkerTable.Cols.UUID + ", " +
                MarkerTable.Cols.TITLE + ", " +
                MarkerTable.Cols.DESCRIPTION + ", " +
                MarkerTable.Cols.TEMPORARY + ", " +
                MarkerTable.Cols.LATITUDE + ", " +
                MarkerTable.Cols.LONGITUDE +
                ")"
        );

        db.execSQL("create table " + LocationTable.NAME + " (" +
                LocationTable.Cols.LATITUDE + ", " +
                LocationTable.Cols.LONGITUDE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

    public int getRowCount(SQLiteDatabase db){
        String rowCount = "SELECT * FROM "+ MarkerTable.NAME;
        Cursor cursor  = db.rawQuery(rowCount, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
}
