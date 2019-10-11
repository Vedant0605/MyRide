package com.example.myride3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myride3.ui.home.HomeFragment;

public class AddContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Button ok = (Button) findViewById(R.id.okcontact);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_main);
                Fragment fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

            }
        });
    }
}
