package com.cv.streamingserver;
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch)

        val videoView = findViewById<VideoView>(R.id.videoView)

        // URL of the video to play
        val videoUrl = "http://192.168.1.35/watch/6/"

        // Create a MediaController
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)

        // Set the MediaController for the VideoView
        videoView.setMediaController(mediaController)

        // Set the URI of the video to be played
        videoView.setVideoURI(Uri.parse(videoUrl))

        // Start playing the video
        videoView.start()
        }
        }
