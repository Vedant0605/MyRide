package com.example.myride3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myride3.ui.home.HomeFragment;

public class AddContact extends AppCompatActivity {
    Button ok,showVal;
    EditText nameEt,numberEt;
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


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    contactDatabase.getReadableDatabase();
                    insertValues(view);
                } catch (Exception e) {
                    Toast.makeText(AddContact.this, "Exception in creating database ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        showVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowValues(view);
            }
        });
    }

        public void ShowValues (View view)
        {
            try {
                SQLiteDatabase objSqLiteDatabase = contactDatabase.getReadableDatabase();
                Cursor objCursor = objSqLiteDatabase.rawQuery("select * from  contacts", null);
                StringBuffer objStringBuffer = new StringBuffer();
                if (objCursor.getCount() == 0) {
                    Toast.makeText(AddContact.this, "No Data is returned", Toast.LENGTH_SHORT).show();
                } else {
                    while (objCursor.moveToNext()) {
                        objStringBuffer.append("| Name: " + objCursor.getString(1)+" | ");
                        objStringBuffer.append(" Number: " + objCursor.getString(2));
                        objStringBuffer.append("\n");
                    }
                    retrieveVal.setText(objStringBuffer);
                }
            } catch (Exception e) {
                Toast.makeText(AddContact.this, "Exception in acquiring values", Toast.LENGTH_SHORT).show();
            }
        }

        public void insertValues (View view)
        {
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
                    Intent intent = new Intent(AddContact.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddContact.this, "Null Values not allowed !", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(AddContact.this, "Database is NUll !", Toast.LENGTH_SHORT).show();
            }
        }

    }

