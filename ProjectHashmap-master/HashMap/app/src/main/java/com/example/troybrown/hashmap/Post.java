package com.example.troybrown.hashmap;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.UUID;

/**
 * Created by troybrown on 2/8/17.
 */
@DynamoDBTable(tableName = "Posts")
public class Post {
    private int mId;
    private String mTitle;
    private String mDescription;
    private Date mDate;
    private int likes;
    private boolean mSolved;
    private LatLng coordinate;


    public Post() {
        mDate = new Date();
    }

    public int getId(){return mId;}
    public void setID(int id){mId = id;}

    public String getTitle(){return mTitle;}

    public void setTitle(String title){mTitle = title;}

    public Date getDate() {return mDate;}
    public void setDate(Date date){mDate = date;}

    public boolean isSolved(){return mSolved;}
    public void setSolved(boolean solved){mSolved = solved;}

    public String getDescription(){return mDescription;}
    public void setDescription(String description){mDescription = description;}

    public LatLng getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(LatLng coordinate) {
        this.coordinate = coordinate;
    }


}
