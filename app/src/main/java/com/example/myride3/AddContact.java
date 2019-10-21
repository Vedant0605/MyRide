package com.example.myride3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddContact extends AppCompatActivity {
    Button ok, showVal, clear;
    EditText nameEt, numberEt;
    ContactDatabase contactDatabase;
    TextView retrieveVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        contactDatabase = new ContactDatabase(this);
        ok = (Button) findViewById(R.id.okcontact);
        showVal = findViewById(R.id.showval);
        nameEt = (EditText) findViewById(R.id.entername);
        numberEt = (EditText) findViewById(R.id.enternumber);
        retrieveVal = findViewById(R.id.showcontacts);
        clear = findViewById(R.id.clearall);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    contactDatabase.getReadableDatabase();
                    insertValues(view);
                } catch (Exception e) {
                    // Toast.makeText(AddContact.this, "Exception in creating database ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        showVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowValues(view);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearDatabase(view);
            }
        });
    }

    public void ShowValues(View view) {
        try {
            SQLiteDatabase objSqLiteDatabase = contactDatabase.getReadableDatabase();
            Cursor objCursor = objSqLiteDatabase.rawQuery("select * from  contacts", null);
            StringBuffer objStringBuffer = new StringBuffer();
            if (objCursor.getCount() == 0) {
                Toast.makeText(AddContact.this, "No Data is returned", Toast.LENGTH_SHORT).show();
            } else {
                while (objCursor.moveToNext()) {
                    objStringBuffer.append("| Name: " + objCursor.getString(1) + " | ");
                    objStringBuffer.append(" Number: " + objCursor.getString(2));
                    objStringBuffer.append("\n");
                }
                retrieveVal.setText(objStringBuffer);
                objSqLiteDatabase.endTransaction();
            }
        } catch (Exception e) {
            //Toast.makeText(AddContact.this, "Exception in acquiring values", Toast.LENGTH_SHORT).show();
        }

    }

    public void ClearDatabase(View view) {
        try {
            SQLiteDatabase objSqLiteDatabase = contactDatabase.getWritableDatabase();
            Cursor cursor = objSqLiteDatabase.rawQuery("delete from contacts", null);

            Toast.makeText(this, "Data Cleared Succesfully", Toast.LENGTH_SHORT).show();
            retrieveVal.setText(null);
            cursor.moveToFirst();
            objSqLiteDatabase.endTransaction();
        } catch (Exception e) {
            Toast.makeText(this, "Error Clearing Database", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertValues(View view) {
        SQLiteDatabase objSqLiteDatabase = contactDatabase.getWritableDatabase();
        if (objSqLiteDatabase != null) {
            if (!nameEt.getText().toString().isEmpty() && !numberEt.getText().toString().isEmpty()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("Name", nameEt.getText().toString());
                contentValues.put("Number", String.valueOf(numberEt.getText()));
                long check = objSqLiteDatabase.insert("contacts", null, contentValues);
                if (check != -1) {
                    Toast.makeText(AddContact.this, "Values inserted in table !", Toast.LENGTH_SHORT).show();
                    nameEt.setText(null);
                    numberEt.setText(null);
                }
            } else {
                Toast.makeText(AddContact.this, "Null Values not allowed !", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(AddContact.this, "Database is NUll !", Toast.LENGTH_SHORT).show();
        }
        objSqLiteDatabase.endTransaction();
    }

    protected void onStop() {
        super.onStop();

    }

}
