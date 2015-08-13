package com.dnakreatif.wayson;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by arifraqilla on 3/3/2015.
 */
public class GpsService extends Service implements LocationListener {

    public final Context mContext;
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    boolean canGetLocation = false;
    Location location;
    double latitude;
    double longitude;
    private static final long MIN_JARAK_GPS_UPDATE = 10; //meter
    private static final long MIN_WAKTU_GPS_UPDATE = 1000 * 60; // 1 menit
    protected LocationManager locManager;

    public GpsService(Context context) {
        mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnable = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnable = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnable) {
                // tidak ada koneksi GPS dan Network
            } else {
                canGetLocation = true;

                // cek koneksi internet
                if (isNetworkEnable) {
                    // ambil posisi berdasarkan network
                    locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_WAKTU_GPS_UPDATE, MIN_JARAK_GPS_UPDATE, this);
                    if (locManager != null) {
                        // ambil posisi terakhir menggunakan network
                        location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        // jika lokasi berhasil didapat
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                // jika GPS bisa digunakan
                if (isGPSEnable) {
                    if (location == null) {
                        // ambil posisi berdasarkan GPS
                        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_WAKTU_GPS_UPDATE, MIN_JARAK_GPS_UPDATE, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locManager != null) {
                            // dapatkan possisi terakhir dari GPS
                            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public double getLatitude() {
        if (location != null)
            latitude = location.getLatitude();
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        if (location != null)
            longitude = location.getLongitude();
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        // title alertnya
        alertDialog.setTitle("GPS Setting");
        // pesan alert
        alertDialog.setMessage("GPS Anda tidak aktif. Masuk ke setting menu?");
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void stopUsingGPS() {
        if (locManager != null)
            locManager.removeUpdates(GpsService.this);
    }
}
