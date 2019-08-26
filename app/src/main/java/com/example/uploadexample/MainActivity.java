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
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        //8Intent intent = new Intent(Intent.ACTION_PICK,Uri.parse("content://media/internal/images/media"));
        startActivityForResult(intent, 7);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 7:

                if (resultCode == RESULT_OK) {

                    try {
                        //String PathHolder = data.getData().getPath();
                        //String x = data.getData().getPath();
                        Uri uri = data.getData();
                        //String x = uri.getPath();
                        String x = FilePath.getPath(this,uri);
                        //String x = getpath(uri);


                        Integer tableId = Integer.parseInt(id.getText().toString());
                        //tv.setText(PathHolder);
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

    public String getpath(Uri uri) {
        if (uri == null) return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            Toast.makeText(getApplicationContext(),"hello2"+cursor.getString(column_index),Toast.LENGTH_LONG).show();
            return cursor.getString(column_index);

        }
        Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG).show();
        return uri.getPath();

    }
}
