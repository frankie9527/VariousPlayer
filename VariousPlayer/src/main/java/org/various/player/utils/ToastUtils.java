package org.various.player.utils;

import android.widget.Toast;

import org.various.player.PlayerConfig;

public class ToastUtils {
    public static void show(String str) {
        Toast.makeText(PlayerConfig.getContext(), str, Toast.LENGTH_LONG).show();
    }
}
