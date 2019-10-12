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
    private String createDatabase = "create TABLE contacts ( id integer primary key autoincrement, name varchar (100),number integer)";
    Context context;



    public ContactDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL(createDatabase);
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
