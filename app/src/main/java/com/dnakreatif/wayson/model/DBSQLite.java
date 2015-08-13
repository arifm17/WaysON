package com.dnakreatif.wayson.model;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by arifraqilla on 5/16/2015.
 */
public class DBSQLite {
    private SQLiteHandler db;
    public static String USERNAME;
    public static String FULLNAME;
    public static String EMAIL;
    public static String LATITUDE;
    public static String LONGITUDE;
    public static String ID_FOTO;
    public static String CREATED_AT;

    public DBSQLite(Context context) {
        db = new SQLiteHandler(context);
        HashMap<String, String> user = db.getUserDetails();
        USERNAME = user.get("username");
        FULLNAME = user.get("fullname");
        EMAIL = user.get("email");
        LATITUDE = user.get("latitude");
        LONGITUDE = user.get("longitude");
        ID_FOTO = user.get("id_foto");
        CREATED_AT = user.get("created_at");

    }
}
