package org.various.player.utils;

import android.util.Log;

/**
 * Created by 江雨寒 on 2020/9/21
 * Email：847145851@qq.com
 * func:
 */
public class StackTraceUtils {
    public static void logStack(String tag){
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        Log.e(tag, "-------------StackTrace  start---------------------- ");
        for (StackTraceElement stackElement : stackElements) {
            String str = "[" + stackElement.getClassName() + stackElement.getLineNumber() + " " + stackElement.getMethodName() + "]";
            Log.e(tag, "StackTrace stack=" + str);
        }
        Log.d(tag, "-------------StackTrace  end---------------------- " );
    }
}
