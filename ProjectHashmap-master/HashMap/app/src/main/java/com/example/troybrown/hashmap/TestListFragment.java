package com.example.troybrown.hashmap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.troybrown.hashmap.adapters.RecyclerAdapter;
import com.example.troybrown.hashmap.models.CardItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by troybrown on 2/10/17.
 */

public class TestListFragment extends Fragment {

    private List<CardItemModel> cardItems = new ArrayList<>(30);
    private RecyclerView recyclerView;
    private MainActivity appCompatActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        appCompatActivity = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_test_list, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
