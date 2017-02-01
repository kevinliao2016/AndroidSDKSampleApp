package com.driversiti.androidsdksampleapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Toast;

import com.driversiti.driversitisdk.Enums.DriversitiEventType;
import com.driversiti.driversitisdk.driversiti.Driversiti;
import com.driversiti.driversitisdk.driversiti.DriversitiEventListener;
import com.driversiti.driversitisdk.driversiti.DriversitiException;
import com.driversiti.driversitisdk.driversiti.DriversitiSDK;
import com.driversiti.driversitisdk.driversiti.event.CarModeEvent;
import com.driversiti.driversitisdk.driversiti.event.CrashDetectedEvent;
import com.driversiti.driversitisdk.driversiti.event.DriverDeviceHandlingEvent;
import com.driversiti.driversitisdk.driversiti.event.GenericDeviceHandlingEvent;
import com.driversiti.driversitisdk.driversiti.event.HardBrakeEvent;
import com.driversiti.driversitisdk.driversiti.event.PassengerDeviceHandlingEvent;
import com.driversiti.driversitisdk.driversiti.event.RapidAccelerationEvent;
import com.driversiti.driversitisdk.driversiti.event.SpeedExceededEvent;
import com.driversiti.driversitisdk.driversiti.event.SpeedRestoredEvent;
import com.driversiti.driversitisdk.driversiti.event.TripEndEvent;
import com.driversiti.driversitisdk.driversiti.event.TripStartEvent;
import com.driversiti.driversitisdk.utils.DialogUtils;
import com.driversiti.driversitisdk.utils.Utils;

/**
 * Created by jamilislam on 2/1/17.
 */

public class FireDebugFragment extends PreferenceFragment {

    private static String
            LOG_TAG = FireDebugFragment.class.getSimpleName();

    private static int UPDATE_FREQUENCY = 5000;

    private SwitchPreference mSalRunning;
    private Preference mStartTrip;
    private Preference mEndTrip;
    private Preference mShutdown;
    private Preference mFireHardBrake;
    private Preference mFireRapidAcceleration;
    private Preference mFireCrashDetection;
    private Preference mMissedEvent;
    private Preference mSpeedingEvent;
    private Preference mSpeedRestoredEvent;
    private Preference mDriverHandlingEvent;
    private Preference mPassengerHandlingEvent;
    private Preference mGenericHandlingEvent;

    private DriversitiSDK mDriversitiSDK;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;
    private DriversitiEventListener mDriversitiEventListener;
    private DialogUtils mDialogUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make sure default values are applied.  In a real app, you would
        // want this in a shared function that is used to retrieve the
        // SharedPreferences wherever they are needed.
//            PreferenceManager.setDefaultValues(getActivity(),
//                    R.xml.advanced_preferences, false);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.fragment_fire_debug_event);
        mDriversitiSDK = Driversiti.getSDK();
        mSalRunning = (SwitchPreference)findPreference("settings_developer_event_actions_key");
        mStartTrip = (Preference)findPreference("settings_developer_event_actions_start_trip_key");
        mEndTrip = (Preference)findPreference("settings_developer_event_actions_end_trip_key");
        mShutdown = (Preference)findPreference("settings_developer_event_actions_shutdown_key");
        mFireHardBrake = (Preference)findPreference("settings_developer_event_actions_fire_hardbrake_trip_key");
        mFireRapidAcceleration = (Preference)findPreference("settings_developer_event_actions_fire_rapid_acceleration_trip_key");
        mFireCrashDetection = (Preference)findPreference("settings_developer_event_actions_fire_crash_detection_trip_key");
        mMissedEvent = (Preference)findPreference("settings_developer_event_actions_missed_event_key");
        mSpeedingEvent = (Preference)findPreference("settings_developer_event_actions_fire_speed_exceeded_key");
        mSpeedRestoredEvent = (Preference)findPreference("settings_developer_event_actions_fire_speed_restored_key");
        mGenericHandlingEvent = (Preference)findPreference("settings_developer_event_actions_generic_handling_event_key");
        mDriverHandlingEvent = (Preference)findPreference("settings_developer_event_actions_driver_handling_event_key");
        mPassengerHandlingEvent = (Preference)findPreference("settings_developer_event_actions_passenger_handling_event_key");

        setListeners();

        mDialogUtils = new DialogUtils(getActivity());
        mDriversitiEventListener = new DriversitiEventListener("EventActionsFragment") {
            @Override
            public void onCarModeStatusChange(CarModeEvent event) {
                mDialogUtils.showToast("Car Mode status change: " + event.getActiveStatus());
            }

            @Override
            public void onRapidAccelerationDetected(RapidAccelerationEvent event) {
                Log.d(LOG_TAG, "onRapidAccelerationDetected()");
                mDialogUtils.showYesNoDialog("RapidAcceleration Detected", "Was this a False positive? Yes or No?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String message = Utils.getFalsePositiveString(DriversitiEventType.HARD_ACCELERATION);
                                Utils.appendMessageToSensorLog(getActivity(), message);
                                mDialogUtils.showToast("False Positive registered: ");
                            }
                        });
            }

            @Override
            public void onHardBrakingDetected(HardBrakeEvent event) {
                Log.d(LOG_TAG, "onHardBrakingDetected()");
                mDialogUtils.showYesNoDialog("Hard Braking Detected", "Was this a False positive? Yes or No?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String message = Utils.getFalsePositiveString(DriversitiEventType.HARD_DECELERATION);
                                Utils.appendMessageToSensorLog(getActivity(), message);
                                mDialogUtils.showToast("False Positive registered: ");
                            }
                        });

            }


            @Override
            public void onCrashDetected(CrashDetectedEvent event) {
                Log.d(LOG_TAG, "onCrashDetected()");
                mDialogUtils.showYesNoDialog("Crash Detected", "Was this a False positive? Yes or No?",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String message = Utils.getFalsePositiveString(DriversitiEventType.CRASH_DETECTED);
                                Utils.appendMessageToSensorLog(getActivity(), message);
                                mDialogUtils.showToast("False Positive registered: ");
                            }
                        });

            }

            @Override
            public void onTripStart(TripStartEvent event) {
                Log.d(LOG_TAG, "onTripStart()");
                mDialogUtils.showToast("onTripStart(): Tripid: " + event.getCloudTripId()
                        + " Trip Start: " + event.getTripStartTime());
            }

            @Override
            public void onTripEnd(TripEndEvent event) {
                super.onTripEnd(event);
                Log.d(LOG_TAG, "onTripEnd()");
                mDialogUtils.showToast("onTripEnd(): Tripid: " + event.getCloudTripId()
                        + " Trip End: " + event.getTripEndTime());
            }

            @Override
            public void onError(DriversitiException e) {
                Log.e(LOG_TAG, "onError(Exception e):", e);
                Toast.makeText(getActivity(), "Apio Exception detected: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSpeedExceeded(SpeedExceededEvent event) {
                Log.d(LOG_TAG, "onSpeedExceeded()");
                mDialogUtils.showYesNoDialog("Speeding Detected", "Was this a False positive? Yes or No?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String message = Utils.getFalsePositiveString(DriversitiEventType.SPEED_THRESHOLD_EXCEEDED);
                                Utils.appendMessageToSensorLog(getActivity(), message);
                                mDialogUtils.showToast("False Positive registered: ");
                            }
                        });

            }

            @Override
            public void onSafeSpeedRestored(SpeedRestoredEvent event) {
                Log.d(LOG_TAG, "onSafeSpeedRestored()");
                mDialogUtils.showYesNoDialog("Safe Speed Resumed", "Was this a False positive? Yes or No?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String message = Utils.getFalsePositiveString(DriversitiEventType.SPEED_THRESHOLD_RESTORED);
                                Utils.appendMessageToSensorLog(getActivity(), message);
                                mDialogUtils.showToast("False Positive registered: ");
                            }
                        });

            }

            @Override
            public void onDriverDeviceHandlingEvent(DriverDeviceHandlingEvent event){
                Log.d(LOG_TAG, "onDriverDeviceHandlingEvent()");
                mDialogUtils.showYesNoDialog("Driver device handling detected", "Was this a False positive? Yes or No?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String message = Utils.getFalsePositiveString(DriversitiEventType.DRIVER_DEVICE_HANDLING);
                                Utils.appendMessageToSensorLog(getActivity(), message);
                                mDialogUtils.showToast("False Positive registered: ");
                            }
                        });
            }

            @Override
            public void onPassengerDeviceHandlingEvent(PassengerDeviceHandlingEvent event) {
                Log.d(LOG_TAG, "onPassengerDeviceHandlingEvent()");
                mDialogUtils.showYesNoDialog("Passenger device handling detected", "Was this a False positive? Yes or No?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String message = Utils.getFalsePositiveString(DriversitiEventType.PASSENGER_DEVICE_HANDLING);
                                Utils.appendMessageToSensorLog(getActivity(), message);
                                mDialogUtils.showToast("False Positive registered: ");
                            }
                        });
            }

            @Override
            public void onGenericDeviceHandlingEvent(GenericDeviceHandlingEvent event) {
                Log.d(LOG_TAG, "onGenericDeviceHandlingEvent()");
                mDialogUtils.showYesNoDialog("Phone device handling detected", "Was this a False positive? Yes or No?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String message = Utils.getFalsePositiveString(DriversitiEventType.GENERAL_DEVICE_HANDLING);
                                Utils.appendMessageToSensorLog(getActivity(), message);
                                mDialogUtils.showToast("False Positive registered: ");
                            }
                        });
            }
        };
    }


    private void initHandler(){
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mSalRunning.setChecked(mDriversitiSDK.isAnalyzing());
                mHandler.postDelayed(mRunnable, UPDATE_FREQUENCY);
            }
        };
        mHandler.postDelayed(mRunnable, UPDATE_FREQUENCY);

    }

    private void setListeners(){
        mSalRunning.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue.equals(Boolean.TRUE)) {
                    Toast.makeText(getActivity(), "Starting sal:", Toast.LENGTH_SHORT).show();
                    mDriversitiSDK.start();
                } else if (newValue.equals(Boolean.FALSE)) {
                    Toast.makeText(getActivity(), "Stopping sal:", Toast.LENGTH_SHORT).show();
                    mDriversitiSDK.stop();
                }
                return true;
            }
        });

        mStartTrip.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                mDriversitiSDK.fireEventDebug(DriversitiEventType.TRIP_START);
                Toast.makeText(getActivity(), "Start Trip", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mEndTrip.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                mDriversitiSDK.fireEventDebug(DriversitiEventType.TRIP_END);
                Toast.makeText(getActivity(), "EndTrip", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mShutdown.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity(), "Shutdown", Toast.LENGTH_SHORT).show();
                mDriversitiSDK.shutdown();
                return true;
            }
        });
        mFireHardBrake.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                mDriversitiSDK.fireEventDebug(DriversitiEventType.HARD_DECELERATION);
                return true;
            }
        });
//        mFireLaneChange.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                mDriversitiSDK mDriversitiSDK).fireEventDebug(DriversitiEventType.LANE_CHANGE_DETECTED);
//                return true;
//            }
//        });
        mFireRapidAcceleration.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                mDriversitiSDK.fireEventDebug(DriversitiEventType.HARD_ACCELERATION);
                return true;
            }
        });
        mFireCrashDetection.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                mDriversitiSDK.fireEventDebug(DriversitiEventType.CRASH_DETECTED);
                return true;
            }
        });
        mSpeedingEvent.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                mDriversitiSDK.fireEventDebug(DriversitiEventType.SPEED_THRESHOLD_EXCEEDED);
                Toast.makeText(getActivity(), "Speeding Detected", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mSpeedRestoredEvent.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                mDriversitiSDK.fireEventDebug(DriversitiEventType.SPEED_THRESHOLD_RESTORED);
                Toast.makeText(getActivity(), "Safe Speed Resumed", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mDriverHandlingEvent.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                mDriversitiSDK.fireEventDebug(DriversitiEventType.DRIVER_DEVICE_HANDLING);
                Toast.makeText(getActivity(), "Driver Handling Event", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mGenericHandlingEvent.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                mDriversitiSDK.fireEventDebug(DriversitiEventType.GENERAL_DEVICE_HANDLING);
                Toast.makeText(getActivity(), "Phone Handling Event", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mPassengerHandlingEvent.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                mDriversitiSDK.fireEventDebug(DriversitiEventType.PASSENGER_DEVICE_HANDLING);
                Toast.makeText(getActivity(), "Passenger Handling Event", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mMissedEvent.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                DialogUtils.displayMissedEventAction(getActivity());
                return true;
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        mDriversitiSDK.addEventListener(mDriversitiEventListener);
        mSalRunning.setChecked(mDriversitiSDK.isAnalyzing());
        initHandler();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDriversitiSDK.removeEventListener(mDriversitiEventListener);
    }


}

