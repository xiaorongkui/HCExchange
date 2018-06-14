package com.example.xiaoentest;

/**
 * Created by lenovo on 2018/1/19.
 */

public class NativeInterfaceTools {
    static {
        System.loadLibrary("xiaoen");
    }

    /**
     * @return
     */
    public static native String getTextFromNative();

}
