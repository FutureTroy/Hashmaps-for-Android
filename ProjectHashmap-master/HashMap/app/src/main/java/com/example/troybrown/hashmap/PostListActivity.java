package com.example.troybrown.hashmap;

import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by troybrown on 2/8/17.
 */

public class PostListActivity extends SingleFragmentActivity {
    private static final String TAG = "post_list_ACTIVITY";

    @Override
    protected Fragment createFragment(){
        Log.i(TAG, "--> createFragment");
        return new PostListFragment();
    }
}
