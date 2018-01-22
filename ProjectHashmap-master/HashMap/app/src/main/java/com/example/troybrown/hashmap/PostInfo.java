package com.example.troybrown.hashmap;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by A on 4/23/2017.
 */

public class PostInfo extends AppCompatActivity {
    PostDatabase pd;
    String poi = "nearby location";
    Post post;
    TextView description;
    TextView markerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_info);

        pd = PostDatabase.get(this);

        Intent intent = getIntent();
        String markerTitle = intent.getStringExtra("title");
        int markerID = Integer.parseInt(intent.getStringExtra("id"));

        post = new Post();
        post.setDescription("failed to find post.");
        List<Post> posts = pd.getPosts();
        for(Post tempPost: posts){
            System.out.println("-----------");
            System.out.println(markerID);
            System.out.println("-----------");
            System.out.println(tempPost.getId());
            System.out.println("-----------");
            System.out.println(tempPost.getTitle());
            if(tempPost.getId() == markerID){
                post = tempPost;
            }
        }
        TextView title = (TextView) findViewById(R.id.markerTitle);
        title.setText(markerTitle);
        description = (TextView) findViewById(R.id.markerDescription);
        description.setText(post.getDescription());
        markerLocation = (TextView) findViewById(R.id.markerLocation);
        new getPOI().execute();
    }

    class getPOI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String returnedData = "here";
            try {
                String urlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyBdpLjcDx30QuTRmeyaVbn6wZclQXSaPrw&location=" +
                        post.getCoordinate().latitude + "," + post.getCoordinate().longitude + "&type=school&rankby=distance";
                DownloadURL info = new DownloadURL();
                returnedData = info.readUrl(urlString);

            }catch(Exception e){}
            return returnedData;
        }

        @Override
        protected void onPostExecute(String result){
            String finalName = "";
            try {
                JSONObject json = new JSONObject(result);
                JSONArray jsonArray = json.getJSONArray("results");
                JSONObject jsonName = jsonArray.getJSONObject(0);
                finalName = jsonName.getString("name");
                markerLocation.setText("@"+finalName);

            }catch (Exception e){}
        }
    }
}