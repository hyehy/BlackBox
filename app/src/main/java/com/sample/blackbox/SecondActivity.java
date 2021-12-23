package com.sample.blackbox;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        videoView = findViewById(R.id.videoView);

        ImageButton btnPlay, btnStop, btnPause,btnRotate;
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnStop = (ImageButton) findViewById(R.id.btnStop);
        btnPause = (ImageButton) findViewById(R.id.btnPause);
        btnRotate = (ImageButton) findViewById(R.id.btnRotate);

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        Intent inIntent = getIntent();
        String test = (inIntent.getStringExtra("Name"));

        test(test);

        Uri uri = Uri.parse(test);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                finish();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                videoView.start();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                videoView.pause();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        });
    }


    void test( String testString){
        // MediaMetadataRetriever 인스턴스 생성
        MediaMetadataRetriever media = new MediaMetadataRetriever();

        // 데이터 소스를 지정한다
        media.setDataSource(testString);
        String TAG = "Sample";
//        Log.d(TAG, "METADATA_KEY_ALBUM: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
//        Log.d(TAG, "METADATA_KEY_ALBUMARTIST: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST));
//        Log.d(TAG, "METADATA_KEY_ARTIST: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
//        Log.d(TAG, "METADATA_KEY_AUTHOR: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR));
//        Log.d(TAG, "METADATA_KEY_CD_TRACK_NUMBER: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER));
//        Log.d(TAG, "METADATA_KEY_COMPILATION: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPILATION));
//        Log.d(TAG, "METADATA_KEY_DATE: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE));
//        Log.d(TAG, "METADATA_KEY_DISC_NUMBER: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DISC_NUMBER));
//        Log.d(TAG, "METADATA_KEY_DURATION: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
//        Log.d(TAG, "METADATA_KEY_GENRE: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));
//        Log.d(TAG, "METADATA_KEY_MIMETYPE: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE));
//        Log.d(TAG, "METADATA_KEY_NUM_TRACKS: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_NUM_TRACKS));
//        Log.d(TAG, "METADATA_KEY_TITLE: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
//        Log.d(TAG, "METADATA_KEY_WRITER: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_WRITER));
//        Log.d(TAG, "METADATA_KEY_YEAR: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR));
//        Log.d(TAG, "METADATA_KEY_LOCATION: "
//                + media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_LOCATION));
    }
}
