warning: LF will be replaced by CRLF in .idea/compiler.xml.
The file will have its original line endings in your working directory.
warning: LF will be replaced by CRLF in .idea/modules.xml.
The file will have its original line endings in your working directory.
warning: LF will be replaced by CRLF in .idea/runConfigurations.xml.
The file will have its original line endings in your working directory.
warning: LF will be replaced by CRLF in .idea/gradle.xml.
The file will have its original line endings in your working directory.
[1mdiff --git a/.idea/gradle.xml b/.idea/gradle.xml[m
[1mindex 7ac24c7..508b3d9 100644[m
[1m--- a/.idea/gradle.xml[m
[1m+++ b/.idea/gradle.xml[m
[36m@@ -11,7 +11,12 @@[m
             <option value="$PROJECT_DIR$/app" />[m
           </set>[m
         </option>[m
[31m-        <option name="resolveModulePerSourceSet" value="false" />[m
[32m+[m[32m        <option name="myModules">[m
[32m+[m[32m          <set>[m
[32m+[m[32m            <option value="$PROJECT_DIR$" />[m
[32m+[m[32m            <option value="$PROJECT_DIR$/app" />[m
[32m+[m[32m          </set>[m
[32m+[m[32m        </option>[m
       </GradleProjectSettings>[m
     </option>[m
   </component>[m
[1mdiff --git a/.idea/misc.xml b/.idea/misc.xml[m
[1mindex 8cdb7eb..5d19981 100644[m
[1m--- a/.idea/misc.xml[m
[1m+++ b/.idea/misc.xml[m
[36m@@ -37,25 +37,10 @@[m
     <ConfirmationsSetting value="0" id="Add" />[m
     <ConfirmationsSetting value="0" id="Remove" />[m
   </component>[m
[31m-  <component name="ProjectRootManager" version="2" languageLevel="JDK_1_8" default="true" assert-keyword="true" jdk-15="true" project-jdk-name="1.8" project-jdk-type="JavaSDK">[m
[32m+[m[32m  <component name="ProjectRootManager" version="2" languageLevel="JDK_1_7" default="true" assert-keyword="true" jdk-15="true" project-jdk-name="1.8" project-jdk-type="JavaSDK">[m
     <output url="file://$PROJECT_DIR$/build/classes" />[m
   </component>[m
   <component name="ProjectType">[m
     <option name="id" value="Android" />[m
   </component>[m
[31m-  <component name="masterDetails">[m
[31m-    <states>[m
[31m-      <state key="ScopeChooserConfigurable.UI">[m
[31m-        <settings>[m
[31m-          <splitter-proportions>[m
[31m-            <option name="proportions">[m
[31m-              <list>[m
[31m-                <option value="0.2" />[m
[31m-              </list>[m
[31m-            </option>[m
[31m-          </splitter-proportions>[m
[31m-        </settings>[m
[31m-      </state>[m
[31m-    </states>[m
[31m-  </component>[m
 </project>[m
\ No newline at end of file[m
[1mdiff --git a/app/build.gradle b/app/build.gradle[m
[1mindex 6850c87..728782e 100644[m
[1m--- a/app/build.gradle[m
[1m+++ b/app/build.gradle[m
[36m@@ -44,4 +44,6 @@[m [mdependencies {[m
     compile 'com.google.android.gms:play-services-maps:9.8.0'[m
     compile 'com.google.android.gms:play-services-analytics:9.8.0'[m
     compile 'com.android.support:multidex:1.0.0'[m
[32m+[m
[32m+[m[32m    compile 'com.facebook.stetho:stetho:1.3.1'[m
 }[m
[1mdiff --git a/app/src/main/java/com/driversiti/androidsdksampleapp/MainActivity.java b/app/src/main/java/com/driversiti/androidsdksampleapp/MainActivity.java[m
[1mindex 0102bdc..40ce5fb 100644[m
[1m--- a/app/src/main/java/com/driversiti/androidsdksampleapp/MainActivity.java[m
[1m+++ b/app/src/main/java/com/driversiti/androidsdksampleapp/MainActivity.java[m
[36m@@ -1,6 +1,8 @@[m
 package com.driversiti.androidsdksampleapp;[m
 [m
 import android.Manifest;[m
[32m+[m[32mimport android.app.FragmentTransaction;[m
[32m+[m[32mimport android.content.Intent;[m
 import android.content.pm.PackageManager;[m
 import android.support.v4.app.ActivityCompat;[m
 import android.support.v4.content.ContextCompat;[m
[36m@@ -32,6 +34,7 @@[m [mimport com.driversiti.driversitisdk.driversiti.event.SpeedExceededEvent;[m
 import com.driversiti.driversitisdk.driversiti.event.SpeedRestoredEvent;[m
 import com.driversiti.driversitisdk.driversiti.event.TripEndEvent;[m
 import com.driversiti.driversitisdk.driversiti.event.TripStartEvent;[m
[32m+[m[32mimport com.facebook.stetho.Stetho;[m
 [m
 import java.util.ArrayList;[m
 import java.util.List;[m
[36m@@ -47,12 +50,19 @@[m [mpublic class MainActivity extends AppCompatActivity {[m
     DriversitiEventListener mListener;[m
     MainActivity mContext;[m
     Button mRegistrationButton;[m
[32m+[m[32m    Button mFireDebugButton;[m
     private static boolean mIsDriversitiSetup = false;[m
 [m
     @Override[m
     protected void onCreate(Bundle savedInstanceState) {[m
         super.onCreate(savedInstanceState);[m
         setContentView(R.layout.activity_main);[m
[32m+[m
[32m+[m[32m        Stetho.initialize(Stetho.newInitializerBuilder(this)[m
[32m+[m[32m                                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))[m
[32m+[m[32m                                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))[m
[32m+[m[32m                                .build());[m
[32m+[m
         mContext = this;[m
         mRegistrationButton = (Button) findViewById(R.id.register_button);[m
         // in case of permission request is needed, delay configuration until necessary permissions are granted[m
[36m@@ -68,11 +78,35 @@[m [mpublic class MainActivity extends AppCompatActivity {[m
             setupRegistrationButton();[m
         }[m
 [m
[32m+[m[32m        mFireDebugButton = (Button) findViewById(R.id.fire_debug_button);[m
[32m+[m[32m        mFireDebugButton.setOnClickListener(new View.OnClickListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onClick(View view) {[m
[32m+[m[32m                FireDebugFragment mFireDebugFragment = new FireDebugFragment();[m
[32m+[m[32m                FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();[m
[32m+[m[32m                mFragmentTransaction.replace(R.id.activity_main, mFireDebugFragment);[m
[32m+[m[32m                mFragmentTransaction.commit();[m
[32m+[m[32m                mRegistrationButton.setVisibility(View.INVISIBLE);[m
[32m+[m[32m                mFireDebugButton.setVisibility(View.INVISIBLE);[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m
[32m+[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public void onBackPressed() {[m
[32m+[m[32m        super.onBackPressed();[m
[32m+[m[32m        Intent i = new Intent(this, MainActivity.class);[m
[32m+[m[32m        startActivity(i);[m
     }[m
 [m
     @Override[m
     protected void onResume() {[m
         super.onResume();[m
[32m+[m[32m        mRegistrationButton.setVisibility(View.VISIBLE);[m
[32m+[m[32m        mFireDebugButton.setVisibility(View.VISIBLE);[m
         if (mIsDriversitiSetup) {[m
             Log.i(LOG_TAG, "onResume Adding Listener");[m
             mDriversitiSDK.addEventListener(mListener);[m
[36m@@ -323,7 +357,7 @@[m [mpublic class MainActivity extends AppCompatActivity {[m
     public void setupDriversitiConfiguration() {[m
         DriversitiConfiguration driversitiConfiguration = new DriversitiConfiguration.ConfigurationBuilder()[m
                 .setContext(this)[m
[31m-                .setApplicationId("")[m
[32m+[m[32m                .setApplicationId("e5b088349a802b84e981bd16273875b3ea68bb0e")[m
                 .setDetectionMode(DriversitiConfiguration.DetectionMode.AUTO_ON)[m
                 .setEnabledEvents(DriversitiConfiguration.getEnabledEvents())[m
                 .setEnableVehicleIdentification(true)[m
[1mdiff --git a/app/src/main/res/layout/activity_main.xml b/app/src/main/res/layout/activity_main.xml[m
[1mindex 738deef..b6057fc 100644[m
[1m--- a/app/src/main/res/layout/activity_main.xml[m
[1m+++ b/app/src/main/res/layout/activity_main.xml[m
[36m@@ -11,6 +11,7 @@[m
     tools:context="com.driversiti.androidsdksampleapp.MainActivity">[m
 [m
 [m
[32m+[m
     <Button[m
         android:text="Register User"[m
         android:layout_width="wrap_content"[m
[36m@@ -18,4 +19,13 @@[m
         android:layout_centerHorizontal="true"[m
         android:layout_marginTop="176dp"[m
         android:id="@+id/register_button" />[m
[32m+[m
[32m+[m[32m    <Button[m
[32m+[m[32m        android:layout_width="wrap_content"[m
[32m+[m[32m        android:layout_height="wrap_content"[m
[32m+[m[32m        android:text="Fire Debug Event"[m
[32m+[m[32m        android:id="@+id/fire_debug_button"[m
[32m+[m[32m        android:layout_marginTop="60dp"[m
[32m+[m[32m        android:layout_below="@+id/register_button"[m
[32m+[m[32m        android:layout_centerHorizontal="true"/>[m
 </RelativeLayout>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/res/values/strings.xml b/app/src/main/res/values/strings.xml[m
[1mindex d2cc31e..4279efd 100644[m
[1m--- a/app/src/main/res/values/strings.xml[m
[1m+++ b/app/src/main/res/values/strings.xml[m
[36m@@ -1,3 +1,48 @@[m
 <resources>[m
     <string name="app_name">AndroidSDKSampleApp</string>[m
[32m+[m
[32m+[m
[32m+[m[32m    <!-- Developer Settings -->[m
[32m+[m[32m    <string name="settings_developer_options_title">Developer Options</string>[m
[32m+[m[32m    <string name="settings_developer_header_bluetooth_title">Bluetooth</string>[m
[32m+[m[32m    <string name="settings_developer_header_event_actions_title">Event Actions</string>[m
[32m+[m[32m    <string name="settings_developer_header_mocksensor_title">Mock Sensor</string>[m
[32m+[m[32m    <string name="settings_developer_header_app_versions_title">App Versions</string>[m
[32m+[m[32m    <string name="settings_developer_header_bluetooth_on_off_toggle">Bluetooth On/Off</string>[m
[32m+[m[32m    <string name="settings_developer_header_sal_analyze_on_off_toggle">Toggle SAL Analyze</string>[m
[32m+[m[32m    <string name="settings_developer_header_sal_analyze_on_off_summary">Turn SAL Analyze On/Off</string>[m
[32m+[m[32m    <string name="settings_developer_header_bluetooth_on_off_summary">Turn bluetooth settings on</string>[m
[32m+[m[32m    <string name="settings_developer_header_bluetooth_pair_a_device">Pair a device</string>[m
[32m+[m[32m    <string name="settings_developer_header_bluetooth_choose_a_device_to_pair">Choose a device to pair</string>[m
[32m+[m
[32m+[m[32m    <!-- Event Actions Settings -->[m
[32m+[m[32m    <string name="settings_developer_event_action_start_trip_title">Start Trip</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_start_trip_summary">Create a new Trip</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_stop_trip_title">End Trip</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_stop_trip_summary">End the current Trip</string>[m
[32m+[m
[32m+[m[32m    <string name="settings_developer_event_action_shutdown_title">Shutdown</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_shutdown_summary">Shutdown SDK</string>[m
[32m+[m
[32m+[m
[32m+[m[32m    <string name="settings_developer_event_action_create_hardbrake_event_title">Hard Break</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_create_lanechange_event_title">Lane Change</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_create_hardbrake_event_summary">Fire a hard break event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_create_lanechange_event_summary">Fire a lane change event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_create_rapid_accel_event_title">Rapid Acceleration</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_create_rapid_accel_event_summary">Fire a rapid acceleration event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_create_crash_detection_event_title">Crash Detection</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_create_crash_detection_event_summary">Fire a crash detection event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_create_speeding_event_title">Speeding Detection</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_create_speeding_event_summary">Fire a speeding event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_create_speed_restored_event_title">Safe Speed Detection</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_create_speed_restored_event_summary">Fire a restored to safe speed event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_missed_event_title">Missed Event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_missed_event_summary">Missed Event Dialog</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_driver_handling_title">Driver Handling Event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_driver_handling_summary">Driver handling event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_generic_handling_title">Generic Handling Event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_generic_handling_summary">Generic handling event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_passenger_handling_title">Passenger Handling Event</string>[m
[32m+[m[32m    <string name="settings_developer_event_action_passenger_handling_summary">Passenger handling event</string>[m
 </resources>[m
warning: LF will be replaced by CRLF in .idea/misc.xml.
The file will have its original line endings in your working directory.
