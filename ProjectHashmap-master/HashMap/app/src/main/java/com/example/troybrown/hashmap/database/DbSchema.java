package com.example.troybrown.hashmap.database;

/**
 * Created by Adam on 2/20/2017.
 */

public class DbSchema {

    public static final class MarkerTable{
        public static final String NAME = "markers";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String TEMPORARY = "temporary";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
        }
    }

    public static final class LocationTable{
        public static final String NAME = "location";

        public static final class Cols{
            public static final String LONGITUDE = "longitude";
            public static final String LATITUDE = "latitude";
        }
    }
}
