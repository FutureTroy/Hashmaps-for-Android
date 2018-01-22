package com.example.troybrown.hashmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by troybrown on 2/10/17.
 */

public class AddPostActivity extends AppCompatActivity {

    private LinearLayout mContainerLayout;
    private String theme;
    private Toolbar mToolbar;
    private FloatingActionButton FAB;
    private final static String TAB_POSTLIST_FRAGMENT_TAG = "tab_postlist";
    private EditText mTitleInput;
    private EditText mContentInput;
    private SwitchCompat mTemporary;
    PostDatabase posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        posts = PostDatabase.get(this);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);

        mTemporary = (SwitchCompat) findViewById(R.id.switchEvent);

        mTitleInput = (EditText)findViewById(R.id.edTxt_user_title);
        mContentInput = (EditText)findViewById(R.id.edit_content);

        mTitleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s == null | s.toString().equals("")){
                    //Title error : can't be emppy
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mContentInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence==null||charSequence.toString().equals("")){
                    //contentInputLayout.setError(getString(R.string.edittext_error));
                }else{
                    //contentInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        FAB = (FloatingActionButton)findViewById(R.id.fabDone);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isEmpty()){
                    Intent data = new Intent();
                    data.putExtra("title", mTitleInput.getText().toString());
                    data.putExtra("description", mContentInput.getText().toString());
                    data.putExtra("temporary", mTemporary.isChecked());
                    data.putExtra("location", posts.getLocation());
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }

            }
        });
        //setSupportActionBar(mToolbar);
    }

    private boolean isEmpty(){
        String titleText = ((EditText)findViewById(R.id.edTxt_user_title))
                .getText().toString().trim();
        String contentText = ((EditText)findViewById(R.id.edit_content))
                .getText().toString().trim();

        if((titleText==null||titleText.equals(""))&&(contentText==null||contentText.equals(""))){
            //Snackbar.make(findViewById(R.id.),
            // getString(R.string.title_content_error),Snackbar.LENGTH_LONG).show();
            return true;
        }else if(titleText==null||titleText.equals("")){
            //Snackbar.make(findViewById(R.id.fab_coordinator_layout),
            // getString(R.string.titletext_error),Snackbar.LENGTH_LONG).show();
            return true;
        }else if(contentText==null||contentText.equals("")){
            //Snackbar.make(findViewById(R.id.fab_coordinator_layout),
            //getString(R.string.contenttext_error),Snackbar.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

}
