package com.example.myride3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

public class CallBroadcastReciever extends BroadcastReceiver {
    ITelephony telephonyService;

    public static boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                      String dbfield, String fieldValue, Context context) {
        ContactDatabase contactDatabase = new ContactDatabase(context);
        SQLiteDatabase sqldb = contactDatabase.getReadableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        SmsManager smsManager = SmsManager.getDefault();
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())) {

            SharedPreferences preferences = context.getSharedPreferences("MyPref", 0);
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            String message = preferences.getString("Message", "User is currently Driving.");
            Boolean isOn = preferences.getBoolean("Auto_reply", true);

            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {

                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    Method m = tm.getClass().getDeclaredMethod("getITelephony");
                    m.setAccessible(true);
                    telephonyService = (ITelephony) m.invoke(tm);
                    if ((number != null)) {
                        if (!CheckIsDataAlreadyInDBorNot("contacts", "Number", number, context)) {
                            audioManager.setRingerMode(0);
                            if (isOn) {
                                Toast.makeText(context, "Sending Messsage: " + message + " To " + number, Toast.LENGTH_SHORT).show();
                                smsManager.sendTextMessage(number, null, message, null, null);
                            }
                            Toast.makeText(context, "Ending the call from: " + number, Toast.LENGTH_SHORT).show();
                            telephonyService.endCall();
                        } else {
                            audioManager.setRingerMode(2);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

