package com.example.streamvideoapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class Player extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView fullScrieenIcom;
    private FrameLayout frameLayout;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Video v = (Video) bundle.getSerializable("videoData");

        TextView title = findViewById(R.id.playTitleId);
        TextView desc = findViewById(R.id.playDescriptionId);
        videoView = findViewById(R.id.videoViewId);
        progressBar = findViewById(R.id.progressBarId);
        fullScrieenIcom = findViewById(R.id.fullScrieenIcon);
        frameLayout = findViewById(R.id.frameLayoutId);

        title.setText(v.getTitle());
        desc.setText(v.getDescription());
        Uri videoVrl = Uri.parse(v.getVideoUrl());
        videoView.setVideoURI(videoVrl);

        getSupportActionBar().setTitle(v.getTitle());

        //Media Controller

        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
               progressBar.setVisibility(View.GONE);
            }
        });

        //Media FullScrieen
        fullScrieenIcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().hide();
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

                frameLayout.setLayoutParams(new LinearLayout.LayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)));
                    videoView.setLayoutParams(new FrameLayout.LayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)));

                    fullScrieenIcom.setVisibility(View.GONE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        fullScrieenIcom.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().show();

        getWindow().clearFlags(WindowManager.LayoutParams.FLAGS_CHANGED);
        int heightValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, heightValue)));
        videoView.setLayoutParams(new FrameLayout.LayoutParams(new ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, heightValue)));

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            super.onBackPressed();
        }
    }
}