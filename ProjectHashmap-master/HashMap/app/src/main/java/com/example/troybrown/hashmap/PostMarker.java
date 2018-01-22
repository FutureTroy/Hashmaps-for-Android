package com.example.troybrown.hashmap;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

import java.util.Date;
import java.util.UUID;

/**
 * Created by justinlarkin on 4/19/17.
 */

@DynamoDBTable(tableName = "PostMarkers")
public class PostMarker {
    private int id;
    private String title;
    private String description;
    private String date;
    private int likes;
    private boolean temp;
    private Double latitude;
    private Double longitude;

    @DynamoDBHashKey(attributeName = "ID")
    public int getId() {return id;}
    public void setId(int id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "Title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDBAttribute(attributeName = "Description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBRangeKey(attributeName = "Date")
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @DynamoDBAttribute(attributeName = "Likes")
    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }

    @DynamoDBAttribute(attributeName = "Temp")
    public boolean isTemp() {
        return temp;
    }
    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    @DynamoDBAttribute(attributeName = "Latitude")
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @DynamoDBAttribute(attributeName = "Longitude")
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
