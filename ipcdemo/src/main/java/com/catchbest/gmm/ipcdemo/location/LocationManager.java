package com.catchbest.gmm.ipcdemo.location;

import com.gmm.www.ipclibrary.annotation.ServiceId;

/**
 * @author:gmm
 * @date:2020/4/23
 * @类说明:
 */
@ServiceId("LocationManager")
public class LocationManager {
    private static final LocationManager ourInstance = new LocationManager();

    public static LocationManager getDefault() {
        return ourInstance;
    }

    private LocationManager() {

    }

    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
