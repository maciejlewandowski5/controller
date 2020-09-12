package com.example.controler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.controler.controlPage.ContrlPage;
import com.example.controler.lightPage.LightPage;
import com.example.controler.musicPage.MusicPage;
import com.example.controler.waterPage.WaterPage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.OnDisconnect;
import com.google.firebase.database.ValueEventListener;

/**
 * Class responsible for synchronise navigation on the mobile device with web app.
 */
public class RealTimeNavigation {

    private DatabaseReference slideRef;
    private Boolean firstRead;
    /**
     * Current slide.
     */
    static private Long slide;
    DatabaseReference controllers;
    OnDisconnect onDisconnectRef;

    public RealTimeNavigation() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        slideRef = database.getReference("slide");
        firstRead = true;
        slide=0L;
        controllers = database.getReference("controllers");
    }


    /**
     * Starts specified activity based on connection status with firebase and slide number.
     * @param context Context in which new Intent should be created.
     * @param activity Current Activity to be base to start new activity.
     */
    public void active(final Context context, final Activity activity) {

        // Sets number of controllers to database when device disconnected.
        onDisconnectRef = controllers.onDisconnect();
        controllers.addValueEventListener (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer a = dataSnapshot.getValue(Integer.class);
                if(a == null){
                    a=0;
                }
                onDisconnectRef.setValue(a - 1); //set number of controller minus 1 this controller.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Starts specified activity based on connection status with firebase and slide number.
        final DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean connected = snapshot.getValue(Boolean.class);
                if(connected == null){
                    connected = false;
                }
                if (!connected) {
                    if(! (activity instanceof ConnectionManager)){

                    Intent nextIntent =  new Intent(context, ConnectionManager.class);
                    activity.startActivity(nextIntent);
                    }
                    firstRead = false;
                } else {

                    slideRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                slide = dataSnapshot.getValue(Long.class);
                                if(slide == null){
                                    slide = 0L;
                                }
                            if (!firstRead) {
                                Intent nextIntent;
                                if (  (slide == 0) || (slide == 2) || (slide == 3) || (slide == 5) || (slide == 6) || (slide == 8) || (slide == 9) || (slide == 11) || (slide == 12)) {
                                    nextIntent = new Intent(context, ContrlPage.class);
                                } else if (slide == 4) {
                                    nextIntent = new Intent(context, GrabPage.class);
                                } else if (slide == 1) {
                                    nextIntent = new Intent(context, LightPage.class);
                                } else if (slide == 13) {
                                    nextIntent = new Intent(context, MusicPage.class);
                                } else if (slide == 10) {
                                    nextIntent = new Intent(context, WagglePage.class);
                                } else if (slide == 7) {
                                    nextIntent = new Intent(context, WaterPage.class);
                                } else {
                                    nextIntent = new Intent(context, ContrlPage.class);
                                }
                                activity.startActivity(nextIntent);
                            }
                            firstRead = false;
                        }

                        /**
                         * Empty method
                         * @param databaseError not used.
                         */
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }

            /**
             * Empty method
             * @param error not used.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    public void setSlide(long newSlide){
        slideRef.setValue(newSlide);
    }
    public  long getSlide(){
        return slide;
    }

}
