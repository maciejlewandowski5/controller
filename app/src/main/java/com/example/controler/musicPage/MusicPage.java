package com.example.controler.musicPage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.controler.RealTimeNavigation;
import com.example.controler.controlPage.ContrlPage;
import com.example.controler.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MusicPage extends AppCompatActivity {

    // UI objects.
    LinearLayout linearLayout;
    TextView textView;
    Button btnPlaySong;
    View backgroundView;

    private DatabaseReference playSongRef;

    public TextColors colorsSlideIN;
    public TextColors colorsFadeOut;

    MicrophoneInput microphoneInput;
    CurrentSongValues currentSongValues;
    AudioPlayer audioPlayer;



    // Spring Animation.
    BackgroundAnimation backgroundAnimation;

    //Handler for ui, main thread.
    Handler backgroundAnimationHandler;
    Handler textAnimationHandler;

    //Text animation object.
    TextAnimation textAnimation;

    RealTimeNavigation realTimeNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_page);
        permissionsCheck();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        playSongRef = database.getReference("playSong");

        backgroundView = findViewById(R.id.ellipse_5);
        linearLayout = findViewById(R.id.linearLayoutText);
        linearLayout.bringToFront();
        backgroundAnimation = new BackgroundAnimation(backgroundView);

        currentSongValues = new CurrentSongValues(1);

        audioPlayer = new AudioPlayer(this);
        btnPlaySong = findViewById(R.id.btnPlaySong);
        initializeHandlers();

        final DatabaseReference playSongRef = database.getReference("playSong");


        // Navigation.
        realTimeNavigation = new RealTimeNavigation();
        realTimeNavigation.active(getBaseContext(), this);


        textAnimation = new TextAnimation(backgroundAnimationHandler, textAnimationHandler, currentSongValues);
        microphoneInput = new MicrophoneInput(backgroundAnimationHandler);

        colorsSlideIN = new TextColors(Color.parseColor("#FFFFFFFF"), Color.parseColor("#00D2003C"));
        colorsFadeOut = new TextColors(Color.parseColor("#00D2003C"), Color.parseColor("#FFFFFFFF"));

        btnPlaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlaySong.setVisibility(View.GONE);
                try {
                    microphoneInput.run();
                    audioPlayer.startSong();
                    textAnimation.run();
                    playSongRef.setValue(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initializeHandlers() {
        textAnimationHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message message) {
                if (message.what == 1)
                    paintText(1);
                else if (message.what == 0) {
                    paintText(0);
                    if (currentSongValues.animPosition == 110)
                    {
                        btnPlaySong.setVisibility(View.VISIBLE);
                        currentSongValues.songPosition = 0;
                        textAnimation.stop();
                        playSongRef.setValue(0);
                        realTimeNavigation.setSlide(14);
                    }
                }
            }
        };

        //Using handler to manage UI, within main thread.
        backgroundAnimationHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message message) {
                backgroundAnimation.animateBackground(message.what);
            }
        };
    }

    public void goToControler(View view) {
        Intent myIntent = new Intent(getBaseContext(), ContrlPage.class);
        startActivity(myIntent);
    }



    //Coloring TextView
    public void paintText(int choice) {

        //choice 1 = slideIN , 0 = fadeOut
        if (choice == 1)
            colorsSlideIN.setSlideColors(currentSongValues.animPosition);
        else colorsFadeOut.setFadeColors(currentSongValues.animPosition);

        textView = new TextView(MusicPage.this);
        textView.setText(currentSongValues.songText[currentSongValues.songPosition]);
        textView.setTextSize(40);

        Shader textShader;
        if (choice == 1) {
            textShader = new LinearGradient(0,0,textView.getPaint().measureText((String)textView.getText()),textView.getTextSize(),
                    colorsSlideIN.getColors(), null, Shader.TileMode.REPEAT);
        }
        else {
            textShader = new LinearGradient(0,0,textView.getPaint().measureText((String)textView.getText()),textView.getTextSize(),
                    colorsFadeOut.getColors(), null, Shader.TileMode.REPEAT);
        }

        textView.getPaint().setShader(textShader);

        linearLayout.removeAllViews();
        linearLayout.addView(textView);
    }

    /**
     * Method responsible for listing currently granted permissions.
     */
    public void permissionsCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 100);
        }
    }
    /**
     * Minimises app when user press back key.
     */
    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }
}
