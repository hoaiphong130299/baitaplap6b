package com.example.lab6_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Address_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "address";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_students_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_NAME);
        db.execSQL(create_students_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_students_table);

        onCreate(db);
    }
    public void addAddress(Address address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, address.getName());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Address getAddress(int addressId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[]{String.valueOf(addressId)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Address address = new Address(cursor.getInt(0), cursor.getString(1));
        return address;
    }

    public List<Address> getAllAddress() {
        List<Address> addressList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Address address = new Address(cursor.getInt(0), cursor.getString(1));
            addressList.add(address);
            cursor.moveToNext();
        }
        return addressList;
    }

    public void deleteAddress(int addID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(addID)});
        db.close();
    }
    public void updateAddress(int addID, String txt){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE address SET name=? where id = ?",new String[]{ txt+"",String.valueOf(addID)});
        db.close();
    }

}
