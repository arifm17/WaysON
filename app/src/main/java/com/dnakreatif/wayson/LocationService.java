package com.dnakreatif.wayson;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by arifraqilla on 4/23/2015.
 */
public class LocationService extends Service {
    //public final Context mContext;
    Thread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        thread = new PeriodicUpdate(getBaseContext());
        // starting the thread
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread = new PeriodicUpdate(getBaseContext());
        if (!thread.isAlive()) {
            thread.start();
        }
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
