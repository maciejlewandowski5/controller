package com.example.controler.controlPage;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controler.R;
import com.example.controler.RealTimeNavigation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Class responsible for sending information to control pointer on computer screen.
 */
public class ContrlPage extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor gameRotationSensor;
    DatabaseReference xRef;
    DatabaseReference yRef;
    DatabaseReference acceptRef;
    /**
     * Variable store information about progress of calibration fo controller.
     */
    private CalculationManager manageControler;
    RealTimeNavigation navigation;
    private static int numberOfActivityStartsAsMainThread = 0;
    static private Boolean toManyControllers = false;
    int layout;

    /**
     * Method responsible for initializing variables when ControlPage activity starts.
     * Sets X,Y to 0 in order to begin calibration in x and y axis.
     * Request microphone permission. Runs navigation. Checks number of controllers connected to
     * firebase. Checks if internet connection is active.
     *
     * @param savedInstanceState bundle for passing arguments.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Database and database references.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        xRef = database.getReference("X");
        yRef = database.getReference("Y");
        acceptRef = database.getReference("accept");
        final DatabaseReference numberOfControllersRef = database.getReference("controllers");


        // Check number of controllers connected to firebase
        if (numberOfActivityStartsAsMainThread == 0) {
            numberOfControllersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      Integer a = dataSnapshot.getValue(Integer.class);
                      if(a == null){
                          a=0;
                      }
                            numberOfControllersRef.setValue(a + 1);
                        if (a > 1) {
                            toManyControllers = true;
                        } else {
                            ContrlPage.this.layout = R.layout.activity_contrl_page;
                            toManyControllers = false;
                        }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        if (toManyControllers) {
            setContentView(R.layout.activity_connection_manager_no_controler);
        } else {
            setContentView(R.layout.activity_contrl_page);


            //  Animation
            AnimateController animateController = new AnimateController(findViewById(R.id.virtualJ), findViewById(R.id.ellipse_4));

            // Calculation Manager
            manageControler = new CalculationManager(animateController);


            // Listeners
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager != null) {
                gameRotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
            } else {
                Toast.makeText(this, "No game rotation sensor", Toast.LENGTH_SHORT).show();
                finish();
            }
            sensorManager.registerListener(manageControler, gameRotationSensor, SensorManager.SENSOR_DELAY_GAME);
        }


        //Navigation
        navigation = new RealTimeNavigation();
        navigation.active(getBaseContext(), this);

        //Permissions
        PermissionManager permissionManager = new PermissionManager();
        permissionManager.requestMicrophonePermission(this, this);
    }

    /**
     * Game rotation listeners are registered.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (!toManyControllers) {
            sensorManager.registerListener(manageControler, gameRotationSensor, SensorManager.SENSOR_DELAY_GAME);
            numberOfActivityStartsAsMainThread++;
        }
    }

    /**
     * Game rotation listeners are unregistered.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (!toManyControllers) {
            sensorManager.unregisterListener(manageControler);
        }
    }


    /**
     * Should be used when user press accept button.
     *
     * @param view Accept button view.
     */
    public void accept(View view) {
        acceptRef.setValue(1);
    }

    /**
     * Reload calibration process.
     *
     * @param view Reset button view
     */
    public void resetMousePointer(View view) {
        manageControler.resetMousePointer();
    }

    /**
     * Method responsible for action when back button is pressed.
     * Here action is blocked when slide requires control page.
     * On other slides it works normally.
     */
    @Override
    public void onBackPressed() {
        long slide = navigation.getSlide();
        if ((slide != 2) && (slide != 3) && (slide != 5) && (slide != 6) && (slide != 8) &&
                (slide != 9) && (slide != 11) && (slide != 12)) {
                    super.onBackPressed();
                }

    }
}


