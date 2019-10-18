package com.example.myride3;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class RideModeOn extends AppCompatActivity implements LocationListener {

    private static final int REQUEST_CALL = 1;
    CallBroadcastReciever callBroadcastReciever = new CallBroadcastReciever();
    TextView tv_speed;
    static int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        hideNavigationBar();
        setContentView(R.layout.activity_ride_mode_on);

        Button emerge = (Button) findViewById(R.id.carEmergency);
        emerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });
        Button EndRideMode = (Button) findViewById(R.id.exitCar);

        EndRideMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(RideModeOn.this, MainActivity.class);
                startActivity(goBack);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            doStuff();
        }
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        storeAvg();
    }

    private void makePhoneCall() {
        String number = "911";
        if (ContextCompat.checkSelfPermission(RideModeOn.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RideModeOn.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    void updateSpeed(CLocation location) {
        TextView textView = findViewById(R.id.textView2);
        counter++;
        float nCurrentSpeed = 0;
        tv_speed = (TextView) findViewById(R.id.tv_speed);
        Formatter fm = new Formatter(new StringBuilder());
        SharedPreferences preferences = this.getSharedPreferences("MyPref", 0);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ContactDatabase contactDatabase = new ContactDatabase(this);
        SQLiteDatabase objSqLiteDatabase = contactDatabase.getWritableDatabase();
        if (location != null) {
            location.setbUseMetricUnits(true);
            nCurrentSpeed = location.getSpeed();
            final float finalNCurrentSpeed = nCurrentSpeed;
            if(objSqLiteDatabase != null){
                ContentValues contentValues = new ContentValues();
                contentValues.put("ridespeed",finalNCurrentSpeed);
                if(finalNCurrentSpeed != 0){
                    long check = objSqLiteDatabase.insert("speed",null,contentValues);
                }
            }
            fm.format(Locale.US, "%5.1f", nCurrentSpeed);
            String strCurrentSpeed = fm.toString();
            strCurrentSpeed = strCurrentSpeed.replace(" ","0");
            tv_speed.setText(strCurrentSpeed + " KMPH");
            if (preferences.getBoolean("Auto_Speed", true)) {
                if (nCurrentSpeed > 80) {
                    tv_speed.setTextColor(Color.parseColor("#ff0000"));
                    textView.setText("Please Slow Down !!");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(10500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        v.vibrate(10500);
                    }
                } else {
                    tv_speed.setTextColor(Color.parseColor("#CFFDFF"));
                    textView.setText("Enjoy your Ride!");
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callBroadcastReciever,filter);
        try {
            audioManager.setRingerMode(0);
        } catch (Exception e) {
            Toast.makeText(this,"Enable Do Not Disturb access.",Toast.LENGTH_SHORT).show();
            NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!mNotificationManager.isNotificationPolicyAccessGranted()) {
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        unregisterReceiver(callBroadcastReciever);
        audioManager.setRingerMode(3);
        storeAvg();
    }
    void storeAvg(){
        ContactDatabase contactDatabase = new ContactDatabase(this);
        SQLiteDatabase objSqLiteDatabase = contactDatabase.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate =  mdformat.format(calendar.getTime());
        float average = 0;
        Cursor objCursor = objSqLiteDatabase.rawQuery("select avg(ridespeed) from  speed", null);
        if (objCursor.moveToFirst()){
            do{
                average = objCursor.getFloat(0);
                // do what ever you want here
            }while(objCursor.moveToNext());
        }
        objCursor.close();

        if(objSqLiteDatabase != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put("avgspeed",average);
            contentValues.put("dateofride",strDate);
            if(average != 0){
                long check = objSqLiteDatabase.insert("avgspeed",null,contentValues);
            }
        }
        Cursor cursor2 = objSqLiteDatabase.rawQuery("delete from speed",null);
        cursor2.moveToFirst();
        objSqLiteDatabase.close();


    }
    void doStuff() {
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        }
        Toast.makeText(this, "Waiting for gps Connection", Toast.LENGTH_SHORT).show();
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            CLocation mylocation = new CLocation(location, true);
            this.updateSpeed(mylocation);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
