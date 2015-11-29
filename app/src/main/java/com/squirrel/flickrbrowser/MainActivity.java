package com.squirrel.flickrbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public String url = "https://api.flickr.com/services/feeds/photos_public.gne?tags=android,lollypop&format=json&nojsoncallback=1";
    private static final String LOG_TAG = "MainActivity";
    private List<Image> mImagesList = new ArrayList<Image>();
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getToolbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, new ArrayList<Image>());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        //set the listener for on image click
        mRecyclerView.addOnItemTouchListener(new ItemClickListener(this, mRecyclerView,
                new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, ViewImageActivity.class);
                intent.putExtra(IMAGE_TRANSFER, mRecyclerViewAdapter.getImage(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, ViewImageActivity.class);
                intent.putExtra(IMAGE_TRANSFER, mRecyclerViewAdapter.getImage(position));
                startActivity(intent);
            }
        }));

//        ProcessImages processImages = new ProcessImages("cat, kitty, black", true);
//        processImages.execute();
//        GetRawData getRawData = new GetRawData(url);
//        GetJsonData getRawData = new GetJsonData("android,lollipop", true);
//        getRawData.execute();

    }


    @Override
    protected void onResume() {
        super.onResume();
        //process what we get from the search
        if(mRecyclerViewAdapter != null){
            String query = getSavedPreferenceData(SEARCH_QUERY);
            if(query.length() > 0){
                ProcessImages processImages = new ProcessImages(query, true);
                processImages.execute();
            }
        }

    }

    private String getSavedPreferenceData(String s){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //if the s is null String it will return ""
        return sharedPreferences.getString(s, "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        //if the search icon is clicked
        if (id == R.id.action_search){
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class ProcessImages extends GetJsonData {

        public ProcessImages(String tags, boolean matchAllTags) {
            super(tags, matchAllTags);
        }

        public void execute(){
            super.execute();
            ProcessData processData = new ProcessData();
            processData.execute();
        }

        public class ProcessData extends DownloadJsonData{
            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);
                mRecyclerViewAdapter.setNewData(getMImagesList());
            }
        }
    }
}
