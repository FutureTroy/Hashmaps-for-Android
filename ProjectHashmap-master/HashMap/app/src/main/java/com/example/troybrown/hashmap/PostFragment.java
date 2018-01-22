package com.example.troybrown.hashmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by troybrown on 2/8/17.
 */

public class PostFragment extends Fragment {
    private static final String TAG = "post_FRAGMENT";

    private static final String ARG_POST_ID = "post_id";
    private Post mPost;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public static PostFragment newInstance(UUID postId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_POST_ID,postId);
        PostFragment fragment = new PostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "--> onCreate");
        super.onCreate(savedInstanceState);

        UUID postId = (UUID) getArguments().getSerializable(ARG_POST_ID);
        mPost = PostDatabase.get(getActivity()).getPost(postId);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "--> onCreateView");

        View v = inflater.inflate(R.layout.fragment_post, container, false);
        mTitleField = (EditText)v.findViewById(R.id.post_title);
        mTitleField.setText(mPost.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPost.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button)v.findViewById(R.id.post_date);
        mDateButton.setText(mPost.getDate().toString());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.post_solved);
        mSolvedCheckBox.setChecked(mPost.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                mPost.setSolved(isChecked);
            }
        } );
        return v;
    }
}
