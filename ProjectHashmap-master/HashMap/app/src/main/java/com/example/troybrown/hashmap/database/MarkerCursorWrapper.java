package com.example.troybrown.hashmap.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.location.Location;

import com.example.troybrown.hashmap.Post;
import com.example.troybrown.hashmap.database.DbSchema.MarkerTable;
import com.example.troybrown.hashmap.database.DbSchema.LocationTable;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Adam on 2/20/2017.
 */

public class MarkerCursorWrapper extends CursorWrapper {
    private static final String TAG = "cursorWrapper";

    public MarkerCursorWrapper(Cursor cursor){ super(cursor);}

    public Post getPost(){
        //Log.i(TAG, "MarkerCursorWrapper --> getCountry");

        String title = getString(getColumnIndex(MarkerTable.Cols.TITLE));
        String description = getString(getColumnIndex(MarkerTable.Cols.DESCRIPTION));
        int temporary = getInt(getColumnIndex(MarkerTable.Cols.TEMPORARY));
        Double latitude = getDouble(getColumnIndex(MarkerTable.Cols.LATITUDE));
        Double longitude = getDouble(getColumnIndex(MarkerTable.Cols.LONGITUDE));
        int id = getInt(getColumnIndex(MarkerTable.Cols.UUID));

        Post post = new Post();
        post.setID(id);
        post.setTitle(title);
        post.setDescription(description);
        LatLng newLatLng = new LatLng(latitude, longitude);
        post.setCoordinate(newLatLng);
        post.setSolved(temporary != 0);

        return post;
    }

    public LatLng getLocation(){
        Double lat = getDouble(getColumnIndex(LocationTable.Cols.LATITUDE));
        Double lon = getDouble(getColumnIndex(LocationTable.Cols.LONGITUDE));

        LatLng latLng = new LatLng(lat, lon);
        return latLng;
    }
}
