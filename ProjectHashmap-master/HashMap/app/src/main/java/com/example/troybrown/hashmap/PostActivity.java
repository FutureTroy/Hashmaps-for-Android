package com.example.troybrown.hashmap;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by troybrown on 2/8/17.
 */

public class PostActivity extends SingleFragmentActivity {
    private static final String TAG = "post_ACTIVITY";

    public static final String EXTRA_POST_ID =
            "com.example.troybrown.hashmap.post_id";

    public static Intent newIntent(Context packageContext, int postId) {
        Intent intent = new Intent(packageContext, PostActivity.class);
        intent.putExtra(EXTRA_POST_ID,postId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID postId = (UUID) getIntent().getSerializableExtra(EXTRA_POST_ID);
        return PostFragment.newInstance(postId);
    }
}
