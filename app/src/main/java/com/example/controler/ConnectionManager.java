package com.example.controler;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Class responsible for displaying information about lack of connection.
 */
public class ConnectionManager extends AppCompatActivity {

    RealTimeNavigation realTimeNavigation;


    /**
     *  Set content view starts navigation.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_manager_no_connection);

        // Navigation
        realTimeNavigation = new RealTimeNavigation();
        realTimeNavigation.active(getApplicationContext(), this);
    }

    /**
     * Empty method to stop changing screen when this activity is required to move to another slide.
     */
    @Override
    public void onBackPressed() { }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Empty method to actions of button.
     */
    public void resetMousePointer(View view) { }
}

