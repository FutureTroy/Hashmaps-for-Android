package com.example.troybrown.hashmap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.troybrown.hashmap.Post;
import com.example.troybrown.hashmap.R;
import com.example.troybrown.hashmap.models.CardItemModel;

import java.util.List;

/**
 * Created by troybrown on 2/10/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    public List<Post> posts;

    public RecyclerAdapter(List<Post> posts) { this.posts = posts; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        public ViewHolder(View itemView){
            super(itemView);
            this.title = (TextView)itemView.findViewById(R.id.card_title);
            this.content = (TextView)itemView.findViewById(R.id.card_content);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.title.setText("THIS IS A TITLE");
        holder.content.setText(posts.get(position).getDescription());
    }

    @Override
    public int getItemCount() {return posts.size();}
}
