package com.squirrel.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by squirrel on 11/26/15.
 * Processing data from flickr feed https://api.flickr.com/services/feeds/photos_public.gne
 */

public class GetJsonData extends GetRawData{

    private String LOG_TAG = GetRawData.class.getSimpleName();
    private List<Image> mImagesList;
    private Uri mUri;

    /**
     * @param tags A comma delimited list of tags to filter the feed by.
     * @param matchAllTags - Control whether items must have ALL the tags (matchAllTags=true),
     *                     or ANY (matchAllTags=false) of the tags.
     */
    public GetJsonData(String tags, boolean matchAllTags) {
        super(null);
        createUrl(tags, matchAllTags);
        mImagesList = new ArrayList<Image>();
    }

    public List<Image> getMImagesList() {
        return mImagesList;
    }

    public void execute(){
        super.setmRawURL(mUri.toString());

        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "The URL created: " + mUri);

        downloadJsonData.execute(mUri.toString());
    }

    public boolean createUrl(String tags, boolean matchAllTags){
        final String BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAG_MODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_CALLBACK_PARAM = "nojsoncallback";

        mUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(TAGS_PARAM,tags)
                                                .appendQueryParameter(TAG_MODE_PARAM, matchAllTags ? "ALL" : "ANY")
                                                .appendQueryParameter(FORMAT_PARAM,"json")
                                                .appendQueryParameter(NO_CALLBACK_PARAM,"1")
                                                .build();
        return mUri != null; //to be sure we got the valid url
    }

    public void process(){
        if(getmStatus() != DownloadStatus.VALID){
            Log.e(LOG_TAG,"Data was not downloaded.");
            return;
        }

        final String ITEMS_OBJ = "items";
        final String TITLE = "title";
        final String MEDIA_OBJ = "media";
        final String IMAGE_URL = "m";
        final String AUTHOR = "author";
        final String AUTHOR_ID = "author_id";
        final String LINK = "link";
        final String TAGS = "tags";
        final String DATE = "date_taken";

        try {
            JSONObject inputJson = new JSONObject(getmData());
            JSONArray jsonArray = inputJson.getJSONArray(ITEMS_OBJ);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject imageObj = jsonArray.getJSONObject(i);
                String title = imageObj.getString(TITLE);
                String author = imageObj.getString(AUTHOR);
                String author_id = imageObj.getString(AUTHOR_ID);
                String link = imageObj.getString(LINK);
                String tags = imageObj.getString(TAGS);
                String date = imageObj.getString(DATE);
                JSONObject mediaObj = imageObj.getJSONObject(MEDIA_OBJ);
                String image_url = mediaObj.getString(IMAGE_URL);

                //String mTitle, String mAuthor, String mAuthorId, String mImage, String mLink, String mTags, String mDate
                Image image = new Image(title, author, author_id, image_url, link, tags, date);

                this.mImagesList.add(image);
            }

            for(Image image : mImagesList){
                Log.i(LOG_TAG, image.toString());
            }

        }catch (JSONException e){
            e.printStackTrace();
            Log.e(LOG_TAG, "Something went wrong! Error procesing JSON.");
        }

    }

    public class DownloadJsonData extends DownloadData {

        @Override
        protected void onPostExecute(String data){
            super.onPostExecute(data);
            process();
        }

        @Override
        protected String doInBackground(String... params) {
            String[] par = { mUri.toString() };

            return super.doInBackground(par);
        }
    }

}
