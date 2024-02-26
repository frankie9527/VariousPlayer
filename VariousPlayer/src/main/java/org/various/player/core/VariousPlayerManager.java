package org.various.player.core;

import androidx.annotation.Nullable;

import org.various.player.utils.LogUtils;

/**
 * author: Frankie
 * Date: 2024/2/26
 * Description:
 */
public class VariousPlayerManager {
    private static final String TAG = "VariousPlayerManager";
    private static int currentStatus = -1;

    public static float getVolume() {
        return PlayerManager.getInstance().getPlayer().getVolume();
    }

    public static boolean isPlaying() {
        return PlayerManager.getInstance().getPlayer().isPlaying();
    }

    public static void pause() {
        PlayerManager.getInstance().getPlayer().pause();
    }

    public static void release() {
        PlayerManager.getInstance().getPlayer().release();
    }

    public static void resume() {
        PlayerManager.getInstance().getPlayer().resume();
    }

    public static void seekTo(long milliseconds) {
        PlayerManager.getInstance().getPlayer().seekTo(milliseconds);
    }

    public static void setVideoUri(@Nullable String url) {
        PlayerManager.getInstance().getPlayer().setVideoUri(url);
    }

    public static void setVolume(float volume) {
        PlayerManager.getInstance().getPlayer().setVolume(volume);
    }

    public static void setSpeed(float speed) {
        PlayerManager.getInstance().getPlayer().setSpeed(speed);
    }

    public static float getSpeed() {
        return PlayerManager.getInstance().getPlayer().getSpeed();
    }

    public static long getDuration() {
        return PlayerManager.getInstance().getPlayer().getDuration();
    }

    public static long getCurrentPosition() {
        return PlayerManager.getInstance().getPlayer().getCurrentPosition();
    }


    public static int getBufferedPercent() {
        return PlayerManager.getInstance().getPlayer().getBufferedPercent();
    }


    public static String getVideoUrl() {
        return PlayerManager.getInstance().getPlayer().getVideoUrl();
    }


    public static void startSyncPlay() {
        PlayerManager.getInstance().getPlayer().startSyncPlay();
    }


    public static void clearVideoSurface() {
        PlayerManager.getInstance().getPlayer().clearVideoSurface();
    }


    public static void playRetry() {
        PlayerManager.getInstance().getPlayer().playRetry();
    }

    public static void setPlayerStatus(int status) {
        LogUtils.e(TAG, "setPlayerStatus status=" + status);
        currentStatus = status;
    }

    public static int getCurrentStatus() {
        return currentStatus;
    }
}
