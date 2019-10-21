package com.example.myride3.ui.home;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myride3.AddContact;
import com.example.myride3.AutoReply;
import com.example.myride3.R;
import com.example.myride3.RideModeOn;

public class HomeFragment extends Fragment {
    private static final int REQUEST_CALL = 1;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        Button Emergency = (Button) root.findViewById(R.id.eMergency);
        Button addContact = (Button) root.findViewById(R.id.contact);
        Button autoReply = (Button) root.findViewById(R.id.setting);
        final Button Exit = (Button) root.findViewById(R.id.EnD);
        final Button startRide = (Button) root.findViewById(R.id.startRide);
        final Intent intent = new Intent(getActivity(), RideModeOn.class);

        startRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startRide.setBackgroundResource(R.drawable.helmetg);
                /*try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                startActivity(intent);
                Toast.makeText(getContext(), "Call Blocking Active", Toast.LENGTH_SHORT).show();

            }
        });
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addC = new Intent(getActivity(), AddContact.class);
                startActivity(addC);
            }
        });
        autoReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AutoReply.class);
                startActivity(intent);
            }
        });
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Double Tap to close the app", Toast.LENGTH_SHORT).show();
                Exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().moveTaskToBack(true);
                        getActivity().finish();
                    }
                });

            }
        });

        Emergency.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        return root;
    }


    private void makePhoneCall() {
        String number = "911";


        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }
}