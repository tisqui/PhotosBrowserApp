package com.squirrel.flickrbrowser;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by squirrel on 11/27/15.
 */
public class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    public final String SEARCH_QUERY = "QUERY";

    protected Toolbar getToolbar(){
        if(mToolbar == null){
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            if(mToolbar != null){
                setSupportActionBar(mToolbar);
            }
        }
        return mToolbar;
    }

    protected Toolbar activateToolbarWithHomeEnabled(){
        getToolbar();
        if(mToolbar!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return mToolbar;
    }

}
