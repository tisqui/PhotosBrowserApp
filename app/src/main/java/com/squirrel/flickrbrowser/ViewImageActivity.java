package com.squirrel.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewImageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        activateToolbarWithHomeEnabled();
        //getting the data which was passed about the item in the list clicked
        Intent intent = getIntent();
        Image image = (Image) intent.getSerializableExtra(IMAGE_TRANSFER);

        TextView imageTitle = (TextView) findViewById(R.id.image_title);
        imageTitle.setText(image.getmTitle());

        TextView imageTags = (TextView) findViewById(R.id.image_tags);
        imageTags.setText(image.getmTags());

        TextView imageAuthor = (TextView) findViewById(R.id.image_author);
        imageAuthor.setText(image.getmAuthor());

        ImageView  imageView = (ImageView) findViewById(R.id.big_image);
        Picasso.with(this).load(image.getmLink())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(imageView);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
