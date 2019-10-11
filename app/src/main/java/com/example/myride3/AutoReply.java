package com.example.myride3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myride3.ui.home.HomeFragment;

public class AutoReply extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_reply);
        final CallBroadcastReciever callBroadcastReciever = new CallBroadcastReciever();
        Switch s = (Switch) findViewById(R.id.switch1);
        final Button check = (Button) findViewById(R.id.button);
        final TextView t = (TextView) findViewById(R.id.textView);
        final EditText e = (EditText) findViewById(R.id.textInputEditText);
        final String[] msg = new String[1];
        final Context context = getApplicationContext();

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        check.setVisibility(View.VISIBLE);
        t.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        e.setText(pref.getString("Message",""));
        s.setChecked(pref.getBoolean("MyPref",false));
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("Auto_reply",true);
                    editor.commit();
                }else {
                    editor.putBoolean("Auto_reply",false);
                    editor.commit();
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg[0] = e.getText().toString();
                String message = msg[0];
                Toast.makeText(context,"Message set to "+message,Toast.LENGTH_SHORT).show();
                editor.putString("Message",message);
                editor.commit();
            }
        });
        editor.commit();
        editor.apply();
    }
}
