package com.example.myride3.ui.slideshow;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myride3.ContactDatabase;
import com.example.myride3.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    ContactDatabase contactDatabase;
    TextView putYest,putAvg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        contactDatabase = new ContactDatabase(getActivity());
        putYest = root.findViewById(R.id.setYesterday);
        putAvg = root.findViewById(R.id.setAverage);
        setAverage(root);
        //showYesterday(root);
        //showPrevious();



        return root;
    }
    public void setAverage(View view)
    {
        SQLiteDatabase sqLiteDatabase = contactDatabase.getReadableDatabase();
        SQLiteDatabase writeableDatabase = contactDatabase.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select avg(ridespeed) from speed",null);
        StringBuffer stringBuffer = new StringBuffer();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(getActivity(), "No Data Returned", Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext())
            {
                stringBuffer.append("Speed" + cursor.getString(1) );
            }
            putYest.setText(stringBuffer);
        }

    }
    public void showYesterday(View view)
    {
        SQLiteDatabase sqLiteDatabase = contactDatabase.getReadableDatabase();
    }
}