package com.squirrel.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by squirrel on 11/28/15.
 */
public class ItemClickListener implements RecyclerView.OnItemTouchListener {

    public static interface OnItemClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;
    private GestureDetector mGestureDetector;

    public ItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            public boolean onSingleTapUp(MotionEvent motionEvent){
                Log.d("Catching", "onSingleTapApp");
                return true;
            }

            public void onLongPress(MotionEvent motionEvent){
                View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                Log.d("Catching", "Called onLongPress");
                if(view != null && mOnItemClickListener != null){
                    mOnItemClickListener.onItemLongClick(view, recyclerView.getChildLayoutPosition(view));
                }
            }
        });
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.d("Catching", "Called onRequestDisallowInterceptTouchEvent");
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(), e.getY());
        if( view != null && mOnItemClickListener != null && mGestureDetector.onTouchEvent(e)){
            mOnItemClickListener.onItemClick(view, rv.getChildLayoutPosition(view));
            //return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d("Catching", "Called onTouchEvent");
    }
}
