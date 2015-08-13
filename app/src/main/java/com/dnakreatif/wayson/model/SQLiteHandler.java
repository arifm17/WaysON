/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.dnakreatif.wayson.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "WaysON";
	// Login table name
	private static final String TABLE_USER = "user";
    private static final String TABLE_LOCATION = "location";

	// User Table Columns names
	private static final String KEY_USERNAME = "username";
	private static final String KEY_FULLNAME = "fullname";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_ID_FOTO = "id_foto";
	private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    // Create table
    public static final String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_LOCATION
            + "(" + KEY_USERNAME + " TEXT PRIMARY KEY," + KEY_LATITUDE + " TEXT,"
            + KEY_LONGITUDE + " TEXT" + ")";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_USERNAME + " TEXT PRIMARY KEY," + KEY_FULLNAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE," + KEY_ID_FOTO + " TEXT UNIQUE,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_USER_TABLE);

		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String username, String fullname, String email,
                        String id_foto, String created_at) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, username); // Username
        values.put(KEY_FULLNAME, fullname); // Fullname
		values.put(KEY_EMAIL, email); // Email
        values.put(KEY_ID_FOTO, id_foto); // ID Foto
        values.put(KEY_CREATED_AT, created_at); // Created At

		// Inserting Row
		long id = db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into SQLite: " + id);
	}

    /**
     * Storing friend location details in database
     * */
    public void storeLocation(String username, String latitude, String longitude) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username); // Username
        values.put(KEY_LATITUDE, latitude); // Latitude
        values.put(KEY_LONGITUDE, longitude); // Longitude

        // Inserting Row
        long id = db.insert(TABLE_LOCATION, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into SQLite: " + id);
    }


    /**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("username", cursor.getString(0));
            user.put("fullname", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("id_foto", cursor.getString(3));
			user.put("created_at", cursor.getString(4));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from SQLite: " + user.toString());

		return user;
	}

    /**
     * Getting location data from database
     * */
    public HashMap<String, String> getFriendLocation() {
        HashMap<String, String> location = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            location.put("username", cursor.getString(0));
            location.put("latitude", cursor.getString(1));
            location.put("longitude", cursor.getString(2));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching location from SQLite: " + location.toString());

        return location;
    }

	/**
	 * Getting user login status return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();

		// return row count
		return rowCount;
	}

	/**
	 * Re create database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from SQLite");
	}

}
