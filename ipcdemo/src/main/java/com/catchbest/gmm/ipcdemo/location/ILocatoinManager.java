package com.catchbest.gmm.ipcdemo.location;

import com.gmm.www.ipclibrary.annotation.ServiceId;

/**
 * @author:gmm
 * @date:2020/4/23
 * @类说明:
 */
@ServiceId("LocationManager")
public interface ILocatoinManager {
    Location getLocation();
}
