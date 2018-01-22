package com.example.troybrown.hashmap;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.troybrown.hashmap.database.DbSchema;
import com.example.troybrown.hashmap.database.DbSchema.MarkerTable;
import com.example.troybrown.hashmap.database.DbSchema.LocationTable;
import com.example.troybrown.hashmap.database.MarkerBaseHelper;
import com.example.troybrown.hashmap.database.MarkerCursorWrapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by troybrown on 2/8/17.
 */

public class PostDatabase {
    private static PostDatabase sPostDatabase;
    private static Location location;
    private static Context mContext;
    public SQLiteDatabase mDatabase;
    AmazonDynamoDBClient ddbClient;
    DynamoDBMapper mapper = null;

    public static PostDatabase get(Context context){
        if (sPostDatabase == null){
            sPostDatabase = new PostDatabase(context);
        }
        return sPostDatabase;
    }

    private PostDatabase(Context context) {

        mContext = context.getApplicationContext();
        /*mDatabase = new MarkerBaseHelper(mContext).getWritableDatabase();
        Cursor crs = mDatabase.rawQuery("SELECT * FROM "+MarkerTable.NAME, null);
        int rows = crs.getCount();
        if(rows >= 1){return;}

        Post testPost = new Post();
        testPost.setID(getSize()+1);
        testPost.setTitle("Test post");
        testPost.setDescription("Please ignore.");
        testPost.setSolved(false);
        testPost.setCoordinate(new LatLng(0,0));
        addPost(testPost);
        addLocation(0.0,0.0);*/

        //Creates credentials provider for Cognitio Identity
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(mContext,
                "us-east-1:0dd7364c-e250-447d-b380-cb09c66cb30f",
                Regions.US_EAST_1);

        //DynamoDB
        ddbClient = new AmazonDynamoDBClient(credentialsProvider);

        mapper = new DynamoDBMapper(ddbClient);
        context.deleteDatabase("markerDatabase.db");
        mDatabase = new MarkerBaseHelper(mContext).getWritableDatabase();

        new RetrievePosts().execute();
    }

    public Post getPost(UUID id){
        MarkerCursorWrapper cursor = queryPosts(
                MarkerTable.Cols.UUID + " = ?",
                new String[] { id.toString()}
        );
        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getPost();
        } finally {
            cursor.close();
        }
    }

    public LatLng getLocation(){
        LatLng latlng;
        MarkerCursorWrapper cursor = queryLocation(null, null);
        try{
            cursor.moveToLast();
            latlng = cursor.getLocation();
        } finally{
            cursor.close();
        }
        return latlng;
    }

    public List<Post> getPosts(){
        List<Post> posts = new ArrayList<>();
        MarkerCursorWrapper cursor = queryPosts(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                posts.add(cursor.getPost());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return posts;
    }

    private MarkerCursorWrapper queryPosts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MarkerTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new MarkerCursorWrapper(cursor);
    }

    private MarkerCursorWrapper queryLocation(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                LocationTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new MarkerCursorWrapper(cursor);
    }

    public void addPost(final Post newPost){
        ContentValues values = getContentValues(newPost);
        mDatabase.insert(MarkerTable.NAME, null, values);
        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here
                PostMarker postMarker = new PostMarker();
                postMarker.setId(newPost.getId());
                postMarker.setTitle(newPost.getTitle());
                postMarker.setDescription(newPost.getDescription());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                postMarker.setDate(df.format(newPost.getDate()));
                postMarker.setLatitude(newPost.getCoordinate().latitude);
                postMarker.setLongitude(newPost.getCoordinate().longitude);
                //postMarker.setLikes(newPost.getLikes());

                mapper.save(postMarker);
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

    public void addLocation(Double lat, Double lon){
        ContentValues values = getLocationValues(lat, lon);
        mDatabase.insert(LocationTable.NAME, null, values);
    }

    public void updateLocation(Double lat, Double lon){
        mDatabase.execSQL("UPDATE TOP(1) "+LocationTable.NAME+ " SET latitude="+lat+",longitude="+lon+";");
    }

    private static ContentValues getContentValues(Post post){
        ContentValues values = new ContentValues();
        values.put(MarkerTable.Cols.UUID, post.getId());
        values.put(MarkerTable.Cols.TITLE, post.getTitle());
        values.put(MarkerTable.Cols.DESCRIPTION, post.getDescription());
        values.put(MarkerTable.Cols.TEMPORARY, post.isSolved());
        values.put(MarkerTable.Cols.LATITUDE, post.getCoordinate().latitude);
        values.put(MarkerTable.Cols.LONGITUDE, post.getCoordinate().longitude);
        return values;
    }

    private static ContentValues getLocationValues(Double lat, Double lon){
        ContentValues values = new ContentValues();
        values.put(LocationTable.Cols.LATITUDE, lat);
        values.put(LocationTable.Cols.LONGITUDE, lon);
        return values;
    }

    private void addPostMarker(PostMarker newPost){
        ContentValues values = getContentValuesPostMarker(newPost);
        mDatabase.insert(MarkerTable.NAME, null, values);
    }

    private static ContentValues getContentValuesPostMarker(PostMarker postM){
        Post post = new Post();
        post.setID(postM.getId());
        post.setTitle(postM.getTitle());
        post.setCoordinate(new LatLng(postM.getLatitude(), postM.getLongitude()));
        SimpleDateFormat asdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            post.setDate(asdf.parse(postM.getDate()));
        }
        catch (Exception e){}
        post.setDescription(postM.getDescription());
        post.setSolved(false);

        ContentValues values = new ContentValues();
        values.put(MarkerTable.Cols.UUID, post.getId());
        values.put(MarkerTable.Cols.TITLE, post.getTitle());
        values.put(MarkerTable.Cols.DESCRIPTION, post.getDescription());
        values.put(MarkerTable.Cols.TEMPORARY, post.isSolved());
        values.put(MarkerTable.Cols.LATITUDE, post.getCoordinate().latitude);
        values.put(MarkerTable.Cols.LONGITUDE, post.getCoordinate().longitude);
        return values;
    }

    class RetrievePosts extends AsyncTask<String, Void, PaginatedList<PostMarker>> {
        protected PaginatedList<PostMarker> doInBackground(String... what){

            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            PaginatedList<PostMarker> result = mapper.scan(PostMarker.class, scanExpression);
            for(PostMarker marker: result){
                addPostMarker(marker);
            }
            return result;
        }
    }

    public int getSize(){
        Cursor crs = mDatabase.rawQuery("SELECT * FROM "+MarkerTable.NAME, null);
        int rows = crs.getCount();
        return rows;
    }
}