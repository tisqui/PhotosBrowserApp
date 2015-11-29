package com.squirrel.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by squirrel on 11/27/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private List<Image> mImagesList;
    private Context mContext;
    private final String LOG_TAG = RecyclerViewAdapter.class.getSimpleName();

    public RecyclerViewAdapter(Context context, List<Image> mImagesList) {
        mContext = context;
        this.mImagesList = mImagesList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        Image imageItem = mImagesList.get(position);
        Log.d(LOG_TAG, "Processing the item: " + imageItem.getmTitle() + " " + position);
        Picasso.with(mContext).load(imageItem.getmLink())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnailImage);
        holder.titleOfImage.setText(imageItem.getmTitle());

    }

    @Override
    public int getItemCount() {
        return (null != mImagesList ? mImagesList.size() : 0);
    }

    public void setNewData (List<Image> newImages){
        mImagesList = newImages;
        notifyDataSetChanged();
    }

    public Image getImage(int position){
        return (null != mImagesList ? mImagesList.get(position) : null);
    }
}
