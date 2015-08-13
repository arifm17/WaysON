package com.dnakreatif.wayson;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dnakreatif.wayson.app.AppConfig;
import com.dnakreatif.wayson.app.AppController;
import com.dnakreatif.wayson.model.DBSQLite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arifraqilla on 3/18/2015.
 */
public class PeriodicUpdate extends Thread {
    public final Context mContext;
    GpsService gps;
    DBSQLite db;

    private static final String TAG = PeriodicUpdate.class.getSimpleName();

    public PeriodicUpdate(Context context){
        mContext = context;
        gps = new GpsService(context);
        db = new DBSQLite(context);
    }

    @Override
    public void run() {

        while (true) {
            try {
                // get location from your location provider
                if (gps.canGetLocation()) {
                    double lat = gps.getLatitude();
                    double lng = gps.getLongitude();

                    String latitude  = Double.toString(lat);
                    String longitude = Double.toString(lng);

                    sendLocation(db.USERNAME, latitude, longitude);
                }
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function to store location in MySQL database will post params
     * (tag, username, latitude, longitude) to server
     * */
    public void sendLocation(final String username, final String latitude, final String longitude) {
        // Tag used to cancel the request
        String tag_string_req = "req_friend_location";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error.Response: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "location");
                params.put("username", username);
                params.put("latitude", latitude);
                params.put("longitude", longitude);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
