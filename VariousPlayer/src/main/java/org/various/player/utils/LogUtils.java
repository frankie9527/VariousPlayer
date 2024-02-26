package org.various.player.utils;

import android.util.Log;

/**
 * author: Frankie
 * Date: 2024/2/26
 * Description:
 */
public class LogUtils {

    public static boolean isDebug = true;


    private static final String TAG = "jyh";


    /**
     * 打印普通日志
     */
    public static void d(String tag, String str) {
        if (isDebug) {
            Log.d(TAG+"_"+tag, str);
        }
    }

    /**
     * 打印错误日志
     */
    public static void e(String tag, String str) {
        if (isDebug) {
            Log.e(TAG+"_"+tag, str);
        }
    }


}