package com.cv.streamingserver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomComponent extends RelativeLayout {

    private ImageView rectangle;
    private ImageView image;
    private TextView text;

    public String getVidUrl() {
        return vidUrl;
    }

    public void setVidUrl(String vidUrl) {
        this.vidUrl = vidUrl;
        ImageView thumbnail=findViewById(R.id.thumbnail);
        thumbnail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),WatchActivity.class);
                i.putExtra("vid",vidUrl);
                getContext().startActivity(i);
            }
        });
    }

    private String vidUrl;

    public CustomComponent(Context context) {
        super(context);
        init(context);
    }

    public CustomComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.video_thumbnail, this, true);

        rectangle = findViewById(R.id.rectangle);
        image = findViewById(R.id.thumbnail);
        text = findViewById(R.id.text);
    }
    public void setText(String t){
        text.setText(t);
    }
    public void setImageFromAssets(String filename) {
        try {
            // Load image from assets
            AssetManager assetManager = getContext().getAssets();
            InputStream inputStream = assetManager.open(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            // Set the image bitmap
            image.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setImageFromUrl(String imageUrl) throws MalformedURLException {
        // Load image from URL using Picasso
        Picasso.get().load(imageUrl).into(image);
        Picasso.get().load(String.valueOf(new URL(imageUrl))).into(image);
    }

    public ImageView getImage() {
        return image;
    }

    // You can add methods to set text, image, etc. dynamically if needed
}
