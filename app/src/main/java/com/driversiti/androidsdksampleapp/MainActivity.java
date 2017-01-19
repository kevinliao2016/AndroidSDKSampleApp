package com.driversiti.androidsdksampleapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.driversiti.driversitisdk.ApioCloud.OnCompleteTaskHandler;
import com.driversiti.driversitisdk.driversiti.Driversiti;
import com.driversiti.driversitisdk.driversiti.DriversitiConfiguration;
import com.driversiti.driversitisdk.driversiti.DriversitiEventListener;
import com.driversiti.driversitisdk.driversiti.DriversitiException;
import com.driversiti.driversitisdk.driversiti.DriversitiSDK;
import com.driversiti.driversitisdk.driversiti.UserManager;
import com.driversiti.driversitisdk.driversiti.data.User;
import com.driversiti.driversitisdk.driversiti.data.UserBuilder;
import com.driversiti.driversitisdk.driversiti.event.CarModeEvent;
import com.driversiti.driversitisdk.driversiti.event.CrashDetectedEvent;
import com.driversiti.driversitisdk.driversiti.event.DriverDeviceHandlingEvent;
import com.driversiti.driversitisdk.driversiti.event.GenericDeviceHandlingEvent;
import com.driversiti.driversitisdk.driversiti.event.HardBrakeEvent;
import com.driversiti.driversitisdk.driversiti.event.LaneChangeEvent;
import com.driversiti.driversitisdk.driversiti.event.PassengerDeviceHandlingEvent;
import com.driversiti.driversitisdk.driversiti.event.RapidAccelerationEvent;
import com.driversiti.driversitisdk.driversiti.event.SpeedExceededEvent;
import com.driversiti.driversitisdk.driversiti.event.SpeedRestoredEvent;
import com.driversiti.driversitisdk.driversiti.event.TripEndEvent;
import com.driversiti.driversitisdk.driversiti.event.TripStartEvent;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_ID = 1;
    UserManager mUserManager;
    DriversitiSDK mDriversitiSDK;
    DriversitiEventListener mListener;
    MainActivity mContext;
    Button mRegistrationButton;
    private static boolean mIsDriversitiSetup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mRegistrationButton = (Button) findViewById(R.id.register_button);
        // in case of permission request is needed, delay configuration until necessary permissions are granted
        // additionally, check if configuration is already setup, if yes, skip the following process
        if (!needRequestPermission() && !mIsDriversitiSetup) {
            Log.i(LOG_TAG, "OnCreate setupDriversitiConfiguration()");
            setupDriversitiConfiguration();
        }

        if (mIsDriversitiSetup) {
            mDriversitiSDK = Driversiti.getSDK();
            mListener = getDriversitiEventListener();
            setupRegistrationButton();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsDriversitiSetup) {
            Log.i(LOG_TAG, "onResume Adding Listener");
            mDriversitiSDK.addEventListener(mListener);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIsDriversitiSetup) {
            Log.i(LOG_TAG, "Removing Listener");
            mDriversitiSDK.removeEventListener(mListener);
        }
    }

    private User getApioUserData() {
        User user = new UserBuilder()
                .setFirstName("android")
                .setLastName("dev")
                .setPassword("drive")
                .setEmail(randomUUID() + "@publicdriversiti.com")
                .setUsername(randomUUID() + "@publicdriversiti.com")
                .setCompanyId("Soteria")
                .setCountry("USA").create();
        return user;
    }

    private void postToUiThreadHelper(Runnable runnable) {
        mContext.runOnUiThread(runnable);
    }

    private DriversitiEventListener getDriversitiEventListener() {
        return new DriversitiEventListener("DrawerActivity") {
            @Override
            public void onCarModeStatusChange(final CarModeEvent event) {
                Log.i(LOG_TAG, "event: " + event.getEventType().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOG_TAG, "Posting " + event.getEventType().toString());
                        Toast.makeText(mContext, "event: " + event.getEventType().toString(), Toast.LENGTH_LONG).show();
                    }
                };
                postToUiThreadHelper(runnable);
            }

            @Override
            public void onRapidAccelerationDetected(final RapidAccelerationEvent event) {
                Log.i(LOG_TAG, "event: " + event.getEventType().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOG_TAG, "Posting " + event.getEventType().toString());
                        Toast.makeText(mContext, "event: " + event.getEventType().toString(), Toast.LENGTH_LONG).show();
                    }
                };
                postToUiThreadHelper(runnable);
            }

            @Override
            public void onHardBrakingDetected(final HardBrakeEvent event) {
                Log.i(LOG_TAG, "event: " + event.getEventType().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOG_TAG, "Posting " + event.getEventType().toString());
                        Toast.makeText(mContext, "event: " + event.getEventType().toString(), Toast.LENGTH_LONG).show();
                    }
                };
                postToUiThreadHelper(runnable);
            }

            @Override
            public void onLaneChangingDetected(final LaneChangeEvent event) {
                Log.i(LOG_TAG, "event: " + event.getEventType().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOG_TAG, "Posting " + event.getEventType().toString());
                        Toast.makeText(mContext, "event: " + event.getEventType().toString(), Toast.LENGTH_LONG).show();
                    }
                };
                postToUiThreadHelper(runnable);
            }

            @Override
            public void onCrashDetected(final CrashDetectedEvent event) {

            }

            @Override
            public void onGenericDeviceHandlingEvent(final GenericDeviceHandlingEvent event) {
                Log.i(LOG_TAG, "event: " + event.getEventType().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOG_TAG, "Posting " + event.getEventType().toString());
                        Toast.makeText(mContext, "event: " + event.getEventType().toString(), Toast.LENGTH_LONG).show();
                    }
                };
                postToUiThreadHelper(runnable);

            }

            @Override
            public void onDriverDeviceHandlingEvent(final DriverDeviceHandlingEvent event) {
                Log.i(LOG_TAG, "event: " + event.getEventType().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOG_TAG, "Posting " + event.getEventType().toString());
                        Toast.makeText(mContext, "event: " + event.getEventType().toString(), Toast.LENGTH_LONG).show();
                    }
                };
                postToUiThreadHelper(runnable);

            }

            @Override
            public void onPassengerDeviceHandlingEvent(final PassengerDeviceHandlingEvent event) {
                Log.i(LOG_TAG, "event: " + event.getEventType().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOG_TAG, "Posting " + event.getEventType().toString());
                        Toast.makeText(mContext, "event: " + event.getEventType().toString(), Toast.LENGTH_LONG).show();
                    }
                };
                postToUiThreadHelper(runnable);

            }

            @Override
            public void onError(final DriversitiException error) {
                Log.e(LOG_TAG, "ApioException: " + error.getMessage());

            }

            @Override
            public void onSpeedExceeded(final SpeedExceededEvent speedExceededEvent) {
                Log.i(LOG_TAG, "event: " + speedExceededEvent.getEventType().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOG_TAG, "Posting " + speedExceededEvent.getEventType().toString());
                        Toast.makeText(mContext, "event: " + speedExceededEvent.getEventType().toString(), Toast.LENGTH_LONG).show();
                    }
                };
                postToUiThreadHelper(runnable);

            }

            @Override
            public void onSafeSpeedRestored(final SpeedRestoredEvent speedRestoredEvent) {
                Log.i(LOG_TAG, "event: " + speedRestoredEvent.getEventType().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOG_TAG, "Posting " + speedRestoredEvent.getEventType().toString());
                        Toast.makeText(mContext, "event: " + speedRestoredEvent.getEventType().toString(), Toast.LENGTH_LONG).show();
                    }
                };
                postToUiThreadHelper(runnable);

            }

            @Override
            public void onTripStart(final TripStartEvent event) {
                Log.i(LOG_TAG, "event: " + event.getEventType().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOG_TAG, "Posting " + event.getEventType().toString());
                        Toast.makeText(mContext, "event: " + event.getEventType().toString(), Toast.LENGTH_LONG).show();
                    }
                };
                postToUiThreadHelper(runnable);

            }

            @Override
            public void onTripEnd(final TripEndEvent event) {
                super.onTripEnd(event);
                Log.i(LOG_TAG, "event: " + event.getEventType().toString());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(LOG_TAG, "Posting " + event.getEventType().toString());
                        Toast.makeText(mContext, "event: " + event.getEventType().toString(), Toast.LENGTH_LONG).show();
                    }
                };
                postToUiThreadHelper(runnable);

            }
        };


    }

    public void setupRegistrationButton() {
        // must register a user in order to allow sdk to initiate
        mUserManager = mDriversitiSDK.getUserManager();
        mRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserManager.getActiveUser() != null) {
                    Toast.makeText(mContext, "User already registered", Toast.LENGTH_LONG).show();
                    return;
                }
                final User userRegistrationRequest = getApioUserData();
                OnCompleteTaskHandler callback = new OnCompleteTaskHandler() {
                    @Override
                    public void onSuccess(Object result) {
                        User newUser = null;
                        if (result != null && result instanceof User) {
                            newUser = (User) result;
                        } else {
                            Log.e(LOG_TAG, "onSuccess() returned a null result, this shouldn't happen...");
                            this.onFailure(new DriversitiException("Error in create user response, unable to parse a null User object"));
                            return;
                        }
                        Log.d(LOG_TAG, "onSuccess() returned a newUser: " + newUser.toString());
                        //Set the unique Id

                        if (mUserManager == null) {
                            mUserManager = Driversiti.getSDK().getUserManager();
                        }
                        // Log the user in.
                        mUserManager.loginUser(newUser);


                    }

                    @Override
                    public void onFailure(Exception errorMessage) {
                        Log.e(LOG_TAG, "Exception during User Registration: " + errorMessage.getMessage(), errorMessage);
                        Toast.makeText(getApplicationContext(), "Unable to register, please try again: " + errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                };
                mUserManager.addUser(userRegistrationRequest, callback);

            }
        });
    }


    public void setupDriversitiConfiguration() {
        DriversitiConfiguration driversitiConfiguration = new DriversitiConfiguration.ConfigurationBuilder()
                .setContext(this)
                .setApplicationId("")
                .setDetectionMode(DriversitiConfiguration.DetectionMode.AUTO_ON)
                .setEnabledEvents(DriversitiConfiguration.getEnabledEvents())
                .setEnableVehicleIdentification(true)
                .setSetupHandler(new DriversitiConfiguration.SetupHandler() {
                    @Override
                    public void onSetupSuccess() {
                        Log.i(LOG_TAG, "Driversiti setup successfull");
                    }

                    @Override
                    public void onSetupFailure(DriversitiException errorMessage) {
                        Log.i(LOG_TAG, "Driversiti setup error: " + errorMessage);
                    }
                })
                .build();

        Driversiti.setConfiguration(driversitiConfiguration);
        mIsDriversitiSetup = true;
    }

    public boolean needRequestPermission() {
        List<String> listPermissionNeeded = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!listPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mContext,
                    listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), REQUEST_ID);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    // configuration is setup here once permissions are granted
                    if (!mIsDriversitiSetup) {
                        setupDriversitiConfiguration();
                        mDriversitiSDK = Driversiti.getSDK();
                        mListener = getDriversitiEventListener();
                        Log.i(LOG_TAG, "Permission Request  Adding Listener");
                        mDriversitiSDK.addEventListener(mListener);
                        setupRegistrationButton();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
