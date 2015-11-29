package com.squirrel.flickrbrowser;

import java.io.Serializable;

/**
 * Created by squirrel on 11/26/15.
 */
public class Image implements Serializable{

    private final Long serialVersion = 1L;
    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mImage;
    private String mLink;
    private String mTags;
    private String mDate;

    public Image(String mTitle, String mAuthor, String mAuthorId, String mImage, String mLink, String mTags, String mDate) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mImage = mImage;
        this.mLink = mLink;
        this.mTags = mTags;
        this.mDate = mDate;
        this.mAuthorId = mAuthorId;
    }

    public Long getSerialVersion() {
        return serialVersion;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmImage() {
        return mImage;
    }

    public String getmLink() {
        return mLink;
    }

    public String getmTags() {
        return mTags;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmAuthorId() {
        return mAuthorId;
    }

    @Override
    public String toString() {
        return "Image{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mDate='" + mDate + '\'' +
                '}';
    }
}
