package com.squirrel.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

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
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        getToolbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProcessImages processImages = new ProcessImages("cat, kitty, black", true);
        processImages.execute();


//        GetRawData getRawData = new GetRawData(url);
//        GetJsonData getRawData = new GetJsonData("android,lollipop", true);
//        getRawData.execute();


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
                mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, getMImagesList());
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
            }
        }
    }
}
