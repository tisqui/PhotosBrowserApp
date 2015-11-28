package com.squirrel.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by squirrel on 11/26/15.
 */

enum DownloadStatus {IDLE,PROCESSING,NOT_INITIALIZED,FAILED,VALID}

public class GetRawData {
    private String LOG_TAG = GetRawData.class.getSimpleName();
    private String mRawURL;
    private String mData;
    private DownloadStatus mStatus;

    public GetRawData(String mRawURL) {
        this.mRawURL = mRawURL;
        this.mStatus = DownloadStatus.IDLE;
    }

    public void reset(){
        this.mStatus=DownloadStatus.IDLE;
        this.mRawURL = null;
        this.mData = null;
    }

    public String getmData() {
        return mData;
    }

    public DownloadStatus getmStatus() {
        return mStatus;
    }

    public void setmRawURL(String mRawURL) {
        this.mRawURL = mRawURL;
    }

    public void execute()
    {
        this.mStatus = DownloadStatus.PROCESSING;
        DownloadData downloadData = new DownloadData();
        downloadData.execute(mRawURL);

    }

    public class DownloadData extends AsyncTask<String, Void, String> {

        protected void onPostExecute(String data){
            mData = data;
            Log.v(LOG_TAG, "Data got from the stream: " + mData);
            if(mData == null){
                //if the URL was not passed
                if(mRawURL == null){
                    mStatus = DownloadStatus.NOT_INITIALIZED;
                } else {
                    mStatus = DownloadStatus.FAILED;
                }
            } else {
                //Success
                mStatus = DownloadStatus.VALID;
            }
        }


        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection httpConnection = null;
            BufferedReader bufferedReader = null;

            if(params==null)
            return null;

            try {
                URL url = new URL(params[0]);
                httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
                InputStream inputStream = httpConnection.getInputStream();
                if(inputStream==null){
                    return null;
                }
                StringBuffer buff = new StringBuffer();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String lineOfText;

                while((lineOfText = bufferedReader.readLine()) != null){
                    buff.append(lineOfText+"\n");
                }

                return buff.toString();

            }catch(IOException e){
                Log.e(LOG_TAG, "Error", e);
                return null;

            }finally{
                if(httpConnection != null){
                    httpConnection.disconnect();
                }
                if(bufferedReader != null){
                    try{
                        bufferedReader.close();

                    }catch (IOException e){
                        Log.e(LOG_TAG, "Error closing bufferedReader", e);
                    }
                }
            }

        }
    }
}
