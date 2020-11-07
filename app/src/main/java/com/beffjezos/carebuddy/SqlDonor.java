package com.beffjezos.carebuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SqlDonor extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Donor_db";
    public static final String CONTACTS_TABLE_NAME = "donor";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_ADDRESS = "address";
    public static final String CONTACTS_COLUMN_NUMBER = "number";
    public static final String CONTACTS_COLUMN_AGE = "age";
    public static final String CONTACTS_COLUMN_GENDER = "gender";

    public SqlDonor(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE donor " + "(id integer PRIMARY KEY,name text, address text,number text,age text,gender text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS donor");
        onCreate(db);
    }

    public void insertContact(String name, String address, String number, String age, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("number", number);
        contentValues.put("age", age);
        contentValues.put("gender", gender);
        db.insert("donor", null, contentValues);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from donor where id=" + id + "", null);
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("donor",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

}