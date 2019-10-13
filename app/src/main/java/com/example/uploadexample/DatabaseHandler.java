package com.example.uploadexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

/*Overview of LOB(Large OBject) Datatypes
The LOB datatypes BLOB(Binary Large OBject), CLOB(Character Large OBject), NCLOB, and BFILE enable you to store and manipulate
 large blocks of unstructured data (such as text, graphic images, video clips, and sound waveforms)
  in binary or character format.
   They provide efficient, random, piece-wise access to the data.
   The BLOB datatype stores unstructured binary data in the database.
    BLOBs can store up to 128 terabytes of binary data.*/
public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, "database.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table files(id integer primary key, file blob not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table  if exists files");
    }

    public Boolean insertFile(String x, Integer i) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            FileInputStream fs = new FileInputStream(x);//Java FileInputStream class, java.io.FileInputStream,
            // makes it possible to read the contents of a file as a stream of bytes.
            byte[] fileByte = new byte[fs.available()];//fs.available=Total file size to read (in bytes)
            fs.read(fileByte);//read(byte[] b)
            //This method reads up to byte.length bytes of data from this input stream into an array of bytes.
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
