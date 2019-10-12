package com.example.myride3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //----------------------------------------------------------------------
        Switch sw1 = (Switch)findViewById(R.id.switch3);
        Switch sw2 = (Switch)findViewById(R.id.switch4);
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        sw1.setChecked(pref.getBoolean("MyPref",false));
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("Auto_Speed",true);
                    editor.commit();
                }/*else {
                    editor.putBoolean("Auto_Speed",false);
                    editor.commit();
                }*/
            }
        });
        sw2.setChecked(pref.getBoolean("MyPref",false));
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("Auto_Start",true);
                    editor.commit();
                }/*else {
                    editor.putBoolean("Auto_Start",false);
                    editor.commit();
                }*/
            }
        });
        //----------------------------------------------------------------------
    }
}
