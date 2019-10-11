package com.example.myride3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

public class CallBroadcastReciever extends BroadcastReceiver {
    ITelephony telephonyService;
    @Override
    public void onReceive(Context context, Intent intent) {
        AutoReply ar = new AutoReply();
        SmsManager smsManager = SmsManager.getDefault();
        if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction())){
            SharedPreferences preferences = context.getSharedPreferences("MyPref",0);
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            String message = preferences.getString("Message","");
            Boolean isOn = preferences.getBoolean("Auto_reply",false);
            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){

                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    Method m = tm.getClass().getDeclaredMethod("getITelephony");
                    m.setAccessible(true);
                    telephonyService = (ITelephony) m.invoke(tm);
                    if ((number != null)) {
                        if(isOn){
                            Toast.makeText(context, "Sending Messsage: "+ message +" To " + number, Toast.LENGTH_SHORT).show();
                            smsManager.sendTextMessage(number, null, message, null, null);
                        }
                        Toast.makeText(context, "Ending the call from: " + number, Toast.LENGTH_SHORT).show();
                        telephonyService.endCall();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

