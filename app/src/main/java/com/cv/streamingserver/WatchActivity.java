package com.cv.streamingserver;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class WatchActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(this);

        // Set media controller to the video view
        videoView.setMediaController(mediaController);

        // Set video URL from web address
        String videoUrl = getIntent().getStringExtra("vid");//"http://192.168.1.35/watch/6/";
        Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);

        // Start video playback
        videoView.start();
    }
}
