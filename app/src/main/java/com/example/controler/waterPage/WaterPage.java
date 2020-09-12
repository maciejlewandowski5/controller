package com.example.controler.waterPage;

import com.example.controler.RealTimeNavigation;
import com.example.controler.controlPage.ContrlPage;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import processing.android.PFragment;
import processing.android.CompatUtils;
import processing.core.PApplet;

/**
 * Class responsible for sending information about number of phone shakes.
 */
public class WaterPage extends AppCompatActivity {
    private DatabaseReference buttonRef;
    /**
     * ProcessingForAndroid frame-work sketch.
     */
    private PApplet sketch;
    /**
     * Method responsible for initializing variables when WagglePage activity starts.
     * @param savedInstanceState bundle for passing arguments.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CompatUtils.getUniqueViewId());
        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        sketch = new Sketch();
        PFragment fragment = new PFragment(sketch);
                fragment.setView(frame,  this);

        // Database and references.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        buttonRef = database.getReference("change");

        // Navigation
        RealTimeNavigation realTimeNavigation = new RealTimeNavigation();
        realTimeNavigation.active(getBaseContext(), this);

        // Listeners
        buttonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer cmp = dataSnapshot.getValue(Integer.class);
                if(cmp == null){
                    cmp=0;
                }
                if (cmp == 1) {
                    buttonRef.setValue(0);
                    Intent myIntent = new Intent(getBaseContext(), ContrlPage.class);
                    startActivity(myIntent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /**
     * change value reset.
     */
    @Override
    protected void onPause() {
        super.onPause();
        buttonRef.setValue(0);
    }

    /**
     * Permissions required by ProcessingForAndroid.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (sketch != null) {
            sketch.onRequestPermissionsResult(
                    requestCode, permissions, grantResults);
        }
    }

    /**
     * Starting sketch on new intent.
     */
    @SuppressLint("MissingSuperCall")
    @Override
    public void onNewIntent(Intent intent) {
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }
    /**
     * Empty method to stop changing screen when this activity is required to move to another slide.
     */
    @Override
    public void onBackPressed()
    {

    }
}