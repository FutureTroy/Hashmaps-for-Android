package com.example.troybrown.hashmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.troybrown.hashmap.R.styleable.View;

/**
 * Created by troybrown on 2/8/17.
 */

public class PostListFragment extends Fragment {
    private static final String TAG = "post_list_FRAGMENT";

    private RecyclerView mPostRecyclerView;
    private PostAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.i(TAG,"onCreateView");

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mPostRecyclerView = (RecyclerView) view.findViewById(R.id.post_recycler_view);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    public void onResume(){
        Log.i(TAG, "onResume");

        super.onResume();;
        updateUI();
    }

    private void updateUI(){
        PostDatabase postDatabse = PostDatabase.get(getActivity());
        List<Post> posts = postDatabse.getPosts();

        if(mAdapter == null) {
            mAdapter = new PostAdapter(posts);
            mPostRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "PostHolder";
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Post mPost;

        public PostHolder (View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Log.i(TAG,"PostHolder --> OnClickListener set");

            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_post_title_text_view);
            mDateTextView = (TextView)itemView.findViewById(R.id.list_item_post_date_text_view);
            mSolvedCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_post_solved_checkbox);
        }

        public void bindPost(Post post){
            mPost = post;
            //Log.i(TAG,"")
            mTitleTextView.setText(mPost.getTitle());
            mDateTextView.setText(mPost.getDate().toString());
            mSolvedCheckBox.setChecked(mPost.isSolved());
        }

        @Override
        public void onClick(View v){
            Log.i(TAG,"PostHolder --> onClick");

            Intent intent = PostActivity.newIntent(getActivity(), mPost.getId());
            startActivity(intent);
        }
    }

    private class PostAdapter extends RecyclerView.Adapter<PostHolder>{
        private static final String TAG = "PostAdapter";
        private List<Post> mPosts;
        public PostAdapter(List<Post> posts){ mPosts = posts; }

        @Override
        public PostHolder onCreateViewHolder(ViewGroup parent, int viewType){
            Log.i(TAG, "PostAdapter -->onCreateView");

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_post, parent, false);
            return new PostHolder(view);
        }

        @Override
        public void onBindViewHolder(PostHolder holder, int postion) {
            Log.i(TAG, "PostAdapter --> onBindViewHolder");

            Post post = mPosts.get(postion);

            holder.bindPost(post);
        }
        public int getItemCount(){return mPosts.size();}
    }
}
