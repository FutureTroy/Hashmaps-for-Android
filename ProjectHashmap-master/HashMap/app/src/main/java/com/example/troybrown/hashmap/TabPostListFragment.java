package com.example.troybrown.hashmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.troybrown.hashmap.adapters.RecyclerAdapter;
import com.example.troybrown.hashmap.models.CardItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by troybrown on 2/10/17.
 */

public class TabPostListFragment extends Fragment {

    private List<Post> posts = new ArrayList<>();
    private RecyclerView recyclerView;
    private MainActivity appCompatActivity;
    private PostAdapter recyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_postlist, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        updateUI();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        PostDatabase postDatabase = PostDatabase.get(getActivity());
        List<Post> posts = postDatabase.getPosts();
        recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        recyclerView.setHasFixedSize(true);
        recyclerAdapter = new PostAdapter(posts);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void updateList(){
        recyclerView.setAdapter(new RecyclerAdapter(posts));
    }

    private class PostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private Post mPost;

        public PostHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)itemView.findViewById(R.id.card_title);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.card_content);
        }

        public void bindPost(Post post) {
            mPost = post;
            mTitleTextView.setText(mPost.getTitle());
            mDescriptionTextView.setText(mPost.getDescription());
        }

        @Override
        public void onClick(View v) {
            //Intent intent = CountryActivity.newIntent(getActivity(), mCountry.getId());
            //startActivity(intent);
        }
    }

    private class PostAdapter extends RecyclerView.Adapter<PostHolder> {
        private static final String TAG = "PostAdapter";
        private List<Post> mPosts;
        private int start = 0;
        public PostAdapter(List<Post> posts) {
            mPosts = posts;
        }

        /* called by RecyclerView when it needs a new View to display an item */
        @Override
        public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           /*LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
           View view = layoutInflater.inflate(R.layout.recycler_item, parent, false);*/
           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
           return new PostHolder(view);
        }


        @Override
        public void onBindViewHolder(PostHolder holder, int position) {
            Post post = mPosts.get(position);
            holder.bindPost(post);
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }

        public void setPosts(List<Post> posts) {
            mPosts = posts;
        }
    }
}
