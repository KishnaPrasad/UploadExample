package com.example.uploadexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

/*Overview of LOB Datatypes
The LOB datatypes BLOB, CLOB, NCLOB, and BFILE enable you to store and manipulate
 large blocks of unstructured data (such as text, graphic images, video clips, and sound waveforms)
  in binary or character format.
   They provide efficient, random, piece-wise access to the data.
   The BLOB datatype stores unstructured binary data in the database.
    BLOBs can store up to 128 terabytes of binary data.*/
public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table files(id integer primary key, file blob not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table  if exists files");
    }

    //insert image
    // x= image location
    public Boolean insertFile(String x, Integer i) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            FileInputStream fs = new FileInputStream(x);
            byte[] fileByte = new byte[fs.available()];
            fs.read(fileByte);
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", i);
            contentValues.put("file", fileByte);
            db.insert("files", null, contentValues);
            fs.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
