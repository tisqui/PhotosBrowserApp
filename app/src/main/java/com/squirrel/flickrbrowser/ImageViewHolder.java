package com.squirrel.flickrbrowser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by squirrel on 11/27/15.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {
    protected ImageView thumbnailImage;
    protected TextView titleOfImage;

    public ImageViewHolder(View itemView) {
        super(itemView);
        this.thumbnailImage = (ImageView) itemView.findViewById(R.id.thumbnailImage);
        this.titleOfImage = (TextView) itemView.findViewById(R.id.titleOfImage);
    }


}
