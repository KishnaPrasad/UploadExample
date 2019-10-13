package com.example.uploadexample;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    EditText id;
    TextView tv;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = findViewById(R.id.location);
        tv = findViewById(R.id.locationText);
        db = new DatabaseHandler(this);
    }

    public void onBtnClick(View view) {
        Intent intent = new Intent();
        intent.setType("*/*");//sets the select file to all types of files
        intent.setAction(Intent.ACTION_GET_CONTENT);//starts new activity to select file and return data
        //Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), 7);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 7:

                if (resultCode == RESULT_OK) {

                    try {
                        Uri uri = data.getData();
                        String x = FilePath.getPath(this,uri);
                        Integer tableId = Integer.parseInt(id.getText().toString());
                        tv.setText(x);
                        if (db.insertFile(x, tableId)) {
                            Toast.makeText(getApplicationContext(), "Successfull!!!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "NOT Successfull!!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
                break;
        }
    }


}
