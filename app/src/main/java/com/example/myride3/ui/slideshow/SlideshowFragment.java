package com.example.myride3.ui.slideshow;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myride3.ContactDatabase;
import com.example.myride3.R;

public class SlideshowFragment extends Fragment {

    ContactDatabase contactDatabase;
    TextView putYest, putAvg;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        contactDatabase = new ContactDatabase(getActivity());
        putAvg = root.findViewById(R.id.setAverage);
        setAverage(root);
        //showYesterday(root);
        //showPrevious();
        final SQLiteDatabase objSqLiteDatabase = contactDatabase.getWritableDatabase();
        final Cursor cursor1 = objSqLiteDatabase.rawQuery("delete from avgspeed", null);
        Button button = (Button) root.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor1.moveToFirst();
                putAvg.setText(null);
                Toast.makeText(getActivity(), "Data Cleared Succesfully!", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    public void setAverage(View view) {
        try {
            SQLiteDatabase objSqLiteDatabase = contactDatabase.getReadableDatabase();
            Cursor objCursor = objSqLiteDatabase.rawQuery("select * from  avgspeed", null);
            StringBuffer objStringBuffer = new StringBuffer();
            if (objCursor.getCount() == 0) {
                Toast.makeText(getActivity(), "No Data is returned", Toast.LENGTH_SHORT).show();
            } else {
                while (objCursor.moveToNext()) {
                    objStringBuffer.append("| Date: " + objCursor.getString(2) + " | ");
                    objStringBuffer.append(" Speed: " + objCursor.getString(1));
                    objStringBuffer.append("\n");
                }
                putAvg.setText(objStringBuffer);
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Exception in acquiring values", Toast.LENGTH_SHORT).show();
        }

    }

    public void showYesterday(View view) {
        try {
            SQLiteDatabase objSqLiteDatabase = contactDatabase.getReadableDatabase();
            Cursor objCursor = objSqLiteDatabase.rawQuery("select * from  speed ", null);
            StringBuffer objStringBuffer = new StringBuffer();
            if (objCursor.getCount() == 0) {
                Toast.makeText(getActivity(), "No Data is returned", Toast.LENGTH_SHORT).show();
            } else {
                while (objCursor.moveToNext()) {
                    objStringBuffer.append("| Date: " + objCursor.getString(0) + " | ");
                    objStringBuffer.append(" Speed: " + objCursor.getString(1));
                    objStringBuffer.append("\n");
                }
                putYest.setText(objStringBuffer);
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Exception in acquiring values", Toast.LENGTH_SHORT).show();
        }
    }
}