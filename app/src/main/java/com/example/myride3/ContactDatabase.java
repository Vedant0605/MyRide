package com.example.myride3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ContactDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="ContactsInfo.db";
    private static final int DATABASE_VERSION = 1;
    private String createContactTable = "create TABLE contacts ( id integer primary key autoincrement, name varchar (100),number integer)";
    private String createSpeedTable = "create TABLE speed ( id integer primary key autoincrement, ridespeed float)";
    private String createAvgSpeedTable = "create TABLE avgspeed(id integer primary key autoincrement,avgspeed float,dateofride date)";
    Context context;



    public ContactDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(createSpeedTable);
            sqLiteDatabase.execSQL(createAvgSpeedTable);
            sqLiteDatabase.execSQL(createContactTable);

            Toast.makeText(context,"Table Created succesfully ",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(context,"Table Creation unsuccesful ",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
