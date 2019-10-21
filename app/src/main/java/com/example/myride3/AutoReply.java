package com.example.myride3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AutoReply extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_reply);

        final SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        final CallBroadcastReciever callBroadcastReciever = new CallBroadcastReciever();
        final Switch s = (Switch) findViewById(R.id.s);
        final Button check = (Button) findViewById(R.id.button);
        final TextView t = (TextView) findViewById(R.id.textView);
        final EditText e = (EditText) findViewById(R.id.textInputEditText);
        final String[] msg = new String[1];
        final Context context = getApplicationContext();
        final Switch sw1 = (Switch) findViewById(R.id.switch3);
        final Switch sw2 = (Switch) findViewById(R.id.switch4);
        e.setText(pref.getString("Auto_Reply", ""));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sw1.setChecked(pref.getBoolean("MyPref", true));
                sw2.setChecked(pref.getBoolean("MyPref", true));
                s.setChecked(pref.getBoolean("MyPref", true));
            }
        });

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("Auto_Speed", true);
                    editor.commit();
                } else {
                    editor.putBoolean("Auto_Speed", false);
                    editor.commit();
                }
            }
        });
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("Auto_Start", true);
                    editor.commit();
                } else {
                    editor.putBoolean("Auto_Start", false);
                    editor.commit();
                }
            }
        });

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("Auto_reply", true);
                    editor.commit();
                } else {
                    editor.putBoolean("Auto_reply", false);
                    editor.commit();
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg[0] = e.getText().toString();
                String message = msg[0];
                Toast.makeText(context, "Message set to " + message, Toast.LENGTH_SHORT).show();
                editor.putString("Message", message);
                editor.commit();
            }
        });
        editor.commit();
        editor.apply();
    }
}
