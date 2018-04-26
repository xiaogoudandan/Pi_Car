package com.william_zhang.base.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

/**
 * Created by william_zhang on 2017/12/11.
 */

public class GPSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(LocationManager.KEY_LOCATION_CHANGED)){

        }
    }
}
