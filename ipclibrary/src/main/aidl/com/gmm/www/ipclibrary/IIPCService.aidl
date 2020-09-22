// IIPCService.aidl
package com.gmm.www.ipclibrary;

// Declare any non-default types here with import statements
import com.gmm.www.ipclibrary.model.Request;
import com.gmm.www.ipclibrary.model.Response;

interface IIPCService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    Response send(in Request request);
}
